package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.NotifyAdapter;
import com.yalianxun.mingshi.beans.Notification;
import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PropertyNotifyActivity extends BaseActivity {
    private TextView untreated_tv;
    private TextView finished_tv;
    List<Notification> listData = new ArrayList<>();
    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_notify);
        TextView tv = findViewById(R.id.av_title);
        finished_tv = findViewById(R.id.finished);
        untreated_tv = findViewById(R.id.untreated);
        tv.setText(R.string.property_notification);
        initData();
        NotifyAdapter adapter = new NotifyAdapter(listData,this);
        ListView listView = findViewById(R.id.notify_lv);
        //将适配器中数据添加到ListView中
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Intent intent = new Intent(this,DetailActivity.class);
//            intent.putExtra("key","notify");
//            startActivity(intent);
        });
        showListContent(untreated_tv,finished_tv,findViewById(R.id.event_type2), findViewById(R.id.event_type1),listData);
        postHttps();
//                HtmlTextView htmlTextView = findViewById(R.id.html_text);
//        String html = "<p>3123123123</p ><div style=\"text-align: center;\" ><img src=\"https://p9-tt.byteimg.com/large/pgc-image/434edf5e7f9248e68af0ad5a217e1064\"alt=\"\"data-height=\"396\"data-width=\"396\"image_type=\"1\"mime_type=\"image/png\"web_uri=\"pgc-image/434edf5e7f9248e68af0ad5a217e1064\"img_width=\"196\"img_height=\"196\"><!----></div><p>12312312313</p >";
//        htmlTextView.setHtml(html,
//                new HtmlHttpImageGetter(htmlTextView));
    }

    private void postHttps(){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", userInfo.getProjectId())
                .add("page", "1")
                .add("rows", "2")
                .build();

        String url = HttpUtils.URL + "notice/list";
        Request request = new Request.Builder()
                .addHeader("openId",userInfo.getUserId())
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ok http", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.body() != null){
                    Log.d("ok http", "onResponse: " + response.body().string());
                }
            }
        });
    }
    private void initData() {
        Notification notify = new Notification("防台风温馨提示","尊敬的业主/住户：\n\t根据【深圳气象】【深圳气象信息快报摘要】第29号台风\"巴蓬\"来袭，同时受其残留云系和高空槽共同影响，深圳29-30日有大雨局部暴雨降水过程 。","2019-12-17",124);
        listData.add(notify);
        notify = new Notification("消杀通知","尊敬的业主/住户：\n\t为有效控制“四害”（老鼠、苍蝇、蚊子蟑螂）的滋生,给大家创造良好生活环境，现通知如下：1. 消杀时间：2019年12月27日上午（周五）","2019-10-17",204);
        listData.add(notify);
        notify = new Notification("催缴通知","尊敬的业主/住户：\n\t小区正常缴费时间为每月的5号——15号，现已24号，服务中心将会28号上午9：00对仍未缴费户型进行停水、停电催费，如有特殊情况请与服务中心联系。请各位业主、住户积极配合小区服务中心的工作，给大家带来不便，敬请谅解","2019-09-11",424);
        listData.add(notify);
        notify = new Notification("停电通知","尊敬的业主/住户：\n\t您们好！因业主反映晚间噪音扰民，多次排查无果，故为尽快解决扰民诉求，小区定于2019年12月26日晚22：00--22：30全面（包括电梯）停电排查，届时不便之处，敬请谅解。","2019-09-11",234);
        listData.add(notify);
        userInfo = getIntent().getParcelableExtra("userInfo");
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
}
