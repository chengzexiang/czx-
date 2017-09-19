package com.bwei.czx.jinritoutiao.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bwei.czx.jinritoutiao.Bean.User;
import com.bwei.czx.jinritoutiao.fragments.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czx on 2017/9/5.
 */

public class VpAdapter extends FragmentPagerAdapter{
    private FragmentManager fm;
    private List<User> titlelist;
    private List<String> urllist;
    private List<NewsFragment> list;
    public VpAdapter(FragmentManager fm, List<User> titlelist, List<String> urllist) {
        super(fm);
        this.fm = fm;
        this.titlelist = titlelist;
        this.urllist = urllist;
        list=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        NewsFragment fragment = (NewsFragment) NewsFragment.getUrl(urllist.get(position),titlelist.get(position).getTitle());
        if(list.size()!=urllist.size()){
            list.add(fragment);
            return fragment;
        }else{
            return list.get(position);
        }

    }

    @Override
    public int getCount() {
        return titlelist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position).getTitle();
    }
}
