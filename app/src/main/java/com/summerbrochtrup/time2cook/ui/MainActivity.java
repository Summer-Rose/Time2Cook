package com.summerbrochtrup.time2cook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.adapters.TimerGridAdapter;
import com.summerbrochtrup.time2cook.database.TimerDataSource;
import com.summerbrochtrup.time2cook.models.Timer;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
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

        TimerDataSource dataSource = new TimerDataSource(this);
//        dataSource.create(new Timer(1, "White Rice", R.drawable.image_two, 1100, "#B1654B", "#FDC08E"));
//        dataSource.create(new Timer(1, "Hard Boiled Egg", R.drawable.image, 1100, "#FDC08E", "#B1654B"));
//        dataSource.create(new Timer(1, "Quinoa", R.drawable.image_two, 1100, "#F79273", "#99D1B7"));
//        dataSource.create(new Timer(1, "Brown Rice", R.drawable.image, 1100, "#99D1B7", "#F79273"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra(Constants.EXTRA_KEY_TIMER, Parcels.wrap(mTimers.get(position)));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TimerDataSource dataSource = new TimerDataSource(this);
        mTimers = dataSource.readTimers();
        mAdapter = new TimerGridAdapter(this, mTimers);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }
}
