package example.sm.euzee.github.com;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

@SuppressWarnings("deprecation")
class NotificationHelper {

    private static final String FRIDAY_CHANNEL_ID = "friday_channel_id";


    private static Notification.Builder getDefault(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(FRIDAY_CHANNEL_ID, context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 100, 50, 100});
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            builder = new Notification.Builder(context, FRIDAY_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context).setVibrate(new long[]{0, 100, 50, 100});
        }

        return builder;
    }

    static Notification getNotification(Context context, String text) {
        return getDefault(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap())
                .setAutoCancel(true)
                .build();
    }
}
