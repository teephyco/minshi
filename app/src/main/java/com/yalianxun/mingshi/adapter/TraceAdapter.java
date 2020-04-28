package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.PayRecord;
import com.yalianxun.mingshi.beans.TripRecord;

import java.util.List;

public class TraceAdapter extends BaseAdapter {
    private List<TripRecord> listViewData;
    private Context mContext;

    public TraceAdapter(List<TripRecord> listViewData, Context mContext) {
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
        View item;

        ViewHolder vh;

        if (view == null) {
            item = LayoutInflater.from(mContext).inflate(R.layout.item_trip_record, viewGroup, false);
            vh = new ViewHolder();
            vh.tv1 = item.findViewById(R.id.tr_timestamp);
            vh.tv2 = item.findViewById(R.id.tr_name);
            vh.tv3 = item.findViewById(R.id.tr_phone);
            vh.tv4 = item.findViewById(R.id.tr_status);

            item.setTag(vh);
        } else {
            //复用ListView给的View
            item = view;
            //拿出ViewHolder
            vh = (ViewHolder) item.getTag();
        }

        TripRecord tr = listViewData.get(position);
        if(tr != null){
            vh.tv1.setText(tr.getTimestamp());
            vh.tv2.setText(tr.getName());
            vh.tv3.setText(tr.getPhone());
            String str = tr.getStatus() != 0 ? "进门":"出门";
            vh.tv4.setText(str);
        }
        return item;
    }

    static class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;

    }
}
