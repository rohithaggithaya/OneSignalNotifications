package com.example.raggitha.onesignalnotifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.raggitha.onesignalnotifications.Data.DatabaseHelper;
import com.example.raggitha.onesignalnotifications.Data.NotificationsContract;

public class NotificationBank extends AppCompatActivity {
    private RecyclerViewAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_bank);

        RecyclerView notificationsRecyclerView = findViewById(R.id.recyclerViewID);

        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllNotifications();
        mAdapter = new RecyclerViewAdapter(this, cursor);
        notificationsRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // COMPLETED (4) Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // COMPLETED (5) Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                // COMPLETED (9) call removeGuest and pass through that id
                //remove from DB
                removeNotification(id);
                // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list
                mAdapter.swapCursor(getAllNotifications());
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(notificationsRecyclerView);
    }


    public long addNewNotification(Context context, String title, String body) {
        DatabaseHelper dbHelp = new DatabaseHelper(context);
        SQLiteDatabase database = dbHelp.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NotificationsContract.NotificationsEntry.COLUMN_TITLE, title);
        cv.put(NotificationsContract.NotificationsEntry.COLUMN_BODY, body);
        long result = database.insert(NotificationsContract.NotificationsEntry.TABLE_NAME, null, cv);
        //mAdapter.swapCursor(getAllNotifications());
        return result;
    }

    private boolean removeNotification(long id) {
        return mDb.delete(NotificationsContract.NotificationsEntry.TABLE_NAME, NotificationsContract.NotificationsEntry._ID + "=" + id, null) > 0;
    }

    private Cursor getAllNotifications() {
        return mDb.query(
                NotificationsContract.NotificationsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NotificationsContract.NotificationsEntry.COLUMN_TIMESTAMP
        );
    }
}
