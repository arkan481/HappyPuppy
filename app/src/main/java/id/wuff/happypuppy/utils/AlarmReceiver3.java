package id.wuff.happypuppy.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import id.wuff.happypuppy.R;
import id.wuff.happypuppy.ui.activities.MainActivity;

public class AlarmReceiver3 extends BroadcastReceiver {

    /**
     * Receiver for night notification
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent morningIntent = PendingIntent.getActivity(context, 120,
                notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context, "notif_feed_3").setSmallIcon(R.drawable.icon_profile)
                .setContentTitle("Wuffy!")
                .setContentText("It's already late! your pets are probably tired, show em some love by giving them some treats!")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.header_img))
                        .bigLargeIcon(null))
                .setContentIntent(morningIntent);
        notificationManager.notify(300, mNotifyBuilder.build());

    }

}