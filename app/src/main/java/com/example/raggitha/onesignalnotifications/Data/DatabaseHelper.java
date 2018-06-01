package com.example.raggitha.onesignalnotifications.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notifications.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + NotificationsContract.NotificationsEntry.TABLE_NAME + " (" +
                NotificationsContract.NotificationsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotificationsContract.NotificationsEntry.COLUMN_TITLE + " TEXT, " +
                NotificationsContract.NotificationsEntry.COLUMN_BODY + " TEXT, " +
                NotificationsContract.NotificationsEntry.COLUMN_URL + "TEXT," +
                NotificationsContract.NotificationsEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";
        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NotificationsContract.NotificationsEntry.TABLE_NAME);
        onCreate(db);
    }
}

