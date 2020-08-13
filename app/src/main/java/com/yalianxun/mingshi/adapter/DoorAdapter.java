package com.yalianxun.mingshi.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
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
            item = LayoutInflater.from(mContext).inflate(R.layout.item_door_status, viewGroup, false);
            vh = new ViewHolder();

            vh.doorName = item.findViewById(R.id.door_name);
            vh.doorStatus = item.findViewById(R.id.door_status);
            vh.wifiStatus = item.findViewById(R.id.wifi_status);
            vh.editText = item.findViewById(R.id.door_mod_et);
            vh.modNameBtn = item.findViewById(R.id.mod_door_name);
            item.setTag(vh);
        }else {
            item = view;
            vh = (ViewHolder) item.getTag();
        }
        Door door = listViewData.get(position);
        if(door != null){
            vh.doorName.setText(door.getName());
            if(door.getStatus() != 0){
                vh.doorStatus.setImageResource(R.drawable.ic_door_normal);
                vh.wifiStatus.setVisibility(View.VISIBLE);
                vh.modNameBtn.setClickable(true);
                vh.modNameBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"",Toast.LENGTH_SHORT).show();
                        vh.editText.setVisibility(View.VISIBLE);
                        vh.editText.requestFocus();
//                        @SuppressLint("InflateParams") View alertView = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog,null,false);
//                        TextView btn_agree = alertView.findViewById(R.id.alert_agree);
//                        TextView btn_cancel = alertView.findViewById(R.id.alert_cancel);
//                        EditText et = alertView.findViewById(R.id.alert_et);
//                        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(alertView).create();
//
//                        btn_agree.setOnClickListener(v12 -> {
//                            if(!et.getText().toString().equals("")){
//                                vh.doorName.setText(et.getText().toString());
//                            }
//                            dialog.dismiss();
//                        });
//                        btn_cancel.setOnClickListener(v1 -> dialog.dismiss());
//                        dialog.show();
                    }
                });
            }else{
                vh.wifiStatus.setVisibility(View.INVISIBLE);
                vh.modNameBtn.setClickable(false);
                vh.doorStatus.setImageResource(R.drawable.ic_door_offline);
            }
        }
        vh.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("xph","hasFocus : " + hasFocus + " @@@@ " + vh.editText);
                if(hasFocus){
                    vh.doorName.setVisibility(View.INVISIBLE);
                    vh.modNameBtn.setVisibility(View.INVISIBLE);
                }else {
                    vh.doorName.setVisibility(View.VISIBLE);
                    vh.modNameBtn.setVisibility(View.VISIBLE);
//                    vh.editText.setVisibility(View.INVISIBLE);
                }
            }
        });
        return item;
    }


    static class ViewHolder {
        TextView doorName;
        ImageView doorStatus;
        ImageView modNameBtn;
        ImageView wifiStatus;
        EditText editText;
    }
}
