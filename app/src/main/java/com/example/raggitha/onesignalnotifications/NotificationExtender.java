package com.example.raggitha.onesignalnotifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.raggitha.onesignalnotifications.Data.DatabaseHelper;
import com.example.raggitha.onesignalnotifications.Data.NotificationsContract;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

public class NotificationExtender extends NotificationExtenderService {

    public static final String TAG = "NotificationExtender";
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {

        String title = receivedResult.payload.title;
        String body = receivedResult.payload.body;
        String url = receivedResult.payload.launchURL;
        Log.e(TAG,title +", "+body);
        addNewNotification(getApplicationContext(),title,body, url);
        // Return true to stop the notification from displaying.
        return false;
    }

    public long addNewNotification(Context context, String title, String body, String url) {
        Log.e(TAG,"Adding new notification: " +
                "title: " + title +
                "body: " + body +
                "Url: " + url);
        DatabaseHelper dbHelp = new DatabaseHelper(context);
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NotificationsContract.NotificationsEntry.COLUMN_TITLE, title);
        cv.put(NotificationsContract.NotificationsEntry.COLUMN_BODY, body);
        cv.put(NotificationsContract.NotificationsEntry.COLUMN_URL,url);
        long result = database.insert(NotificationsContract.NotificationsEntry.TABLE_NAME, null, cv);
        //mAdapter.swapCursor(getAllNotifications());
        return result;
    }
}
