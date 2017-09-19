package com.bwei.czx.jinritoutiao.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwei.czx.jinritoutiao.Bean.User;
import com.bwei.czx.jinritoutiao.R;

import java.util.List;

/**
 * Created by czx on 2017/9/6.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<User> list;

    public GridViewAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list.size()>0){
            return list.size();
        }else{
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.gridview_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.gvbtn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(list.get(position).getTitle());
        return convertView;
    }
    class ViewHolder{
        TextView name;
    }
}
