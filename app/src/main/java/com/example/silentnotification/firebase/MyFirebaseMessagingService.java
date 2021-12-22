package com.example.silentnotification.firebase;


import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.silentnotification.AppController;
import com.example.silentnotification.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                Log.e(TAG, String.valueOf(json));
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(JSONObject data) {
        try {

          //  JSONObject data = json.getJSONObject(Constant.DATA);

            String type = data.getString("type");
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = "null"; //data.getString("image");
            String id = data.getString("id");

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            boolean isSilentNotif = false;
            if(data.has("is_silent_notification") && data.getBoolean("is_silent_notification")){
                isSilentNotif = true;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){

                    Intent intent3 = new Intent(AppController.FETCH_USER_LOCATION);
                    sendBroadcast(intent3);
                }else{

                    Intent intent3 = new Intent(AppController.FETCH_USER_LOCATION);
                    sendBroadcast(intent3);
                }
            }

            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            if (imageUrl.equals("null") || imageUrl.equals("")) {

                if(!isSilentNotif)
                    mNotificationManager.showSmallNotification(title, message, intent);

            } else {
                if(!isSilentNotif)
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        AppController.getInstance().setDeviceToken(s);
    }

}
