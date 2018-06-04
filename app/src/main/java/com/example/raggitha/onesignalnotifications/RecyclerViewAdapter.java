package com.example.raggitha.onesignalnotifications;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raggitha.onesignalnotifications.Data.NotificationsContract;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.mViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    @NonNull
    @Override
    public RecyclerViewAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notification_view_content, parent, false);
        return (new mViewHolder(v));
    }

    public RecyclerViewAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.mViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;// bail if returned null

        String title = mCursor.getString(mCursor.getColumnIndex(NotificationsContract.NotificationsEntry.COLUMN_TITLE));
        String body = mCursor.getString(mCursor.getColumnIndex(NotificationsContract.NotificationsEntry.COLUMN_BODY));
        final String url = mCursor.getString(mCursor.getColumnIndex(NotificationsContract.NotificationsEntry.COLUMN_URL));
        long id = mCursor.getLong(mCursor.getColumnIndex(NotificationsContract.NotificationsEntry._ID));
        holder.titleText.setText(title);
        holder.bodyText.setText(String.valueOf(body));
        holder.itemView.setTag(id);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url!=null && !url.isEmpty())
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(holder.itemView.getContext(), "No application can handle this request."
                                + " Please install a webbrowser", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                // Add else condition to run IWT or any other function
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    class mViewHolder extends RecyclerView.ViewHolder {
        View layout;
        TextView titleText, bodyText;

        mViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            titleText = layout.findViewById(R.id.titleTextID);
            bodyText = layout.findViewById(R.id.bodyTextID);
        }
    }
}
