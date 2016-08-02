package com.summerbrochtrup.time2cook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.summerbrochtrup.time2cook.models.Timer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by summerbrochtrup on 7/22/16.
 */
public class TimerSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "timers.db";
    private static final int DB_VERSION = 1;
    public static final String TIMERS_TABLE = "TIMERS";
    public static final String COLUMN_TIMER_NAME = "NAME";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_DIRECTIONS = "DIRECTIONS";
    public static final String COLUMN_STYLE_INDEX = "STYLE_INDEX";
    private static final String TAG = TimerSQLiteHelper.class.getSimpleName();
    private static String CREATE_TIMERS = "CREATE TABLE " + TIMERS_TABLE
            + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TIMER_NAME + " TEXT, "
            + COLUMN_TIME + " INTEGER, "
            + COLUMN_DIRECTIONS + " TEXT, "
            + COLUMN_STYLE_INDEX + " INTEGER)"
            ;


    public TimerSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

