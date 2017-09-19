package com.bwei.czx.jinritoutiao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.czx.jinritoutiao.Adapter.VpAdapter;
import com.bwei.czx.jinritoutiao.Bean.CeListData;
import com.bwei.czx.jinritoutiao.Bean.User;
import com.bwei.czx.jinritoutiao.Data.SqliteDao;
import com.bwei.czx.jinritoutiao.fragments.NewsFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.bwei.czx.jinritoutiao.R.id.cename;

public class SecondActivity extends AppCompatActivity {

    private ViewPager vp;
    List<String> titleArray = Arrays.asList("推荐", "热点", "本地", "视频", "社会", "娱乐", "科技", "汽车", "体育", "财经", "军事", "国际", "段子", "趣图", "健康", "美女");
    List<String> urlArray = Arrays.asList(
            "http://ic.snssdk.com/2/article/v25/stream/?count=20&min_behot_time=1455521444&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82" +
            "&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455521401&loc_mode=5&la" +
            "c=4527&cid=28883&iid=3642583580&d" +
            "evice_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&" +
            "version_code=460&device_platform=android&d" +
            "evice_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000",
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_hot&count=20&min_behot_" +
            "time=1455521166&bd_city=%E5%8C%97%E4%BA%AC%E5%B8" +
            "%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_ti" +
            "me=1455521401&loc_mode=5&lac=4527&cid=28883&iid=3642583580&dev" +
            "ice_id=11131669133&ac=wifi&channel=baidu&aid=13&app_nme=" +
            "news_article&version_code=460&device_platform=android&device_type" +
            "=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000",
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_local&coun" +
            "t=20&min_behot_time=1455521226&bd_city=%E5%8C%97%E" +
            "4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitud" +
            "e=116.296499&bd_loc_time=1455521401&loc_mode=5&user_cit" +
            "y=%E5%8C%97%E4%BA%AC&lac=4527&cid=28883&iid=3642583580" +
            "&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name" +
            "=news_article&version_code=460&device_platform=android&devic" +
            "e_type=SCH-I919U&os_api=19&os_version=4.4.2&" +
            "uuid=285592931621751&openudid=AC9E172CE2490000",
            "http://ic.snssdk.com/2/article/v25/stream/?category=video&count=20&min_behot_time=1455521349&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522107&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_society&count=20&min_behot_time=1455521720&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522107&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_entertainment&count=20&min_behot_time=1455522338&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_tech&count=20&min_behot_time=1455522427&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_car&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_sports&count=20&min_behot_time=1455522629&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_finance&count=20&min_behot_time=1455522899&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_military&count=20&min_behot_time=1455522991&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_world&count=20&min_behot_time=1455523059&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=essay_joke&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=image_funny&count=20&min_behot_time=1455524031&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=news_health&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455524092&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
            ,
            "http://ic.snssdk.com/2/article/v25/stream/?category=image_ppmm&count=20&min_behot_time=1455524172&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455524092&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"


            );
    private List<Fragment> fragmentList;
    private FragmentManager fm;
    private List<CeListData> functionList;


    //qq第三方登录
    private static final String TAG = "SecondActivity";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private ImageView qqlogin;
    private ImageView cellphone;
    private ImageView sinalogin;
    private TextView morelogin;
    private ImageView headlogin;
    private ImageView night;
    private LinearLayout cehead;
    private ImageView titlehead;
    private ImageView pindao;
    private SqliteDao dao;
    private SharedPreferences.Editor editor;
    private EventHandler eventHandler;
    private RelativeLayout rel;
    private TextView shownumber;
    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        dao = new SqliteDao(this);



        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,SecondActivity.this.getApplicationContext());
        //侧滑
        final SlidingMenu slidingMenu = new SlidingMenu(this);

        slidingMenu.setBehindOffset(100);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setMenu(R.layout.slidingmenu_layout);

        //依赖到Activity
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        headlogin = (ImageView) findViewById(R.id.second_headlogin);
        headlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.second_tab);

        fragmentList = new ArrayList<>();
        for (int i = 0; i < titleArray.size(); i++) {
            NewsFragment newsfragment = new NewsFragment();
            fragmentList.add(newsfragment);
        }
        vp = (ViewPager) findViewById(R.id.second_vp);
        vp.setOffscreenPageLimit(titleArray.size());
        //tablaut与viewpager联动
        tabLayout.setupWithViewPager(vp);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

