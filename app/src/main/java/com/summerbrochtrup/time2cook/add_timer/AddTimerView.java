package com.summerbrochtrup.time2cook.add_timer;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public interface AddTimerView {
    void displayEmptyNameError();
    void displayNoTimeError();
    void displaySuccess(String name);
}
