package com.bwei.czx.recyclerview;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by czx on 2017/9/8.
 */

public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            MobSDK.init(this, "20d1f6079ecbc", "4ce76529995feeddb74afef0a1880d52");
        }
}
