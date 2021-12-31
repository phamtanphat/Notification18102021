package com.example.notification18102021;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity2 extends AppCompatActivity {

    Button mBtnOpen, mBtnClose;
    String CHANNEL_ID = "mychannel";
    MediaSessionCompat mediaSessionCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mBtnClose = findViewById(R.id.buttonCloseNotification);
        mBtnOpen = findViewById(R.id.buttonOpenNotification);

        setupMediaSession();
        mBtnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                PendingIntent notifyPendingIntent = PendingIntent.getActivity(
//                        MainActivity2.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
//                );

                NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(MainActivity2.this, CHANNEL_ID);
                notificationCompat.setSmallIcon(R.mipmap.ic_launcher);
                notificationCompat.setContentTitle("Thông báo app có phiên bản mới");
                notificationCompat.setContentText("Phiên bản 2.5 vừa được cập nhật");
                notificationCompat.setShowWhen(true);
//                notificationCompat.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.musicdata));
//                notificationCompat.setContentIntent(notifyPendingIntent);

                notificationCompat.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession());
//                notificationCompat.addAction(R.drawable.ic_launcher_background,"Open App" ,pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "New_Version", NotificationManager.IMPORTANCE_DEFAULT);
//                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                            .build();
//                    notificationChannel.setSound(soundUri,audioAttributes);
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                notificationManager.notify(1, notificationCompat.build());
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

    private void setupMediaSession() {
        ComponentName receiver = new ComponentName(getPackageName(), MainActivity2.class.getName());
        mediaSessionCompat = new MediaSessionCompat(this, "StreamService", receiver, null);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setPlaybackState(new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PAUSED, 0, 0)
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE)
                .build());
        mediaSessionCompat.setMetadata(new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "Test Artist")
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, "Test Album")
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "Test Track Name")
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 10000)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, "Test Artist")
                .build());

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                // Ignore
            }
        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        mediaSessionCompat.setActive(true);
    }

}