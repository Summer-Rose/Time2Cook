package com.summerbrochtrup.time2cook.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.summerbrochtrup.time2cook.ui.AddTimerStepOneFragment;
import com.summerbrochtrup.time2cook.ui.AddTimerStepTwoFragment;

/**
 * Created by summerbrochtrup on 7/28/16.
 */
public class AddTimerPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 2;

    public AddTimerPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AddTimerStepOneFragment.newInstance();
            case 1:
                return AddTimerStepTwoFragment.newInstance();
            default:
                return null;
        }
    }
}
