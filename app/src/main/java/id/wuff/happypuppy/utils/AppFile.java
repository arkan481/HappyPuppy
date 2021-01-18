package id.wuff.happypuppy.utils;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import id.wuff.happypuppy.ui.activities.MainActivity;

/**
 * Registered AppFile in the manifest.xml to be called when the app is opened for the first time.
 */

public class AppFile extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        registerNotificationTime1();

        registerNotificationTime2();

        registerNotificationTime3();
    }

    /**
     * Method to create notification channel
     * There are 3 notification channel, they are feednotif, feednotif2, and feednotif3
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "feednotif";
            String description = "feed your pet reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notif_feed", name, importance);
            channel.setDescription(description);

            CharSequence name2 = "feednotif2";
            String description2 = "feed your pet reminder2";
            NotificationChannel channel2 = new NotificationChannel("notif_feed_2", name2, importance);
            channel.setDescription(description2);

            CharSequence name3 = "feednotif3";
            String description3 = "feed your pet reminder3";
            NotificationChannel channel3 = new NotificationChannel("notif_feed_3", name3, importance);
            channel.setDescription(description3);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channel2);
            notificationManager.createNotificationChannel(channel3);

        }
    }

    /**
     * Method to register the alarm receiver so the notification will trigger depending on its registered time
     */
    private void registerNotificationTime1() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 6);
        Intent intent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 100, intent1, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
    }

    private void registerNotificationTime2() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 12);
        Intent intent1 = new Intent(getApplicationContext(), AlarmReceiver2.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 110, intent1, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
    }

    private void registerNotificationTime3() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 20);
        Intent intent1 = new Intent(getApplicationContext(), AlarmReceiver2.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 120, intent1, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
    }
}
