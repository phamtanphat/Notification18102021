package com.example.notification18102021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    Button mBtnOpen, mBtnClose;
    String CHANNEL_ID = "mychannel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mBtnClose = findViewById(R.id.buttonCloseNotification);
        mBtnOpen = findViewById(R.id.buttonOpenNotification);

        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,MainActivity2.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity2.this);
                stackBuilder.addNextIntentWithParentStack(intent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(MainActivity2.this,CHANNEL_ID);
                notificationCompat.setSmallIcon(R.mipmap.ic_launcher);
                notificationCompat.setContentTitle("Thông báo app có phiên bản mới");
                notificationCompat.setContentText("Phiên bản 2.5 vừa được cập nhật");
                notificationCompat.setShowWhen(true);
                notificationCompat.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" + getPackageName()+"/"+R.raw.musicdata));
                notificationCompat.setContentIntent(pendingIntent);
//                notificationCompat.addAction(R.drawable.ic_launcher_background,"Open App" ,pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"New_Version",NotificationManager.IMPORTANCE_DEFAULT);
//                    Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getPackageName() + "/" + R.raw.musicdata);
//                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                            .build();
//                    notificationChannel.setSound(soundUri,audioAttributes);
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                notificationManager.notify(1 , notificationCompat.build());
            }
        });

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(1);
            }
        });
    }
}