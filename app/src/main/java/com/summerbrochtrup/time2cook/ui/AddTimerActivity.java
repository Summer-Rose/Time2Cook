package com.summerbrochtrup.time2cook.ui;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.adapters.TimerInputPagerAdapter;
import com.summerbrochtrup.time2cook.database.TimerDataSource;
import com.summerbrochtrup.time2cook.models.Timer;

import java.util.List;


public class AddTimerActivity extends AppCompatActivity implements View.OnClickListener {
    private TimerInputPagerAdapter mAdapterViewPager;
    private Button mSubmitTimerButton;
    private ViewPager mViewPager;
    private Dialog mDialog;
    private boolean newTimerComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSubmitTimerButton = (Button) findViewById(R.id.submitTimerButton);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapterViewPager = new TimerInputPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapterViewPager);
        mSubmitTimerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitTimerButton:
                List<Fragment> frags = getSupportFragmentManager().getFragments();
                AddTimerStepOneFragment stepOneFrag = (AddTimerStepOneFragment) frags.get(1);
                AddTimerStepTwoFragment stepTwoFragment = (AddTimerStepTwoFragment) frags.get(0);
                String name = stepOneFrag.getTimerName();
                int milliseconds = (int) stepOneFrag.getMilliseconds();
                String directions = stepTwoFragment.getDirections();
                boolean areGoodInputs = verifyInputs(name, milliseconds);
                if (!areGoodInputs) break;
                saveTimerToDatabase(name, milliseconds, directions);
                newTimerComplete = true;
                makeDialog("Success!", "Your timer, " + name + ", was saved!");
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

    private void saveTimerToDatabase(String name, int time, String directions) {
        final int PLACEHOLDER_INDEX = 0;
        final int STYLE_INDEX_ONE = 0;
        final int STYLE_INDEX_FOUR = 3;
        TimerDataSource dataSource = new TimerDataSource(this);
        int lastStyleIndex = dataSource.getLastStyleIndex();
        Timer newTimer;
        if (lastStyleIndex == STYLE_INDEX_FOUR) {
            newTimer = new Timer(PLACEHOLDER_INDEX, name, time, directions, STYLE_INDEX_ONE);
        } else {
            newTimer = new Timer(PLACEHOLDER_INDEX, name, time, directions, lastStyleIndex + 1);
        }
        dataSource.create(newTimer);
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

    public void setCurrentItem (int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(item, smoothScroll);
    }
}
