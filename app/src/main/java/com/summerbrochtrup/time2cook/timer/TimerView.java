package com.summerbrochtrup.time2cook.timer;

import com.summerbrochtrup.time2cook.models.Timer;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public interface TimerView {
    void displayTimer(Timer timer, String formattedTime);
    void displayDirections(ArrayList<String> directions, String timerName);
    void updateTimerDisplay(long millis, String formattedTime);
    void displayStart();
    void displayPause();
    void onFinish();
}
