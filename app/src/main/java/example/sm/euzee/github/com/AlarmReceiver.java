package example.sm.euzee.github.com;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;

import sm.euzee.github.com.servicemanager.ServiceCallback;
import sm.euzee.github.com.servicemanager.ServiceManager;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final long START_DELAY = 1000 * 5; //5 sec
    private static final long RESTART_DELAY = 1000 * 25; //25 sec
    private static final long REPEAT_INTERVAL = 60 * 1000;// 1 minutes
    private static final int NOTIFICATION_ID = 1001;

    public static void stopAlarmManager(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(buildAlarmIntent(context));
    }

    private static PendingIntent buildAlarmIntent(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public static void startAlarmManager(Context context) {
        restartAlarmManager(context, START_DELAY);
    }

    private static void restartAlarmManager(Context context, long restartTime) {
        stopAlarmManager(context);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + restartTime, REPEAT_INTERVAL, buildAlarmIntent(context));
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        ServiceManager.runService(context, new ServiceCallback() {
            @Override
            public void onHandleWork() {
                startHeartBeat(context);
            }
        }, true);
        restartAlarmManager(context, RESTART_DELAY);
    }

    private void startHeartBeat(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        for (int i = 0; i < 4; i++) {
            manager.notify(NOTIFICATION_ID, NotificationHelper.getNotification(context, context.getString(R.string.notification) + " " + Math.random()));
            SystemClock.sleep(1266);
        }
    }
}
