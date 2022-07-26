package com.mylike.smallwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ydh on 2022/7/26
 */
public class WidgetTimerService extends Service {
    /**
     * 周期性更新 widget 的周期
     */
    private static final int UPDATE_TIME = 1000;
    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    public void onCreate() {
        super.onCreate();
        // 每经过指定时间，发送一次广播
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Intent updateIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                sendBroadcast(updateIntent);
            }
        };
        mTimer.schedule(mTimerTask, UPDATE_TIME, UPDATE_TIME);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        mTimerTask.cancel();
        mTimer.cancel();
    }
}
