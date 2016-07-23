package com.summerbrochtrup.time2cook.models;

import org.parceler.Parcel;

/**
 * Created by summerbrochtrup on 7/21/16.
 */
@Parcel
public class Timer {
    private int mId;
    private String mTimerName;
    private String mImageBackgroundColor;
    private String mTextBackgroundColor;
    private int mImage;
    private int mTime;

    public Timer() {
    }

    public Timer(int id, String timerName, int image, int time, String imageBackgroundColor, String textBackgroundColor) {
        mId = id;
        mTimerName = timerName;
        mImage = image;
        mTime = time;
        mImageBackgroundColor = imageBackgroundColor;
        mTextBackgroundColor = textBackgroundColor;
    }

    public String getTimerName() {
        return mTimerName;
    }

    public String getImageBackgroundColor() {
        return mImageBackgroundColor;
    }

    public String getTextBackgroundColor() {
        return mTextBackgroundColor;
    }

    public int getImage() {
        return mImage;
    }

    public int getTime() {
        return mTime;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
