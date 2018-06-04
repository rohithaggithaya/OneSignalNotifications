package com.example.raggitha.onesignalnotifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedResult;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(new NotificationReceivedHandler())
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        button = findViewById(R.id.buttonID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*NotificationBank  nb = new NotificationBank();
                String title = "New Title - 1";
                String body = "New Message - 1";
                nb.addNewNotification(getApplicationContext(), title,body,"https://www.google.com");*/
                startActivity(new Intent(MainActivity.this, NotificationBank.class));
                //finish();
            }
        });

    }

    class NotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            NotificationBank nb = new NotificationBank();
            String title = notification.payload.title;
            String body = notification.payload.body;
            String url = notification.payload.launchURL;
            nb.addNewNotification(getApplicationContext(), title, body,url);
        }
    }
}


