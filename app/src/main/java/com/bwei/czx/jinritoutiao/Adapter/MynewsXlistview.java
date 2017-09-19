package com.bwei.czx.jinritoutiao.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.czx.jinritoutiao.Bean.TuijianData;
import com.bwei.czx.jinritoutiao.R;
import com.bwei.czx.jinritoutiao.StreamTools;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by czx on 2017/9/1.
 */

public class MynewsXlistview extends BaseAdapter{
    private Context context;
    List<TuijianData.DataBean> list;

    public MynewsXlistview(Context context, List<TuijianData.DataBean> list) {
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

    public void loadMore(List<TuijianData.DataBean> beanList,boolean flag){
            if(flag){
                list.addAll(0,beanList);
            }else{
                list.addAll(beanList);
            }
            notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getVideo_detail_info() == null){
            //不是视频
            if(list.get(position).getImage_list() == null){
                return 0;
            }else if(list.get(position).getImage_list().size() == 3){
                return 1;
            }else{//一张或者两张或者是多张的除了三张
                return 2;
            }
        }else{
            //是视频
            return 3;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        switch (type){
            case 0://只有文字
                ViewHolder1 viewHolder;
                if(convertView == null){
                    convertView = View.inflate(context, R.layout.newsxlistviewtext_item,null);
                    viewHolder = new ViewHolder1();
                    viewHolder.title = (TextView) convertView.findViewById(R.id.newstexttitle);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder1) convertView.getTag();
                }
                viewHolder.title.setText(list.get(position).getTitle());
                break;
            case 1://有三张图片
                ViewHolder2 viewHolder2;
                if(convertView == null){
                    convertView = View.inflate(context, R.layout.newsxlistview3_item,null);
                    viewHolder2 = new ViewHolder2();
                    viewHolder2.title = (TextView) convertView.findViewById(R.id.newsthirtitle);
                    viewHolder2.img1 = (ImageView) convertView.findViewById(R.id.newthirimg1);
                    viewHolder2.img2 = (ImageView) convertView.findViewById(R.id.newthirimg2);
                    viewHolder2.img3 = (ImageView) convertView.findViewById(R.id.newthirimg3);
                    convertView.setTag(viewHolder2);
                }else{
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                }
                viewHolder2.title.setText(list.get(position).getTitle());
                viewHolder2.img1.setImageResource(R.mipmap.ic_launcher);
                viewHolder2.img2.setImageResource(R.mipmap.ic_launcher);
                viewHolder2.img3.setImageResource(R.mipmap.ic_launcher);
                for (int i = 0;i < list.get(position).getImage_list().size();i++){
                    ImageLoader.getInstance().displayImage(list.get(position).getImage_list().get(0).getUrl(),viewHolder2.img1, StreamTools.ForOptions());
                    ImageLoader.getInstance().displayImage(list.get(position).getImage_list().get(1).getUrl(),viewHolder2.img1, StreamTools.ForOptions());
                    ImageLoader.getInstance().displayImage(list.get(position).getImage_list().get(2).getUrl(),viewHolder2.img1, StreamTools.ForOptions());
                }
                break;
            case 2://有一张图或者多张图
                ViewHolder3 viewHolder3;
                if(convertView == null){
                    convertView = View.inflate(context, R.layout.newsxlistview2_item,null);
                    viewHolder3 = new ViewHolder3();
                    viewHolder3.title = (TextView) convertView.findViewById(R.id.newsonetitle);
                    viewHolder3.img = (ImageView) convertView.findViewById(R.id.newsoneimg);
                    convertView.setTag(viewHolder3);
                }else{
                    viewHolder3 = (ViewHolder3) convertView.getTag();
                }
                viewHolder3.title.setText(list.get(position).getTitle());
                viewHolder3.img.setImageResource(R.mipmap.ic_launcher);
                    Log.i("================", "getView: " + list.get(position).getMiddle_image().getUrl());
                    ImageLoader.getInstance().displayImage(list.get(position).getMiddle_image().getUrl(),viewHolder3.img, StreamTools.ForOptions());

                break;
            case 3:
                ViewHolder viewHolder4;
                if(convertView == null){
                    convertView = View.inflate(context, R.layout.newsxlistview_item,null);
                    viewHolder4 = new ViewHolder();
                    viewHolder4.title = (TextView) convertView.findViewById(R.id.newstitle);
                    viewHolder4.img = (ImageView) convertView.findViewById(R.id.newsimg);
                    convertView.setTag(viewHolder4);
                }else{
                    viewHolder4 = (ViewHolder) convertView.getTag();
                }
                viewHolder4.title.setText(list.get(position).getTitle());
                viewHolder4.img.setImageResource(R.mipmap.ic_launcher);
                Log.i("================", "getView: " + list.get(position).getMiddle_image().getUrl());
                ImageLoader.getInstance().displayImage(list.get(position).getMiddle_image().getUrl(),viewHolder4.img, StreamTools.ForOptions());
                break;
        }
        return convertView;
    }
    class ViewHolder{//视频
        TextView title;
        ImageView img;
    }
    class ViewHolder1{//只有文字
        TextView title;
    }
    class ViewHolder2{//三张图
        TextView title;
        ImageView img1;
        ImageView img2;
        ImageView img3;
    }
    class ViewHolder3{//一张
        TextView title;
        ImageView img;
    }
}
