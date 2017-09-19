package com.bwei.czx.jinritoutiao.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.czx.jinritoutiao.Bean.YscData;
import com.bwei.czx.jinritoutiao.R;
import com.bwei.czx.jinritoutiao.StreamTools;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by czx on 2017/9/10.
 */

public class ScAdapter extends BaseAdapter {
    private Context context;
    private List<YscData> list;

    public ScAdapter(Context context, List<YscData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = View.inflate(context, R.layout.sclistviewitem_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.scitem_title);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.scitem_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getName());
        ImageLoader.getInstance().displayImage(list.get(position).getImg(),viewHolder.img, StreamTools.ForOptions());
        return convertView;
    }
    class ViewHolder{
        TextView title;
        ImageView img;
    }
}
