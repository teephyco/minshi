package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.DevelopAdapter;
import com.yalianxun.mingshi.beans.Notification;

import java.util.ArrayList;
import java.util.List;

public class PropertyDevelopActivity extends BaseActivity {
    List<Notification> listData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_develop);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.property_dynamic);
        initData();
        DevelopAdapter adapter = new DevelopAdapter(listData,this);
        ListView listView = findViewById(R.id.develop_lv);
        //将适配器中数据添加到ListView中
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Intent intent = new Intent(this,DetailActivity.class);
//            intent.putExtra("key","develop");
//            startActivity(intent);
        });
    }

    private void initData() {
        Notification notify = new Notification("消杀工作","夏季是有害细菌滋生、蚊虫繁殖的季节，为了减少蚊蝇对住户的影响，有效地降低蚊蝇密度。2月26日上午，锦绣山庄物业管理处组织专人对小区开展全面喷药消杀工作。通过本次消杀，小区的草丛内、阴沟内的蚊蝇明显减少，极大限度地控制了蚊蝇的滋生、病毒害的传播，使小区环境得到了进一步的提升，为居民创造了一个良好、舒适的生活环境，进一步推动了全国文明城市创建工作。","2019-03-27",121,"https://pics6.baidu.com/feed/5243fbf2b2119313350a7141b34343d190238d7c.jpeg?token=3f2a65790e7583d7757328455ff05171&s=A4AA47B04A5057DE0EA9ECB603001010");
        listData.add(notify);
        notify = new Notification("公司党支部成立","为全面贯彻落实习近平新时代中国特色社会主义思想和党的十九大精神，住宅物业旗下子公司深圳市长广深物业管理有限公司党支部正式成立！2019年2月27日下午，在公司党支部举行了支部成立大会暨揭牌仪式。街道党建服务中心主任武俊华同志、南岭村社区党委书记张育彪同志、南岭产业园党委书记李斌同志、集团董事长陈芝明先生及公司全体党员和高层管理人员参加了会议。","2019-03-27",77,"");
        listData.add(notify);

//        for (int i=0;i<15;i++){
//            Notification noti = new Notification("0","","",100*(i+1),"");
//            listData.add(noti);
//        }
    }

    public void goBack(View view) {
        finish();
    }

}
