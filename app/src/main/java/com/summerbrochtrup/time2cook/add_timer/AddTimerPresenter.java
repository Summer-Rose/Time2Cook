package com.summerbrochtrup.time2cook.add_timer;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public interface AddTimerPresenter  {
    boolean areGoodInputs(String name, long millis);
    void saveTimerToDatabase(String name, int millis, String directions);
    boolean checkIfCreationComplete();
    int getMilliseconds(int hour, int min, int sec);
}
