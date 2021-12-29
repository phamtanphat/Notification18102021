package com.example.notification18102021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mBtnOpen, mBtnClose;
    String CHANNEL_ID = "mychannel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnClose = findViewById(R.id.buttonCloseNotification);
        mBtnOpen = findViewById(R.id.buttonOpenNotification);

        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID);
                notificationCompat.setSmallIcon(R.mipmap.ic_launcher);
                notificationCompat.setContentTitle("Thông báo app có phiên bản mới");
                notificationCompat.setContentText("Phiên bản 2.5 vừa được cập nhật");
                notificationCompat.setShowWhen(true);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(1 , notificationCompat.build());
            }
        });
    }
}