package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.NotifyAdapter;
import com.yalianxun.mingshi.beans.Notification;
import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.utils.DateUtil;
import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import kotlin.text.Regex;

public class PropertyNotifyActivity extends BaseActivity {
    private TextView untreated_tv;
    private TextView finished_tv;
    List<Notification> listData = new ArrayList<>();
    private UserInfo userInfo;
    private NotifyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_notify);
        TextView tv = findViewById(R.id.av_title);
        finished_tv = findViewById(R.id.finished);
        untreated_tv = findViewById(R.id.untreated);
        tv.setText(R.string.property_notification);
        userInfo = getIntent().getParcelableExtra("userInfo");
        adapter = new NotifyAdapter(listData,this);
        ListView listView = findViewById(R.id.notify_lv);
        //将适配器中数据添加到ListView中
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra("key","notify");
            intent.putExtra("title",listData.get(position).getTitle());
            intent.putExtra("content",listData.get(position).getContent());
            startActivity(intent);
        });
        showListContent(untreated_tv,finished_tv,findViewById(R.id.event_type2), findViewById(R.id.event_type1),listData);
        HttpUtils.getAllNotification(userInfo.getProjectId(), "1", "10", userInfo.getUserId(), new HttpUtils.OnNetResponseListener() {
            @Override
            public void onNetResponseError(String msg) {
                runOnUiThread(() -> ToastUtils.showTextToast(PropertyNotifyActivity.this,msg));
            }

            @Override
            public void onNetResponseSuccess(String string) {
                runOnUiThread(()-> refreshData(string));
            }
        });

    }

    public void goBack(View view) {
        finish();
    }

    public void switchList(View view) {
        if(view.findViewById(R.id.event_type1) != null){
            showListContent(untreated_tv,finished_tv,findViewById(R.id.event_type2), findViewById(R.id.event_type1),listData);
        }else {
            showListContent(finished_tv,untreated_tv,findViewById(R.id.event_type1), findViewById(R.id.event_type2),listData);
        }
    }

    private void showListContent(TextView tv1,TextView tv2,View v1,View v2, List<Notification> list){
        tv1.setTextColor(Color.parseColor("#1677ff"));
        tv2.setTextColor(Color.parseColor("#000000"));
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.VISIBLE);
    }

    private void refreshData(String value){
        try {
            JSONObject jsonObjectALL = new JSONObject(value);
            JSONObject data = jsonObjectALL.optJSONObject("data");
            assert data != null;
            JSONArray jsonArray = data.getJSONArray("dataList");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Notification notify = new Notification();
                notify.setTitle(jsonObject.optString("title",""));
                String regFormat = "\\s*|\t|\r|\n";
                String regTag = "<[^>]*>";
                String content = jsonObject.optString("content","");
                notify.setContent(content.replaceAll(regFormat,"").replaceAll(regTag,""));
                notify.setContentLink(jsonObject.optString("content",""));
                long createTime = jsonObject.optLong("createTime",0);
                notify.setTimestamp(DateUtil.getTime(createTime,1));
                notify.setCountNum(108);
                listData.add(notify);
            }

            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
