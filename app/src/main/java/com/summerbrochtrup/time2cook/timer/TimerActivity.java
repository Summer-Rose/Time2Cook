package com.summerbrochtrup.time2cook.timer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.adapters.DirectionsListAdapter;
import com.summerbrochtrup.time2cook.models.Timer;
import com.summerbrochtrup.time2cook.util.StyleIndexHelper;

import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener, TimerView {
    private final int REQUEST_CODE = 5;
    private Button mStartPauseButton, mStopButton;
    private ImageView mTimerImage;
    private TextView mTimeTextView;
    private CircleProgressView mCircleView;
    private TimerPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mPresenter = new TimerPresenterImpl(this, this);
        initializeViews();
        mPresenter.getTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_directions:
                mPresenter.getDirections();
                return true;
            case R.id.action_select_tone:
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getResources().getString(R.string.ringtone_chooser_menu_title));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                TimerActivity.this.startActivityForResult(intent, REQUEST_CODE);
                return true;
            case R.id.action_edit_timer:
                mPresenter.navigateToEditTimerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeViews() {
        mStartPauseButton = (Button) findViewById(R.id.startPauseButton);
        mStopButton = (Button) findViewById(R.id.stopButton);
        mTimerImage = (ImageView) findViewById(R.id.timerImage);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStartPauseButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                mPresenter.setCustomTone(uri);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startPauseButton:
                mPresenter.startPauseTimer();
                break;
            case R.id.stopButton:
                mPresenter.stopTimer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.bindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unbindService();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public void displayTimer(Timer timer, String formattedTime) {
        StyleIndexHelper styleHelper = new StyleIndexHelper(this);
        styleHelper.styleImageView(mTimerImage, timer.getStyleIndex());
        getSupportActionBar().setTitle(timer.getTimerName());
        mTimeTextView.setText(formattedTime);
        mCircleView.setMaxValue(timer.getTime() / 1000);
        mCircleView.setValue(timer.getTime() / 1000);
    }

    @Override
    public void displayStart() {
        mStartPauseButton.setText(getResources().getString(R.string.button_start));
    }

    @Override
    public void displayPause() {
        mStartPauseButton.setText(getResources().getString(R.string.button_pause));
    }

    @Override
    public void updateTimerDisplay(long time, String formattedTime) {
        mCircleView.setValue(time);
        mTimeTextView.setText(formattedTime);
    }

    @Override
    public void onFinish() {
        mCircleView.setValue(0);
        mTimeTextView.setText(getResources().getString(R.string.time_completed_text));
        mStartPauseButton.setText(getResources().getString(R.string.button_start));
    }

    @Override
    public void displayDirections(ArrayList<String> directions, String timerName) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cooking_directions);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText(String.format(getResources().getString(R.string.cooking_directions_title), timerName));
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);

        DirectionsListAdapter adapter = new DirectionsListAdapter(directions, false);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        ImageView closeDialogButton = (ImageView) dialog.findViewById(R.id.dismissDialogIcon);
        closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
