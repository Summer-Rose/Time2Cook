package com.summerbrochtrup.time2cook.timers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.adapters.TimerGridAdapter;
import com.summerbrochtrup.time2cook.models.Timer;
import com.summerbrochtrup.time2cook.timer.TimerActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

public class TimersFragment extends Fragment implements TimersView, AdapterView.OnItemClickListener {
    private TimersPresenter mPresenter;
    private GridView mGridView;
    private TimerGridAdapter mAdapter;
    private ArrayList<Timer> mTimers = new ArrayList<>();

    public TimersFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TimersPresenterImpl(this, getActivity());
        boolean isFirstUse = checkIfFirstUse();
        if (isFirstUse) { mPresenter.createDatabase(); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timers, container, false);
        mGridView = (GridView) view.findViewById(R.id.timersGrid);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getTimers();
    }

    @Override
    public void displayTimers(ArrayList<Timer> timers) {
        mTimers = timers;
        mAdapter = new TimerGridAdapter(getActivity(), timers);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    private boolean checkIfFirstUse() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();
        boolean isFirstUse = sp.getBoolean(Constants.PREFERENCES_KEY_FIRST_USE, true);
        if (isFirstUse) {
            editor.putBoolean(Constants.PREFERENCES_KEY_FIRST_USE, false).apply();
        }
        return isFirstUse;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPresenter.navigateToTimerActivity(mTimers.get(position));
    }
}
