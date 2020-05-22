package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.NotifyAdapter;
import com.yalianxun.mingshi.beans.Notification;

import java.util.ArrayList;
import java.util.List;

public class PropertyNotifyActivity extends BaseActivity {
    List<Notification> listData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_notify);
        TextView tv = findViewById(R.id.av_title);
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
    }

    private void initData() {
        Notification notify = new Notification("防台风温馨提示","关于防台风的温馨提示尊敬的业主住户：根据【深圳气象】【深圳气象信息快报摘要】第29号台风\"巴蓬\"来袭，同时受其残留云系和高空槽共同影响，深圳29-30日有大雨局部暴雨降水过程 。","2019-12-17",124);
        listData.add(notify);
        notify = new Notification("消杀通知","消杀通知尊敬的业主住户：为有效控制“四害”（老鼠、苍蝇、蚊子蟑螂）的滋生,给大家创造良好生活环境，现通知如下：1. 消杀时间：2019年12月27日上午（周五）","2019-10-17",204);
        listData.add(notify);
        notify = new Notification("催缴通知","温馨提示尊敬的业主住户：小区正常缴费时间为每月的5号——15号，现已24号，服务中心将会28号上午9：00对仍未缴费户型进行停水、停电催费，如有特殊情况请与服务中心联系。请各位业主、住户积极配合小区服务中心的工作，给大家带来不便，敬请谅解","2019-09-11",424);
        listData.add(notify);
        notify = new Notification("停电通知","温馨提示尊敬的业主/住户：您们好！因业主反映晚间噪音扰民，多次排查无果，故为尽快解决扰民诉求，小区定于2019年12月26日晚22：00--22：30全面（包括电梯）停电排查，届时不便之处，敬请谅解。","2019-09-11",234);
        listData.add(notify);
//        for (int i=0;i<15;i++){
//
//            Notification noti = new Notification("0","","",100*(i+1));
//            listData.add(noti);
//        }
    }

    public void goBack(View view) {
        finish();
    }
}
