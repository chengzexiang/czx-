package com.bwei.czx.jinritoutiao.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bwei.czx.jinritoutiao.Adapter.MynewsXlistview;
import com.bwei.czx.jinritoutiao.Bean.Cache;
import com.bwei.czx.jinritoutiao.Bean.TuijianData;
import com.bwei.czx.jinritoutiao.Data.SqliteDao;
import com.bwei.czx.jinritoutiao.Data.WebViewActivity;
import com.bwei.czx.jinritoutiao.R;
import com.bwei.czx.jinritoutiao.StreamTools;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by czx on 2017/8/31.
 */

public class NewsFragment extends Fragment implements XListView.IXListViewListener {

    private XListView xListView;
    private MynewsXlistview adapter;
    private boolean flag;
    private String path;
    private String title;
    private SqliteDao dao;

    public static Fragment getUrl(String path,String title){
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path",path);
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newsfragment_layout,null);
        xListView = (XListView) view.findViewById(R.id.newsXlistview);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        dao = new SqliteDao(getActivity());
        //得到路径
        Bundle bundle = getArguments();
        path = bundle.getString("path");
        title = bundle.getString("title");
        ForData();

        return view;
    }
    public void ForData(){
       getData(path);
          }
    //请求数据
    public void getData(String url){
        new AsyncTask<String,Void,String>(){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<Cache> cacheList = dao.selectchche(title);
                if(s == null){
                    //插入缓存数据的逻辑
                    if(cacheList != null){

                        s = cacheList.get(0).getGson();
                    }
                    Toast.makeText(getActivity(),"没网",Toast.LENGTH_SHORT).show();
                }
                    //添加数据到数据库
                    if(cacheList.size() == 0 ){
                        System.out.println("//////////////////////////////"+s);
                        dao.addcache(title,s);
                    }
                    //不断刷新数据库
                    dao.updatecache(title,s);
                    Gson gson = new Gson();
                    Log.i("=================", "onPostExecute: " + s);
                    TuijianData tuijianData = gson.fromJson(s, TuijianData.class);
                    final List<TuijianData.DataBean> beanList = tuijianData.getData();
                    if(adapter == null){
                        adapter = new MynewsXlistview(getActivity(),beanList);
                        xListView.setAdapter(adapter);
                    }else{
                        adapter.loadMore(beanList,flag);
                    }
                    xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra("beanList",(TuijianData.DataBean) adapter.getItem(position-1));
                            startActivity(intent);
                        }
                    });

            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url1 = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    int code = connection.getResponseCode();
                    if(code == 200){
                        InputStream is = connection.getInputStream();
                        String s = StreamTools.getstr(is);
                        Log.i("=========url1========", "doInBackground: " + url1);
                        Log.i("========s=========", "doInBackground: " + s);
                        return s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url);
    }


    @Override
    public void onRefresh() {
        ForData();
        flag = true;
        xListView.stopRefresh(true);
    }

    @Override
    public void onLoadMore() {
        ForData();
        flag = false;
        xListView.stopLoadMore();
    }
}
