package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.PayRecord;

import java.util.List;

public class PayAdapter extends BaseAdapter {
    private List<PayRecord> listViewData;

    private Context mContext;

    public PayAdapter(List<PayRecord> listViewData, Context mContext) {
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
            item = LayoutInflater.from(mContext).inflate(R.layout.item_pay, viewGroup, false);
            vh = new ViewHolder();
            //找到文本框
            vh.tv1 = item.findViewById(R.id.pay_date_month);
            vh.tv2 = item.findViewById(R.id.pay_date_year);
            vh.tv3 = item.findViewById(R.id.pay_location);
            vh.tv4 = item.findViewById(R.id.pay_user_info);
            vh.tv5 = item.findViewById(R.id.pay_cost);
            vh.tv6 = item.findViewById(R.id.pay_status);

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
        PayRecord pr = listViewData.get(position);
//        Log.d("xph", "payTime: " + pr.getPayTime());
        vh.tv1.setText(pr.getDateMonth());
        vh.tv2.setText(pr.getDateYear());
        String str = "¥" + pr.getFee();
        vh.tv5.setText(str);
        if (pr.isStatus()){
            vh.tv1.setTextColor(Color.parseColor("#cccccc"));
            vh.tv2.setTextColor(Color.parseColor("#cccccc"));
            vh.tv3.setTextColor(Color.parseColor("#cccccc"));
            vh.tv4.setTextColor(Color.parseColor("#cccccc"));
            vh.tv5.setTextColor(Color.parseColor("#cccccc"));
            vh.tv6.setText("已缴费");
            vh.tv6.setTextColor(Color.parseColor("#cccccc"));
        }else {
            vh.tv1.setTextColor(Color.parseColor("#199ED8"));
            vh.tv2.setTextColor(Color.parseColor("#199ED8"));
            vh.tv3.setTextColor(Color.parseColor("#222222"));
            vh.tv4.setTextColor(Color.parseColor("#222222"));
            vh.tv5.setTextColor(Color.parseColor("#199ED8"));
            vh.tv6.setText("未缴费");
            vh.tv6.setTextColor(Color.parseColor("#199ED8"));
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
        PayRecord pr;
    }

}

