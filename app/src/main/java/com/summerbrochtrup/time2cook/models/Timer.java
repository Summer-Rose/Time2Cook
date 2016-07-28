package com.summerbrochtrup.time2cook.models;

import org.parceler.Parcel;

/**
 * Created by summerbrochtrup on 7/21/16.
 */
@Parcel
public class Timer {
    private int mId;
    private String mTimerName;
    private int mTime;
    private String mDirections;
    private int mStyleIndex;

    public Timer() {}

    public Timer(int id, String timerName, int time, String directions, int styleIndex) {
        mId = id;
        mTimerName = timerName;
        mTime = time;
        mDirections = directions;
        mStyleIndex = styleIndex;
    }


    public int getId() {
        return mId;
    }

    public String getTimerName() {
        return mTimerName;
    }

    public void setTimerName(String name) {
        mTimerName = name;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public String getDirections() {
        return mDirections;
    }

    public void setDirections(String directions) {
        mDirections = directions;
    }

    public int getStyleIndex() {
        return mStyleIndex;
    }
}