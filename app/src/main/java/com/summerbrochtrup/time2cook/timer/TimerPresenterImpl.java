package com.summerbrochtrup.time2cook.timer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.models.Timer;
import com.summerbrochtrup.time2cook.service.TimerService;
import com.summerbrochtrup.time2cook.ui.EditTimerActivity;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public class TimerPresenterImpl implements TimerPresenter {
    private TimerView mView;
    private Activity mActivity;
    private CountDownTimer mCountDownTimer;
    private TimerService mTimerService;
    private boolean mBound;
    private boolean mTimerCounting = false;
    private boolean mTimerStarted = false;
    private Uri mUri = Uri.parse(Constants.URI_SYSTEM_RINGTONE);
    private ServiceConnection mServiceConnection;
    Timer mTimer;
    private long mMillisUntilFinished;

    public TimerPresenterImpl(TimerView view, Activity activity) {
        mView = view;
        mActivity = activity;
    }

    @Override
    public void getTimer() {
        mTimer = Parcels.unwrap(mActivity.getIntent().getParcelableExtra(Constants.EXTRA_KEY_TIMER));
        mMillisUntilFinished = mTimer.getTime();
        mView.displayTimer(mTimer, formatTime(mTimer.getTime()));
    }

    @Override
    public void navigateToEditTimerActivity() {
        Intent intent = new Intent(mActivity, EditTimerActivity.class);
        intent.putExtra(Constants.EXTRA_KEY_TIMER,  Parcels.wrap(mTimer));
        mActivity.startActivity(intent);
    }

    @Override
    public void getDirections() {
        ArrayList<String> directionsList = new ArrayList<>();
        if (mTimer.getDirections().equals("")) {
            directionsList.add(mActivity.getResources().getString(R.string.no_directions_default));
        } else {
            for (String direction : mTimer.getDirections().split("\\.")) {
                directionsList.add(direction);
            }
        }
        mView.displayDirections(directionsList, mTimer.getTimerName());
    }

    @Override
    public void startPauseTimer() {
        if (!mTimerCounting) { //timer has not started counting
            mTimerCounting = true;
            mTimerStarted = true;
            if (!mBound) {
                bindService();
            }
            createCountDowntimer(mMillisUntilFinished);
            mView.displayPause();
        } else {
            mTimerCounting = false;
            mCountDownTimer.cancel();
            mView.displayStart();
        }
    }

    @Override
    public void stopTimer() {
        if (mBound) {
            if (mTimerService.isPlaying()) {
                mTimerService.stop();
            }
            mTimerService.cancelNotification();
            unbindService();
            mBound = false;
        }
        mCountDownTimer.cancel();
        mMillisUntilFinished = mTimer.getTime();
        mTimerCounting = false;
        mView.updateTimerDisplay(mMillisUntilFinished, formatTime(mMillisUntilFinished));
        mView.displayStart();
    }

    @Override
    public void getService(IBinder binder) {
        TimerService.LocalBinder localBinder = (TimerService.LocalBinder) binder;
        mTimerService = localBinder.getService();
        mBound = true;
    }

    @Override
    public void disconnectService() {
        mBound = false;
    }

    @Override
    public void setCustomTone(Uri uri) {
        mUri = uri;
        try {
            mTimerService.setCustomTone(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindService() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                getService(binder);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                disconnectService();
            }
        };
        Intent intent = new Intent(mActivity, TimerService.class);
        intent.putExtra(Constants.EXTRA_KEY_TIMER, Parcels.wrap(mTimer));
        intent.putExtra(Constants.EXTRA_KEY_URI, mUri.toString());
        mActivity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void unbindService() {
        if (mBound && !mTimerStarted) {
            mActivity.unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private void createCountDowntimer(long millis) {
        mCountDownTimer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mMillisUntilFinished = millisUntilFinished;
                String formattedTime = formatTime(millisUntilFinished);
                if (mBound) {
                    mTimerService.createNotification("Time remaining : " + formattedTime);
                }
                mView.updateTimerDisplay(millisUntilFinished / 1000, formattedTime);
            }

            @Override
            public void onFinish() {
                mMillisUntilFinished = 0;
                mView.onFinish();
                mTimerService.play();
            }
        }.start();
    }

    private String formatTime(long millis) {
        int secondsLeft = (int) millis / 1000;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        return Integer.toString(minutes) + ":" + secondString;
    }
}
