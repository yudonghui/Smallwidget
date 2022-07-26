package com.mylike.smallwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 * 参考资料：https://blog.csdn.net/hwb04160011/article/details/119728412
 */
public class NewAppWidget extends AppWidgetProvider {
    public static final String CLICK_ALIPAY_ACTION = "com.mylike.alipay.action.CLICK"; // 支付宝
    public static final String CLICK_WX_ACTION = "com.mylike.alipay.action.CLICK"; // 微信
    public static final String WECHAT_APP_PACKAGE = "com.tencent.mm"; // 微信
    public static final String WECHAT_LAUNCHER_UI_CLASS = "com.tencent.mm.ui.LauncherUI"; // 微信
    public static final String WECHAT_OPEN_SCANER_NAME = "LauncherUI.From.Scaner.Shortcut"; // 微信

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        if (isAliPayInstalled(context))
            views.setImageViewBitmap(R.id.iv_alipay, BitmapFactory.decodeResource(context.getResources(), R.mipmap.wx_scan));

        Uri uriAliScan = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
        Intent intentAliScan = new Intent(Intent.ACTION_VIEW, uriAliScan);
        PendingIntent pendingIntentAliScan = PendingIntent.getActivity(context, 0, intentAliScan, 0);
        views.setOnClickPendingIntent(R.id.ll_alipay_scan, pendingIntentAliScan);


        Uri uriAliPay = Uri.parse("alipayqr://platformapi/startapp?saId=20000056");
        Intent intentAliPay = new Intent(Intent.ACTION_VIEW, uriAliPay);
        PendingIntent pendingIntentAliPay = PendingIntent.getActivity(context, 0, intentAliPay, 0);
        views.setOnClickPendingIntent(R.id.ll_alipay_pay, pendingIntentAliPay);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(WECHAT_OPEN_SCANER_NAME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ComponentName componentName = new ComponentName(WECHAT_APP_PACKAGE, WECHAT_LAUNCHER_UI_CLASS);
        intent.setComponent(componentName);
        //PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, R.id.ll_wx_scan, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ll_wx_scan, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();
        if (CLICK_ALIPAY_ACTION.equals(action)) {
            Log.e("点击事件", "点击了支付宝");
            Toast.makeText(context, "点击了支付宝", Toast.LENGTH_SHORT).show();
        } else if (CLICK_WX_ACTION.equals(action)) {
            Log.e("点击事件", "点击了微信");
            Toast.makeText(context, "点击了微信", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */

    @Override

    public void onDeleted(Context context, int[] appWidgetIds) {

        super.onDeleted(context, appWidgetIds);

    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */

    @Override

    public void onDisabled(Context context) {

        super.onDisabled(context);

    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */

    @Override

    public void onEnabled(Context context) {

        super.onEnabled(context);
    }

    /**
     * 当小部件大小改变时
     */

    @Override

    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

    }

    /**
     * 当小部件从备份恢复时调用该方法
     */

    @Override

    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {

        super.onRestored(context, oldWidgetIds, newWidgetIds);

    }

    /**
     * 是否安装了微信
     */
    private static boolean isWxInstall(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                String packageName = installedPackages.get(i).packageName;
                if (packageName.equals(WECHAT_APP_PACKAGE)) {//微信
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                String pn = installedPackages.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 是否安装了支付宝
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;

    }

    /**
     * sina
     * <p>
     * 判断是否安装新浪微博
     */

    public static boolean isSinaInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                String pn = installedPackages.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 更新组件
     */
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int imgRes) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //这里设置更新的控件内容，例如 views.setTextViewText(R.id.appwidget_text, widgetText);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

