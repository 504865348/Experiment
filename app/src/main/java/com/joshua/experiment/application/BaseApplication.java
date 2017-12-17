package com.joshua.experiment.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * ============================================================
 * <p/>
 * 版 权 ： 吴奇俊  (c) 2016
 * <p/>
 * 作 者 : 吴奇俊
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ： 2016/10/26 10:26
 * <p/>
 * 描 述 ：
 * <p/>
 * ============================================================
 **/
public class BaseApplication extends Application {
    private static BaseApplication application;
    private static int mainTid;
    private static Handler handler;

    {
        Config.DEBUG = true;
        PlatformConfig.setWeixin("wxb254d74dda664d63", "b1761920a55dbc9c25aa8e97a8b7e07d");
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //友盟集成
        UMShareAPI.get(this);

        application = this;
        mainTid = android.os.Process.myTid();
        handler = new Handler();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    public static Context getApplication() {
        return application;
    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }


}
