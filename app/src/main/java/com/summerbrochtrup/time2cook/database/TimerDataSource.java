package com.summerbrochtrup.time2cook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.summerbrochtrup.time2cook.models.Timer;

import java.util.ArrayList;

/**
 * Created by summerbrochtrup on 7/22/16.
 */
public class TimerDataSource {
    private Context mContext;
    private TimerSQLiteHelper mTimerSQLiteHelper;

    public TimerDataSource(Context context) {
        mContext = context;
        mTimerSQLiteHelper = new TimerSQLiteHelper(context);
        SQLiteDatabase database = mTimerSQLiteHelper.getReadableDatabase();
        database.close();
    }

    private SQLiteDatabase open() {
        return mTimerSQLiteHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database) {
        database.close();
    }

    public void create(Timer timer) {
        SQLiteDatabase database = open();
        database.beginTransaction();
        ContentValues timerValues = new ContentValues();
        timerValues.put(TimerSQLiteHelper.COLUMN_TIMER_NAME, timer.getTimerName());
        timerValues.put(TimerSQLiteHelper.COLUMN_TIME, timer.getTime());
        timerValues.put(TimerSQLiteHelper.COLUMN_DIRECTIONS, timer.getDirections());
        timerValues.put(TimerSQLiteHelper.COLUMN_STYLE_INDEX, timer.getStyleIndex());
        long timerID = database.insert(TimerSQLiteHelper.TIMERS_TABLE, null, timerValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Timer> readTimers() {
        SQLiteDatabase database = open();
        Cursor cursor = database.query(
                TimerSQLiteHelper.TIMERS_TABLE,
                new String [] {
                        BaseColumns._ID,
                        TimerSQLiteHelper.COLUMN_TIMER_NAME,
                        TimerSQLiteHelper.COLUMN_TIME,
                        TimerSQLiteHelper.COLUMN_DIRECTIONS,
                        TimerSQLiteHelper.COLUMN_STYLE_INDEX
                },
                null, //Selection
                null, //selection args
                null, //group by
                null, //having
                null); //order
        ArrayList<Timer> timers = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Timer timer = new Timer(getIntFromColumnName(cursor, BaseColumns._ID),
                        getStringFromColumnName(cursor, TimerSQLiteHelper.COLUMN_TIMER_NAME),
                        getIntFromColumnName(cursor, TimerSQLiteHelper.COLUMN_TIME),
                        getStringFromColumnName(cursor, TimerSQLiteHelper.COLUMN_DIRECTIONS),
                        getIntFromColumnName(cursor, TimerSQLiteHelper.COLUMN_STYLE_INDEX)
                );
                timers.add(timer);
            } while(cursor.moveToNext());
        }
        cursor.close();
        close(database);
        return timers;
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public int getLastStyleIndex() {
        int styleIndex = 0;
        SQLiteDatabase db = open();
        Cursor cursor = db.query(TimerSQLiteHelper.TIMERS_TABLE, new String[] {TimerSQLiteHelper.COLUMN_STYLE_INDEX}, null, null, null, null, null);

        if (cursor.moveToLast()) {
            styleIndex = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return styleIndex;
    }
}
