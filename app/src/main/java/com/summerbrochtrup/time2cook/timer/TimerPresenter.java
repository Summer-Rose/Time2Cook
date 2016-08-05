package com.summerbrochtrup.time2cook.timer;

import android.net.Uri;
import android.os.IBinder;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public interface TimerPresenter {
    void getTimer();
    void navigateToEditTimerActivity();
    void getDirections();
    void getService(IBinder binder);
    void bindService();
    void unbindService();
    void setCustomTone(Uri uri);
    void disconnectService();
    void startPauseTimer();
    void stopTimer();
}
