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
//            Intent intent = new Intent(this,DetailActivity.class);
//            intent.putExtra("key","develop");
//            startActivity(intent);
        });
    }

    private void initData() {
        Notification notify = new Notification("我们常吃的油有什么区别，10种食用油营养大公开！","","2019-08-27",121,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590135975305&di=865f12157e5ac378f6f2ee52a84113fd&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180531%2F5842020df9094fe3ad0fc64a3f2079ec.jpeg");
        listData.add(notify);
        notify = new Notification("海鲜味道怎么选？颜色，气味味道，缺一不可","","2019-03-27",77,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590136058638&di=8cd1e7590b64c29c8673998580ec9768&imgtype=0&src=http%3A%2F%2Fd.ifengimg.com%2Fw600%2Fe0.ifengimg.com%2F09%2F2018%2F1225%2FC98CBA8A2DC97A529B40F695DBD8B4A36337DCF8_size92_w790_h525.jpeg");
        listData.add(notify);
    }
    public void goBack(View view) {
        finish();
    }
}
