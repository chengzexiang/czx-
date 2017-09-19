package com.bwei.czx.jinritoutiao.Data;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwei.czx.jinritoutiao.Bean.TuijianData;
import com.bwei.czx.jinritoutiao.R;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private CheckBox shoucang;
    private ImageView fenxiang;
    private ImageView pinlun;
    private SqliteDao dao;


    private Tencent mTencent;
    private String APP_ID = "222222";
    private String SCOPE = "all";
    private TuijianData.DataBean bean;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        mTencent = Tencent.createInstance(APP_ID,WebViewActivity.this.getApplicationContext());

        dao = new SqliteDao(WebViewActivity.this);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        bean = (TuijianData.DataBean) getIntent().getSerializableExtra("beanList");
        if(bean != null){
            url = bean.getUrl();
        }else{
            url = getIntent().getStringExtra("url");
        }


        boolean show = getIntent().getBooleanExtra("show", false);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);

        //找控件
        shoucang = (CheckBox) findViewById(R.id.shoucang);
        fenxiang = (ImageView) findViewById(R.id.fenxiang);
        pinlun = (ImageView) findViewById(R.id.pinlun);

        SharedPreferences preferences = getSharedPreferences("config",MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();


        //收藏
        shoucang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //没有收藏
                    //开始判断
                    if(bean.getVideo_detail_info() == null){
                        //不是视频
                        if(bean.getImage_list().size() == 3){
                            //三张图片
                            dao.addsc(bean.getTitle(),bean.getUrl(),"1",bean.getImage_list().get(0).getUrl(),bean.getImage_list().get(1).getUrl(),bean.getImage_list().get(2).getUrl(),bean.getMiddle_image().getUrl());
                            Toast.makeText(WebViewActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                        }else if(bean.getImage_list() == null){
                            dao.addsc(bean.getTitle(),bean.getUrl(),"1",null,null,null,null);
                            Toast.makeText(WebViewActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                        }else{
                            dao.addsc(bean.getTitle(),bean.getUrl(),"1",bean.getMiddle_image().getUrl(),null,null,null);
                            Toast.makeText(WebViewActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        //是视频
                        dao.addsc(bean.getTitle(),bean.getUrl(),"1",null,null,null,bean.getMiddle_image().getUrl());
                        Toast.makeText(WebViewActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(WebViewActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    dao.deletesc(bean.getTitle());
                }
            }
        });


        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, bean.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,bean.getKeywords());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,bean.getUrl());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDN");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                mTencent.shareToQQ(WebViewActivity.this, params, new shareListener());
            }
        });
        pinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, bean.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,bean.getKeywords());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,bean.getUrl());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,bean.getImage_list().get(0).getUrl());
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDN");
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                mTencent.shareToQQ(WebViewActivity.this, params, new shareListener());
            }
        });

    }
    class shareListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            //分享成功后回调
            Toast.makeText(WebViewActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            //分享失败后回调
        }

        @Override
        public void onCancel() {
            //取消分享后回调
        }
    };
}
