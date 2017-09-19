package com.bwei.czx.jinritoutiao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<View> viewList;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager vp = (ViewPager) findViewById(R.id.shouvp);

        //sp判断二次进入
        SharedPreferences preferences = getSharedPreferences("config",MODE_PRIVATE);
        editor = preferences.edit();
        boolean flag = preferences.getBoolean("flag", false);
        if(flag){
            setContentView(R.layout.welcome_layout);
            //欢迎界面点击按钮进入
//            Button jump = (Button) findViewById(R.id.wel_jump);
//            jump.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
            autoplay();
        }else{
            //判断赋值
        editor.putBoolean("flag",true);
        editor.commit();
    }

        //添加布局
        viewList = new ArrayList<>();
        View view1 = View.inflate(MainActivity.this,R.layout.daohang1_layout,null);
        View view2 = View.inflate(MainActivity.this,R.layout.danghang2_layout,null);
        View view3 = View.inflate(MainActivity.this,R.layout.daohang3_layout,null);
        TextView into = (TextView)view3.findViewById(R.id.daohang_into);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        vp.setAdapter(new MyAdapter());
        //导航页点击进入
        into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //viewpager适配器
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    //延迟自动跳转
    public void autoplay(){
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
