package com.summerbrochtrup.time2cook.timers;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.summerbrochtrup.time2cook.Constants;
import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.database.TimerDataSource;
import com.summerbrochtrup.time2cook.models.Timer;
import com.summerbrochtrup.time2cook.timer.TimerActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 8/5/16.
 */
public class TimersPresenterImpl implements TimersPresenter {
    private TimersView mView;
    private Activity mActivity;

    public TimersPresenterImpl(TimersView view, Activity activity) {
        mView = view;
        mActivity = activity;
    }

    @Override
    public void getTimers() {
        TimerDataSource dataSource = new TimerDataSource(mActivity);
        ArrayList<Timer> timers = dataSource.readTimers();
        mView.displayTimers(timers);
    }

    @Override
    public void createDatabase() {
        final int PLACEHOLDER_INDEX = 1;
        TimerDataSource dataSource = new TimerDataSource(mActivity);
        dataSource.create(new Timer(PLACEHOLDER_INDEX, "White Rice", 1080000, "Use 2 cups of water for each cup of white rice. Bring the water to a boil. " + "Add rice and a dash of salt. Cover the rice and reduce the heat to low. " + "When finished, turn off the heat and let stand for a few minutes before serving.", 0));
        dataSource.create(new Timer(PLACEHOLDER_INDEX, "Brown Rice", 2400000, "Rinse and toast the desired amount of rice." + "Let rice stand to cool." + "Use 2.5 of water for each cup of brown rice. Bring water to a boil." + "Reduce heat to a simmer and cook for 40-50 minutes.", 1));
        dataSource.create(new Timer(PLACEHOLDER_INDEX, "Quinoa", 1020000 , "Use 2 cups of water for each cup of quinoa. Bring water to a boil." + "Reduce heat to low and cover." + "Cook for 17 minutes", 2));
        dataSource.create(new Timer(PLACEHOLDER_INDEX, "Cous cous", 600000, "Use 1 cup of water for each cup of cous cous." + "Bring water and 1-2 tablespoons of butter to a boil." + "Remove the pan from the heat and add cous cous." + "Cover and cook for 10 minutes.", 3));
        dataSource.create(new Timer(PLACEHOLDER_INDEX, "Corn on the cob", 210000, "Husk the corn." + "Bring large pot of water to a boil." + "Add corn ears and cover." + "Cook 3-4 minutes.", 0));
        dataSource.create(new Timer(PLACEHOLDER_INDEX, "Hard Boiled Egg", 540000, "Add eggs to saucepan in single layer, add water, and then bring to a boil." + "Remove from the burner and cover." + "Let eggs stand for 9 minutes." + "Drain and serve.", 1));
    }

    @Override
    public void navigateToTimerActivity(Timer timer) {
        ImageView timerImage = (ImageView) mActivity.findViewById(R.id.timerImage);
        Intent intent = new Intent(mActivity, TimerActivity.class);
        intent.putExtra(Constants.EXTRA_KEY_TIMER, Parcels.wrap(timer));
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, timerImage, "image");
        mActivity.startActivity(intent, optionsCompat.toBundle());
    }
}
