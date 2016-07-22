package com.summerbrochtrup.time2cook.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.models.Timer;

import org.parceler.Parcels;

import at.grabner.circleprogress.CircleProgressView;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {
    private  final String TAG = getClass().getSimpleName();
    private final int REQUEST_CODE = 5;
    private Timer mTimer;
    private CountDownTimer mCountDownTimer;
    private Button mStartPauseButton, mStopButton;
    private ImageView mTimerImage;
    private TextView mTimeTextView;
    private boolean timerStarted = false;
    private long mMillisUntilFinished;
    private CircleProgressView mCircleView;
    private Uri mUri = Uri.parse(Constants.URI_SYSTEM_RINGTONE);
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        initializeView();
    }

    private void initializeView() {
        mTimer = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_TIMER));
        mMillisUntilFinished = mTimer.getTime();
        /* Bind views */
        mStartPauseButton = (Button) findViewById(R.id.startPauseButton);
        mStopButton = (Button) findViewById(R.id.stopButton);
        mTimerImage = (ImageView) findViewById(R.id.timerActivityImage);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* Customize views */
        getSupportActionBar().setTitle(mTimer.getTimerName());
        mTimerImage.setImageResource(mTimer.getImage());
        mTimerImage.setBackgroundColor(Color.parseColor(mTimer.getImageBackgroundColor()));
        /* Set up CircleView and Timer */
        mCircleView.setMaxValue(mTimer.getTime() / 1000);
        updateTimer(mTimer.getTime());
        /* Set click listeners */
        mStartPauseButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                mUri = uri;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startPauseButton:
                if (!timerStarted) { //timer has not been started
                    timerStarted = true;
                    createCountDownTimer();
                    mStartPauseButton.setText(getResources().getString(R.string.button_pause));
                } else { //timer has been started. this click will pause
                    timerStarted = false;
                    mCountDownTimer.cancel();
                    mStartPauseButton.setText(getResources().getString(R.string.button_start));
                }
                break;
            case R.id.stopButton:
                mCountDownTimer.cancel();
                timerStarted = false;
                mPlayer.stop();
                break;
            case R.id.fab:
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getResources().getString(R.string.ringtone_chooser_menu_title));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                TimerActivity.this.startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private void createCountDownTimer() {
        mPlayer = MediaPlayer.create(this, mUri);
        mCountDownTimer = new CountDownTimer(mMillisUntilFinished, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mMillisUntilFinished = millisUntilFinished;
                updateTimer(millisUntilFinished);
                mCircleView.setValue(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mMillisUntilFinished = mTimer.getTime();
                mCircleView.setValue(0);
                mTimeTextView.setText(getResources().getString(R.string.time_completed_text));
                mStartPauseButton.setText(getResources().getString(R.string.button_start));
                mPlayer.start();
            }
        }.start();
    }

    private void updateTimer(long millisLeft) {
        int secondsLeft = (int) millisLeft / 1000;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        mTimeTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }
}
