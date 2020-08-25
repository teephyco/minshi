package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.Door;

import java.util.List;

public class LocationChooseAdapter extends BaseAdapter {
    private List<Door> listViewData;
    private Context mContext;

    public LocationChooseAdapter(List<Door> listViewData,Context context) {
        this.listViewData = listViewData;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listViewData.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item;
        ViewHolder vh;
        if (convertView == null){
            item = LayoutInflater.from(mContext).inflate(R.layout.item_location_choose, parent, false);
            vh = new ViewHolder();

            vh.doorName = item.findViewById(R.id.location_name);
            vh.doorStatus = item.findViewById(R.id.selected_status);

            item.setTag(vh);
        }else {
            item = convertView;
            vh =  (ViewHolder) item.getTag();
        }
        Door door = listViewData.get(position);
        if(door != null){
            vh.doorStatus.setImageResource(door.getStatus() == 0 ?R.drawable.ic_cercles_concentriques:R.drawable.ic_cercles_un);
            vh.doorName.setText(door.getName());
        }
        return item;
    }

    static class ViewHolder {
        TextView doorName;
        ImageView doorStatus;
    }
}
