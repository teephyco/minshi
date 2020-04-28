package com.yalianxun.mingshi.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.Door;
import com.yalianxun.mingshi.utils.ScreenUtils;

import java.util.List;
import java.util.Objects;

public class DoorAdapter extends BaseAdapter {
    private List<Door> listViewData;
    private Context mContext;

    public DoorAdapter(List<Door> listViewData, Context mContext) {
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
        if (view == null){
            item = LayoutInflater.from(mContext).inflate(R.layout.gv_open_door, viewGroup, false);
            vh = new ViewHolder();

            vh.fl = item.findViewById(R.id.door_top_fl);
            vh.tv = item.findViewById(R.id.door_name);
            vh.iv = item.findViewById(R.id.wifi_status);
            vh.shadowV = item.findViewById(R.id.shadow_gv);
            item.setTag(vh);
        }else {
            item = view;
            vh = (ViewHolder) item.getTag();
        }
        Door door = listViewData.get(position);
        if(door != null){
            vh.tv.setText(door.getName());
            if(door.getStatus() != 0){
                vh.shadowV.setVisibility(View.GONE);
                vh.iv.setImageResource(R.drawable.wifi_online);
                vh.tv.setOnLongClickListener(v -> {

                    @SuppressLint("InflateParams") View alertView = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog,null,false);
                    TextView btn_agree = alertView.findViewById(R.id.alert_agree);
                    TextView btn_cancel = alertView.findViewById(R.id.alert_cancel);
                    EditText et = alertView.findViewById(R.id.alert_et);
                    final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(alertView).create();

                    btn_agree.setOnClickListener(v12 -> {
                        if(!et.getText().toString().equals("")){
                            vh.tv.setText(et.getText().toString());
                        }
                        dialog.dismiss();
                    });
                    btn_cancel.setOnClickListener(v1 -> dialog.dismiss());

                    dialog.show();
                    Objects.requireNonNull(dialog.getWindow()).setLayout((ScreenUtils.getScreenWidth(mContext)/4*3), LinearLayout.LayoutParams.WRAP_CONTENT);
                    return false;
                });
                vh.fl.setOnClickListener(v -> Toast.makeText(mContext,"开门",Toast.LENGTH_SHORT).show());
            }else{
                vh.shadowV.setVisibility(View.VISIBLE);
                vh.iv.setImageResource(R.drawable.wifi_offline);
            }
        }
        return item;
    }


    static class ViewHolder {
        TextView tv;
        FrameLayout fl;
        ImageView iv;
        View shadowV;

    }
}
