package com.bwei.czx.jinritoutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bwei.czx.jinritoutiao.Adapter.GridViewAdapter;
import com.bwei.czx.jinritoutiao.Bean.User;
import com.bwei.czx.jinritoutiao.Data.SqliteDao;

import java.util.List;

public class AddPindaoActivity extends AppCompatActivity {

    private List<User> select;
    private List<User> select2;
    private SqliteDao dao;
    private GridViewAdapter adapter;
    private GridViewAdapter adapter1;
    private TextView fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pindao);
        GridView gridView1 = (GridView) findViewById(R.id.gridview1);
        GridView gridView2 = (GridView) findViewById(R.id.gridview2);
        dao = new SqliteDao(this);

        fanhui = (TextView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPindaoActivity.this,SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
        select = dao.select();
        select2 = dao.select2();
        adapter = new GridViewAdapter(AddPindaoActivity.this, select);

        adapter1 = new GridViewAdapter(AddPindaoActivity.this, select2);
        gridView2.setAdapter(adapter1); gridView1.setAdapter(adapter);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dao.update(select.get(position).getTitle());
                if(!select.get(position).getTitle().equals("推荐")){
                    select2.add(select.get(position));
                    select.remove(select.get(position));
                    adapter1.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dao.update2(select2.get(position).getTitle());
                select.add(select2.get(position));
                select2.remove(select2.get(position));
                adapter1.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
