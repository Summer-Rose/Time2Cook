package com.summerbrochtrup.time2cook.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.models.Timer;

import org.parceler.Parcels;

import java.io.IOException;

/**
 * Created by summerbrochtrup on 7/23/16.
 */
public class TimerService extends Service {
    private static final String TAG = TimerService.class.getSimpleName();
    private MediaPlayer mPlayer;
    private Uri mUri = Uri.parse(Constants.URI_SYSTEM_RINGTONE);
    private Timer mTimer;
    private IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        mPlayer = MediaPlayer.create(this, mUri);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        mTimer = Parcels.unwrap(intent.getParcelableExtra(Constants.EXTRA_KEY_TIMER));
        mUri = Uri.parse(intent.getStringExtra(Constants.EXTRA_KEY_URI));
        mPlayer = MediaPlayer.create(this, mUri);
        Log.d(TAG, "timer: " + mTimer.getTimerName() + " uri: " + mUri.toString());
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mPlayer.release();
    }

    public class LocalBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    //client methods
    public void play() {
        Log.d(TAG, "play");
        mPlayer.start();
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void stop() {
        Log.d(TAG, "stop");
        mPlayer.stop();
    }

    public void setCustomTone(Uri uri) throws IOException {
        mUri = uri;
        Log.d(TAG, uri.toString());
        mPlayer = MediaPlayer.create(this, uri);
    }
}
