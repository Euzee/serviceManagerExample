package example.sm.euzee.github.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import static example.sm.euzee.github.com.AlarmReceiver.startAlarmManager;
import static example.sm.euzee.github.com.AlarmReceiver.stopAlarmManager;


public class App extends android.app.Application {


    private static App instance;
    private BroadcastReceiver screenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                startAlarmManager(context);
            } else {
                stopAlarmManager(context);
            }
        }
    };

    public static App getInstance() {
        return instance;
    }

    private void registerScreenReceiver() {
        registerReceiver(screenOffReceiver, getScreenOnOffFilter());
    }

    private IntentFilter getScreenOnOffFilter() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        return filter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        registerScreenReceiver();
    }
}
