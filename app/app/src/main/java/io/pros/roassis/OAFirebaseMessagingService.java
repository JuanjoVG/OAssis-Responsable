package io.pros.roassis;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by JuanjoVG on 21/07/2016.
 */
public class OAFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "OAFBMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

        //Calling method to generate notification
        sendNotification(remoteMessage.getData().get("message"),
                remoteMessage.getData().get("lat"),
                remoteMessage.getData().get("lon"));
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String message, String lat, String lon) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "659984265"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(this)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle("OAssis Alert")
                .setContentText("Hans Moleman "  + message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_call, "Call Now", contentIntent)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }
}
