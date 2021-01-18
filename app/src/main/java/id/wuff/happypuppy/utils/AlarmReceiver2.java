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

public class AlarmReceiver2 extends BroadcastReceiver {

    /**
     * Receiver for afternoon notification
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent morningIntent = PendingIntent.getActivity(context, 110,
                notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context, "notif_feed_2").setSmallIcon(R.drawable.icon_profile)
                .setContentTitle("Wuffy!")
                .setContentText("It's already afternoon your pets are hungry, but also very energetic let's play with them and don't forget to give them some treats!")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.header_img))
                        .bigLargeIcon(null))
                .setContentIntent(morningIntent);
        notificationManager.notify(200, mNotifyBuilder.build());
    }

}