package com.yalianxun.mingshi.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.LifeInfoAdapter;
import com.yalianxun.mingshi.beans.Notification;

import java.util.ArrayList;
import java.util.List;

public class LifeInfoActivity extends BaseActivity {
    List<Notification> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_info);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.life_information);
        initData();
        LifeInfoAdapter adapter = new LifeInfoAdapter(listData,this);
        ListView listView = findViewById(R.id.live_lv);
        //将适配器中数据添加到ListView中
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra("key","life");
            intent.putExtra("url",listData.get(position).getSourceLink());
            startActivity(intent);
        });
    }

    private void initData() {
        Notification notify = new Notification("舌尖上的七夕：让美食也传递爱与浪漫","","2020-08-20",121,"http://shipin.people.com.cn/NMediaFile/2020/0820/MAIN202008200913164085826383509.jpg");
        notify.setSourceLink("http://shipin.people.com.cn/n1/2020/0820/c85914-31829898.html");
        listData.add(notify);
        notify = new Notification("爱吃发酵湿米面制品？小心米酵菌酸中毒","","2020-08-19",77,"http://shipin.people.com.cn/NMediaFile/2020/0819/MAIN202008190610386858115064550.jpg");
        notify.setSourceLink("http://shipin.people.com.cn/n1/2020/0819/c85914-31827396.html");
        listData.add(notify);
    }
    public void goBack(View view) {
        finish();
    }
}
