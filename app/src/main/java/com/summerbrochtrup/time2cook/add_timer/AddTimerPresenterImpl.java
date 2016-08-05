package com.summerbrochtrup.time2cook.add_timer;

import android.app.Activity;

import com.summerbrochtrup.time2cook.database.TimerDataSource;
import com.summerbrochtrup.time2cook.models.Timer;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public class AddTimerPresenterImpl implements AddTimerPresenter {
    private AddTimerView mView;
    private Activity mActivity;
    private boolean newTimerComplete = false;

    public AddTimerPresenterImpl(AddTimerView view, Activity activity) {
        mView = view;
        mActivity = activity;
    }

    @Override
    public boolean areGoodInputs(String name, long millis) {
        if (name.equals("")) {
            mView.displayEmptyNameError();
            return false;
        } else if (millis == 0) {
            mView.displayNoTimeError();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void saveTimerToDatabase(String name, int millis, String directions) {
        final int PLACEHOLDER_INDEX = 0;
        final int STYLE_INDEX_ONE = 0;
        final int STYLE_INDEX_FOUR = 3;
        TimerDataSource dataSource = new TimerDataSource(mActivity);
        int lastStyleIndex = dataSource.getLastStyleIndex();
        Timer newTimer;
        if (lastStyleIndex == STYLE_INDEX_FOUR) {
            newTimer = new Timer(PLACEHOLDER_INDEX, name, millis, directions, STYLE_INDEX_ONE);
        } else {
            newTimer = new Timer(PLACEHOLDER_INDEX, name, millis, directions, lastStyleIndex + 1);
        }
        dataSource.create(newTimer);
        newTimerComplete = true;
        mView.displaySuccess(name);
    }

    @Override
    public boolean checkIfCreationComplete() {
        return newTimerComplete;
    }

    @Override
    public int getMilliseconds(int hourInput, int minInput, int secInput) {
        final int MILLIS_IN_HOUR = 3600000;
        final int MILLIS_IN_MIN = 60000;
        final int MILLIS_IN_SEC = 1000;
        long hour = hourInput * MILLIS_IN_HOUR;
        long mins = minInput * MILLIS_IN_MIN;
        long secs = secInput * MILLIS_IN_SEC;
        return (int) (hour + mins + secs);
    }
}
