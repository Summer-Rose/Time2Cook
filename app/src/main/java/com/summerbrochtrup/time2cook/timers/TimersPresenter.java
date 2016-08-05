package com.summerbrochtrup.time2cook.timers;

import com.summerbrochtrup.time2cook.models.Timer;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public interface TimersPresenter {
    void getTimers();
    void createDatabase();
    void navigateToTimerActivity(Timer timer);
}
