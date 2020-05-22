package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.Notification;

import java.util.List;

public class LifeInfoAdapter extends BaseAdapter {
    private List<Notification> listViewData;

    private Context mContext;

    public LifeInfoAdapter(List<Notification> listViewData, Context mContext) {
        this.listViewData = listViewData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listViewData.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //Item对应的试图
        View item;

        ViewHolder vh;

        if (view == null) {
            item = LayoutInflater.from(mContext).inflate(R.layout.item_life_info, viewGroup, false);
            vh = new ViewHolder();
            //找到文本框
            vh.tv1 = item.findViewById(R.id.life_info_title);
            vh.tv2 = item.findViewById(R.id.life_source);
            vh.tv3 = item.findViewById(R.id.life_timestamp);
            vh.tv4 = item.findViewById(R.id.life_click_count);
            vh.iv = item.findViewById(R.id.life_iv);
            //找到复选框
            //让item和ViewHolder绑定在一起
            item.setTag(vh);
        } else {
            //复用ListView给的View
            item = view;
            //拿出ViewHolder
            vh = (ViewHolder) item.getTag();
        }

        //设置文本内容
        Notification notification = listViewData.get(position);
        vh.tv1.setText(notification.getTitle());
        String str = "" + notification.getCountNum();
        vh.tv4.setText(str);
        Glide.with(mContext)
                .load(notification.getImgUrl())
                .placeholder(R.drawable.placeholder_pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vh.iv);
        return item;
    }


    /**
     * 用于存放一个ItemView中的控件,由于这里只有两个控件,那么声明两个控件即可
     */
    static class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        ImageView iv;
    }
}
