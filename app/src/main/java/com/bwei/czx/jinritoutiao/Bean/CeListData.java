package com.bwei.czx.jinritoutiao.Bean;

/**
 * Created by czx on 2017/8/31.
 */

public class CeListData {
    private String name;
    private int pic;

    public CeListData(String name, int pic) {
        this.name = name;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
