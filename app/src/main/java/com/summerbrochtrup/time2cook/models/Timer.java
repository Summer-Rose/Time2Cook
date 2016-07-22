package com.summerbrochtrup.time2cook.models;

import org.parceler.Parcel;

/**
 * Created by summerbrochtrup on 7/21/16.
 */
@Parcel
public class Timer {
    private String mTimerName;
    private String mImageBackgroundColor;
    private String mTextBackgroundColor;
    private int mImage;
    private int mTime;

    public Timer() {
    }

    public Timer(int image, String timerName, String imageBackgroundColor, String textBackgroundColor) {
        mImage = image;
        mTimerName = timerName;
        mImageBackgroundColor = imageBackgroundColor;
        mTextBackgroundColor = textBackgroundColor;
        mTime = 11000;
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
}
