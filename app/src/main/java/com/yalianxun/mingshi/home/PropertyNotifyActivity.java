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
        for (int i=0;i<15;i++){
            Notification noti = new Notification("0","","",100*(i+1));
            listData.add(noti);
        }
    }

    public void goBack(View view) {
        finish();
    }
}
