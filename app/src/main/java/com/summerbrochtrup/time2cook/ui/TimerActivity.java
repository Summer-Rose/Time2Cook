package com.summerbrochtrup.time2cook.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.models.Timer;

import org.parceler.Parcels;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {
    private  final String TAG = getClass().getSimpleName();
    private Timer mTimer;
    private CountDownTimer mCountDownTimer;
    private Button mStartPauseButton;
    private Button mStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTimer = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_TIMER));
        Log.d(TAG, "Timer name: " + mTimer.getTimerName());
        mStartPauseButton = (Button) findViewById(R.id.startPauseButton);
        mStopButton = (Button) findViewById(R.id.stopButton);
        mStartPauseButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

        mCountDownTimer = new CountDownTimer(mTimer.getTime(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "tick " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "timer complete");
            }
        };


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startPauseButton:
                mCountDownTimer.start();
                break;
            case R.id.stopButton:
                mCountDownTimer.cancel();
                break;
        }
    }
}
