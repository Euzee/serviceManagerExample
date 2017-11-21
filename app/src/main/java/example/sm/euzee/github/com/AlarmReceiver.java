package example.sm.euzee.github.com;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import sm.euzee.github.com.servicemanager.ServiceManager;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final long START_DELAY = 1000; //1 sec
    private Recognizer recognizer;

    public static void stopAlarmManager(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmMgr != null) {
            alarmMgr.cancel(buildAlarmIntent(context));
        }
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
        if (alarmMgr != null) {
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + restartTime, buildAlarmIntent(context));
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        ServiceManager.runService(context, () -> startDetection(context.getApplicationContext()), true);
    }

    private void startDetection(Context context) {
        Log.e("Detect", "startDetection");
//        RecognitionStarter.startActivity(context);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (recognizer == null)
                recognizer = new Recognizer(context);
            recognizer.startRecognize();
        });
    }
}
