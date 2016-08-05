package com.summerbrochtrup.time2cook.add_timer;

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
import com.summerbrochtrup.time2cook.timers.MainActivity;

import java.util.List;


public class AddTimerActivity extends AppCompatActivity implements View.OnClickListener, AddTimerView {
    private TimerInputPagerAdapter mAdapterViewPager;
    private Button mSubmitTimerButton;
    private ViewPager mViewPager;
    private Dialog mDialog;
    private AddTimerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);
        mPresenter = new AddTimerPresenterImpl(this, this);
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
                int milliseconds = mPresenter.getMilliseconds(stepOneFrag.getHour(),
                        stepOneFrag.getMin(), stepOneFrag.getSec());
                String directions = stepTwoFragment.getDirections();
                if (directions.equals("")) {
                    directions = "none";
                }
                boolean areGoodInputs = mPresenter.areGoodInputs(name, milliseconds);
                if (!areGoodInputs) break;
                mPresenter.saveTimerToDatabase(name, milliseconds, directions);
                mDialog.setCancelable(false);
                break;
            case R.id.dismissDialogIcon:
                mDialog.dismiss();
                if (mPresenter.checkIfCreationComplete()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;
        }
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

    @Override
    public void displayEmptyNameError() {
        makeDialog(getResources().getString(R.string.add_timer_error_title),
                getResources().getString(R.string.error_message_no_name));
    }

    @Override
    public void displayNoTimeError() {
        makeDialog(getResources().getString(R.string.add_timer_error_title),
                getResources().getString(R.string.error_message_no_time));
    }

    @Override
    public void displaySuccess(String name) {
        makeDialog(getResources().getString(R.string.success_title),
                String.format(getResources().getString(R.string.save_success_message), name));
    }
}
