package com.summerbrochtrup.time2cook.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.adapters.TimerInputPagerAdapter;
import com.summerbrochtrup.time2cook.database.TimerDataSource;
import com.summerbrochtrup.time2cook.models.Timer;
import org.parceler.Parcels;
import java.util.List;

public class EditTimerActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private Dialog mDialog;
    private boolean newTimerComplete = false;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timer);
        mTimer = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_TIMER));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button submitTimerButton = (Button) findViewById(R.id.submitTimerButton);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);
        toolbar.setTitle(mTimer.getTimerName());
        TimerInputPagerAdapter adapterViewPager = new TimerInputPagerAdapter(getSupportFragmentManager(), this, mTimer);
        mViewPager.setAdapter(adapterViewPager);
        submitTimerButton.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitTimerButton:
                List<Fragment> frags = getSupportFragmentManager().getFragments();
                EditTimerStepOneFragment stepOneFrag = (EditTimerStepOneFragment) frags.get(1);
                EditTimerStepTwoFragment stepTwoFragment = (EditTimerStepTwoFragment) frags.get(0);
                String name = stepOneFrag.getTimerName();
                int milliseconds = (int) stepOneFrag.getMilliseconds();
                String directions = stepTwoFragment.getDirections();
                boolean areGoodInputs = verifyInputs(name, milliseconds);
                if (!areGoodInputs) break;
                updateTimerInDatabase(name, milliseconds, directions);
                newTimerComplete = true;
                makeDialog("Success!", "Your timer, " + name + ", was updated!");
                mDialog.setCancelable(false);
                break;
            case R.id.dismissDialogIcon:
                mDialog.dismiss();
                if (newTimerComplete) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;
            case R.id.fab:
                makeConfirmDialog();
                break;
            case R.id.confirmDeleteButton:
                TimerDataSource dataSource = new TimerDataSource(this);
                dataSource.delete(mTimer.getId());
                mDialog.dismiss();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.dismissDialogButton:
                mDialog.dismiss();
                break;
        }
    }

    private boolean verifyInputs(String name, int milliseconds) {
        if (name.equals("")) {
            makeDialog("Uh oh!", "Please give your timer a name");
            return false;
        } else if (milliseconds == 0) {
            makeDialog("Uh oh!", "Please give your timer a duration");
            return false;
        } else {
            return true;
        }
    }

    private void updateTimerInDatabase(String name, int time, String directions) {
        TimerDataSource dataSource = new TimerDataSource(this);
        mTimer.setTimerName(name);
        mTimer.setTime(time);
        mTimer.setDirections(directions);
        dataSource.update(mTimer);
    }

    private void makeDialog(String title, String message) {
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_add_timer);
        TextView titleTextView = (TextView) mDialog.findViewById(R.id.dialogTitle);
        TextView messageTextView = (TextView) mDialog.findViewById(R.id.messageTextView);
        ImageView closeDialogButton = (ImageView) mDialog.findViewById(R.id.dismissDialogIcon);
        titleTextView.setText(title);
        messageTextView.setText(message);
        closeDialogButton.setOnClickListener(this);
        mDialog.show();
    }

    private void makeConfirmDialog() {
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_delete_timer);
        mDialog.setCancelable(false);
        Button confirmDelete = (Button) mDialog.findViewById(R.id.confirmDeleteButton);
        Button dismissDialog = (Button) mDialog.findViewById(R.id.dismissDialogButton);
        confirmDelete.setOnClickListener(this);
        dismissDialog.setOnClickListener(this);
        mDialog.show();
    }

    public void setCurrentItem (int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(item, smoothScroll);
    }
}
