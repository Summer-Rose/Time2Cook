package com.summerbrochtrup.time2cook.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.models.Timer;
import com.summerbrochtrup.time2cook.timer.TimerActivity;

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

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 22;

    @Override
    public void onCreate() {
        mPlayer = MediaPlayer.create(this, mUri);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimer = Parcels.unwrap(intent.getParcelableExtra(Constants.EXTRA_KEY_TIMER));
        mUri = Uri.parse(intent.getStringExtra(Constants.EXTRA_KEY_URI));
        createNotification("started");
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mTimer = Parcels.unwrap(intent.getParcelableExtra(Constants.EXTRA_KEY_TIMER));
        mUri = Uri.parse(intent.getStringExtra(Constants.EXTRA_KEY_URI));
        mPlayer = MediaPlayer.create(this, mUri);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mPlayer.release();
    }

    public class LocalBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    //client methods
    public void play() {
        mPlayer = MediaPlayer.create(this, mUri);
        mPlayer.start();
        createNotification("Your " + mTimer.getTimerName() + " is finished!");
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void stop() {
        mPlayer.stop();
        mPlayer.release();
    }

    public void setCustomTone(Uri uri) throws IOException {
        mUri = uri;
        mPlayer = MediaPlayer.create(this, uri);
    }

    public void createNotification(String message) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.btn_star)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message);

        Intent doneIntent = new Intent(this, TimerActivity.class);
        doneIntent.putExtra(Constants.EXTRA_KEY_TIMER, Parcels.wrap(mTimer));
        doneIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(TimerActivity.class);
        stackBuilder.addNextIntent(doneIntent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,PendingIntent.FLAG_CANCEL_CURRENT, doneIntent, 0);
        mBuilder.setContentIntent(pendingIntent);

        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public void cancelNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
