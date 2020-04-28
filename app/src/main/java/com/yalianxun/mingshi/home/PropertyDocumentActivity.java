package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.DownloadImageAdapter;
import com.yalianxun.mingshi.beans.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class PropertyDocumentActivity extends BaseActivity {
    final String[] pictures = new String[]{
            "http://pic1.win4000.com/wallpaper/2020-04-13/5e93d6241579d.jpg",
            "http://pic1.win4000.com/wallpaper/2020-04-14/5e955308843bb.jpg",
            "http://pic1.win4000.com/wallpaper/2020-03-09/5e65bb2136d3d.jpg", };
    final String[] titles = new String[]{
            "长广深物业营业执照",
            "锦绣山庄小区物业服务合同",
            "施工许可证"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_document);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.property_file);
        List<ImageInfo> imageInfoList = new ArrayList<>();

        for(int i = 0; i<pictures.length; i++){
            ImageInfo info = new ImageInfo(pictures[i],titles[i]);
            imageInfoList.add(info);
        }
        DownloadImageAdapter adapter = new DownloadImageAdapter(R.layout.gv_pd_item,imageInfoList,this);
        GridView gv = findViewById(R.id.pd_gv);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,CommonDetailActivity.class);
            intent.putExtra("title",titles[position]);
            startActivity(intent);
        });
    }
    public void goBack(View view) {
        finish();
    }
}
