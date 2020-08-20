package com.yalianxun.mingshi.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.EventAdapter;
import com.yalianxun.mingshi.beans.Event;
import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.utils.DateUtil;
import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EventListActivity extends BaseActivity {
    private List<Event> unsettledData = new ArrayList<>();
    private List<Event> settledData = new ArrayList<>();
    private List<Event> allData = new ArrayList<>();
    private EventAdapter adapter;
    private TextView untreated_tv;
    private TextView finished_tv;
    private ListView listView;
    private UserInfo userInfo;
    private RelativeLayout shadowRL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.event_list);
        userInfo = getIntent().getParcelableExtra("userInfo");
        listView = findViewById(R.id.event_list_lv);
        shadowRL = findViewById(R.id.shadow_rl);
//        String brand = android.os.Build.BRAND;
//        Log.d("xph","brand " + brand + " type " + Build.MODEL);
        initData();
        changStatusIconColor(true);
        finished_tv = findViewById(R.id.finished);
        untreated_tv = findViewById(R.id.untreated);
    }

    private void initData() {
        shadowRL.setVisibility(View.VISIBLE);
        HttpUtils.getReportList(userInfo.getProjectId(),userInfo.getHouseNum(),"1","10",userInfo.getUserId(),new HttpUtils.OnNetResponseListener(){
            @Override
            public void onNetResponseError(String msg) {
                runOnUiThread(()-> {
                    ToastUtils.showTextToast(EventListActivity.this,msg);
                    shadowRL.setVisibility(View.GONE);
                });
            }

            @Override
            public void onNetResponseSuccess(String string) {
                runOnUiThread(()-> updateData(string));
            }
        });

    }

    private void updateData(String value){
        try {
            JSONObject jsonObjectALL = new JSONObject(value);
            JSONObject data = jsonObjectALL.optJSONObject("data");
            assert data != null;
            JSONArray jsonArray = data.getJSONArray("dataList");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Event event = new Event();
                event.setCurrentStatus(jsonObject.optString("title",""));
                event.setContent(jsonObject.optString("content",""));
                event.setImageOne(jsonObject.optString("imageOne",""));
                event.setImageTwo(jsonObject.optString("imageTwo",""));
                event.setImageThree(jsonObject.optString("imageThree",""));
                event.setFeedback(jsonObject.optString("feedback",""));
                long createTime = jsonObject.optLong("createTime", 0);
                event.setStartTime(DateUtil.getTime(createTime,1));
                long endTime = jsonObject.optLong("endTime", 0);
                event.setEndTime(DateUtil.getTime(endTime,1));
                String status = jsonObject.optString("status","0");
                event.setStatus(Integer.parseInt(status));
                allData.add(event);
            }
            for (Event e:allData) {
                if(e.getStatus() == 1){
                    unsettledData.add(e);
                }else
                    settledData.add(e);
            }
            shadowRL.setVisibility(View.GONE);
            showListContent(untreated_tv,finished_tv,findViewById(R.id.event_type2), findViewById(R.id.event_type1),unsettledData);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void goBack(View view) {
        finish();
    }

    public void switchList(View view) {

        if(view.findViewById(R.id.event_type1) != null){
            showListContent(untreated_tv,finished_tv,findViewById(R.id.event_type2), findViewById(R.id.event_type1),unsettledData);
        }else {
            showListContent(finished_tv,untreated_tv,findViewById(R.id.event_type1), findViewById(R.id.event_type2),settledData);
        }
    }

    private void showListContent(TextView tv1,TextView tv2,View v1,View v2, List<Event> list){
        tv1.setTextColor(Color.parseColor("#1677ff"));
        tv2.setTextColor(Color.parseColor("#000000"));
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.VISIBLE);
        EventAdapter adapter = new EventAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(this,EventDetailActivity.class);
            Event event = list.get(position);
            intent.putExtra("event",event);
            startActivity(intent);
        });
    }
}
