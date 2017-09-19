package com.bwei.czx.jinritoutiao;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.File;

/**
 * Created by czx on 2017/9/1.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this, "20d1f6079ecbc", "4ce76529995feeddb74afef0a1880d52");
        String path = Environment.getExternalStorageDirectory()+"1507Djrtt";
        File file = new File(path);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)
                .threadPriority(100)
                .memoryCacheExtraOptions(400,800)
                .memoryCacheSize(2*1024*1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50*1024*1024)
                .diskCache(new UnlimitedDiskCache(file))
                .build();
        ImageLoader.getInstance().init(config);

        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("111111111111111111111", "onSuccess: "+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("111111111111111111111", "onFailure: "+s +"333333"+ s1 +"====");
            }
        });
    }


}
