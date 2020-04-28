package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.EventAdapter;
import com.yalianxun.mingshi.beans.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends BaseActivity {
    List<Event> unsettledData = new ArrayList<>();
    List<Event> settledData = new ArrayList<>();
    EventAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.event_list);
        initData();
        adapter = new EventAdapter(unsettledData,this);
        listView = findViewById(R.id.event_list_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,EventDetailActivity.class);
            Event event = unsettledData.get(position);
            intent.putExtra("event",event);
            startActivity(intent);
        });
    }
    private void initData() {
//        for (int i=0;i<9;i++){
//            Event notify = new Event(i+1,"",0,"","","","");
//            listData.add(notify);
//        }
        Event event = new Event(0,"2019-06-07 16:48:25",0,"公区报修",
                "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                "","");
        unsettledData.add(event);
        event = new Event(5,"2019-06-07 16:48:25",1,"公区报修",
                "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                "2019-06-12 16:48:25","");
        unsettledData.add(event);
        event = new Event(2,"2019-06-07 16:48:25",1,"家庭维修",
                "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                "2019-06-12 16:48:25","");
        unsettledData.add(event);
        for(int i = 0;i < 7; i++){
            event = new Event(6,"2019-06-07 16:48:25",1,"家庭维修",
                    "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                    "2019-06-12 16:48:25","");
            unsettledData.add(event);
        }
        for(int i = 0;i < 9; i++){
            event = new Event(i,"2019-06-07 16:48:25",2,"家庭维修",
                    "腾讯吐个槽进行产品评测,而腾讯吐个槽是一款专注于用户反馈的产品,前身是腾讯内部最常用的用户反馈产品",
                    "2019-06-12 16:48:25","2019-07-12 15:48:25");
            settledData.add(event);
        }


    }
    public void goBack(View view) {
        finish();
    }

    public void switchList(View view) {
        if(view.findViewById(R.id.event_type1) != null){
            findViewById(R.id.event_type2).setVisibility(View.INVISIBLE);
            findViewById(R.id.event_type1).setVisibility(View.VISIBLE);
            adapter = new EventAdapter(unsettledData,this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, v, position, id) -> {
                Intent intent = new Intent(this,EventDetailActivity.class);
                Event event = unsettledData.get(position);
                intent.putExtra("event",event);
                startActivity(intent);
            });
        }else {
            findViewById(R.id.event_type2).setVisibility(View.VISIBLE);
            findViewById(R.id.event_type1).setVisibility(View.INVISIBLE);
            adapter = new EventAdapter(settledData,this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, v, position, id) -> {
                Intent intent = new Intent(this,EventDetailActivity.class);
                Event event = settledData.get(position);
                intent.putExtra("event",event);
                startActivity(intent);
            });
        }
    }
}
