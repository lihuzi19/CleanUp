package com.example.lihuzi.cleanup.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.lihuzi.cleanup.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (!hasPermission()) {
//                startActivityForResult(
//                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
//                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
//            }
//        }


        initData();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            } else {
//                addControllView();
            }
        }
    }

    private void addControllView() {
        System.out.println("initview 11111111111");
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        RelativeLayout buttonLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.item_controll, null);
        Button button = buttonLayout.findViewById(R.id.item_controll_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click           11111111111");
            }
        });
        //赋值WindowManager&LayoutParam.
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        WindowManager windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //设置效果为背景透明.
        params.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //设置窗口初始停靠位置.
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;

        //设置悬浮窗口长宽数据.
        //注意，这里的width和height均使用px而非dp.这里我偷了个懒
        //如果你想完全对应布局设置，需要先获取到机器的dpi
        //px与dp的换算为px = dp * (dpi / 160).
        params.width = 300;
        params.height = 300;

        windowManager.addView(buttonLayout, params);


        //主动计算出当前View的宽高信息.
        buttonLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

    }

    private boolean hasPermission() {
//        AppOpsManager appOps = (AppOpsManager)
//                getSystemService(Context.APP_OPS_SERVICE);
//        int mode = 0;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
//                    android.os.Process.myUid(), getPackageName());
//        }
//        return mode == AppOpsManager.MODE_ALLOWED;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
//            if (!hasPermission()) {
//                startActivityForResult(
//                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
//                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
//            }
//        }
    }

    private void initData() {
//        ActivityManager systemService = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//
//        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = systemService.getRunningAppProcesses();
//
//        PackageManager PM = getPackageManager();
//
//        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
//            System.out.println(runningAppProcess.processName);
//        }
    }

    public static long getTotalMemSize() {
        long size = 0;
        File file = new File("/proc/meminfo");
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String memInfo = buffer.readLine();
            int startIndex = memInfo.indexOf(":");
            int endIndex = memInfo.indexOf("k");
            memInfo = memInfo.substring(startIndex + 1, endIndex).trim();
            size = Long.parseLong(memInfo);
            size *= 1024;
            buffer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    private static String memInfo;

    public static long getAviableMemSize() {
        long size = 0;
        File file = new File("/proc/meminfo");
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String memInfos = new String();
            int i = 0;
            while ((memInfos = buffer.readLine()) != null) {
                i++;
                if (i == 2) {
                    memInfo = memInfos;
                }

            }
            int startIndex = memInfo.indexOf(":");
            int endIndex = memInfo.indexOf("k");
            memInfo = memInfo.substring(startIndex + 1, endIndex).trim();
            size = Long.parseLong(memInfo);
            size *= 1024;
            buffer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return size;
    }
}
