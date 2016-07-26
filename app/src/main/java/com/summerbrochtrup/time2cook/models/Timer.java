package com.summerbrochtrup.time2cook.models;

import org.parceler.Parcel;

/**
 * Created by summerbrochtrup on 7/21/16.
 */
@Parcel
public class Timer {
    private int mId;
    private String mTimerName;
    private int mImage;
    private int mTime;
    private String mDirections;
    private String mImageBackgroundColor;
    private String mTextBackgroundColor;

    public Timer() {}

    public Timer(int id, String timerName, int image, int time, String directions, String imageBackgroundColor, String textBackgroundColor) {
        mId = id;
        mTimerName = timerName;
        mImage = image;
        mTime = time;
        mDirections = directions;
        mImageBackgroundColor = imageBackgroundColor;
        mTextBackgroundColor = textBackgroundColor;
    }


    public int getId() {
        return mId;
    }

    public String getTimerName() {
        return mTimerName;
    }

    public int getImage() {
        return mImage;
    }

    public int getTime() {
        return mTime;
    }

    public String getDirections() {
        return mDirections;
    }

    public String getImageBackgroundColor() {
        return mImageBackgroundColor;
    }

    public String getTextBackgroundColor() {
        return mTextBackgroundColor;
    }
}

