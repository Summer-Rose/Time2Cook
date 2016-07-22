package com.summerbrochtrup.time2cook.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.adapters.TimerGridAdapter;
import com.summerbrochtrup.time2cook.models.Timer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Timer> mTimers = new ArrayList<>();
    private GridView mGridView;
    private TimerGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGridView = (GridView) findViewById(R.id.timersGrid);

        mTimers.add(new Timer(R.drawable.image, "Brown Rice", "#99D1B7", "#F79273"));
        mTimers.add(new Timer(R.drawable.image_two, "White Rice", "#B1654B", "#FDC08E"));
        mTimers.add(new Timer(R.drawable.image, "Hard Boiled Egg", "#FDC08E", "#B1654B"));
        mTimers.add(new Timer(R.drawable.image_two, "Quinoa", "#F79273", "#99D1B7"));
        mTimers.add(new Timer(R.drawable.image, "Brown Rice", "#99D1B7", "#F79273"));
        mTimers.add(new Timer(R.drawable.image_two, "White Rice", "#B1654B", "#FDC08E"));
        mTimers.add(new Timer(R.drawable.image, "Hard Boiled Egg", "#FDC08E", "#B1654B"));
        mTimers.add(new Timer(R.drawable.image_two, "Quinoa", "#F79273", "#99D1B7"));

        mAdapter = new TimerGridAdapter(this, mTimers);
        mGridView.setAdapter(mAdapter);
    }
}