//进行联网判断
        if(isOnline()) {
            //有网，联网请求数据
            titleData();
            fragmentList = new ArrayList<>();
            for (int i = 0; i < titleArray.size(); i++) {
                NewsFragment newsfragment = new NewsFragment();
                fragmentList.add(newsfragment);
            }
            fm = getSupportFragmentManager();
            List<User> selectlist = dao.select();
            VpAdapter vpAdapter = new VpAdapter(fm,selectlist,urlArray);
            vp.setAdapter(vpAdapter);
        }else{
            //弹出提示对话框
            showDialog();
        }

        //频道管理
        pindao = (ImageView) findViewById(R.id.addpindao);
        pindao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,AddPindaoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.transet,R.anim.out);
            }
        });

        initLeft();

    }
    //侧滑界面
    private void initLeft() {
        listview = (ListView) findViewById(R.id.second_listview);
        qqlogin = (ImageView) findViewById(R.id.qqlogin);
        cellphone = (ImageView) findViewById(R.id.cellphone);
        sinalogin = (ImageView) findViewById(R.id.sinalogin);
        morelogin = (TextView) findViewById(R.id.morelogin);
        night = (ImageView) findViewById(R.id.night);
        titlehead = (ImageView) findViewById(R.id.titlehead);
        functionList = new ArrayList<>();
        functionList.add(new CeListData("好友动态",R.mipmap.dongtai));
        functionList.add(new CeListData("我的话题",R.mipmap.huati));
        functionList.add(new CeListData("收藏",R.mipmap.shoucang));
        functionList.add(new CeListData("活动",R.mipmap.huodong));
        functionList.add(new CeListData("商城",R.mipmap.shangcheng));
        functionList.add(new CeListData("反馈",R.mipmap.fankui));
        listview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return functionList.size();
            }

            @Override
            public Object getItem(int position) {
                return functionList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if(convertView == null){
                    convertView = View.inflate(SecondActivity.this,R.layout.celistview_layout,null);
                    viewHolder = new ViewHolder();
                    viewHolder.img = (ImageView) convertView.findViewById(R.id.ceimg);
                    viewHolder.name = (TextView) convertView.findViewById(cename);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.img.setImageResource(functionList.get(position).getPic());
                viewHolder.name.setText(functionList.get(position).getName());
                return convertView;
            }
        });

        qqlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(SecondActivity.this,"all", mIUiListener);
                mUserInfo = new UserInfo(SecondActivity.this, mTencent.getQQToken());
                mUserInfo.getUserInfo(mIUiListener);

            }
        });
        rel = (RelativeLayout) findViewById(R.id.phoneRel);
        shownumber = (TextView) findViewById(R.id.shownumber);
        Intent intent = getIntent();
        String phonenumber = intent.getStringExtra("phonenumber");
        if(phonenumber != null){
            rel.setVisibility(View.VISIBLE);
            shownumber.setText("用户："+phonenumber);
        }
        //短信验证
        cellphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,PhoneActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.outy,R.anim.transet);
            }
        });
        final int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode == Configuration.UI_MODE_NIGHT_YES) {
                    morelogin.setBackgroundResource(R.drawable.sharp1);

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                    morelogin.setBackgroundResource(R.drawable.shape);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                recreate();
            }
        });

        //收藏functionList
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 2:
                        Intent intent1 = new Intent(SecondActivity.this,ShoucangActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.transet,R.anim.out);
                        break;
                }
            }
        });




    }
    class ViewHolder{
        ImageView img;
        TextView name;
    }
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(SecondActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
        mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object response) {
                Log.e(TAG,"登录成功"+response.toString());
                Toast.makeText(SecondActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                if(response == null){
                    return;
                }

                try {
                        JSONObject jo = (JSONObject) response;
                        String nickName = jo.getString("nickname");
                        //figureurl_1
                        String img = jo.getString("figureurl_1");
                        morelogin.setText(nickName);
//                    titlehead.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage(img,cellphone,StreamTools.getOptions());
                        ImageLoader.getInstance().displayImage(img,headlogin,StreamTools.getOptions());
                        qqlogin.setVisibility(View.INVISIBLE);
                    sinalogin.setVisibility(View.INVISIBLE);
//                    cellphone.setVisibility(View.INVISIBLE);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG,"登录失败"+uiError.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG,"登录取消");

            }
        });
    } catch (JSONException e) {
        e.printStackTrace();
    }
}

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(SecondActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(SecondActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }
    //联网判断
    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("怎么可没网呢");
        builder.setNegativeButton("不联网，", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                titleData();
                fragmentList = new ArrayList<>();
                for (int i = 0; i < titleArray.size(); i++) {
                    NewsFragment newsfragment = new NewsFragment();
                    fragmentList.add(newsfragment);
                }
                fm = getSupportFragmentManager();
                List<User> selectlist = dao.select();
                VpAdapter vpAdapter = new VpAdapter(fm,selectlist,urlArray);
                vp.setAdapter(vpAdapter);
            }
        });
        builder.setPositiveButton("联网", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //跳转网络设置界面
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

            }
        });

        builder.create().show();
    }
    //联网
    public boolean isOnline() {

        //得到一个连接管理者
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        //得到联网信息
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //判断设备是否联网
        return (networkInfo != null && networkInfo.isConnected());
    }
    //存储数据
    public void titleData(){
        SharedPreferences preferences = getSharedPreferences("config",MODE_PRIVATE);
        editor = preferences.edit();
        boolean fl = preferences.getBoolean("fl", false);
        if(fl){

        }else{
            List<String> titleArray = Arrays.asList("推荐", "热点", "本地", "视频", "社会", "娱乐", "科技", "汽车", "体育", "财经", "军事", "国际", "段子", "趣图", "健康", "美女");
            List<String> urlArray = Arrays.asList(
                    "http://ic.snssdk.com/2/article/v25/stream/?count=20&min_behot_time=1455521444&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82" +
                            "&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455521401&loc_mode=5&la" +
                            "c=4527&cid=28883&iid=3642583580&d" +
                            "evice_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&" +
                            "version_code=460&device_platform=android&d" +
                            "evice_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000",
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_hot&count=20&min_behot_" +
                            "time=1455521166&bd_city=%E5%8C%97%E4%BA%AC%E5%B8" +
                            "%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_ti" +
                            "me=1455521401&loc_mode=5&lac=4527&cid=28883&iid=3642583580&dev" +
                            "ice_id=11131669133&ac=wifi&channel=baidu&aid=13&app_nme=" +
                            "news_article&version_code=460&device_platform=android&device_type" +
                            "=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000",
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_local&coun" +
                            "t=20&min_behot_time=1455521226&bd_city=%E5%8C%97%E" +
                            "4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitud" +
                            "e=116.296499&bd_loc_time=1455521401&loc_mode=5&user_cit" +
                            "y=%E5%8C%97%E4%BA%AC&lac=4527&cid=28883&iid=3642583580" +
                            "&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name" +
                            "=news_article&version_code=460&device_platform=android&devic" +
                            "e_type=SCH-I919U&os_api=19&os_version=4.4.2&" +
                            "uuid=285592931621751&openudid=AC9E172CE2490000",
                    "http://ic.snssdk.com/2/article/v25/stream/?category=video&count=20&min_behot_time=1455521349&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522107&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_society&count=20&min_behot_time=1455521720&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522107&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_entertainment&count=20&min_behot_time=1455522338&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_tech&count=20&min_behot_time=1455522427&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_car&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_sports&count=20&min_behot_time=1455522629&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_finance&count=20&min_behot_time=1455522899&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_military&count=20&min_behot_time=1455522991&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_world&count=20&min_behot_time=1455523059&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=essay_joke&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=image_funny&count=20&min_behot_time=1455524031&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=news_health&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455524092&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"
                    ,
                    "http://ic.snssdk.com/2/article/v25/stream/?category=image_ppmm&count=20&min_behot_time=1455524172&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455524092&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000"


            );
            for (int i = 0; i < titleArray.size(); i++) {
                dao.add(titleArray.get(i),urlArray.get(i),"1");
            }
            editor.putBoolean("fl",true);
            editor.commit();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }


}
