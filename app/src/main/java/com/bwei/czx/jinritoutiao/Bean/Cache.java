package com.bwei.czx.jinritoutiao.Bean;

/**
 * Created by czx on 2017/9/15.
 */

public class Cache {
    private String title;
    private String gson;

    public Cache(String title, String gson) {
        this.title = title;
        this.gson = gson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGson() {
        return gson;
    }

    public void setGson(String gson) {
        this.gson = gson;
    }
}
