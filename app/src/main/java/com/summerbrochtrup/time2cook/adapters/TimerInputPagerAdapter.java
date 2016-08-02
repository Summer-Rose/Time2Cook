package com.summerbrochtrup.time2cook.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.summerbrochtrup.time2cook.models.Timer;
import com.summerbrochtrup.time2cook.ui.AddTimerActivity;
import com.summerbrochtrup.time2cook.ui.AddTimerStepOneFragment;
import com.summerbrochtrup.time2cook.ui.AddTimerStepTwoFragment;
import com.summerbrochtrup.time2cook.ui.EditTimerStepOneFragment;
import com.summerbrochtrup.time2cook.ui.EditTimerStepTwoFragment;

/**
 * Created by summerbrochtrup on 7/28/16.
 */
public class TimerInputPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 2;
    private Context mContext;
    private Timer mTimer;

    public TimerInputPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
    }

    public TimerInputPagerAdapter(FragmentManager fragmentManager, Context context, Timer timer) {
        super(fragmentManager);
        mContext = context;
        mTimer = timer;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mContext.getClass().getSimpleName().equals(AddTimerActivity.class.getSimpleName())) {
                    return AddTimerStepOneFragment.newInstance();
                } else {
                    return EditTimerStepOneFragment.newInstance(mTimer);
                }
            case 1:
                if (mContext.getClass().getSimpleName().equals(AddTimerActivity.class.getSimpleName())) {
                    return AddTimerStepTwoFragment.newInstance();
                } else {
                    return EditTimerStepTwoFragment.newInstance(mTimer);
                }
            default:
                return null;
        }
    }
}
