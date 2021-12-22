package com.example.silentnotification.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.silentnotification.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;


public class MyNotificationManager {
    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;

    final Context mCtx;
    final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void showBigNotification(String title, String message, String url, Intent intent) {
        alarm();
        PendingIntent resultPendingIntent = PendingIntent.getActivity(mCtx, ID_BIG_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(Html.fromHtml(title).toString());
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, "notification");
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title)
                .setShowWhen(true)
                .setWhen(Calendar.getInstance().getTimeInMillis())
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(Html.fromHtml(title).toString())
                .setContentText(Html.fromHtml(message).toString())
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSound(defaultSoundUri)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notificationManager);
        }
        notificationManager.notify(ID_BIG_NOTIFICATION, notification);
    }

    public void showSmallNotification(String title, String message, Intent intent) {


            alarm();

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mCtx, ID_SMALL_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            //NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, "notification");
            Notification notification;

            if (title.startsWith("New order placed")){

                notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title)
                        .setShowWhen(true)
                        .setWhen(Calendar.getInstance().getTimeInMillis())
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent)
                        .setContentTitle(Html.fromHtml(title).toString())
                        .setContentText(Html.fromHtml(message).toString())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(message).toString()))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setSound(defaultSoundUri)
                        .setFullScreenIntent(resultPendingIntent, true)
                        .build();


            }
            else{
                notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title)
                        .setShowWhen(true)
                        .setWhen(Calendar.getInstance().getTimeInMillis())
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent)
                        .setContentTitle(Html.fromHtml(title).toString())
                        .setContentText(Html.fromHtml(message).toString())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(message).toString()))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setSound(defaultSoundUri)
                        .build();
            }


            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannel(notificationManager);
            }
            notificationManager.notify((int) System.currentTimeMillis(), notification);


    }

    private void alarm() {
        Uri alert = Uri.parse("android.resource://"
                + mCtx.getPackageName() + "/" + R.raw.notification1);
        Ringtone r = RingtoneManager.getRingtone(mCtx.getApplicationContext(), alert);
        r.play();
    }


    //The method will return Bitmap from an image URL
    Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {

            return null;
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    void createChannel(NotificationManager notificationManager) {
        String name = "notification";
        String description = "Notifications for download status";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel("notification", name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        mChannel.setShowBadge(true);
        mChannel.setSound(defaultSoundUri,new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build());
        notificationManager.createNotificationChannel(mChannel);
    }
}
