package com.bwei.czx.jinritoutiao.Bean;

/**
 * Created by czx on 2017/9/7.
 */

public class ScData {
    private String title;
    private String url;
    private String type;
    private String img1;
    private String img2;
    private String img3;
    private String video;

    public ScData(String title, String url, String type, String img1, String img2, String img3, String video) {
        this.title = title;
        this.url = url;
        this.type = type;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.video = video;
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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
