package com.bwei.czx.jinritoutiao.Bean;

/**
 * Created by czx on 2017/9/10.
 */

public class YscData {
    private String name;
    private String img;
    private String url;

    public YscData(String name, String img, String url) {

        this.name = name;
        this.img = img;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
