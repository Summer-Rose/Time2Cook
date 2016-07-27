package com.summerbrochtrup.time2cook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.util.TimerNumberPicker;


public class AddTimerActivity extends AppCompatActivity implements View.OnClickListener {
    private TimerNumberPicker mHourNumberPicker, mMinNumberPicker, mSecNumberPicker;
    private ImageView mNextStep, mBackStep;
    private LinearLayout mStepOne, mStepTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupNumberPickers();
        mStepOne = (LinearLayout) findViewById(R.id.formOneLayout);
        mStepTwo = (LinearLayout) findViewById(R.id.formTwoLayout);
        mNextStep = (ImageView) findViewById(R.id.nextStepButton);
        mBackStep = (ImageView) findViewById(R.id.backStepButton);
        mNextStep.setOnClickListener(this);
        mBackStep.setOnClickListener(this);
    }

    private void setupNumberPickers() {
        String[] minSecNums = getResources().getStringArray(R.array.spinner_minutes_seconds);
        mHourNumberPicker = (TimerNumberPicker)findViewById(R.id.hourNumberPicker);
        mHourNumberPicker.setMinValue(0);
        mHourNumberPicker.setMaxValue(12);

        mMinNumberPicker = (TimerNumberPicker) findViewById(R.id.minNumberPicker);
        mMinNumberPicker.setMinValue(0);
        mMinNumberPicker.setMaxValue(59);
        mMinNumberPicker.setDisplayedValues(minSecNums);

        mSecNumberPicker = (TimerNumberPicker) findViewById(R.id.secNumberPicker);
        mSecNumberPicker.setMinValue(0);
        mSecNumberPicker.setMaxValue(59);
        mSecNumberPicker.setDisplayedValues(minSecNums);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextStepButton:
                mStepOne.setVisibility(View.GONE);
                mStepTwo.setVisibility(View.VISIBLE);
                break;
            case R.id.backStepButton:
                mStepTwo.setVisibility(View.GONE);
                mStepOne.setVisibility(View.VISIBLE);
                break;
        }

    }
}
