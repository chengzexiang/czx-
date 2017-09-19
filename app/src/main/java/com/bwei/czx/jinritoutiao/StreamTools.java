package com.bwei.czx.jinritoutiao;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by czx on 2017/9/1.
 */

public class StreamTools {
    public static String getstr(InputStream is){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len = is.read(arr))!= -1){
                bos.write(arr,0,len);
            }
            return bos.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static DisplayImageOptions getOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnFail(R.mipmap.ic_error)
                .showImageOnLoading(R.mipmap.loading)
                .displayer(new CircleBitmapDisplayer())
                .build();
        return options;
    }
    public static DisplayImageOptions ForOptions(){
        DisplayImageOptions options1 = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnFail(R.mipmap.ic_error)
                .showImageOnLoading(R.mipmap.loading)
                .build();
        return options1;
    }
}
