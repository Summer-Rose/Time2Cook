package com.summerbrochtrup.time2cook.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.summerbrochtrup.time2cook.R;
import com.summerbrochtrup.time2cook.models.Timer;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 7/21/16.
 */
public class TimerGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Timer> mTimers;

    public TimerGridAdapter(Context context, ArrayList<Timer> timers) {
        mContext = context;
        mTimers = timers;
    }

    @Override
    public int getCount() {
        return mTimers.size();
    }

    @Override
    public Timer getItem(int position) {
        return mTimers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimerGridViewHolder holder;
        Timer timer = mTimers.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_timer, null);
            holder = new TimerGridViewHolder();
            holder.timerNameTextView = (TextView) convertView.findViewById(R.id.timerNameTv);
            holder.timerImage = (ImageView) convertView.findViewById(R.id.timerImage);
            convertView.setTag(holder);
        } else {
            holder = (TimerGridViewHolder) convertView.getTag();
        }
        holder.timerNameTextView.setText(timer.getTimerName());
        holder.timerNameTextView.setBackgroundColor(Color.parseColor(timer.getTextBackgroundColor()));
        holder.timerImage.setImageResource(timer.getImage());
        holder.timerImage.setBackgroundColor(Color.parseColor(timer.getImageBackgroundColor()));
        return convertView;
    }

    public static class TimerGridViewHolder {
        TextView timerNameTextView;
        ImageView timerImage;
    }
}
