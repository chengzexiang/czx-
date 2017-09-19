package com.bwei.czx.jinritoutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bwei.czx.jinritoutiao.Adapter.ScAdapter;
import com.bwei.czx.jinritoutiao.Bean.YscData;
import com.bwei.czx.jinritoutiao.Data.SqliteDao;
import com.bwei.czx.jinritoutiao.Data.WebViewActivity;

import java.util.List;

public class ShoucangActivity extends AppCompatActivity {

    private ListView listView;
    private List<YscData> list;
    private ImageView fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang);

        listView = (ListView) findViewById(R.id.sclistview);
        fanhui = (ImageView) findViewById(R.id.fanhui);
        SqliteDao dao = new SqliteDao(this);
        list = dao.selectScAll();
        ScAdapter adapter = new ScAdapter(ShoucangActivity.this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShoucangActivity.this, WebViewActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                intent.putExtra("show",true);
                startActivity(intent);
                overridePendingTransition(R.anim.out,R.anim.transet);
            }
        });
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
