package com.summerbrochtrup.time2cook.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        boolean isFirstUse = checkIfFirstUse();
        if (isFirstUse) { createDatabase(); }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGridView = (GridView) findViewById(R.id.timersGrid);}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_timer:
                Intent intent = new Intent(this, AddTimerActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private boolean checkIfFirstUse() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        boolean isFirstUse = sp.getBoolean(Constants.PREFERENCES_KEY_FIRST_USE, true);
        if (isFirstUse) {
            editor.putBoolean(Constants.PREFERENCES_KEY_FIRST_USE, false).apply();
        }
        return isFirstUse;
    }

    private void createDatabase() {
        TimerDataSource dataSource = new TimerDataSource(this);
        dataSource.create(new Timer(1, "White Rice", 1080000, "Use 2 cups of water for each cup of white rice. Bring the water to a boil. " + "Add rice and a dash of salt. Cover the rice and reduce the heat to low. " + "When finished, turn off the heat and let stand for a few minutes before serving.", 0));
        dataSource.create(new Timer(1, "Hard Boiled Egg", 540000, "Add eggs to saucepan in single layer, add water, and then bring to a boil." + "Remove from the burner and cover." + "Let eggs stand for 9 minutes." + "Drain and serve.", 1));
        dataSource.create(new Timer(1, "Quinoa", 1020000 , "Use 2 cups of water for each cup of quinoa. Bring water to a boil." + "Reduce heat to low and cover." + "Cook for 17 minutes", 2));
        dataSource.create(new Timer(1, "Brown Rice", 2400000, "Rinse and toast the desired amount of rice." + "Let rice stand to cool." + "Use 2.5 of water for each cup of brown rice. Bring water to a boil." + "Reduce heat to a simmer and cook for 40-50 minutes.", 3));
    }
}
