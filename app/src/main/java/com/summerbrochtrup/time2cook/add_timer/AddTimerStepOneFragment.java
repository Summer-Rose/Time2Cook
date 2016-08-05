package com.summerbrochtrup.time2cook.add_timer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.util.TimerNumberPicker;

/**
 * Created by summerbrochtrup on 7/28/16.
 */
public class AddTimerStepOneFragment extends Fragment implements View.OnClickListener {
    private ImageView mNextStep;
    private EditText mTimerNameEditText;
    private TimerNumberPicker mHourNumberPicker, mMinNumberPicker, mSecNumberPicker;

    public static AddTimerStepOneFragment newInstance() {
        AddTimerStepOneFragment fragmentFirst = new AddTimerStepOneFragment();
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_timer_step_one, container, false);
        mNextStep = (ImageView) view.findViewById(R.id.nextStepButton);
        mTimerNameEditText = (EditText) view.findViewById(R.id.timeNameEditText);
        mHourNumberPicker = (TimerNumberPicker) view.findViewById(R.id.hourNumberPicker);
        mMinNumberPicker = (TimerNumberPicker) view.findViewById(R.id.minNumberPicker);
        mSecNumberPicker = (TimerNumberPicker) view.findViewById(R.id.secNumberPicker);
        mNextStep.setOnClickListener(this);
        setupNumberPickers();
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mNextStep) {
            ((AddTimerActivity) getActivity()).setCurrentItem (1, true);
        }
    }

    private void setupNumberPickers() {
        String[] minSecNums = getResources().getStringArray(R.array.spinner_minutes_seconds);
        mHourNumberPicker.setMinValue(0);
        mHourNumberPicker.setMaxValue(12);

        mMinNumberPicker.setMinValue(0);
        mMinNumberPicker.setMaxValue(59);
        mMinNumberPicker.setDisplayedValues(minSecNums);

        mSecNumberPicker.setMinValue(0);
        mSecNumberPicker.setMaxValue(59);
        mSecNumberPicker.setDisplayedValues(minSecNums);
    }

    public String getTimerName() {
        return mTimerNameEditText.getText().toString().trim();
    }

    public int getHour(){
        return mHourNumberPicker.getValue();
    }

    public int getMin() {
        return mMinNumberPicker.getValue();
    }

    public int getSec() {
        return mSecNumberPicker.getValue();
    }
}
