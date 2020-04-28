package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.Event;

import java.util.List;

public class EventAdapter extends BaseAdapter {
    private List<Event> listViewData;

    private Context mContext;

    public EventAdapter(List<Event> listViewData, Context mContext) {
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
            item = LayoutInflater.from(mContext).inflate(R.layout.item_event, viewGroup, false);
            vh = new ViewHolder();
            //找到文本框
            vh.tv1 = item.findViewById(R.id.event_content);
            vh.tv2 = item.findViewById(R.id.event_pic_num);
            vh.tv3 = item.findViewById(R.id.event_status_des);
            vh.tv4 = item.findViewById(R.id.event_timestamp);
            vh.tv5 = item.findViewById(R.id.event_finish);
            vh.tv6 = item.findViewById(R.id.end_timestamp);
            vh.tv7 = item.findViewById(R.id.event_status);
            //找到布局框
            vh.ll = item.findViewById(R.id.event_show_pic);
            vh.ll2 = item.findViewById(R.id.event_feedback);
            vh.ll3 = item.findViewById(R.id.event_end_layout);
            //找到图片框
            vh.iv1 = item.findViewById(R.id.event_pic1);
            vh.iv2 = item.findViewById(R.id.event_pic2);
            vh.iv3 = item.findViewById(R.id.event_pic3);
            //找到复选框
            //让item和ViewHolder绑定在一起
            item.setTag(vh);
        } else {
            //复用ListView给的View
            item = view;
            //拿出ViewHolder
            vh = (ViewHolder) item.getTag();
        }

        //设置内容
        Event pr = listViewData.get(position);
        if(pr != null){
            vh.tv1.setText(pr.getContent());
            vh.tv3.setText(pr.getCurrentStatus());
            vh.tv4.setText(pr.getTimeStamp());
            if(pr.getStatus() == 0){
                vh.tv7.setText("未处理");
                vh.ll3.setVisibility(View.GONE);
                vh.ll2.setVisibility(View.VISIBLE);
            }else if(pr.getStatus() == 1){
                vh.tv7.setText("处理中");
                vh.ll3.setVisibility(View.GONE);
                vh.tv3.setText("物业正在处理");
                vh.tv4.setText(pr.getStartTime());
                vh.ll2.setVisibility(View.INVISIBLE);
            }else if(pr.getStatus() == 2){
                vh.tv7.setText("已完成");
                vh.ll3.setVisibility(View.VISIBLE);
                vh.tv5.setText("完成时间");
                vh.tv6.setText(pr.getEndTime());
                vh.ll2.setVisibility(View.GONE);
            }
            if (pr.getPictureNum() == 0){
                vh.ll.setVisibility(View.GONE);
            }else{
                vh.ll.setVisibility(View.VISIBLE);
                if(pr.getPictureNum() > 3){
                    vh.tv2.setVisibility(View.VISIBLE);
                    vh.iv3.setVisibility(View.VISIBLE);
                    vh.iv2.setVisibility(View.VISIBLE);
                    String str = " " + pr.getPictureNum();
                    vh.tv2.setText(str);
                }else {
                    vh.tv2.setVisibility(View.GONE);
                    vh.iv3.setVisibility(View.VISIBLE);
                    vh.iv2.setVisibility(View.VISIBLE);
                    if(pr.getPictureNum() <= 2){
                        vh.iv3.setVisibility(View.GONE);
                        if(pr.getPictureNum() <= 1){
                            vh.iv2.setVisibility(View.GONE);
                        }
                    }
                }
            }


        }




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
        TextView tv5;
        TextView tv6;
        TextView tv7;
        LinearLayout ll;
        LinearLayout ll2;
        LinearLayout ll3;
        ImageView iv1;
        ImageView iv2;
        ImageView iv3;
    }
}
