package com.example.raggitha.onesignalnotifications.Data;

import android.provider.BaseColumns;

public class NotificationsContract {

    public static final class NotificationsEntry implements BaseColumns {
        public static final String TABLE_NAME = "notifications";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_URL = "launchurl";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
