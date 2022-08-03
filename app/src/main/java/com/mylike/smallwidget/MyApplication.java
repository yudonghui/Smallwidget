package com.mylike.smallwidget;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by ydh on 2022/7/28
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        startService();
    }

    public void startService() {
        Log.i("开启服务", "MyApplication startService");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上系统启动为前台服务, 否则在后台, 测试中发现过几分钟后MediaController监听不到音乐信息
            startForegroundService(new Intent(this, MediaControllerService.class));
        } else {
            startService(new Intent(this, MediaControllerService.class));
        }
    }

}
