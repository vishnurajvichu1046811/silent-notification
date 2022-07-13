package com.example.silentnotification.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.silentnotification.MainActivity;
import com.example.silentnotification.R;

public class ForegroundService extends Service {

    private static final String CHANNEL_ID = "channel_0";
        @Override public void onCreate() {
        super.onCreate();
        }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "MyChannel", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(serviceChannel);
        }
        Intent notificationIntent = new Intent(this, ForegroundService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myServiceChannel")
        .setContentTitle("foregroundServiceNotificationTitle")
        .setContentText("input")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentIntent(pendingIntent);
        // todo don't wait for 10 seconds
        //.setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
        Notification notification = builder.build();
        startForeground(1, notification);
        return START_NOT_STICKY;
        }

        @Override public void onDestroy() {
        super.onDestroy();
        }

    @Override public IBinder onBind(Intent intent) {
        return null;
        }
        }
