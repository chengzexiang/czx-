package com.bwei.czx.jinritoutiao.Bean;

/**
 * Created by czx on 2017/9/6.
 */

public class User {
    private String title;
    private String url;
    private String type;

    public User(String title, String url, String type) {
        this.title = title;
        this.url = url;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
