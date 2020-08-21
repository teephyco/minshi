package com.yalianxun.mingshi.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.itheima.library.PhotoView;
import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.DownloadImageAdapter;
import com.yalianxun.mingshi.beans.ImageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommonDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_detail);
        changStatusIconColor(true);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(getIntent().getStringExtra("title"));
//        tv = findViewById(R.id.cd_title);
//        tv.setText(getIntent().getStringExtra("title"));
//        List<ImageInfo> imageInfoList = new ArrayList<>();
//
//        if(Objects.equals(getIntent().getStringExtra("title"), "长广深物业营业执照")){
//            ImageInfo info = new ImageInfo("drawable"+R.drawable.license,"");
//            imageInfoList.add(info);
//        }else if(Objects.equals(getIntent().getStringExtra("title"), "施工许可证")){
//            ImageInfo info = new ImageInfo("drawable"+R.drawable.certificate,"");
//            imageInfoList.add(info);
//        }else {
//            ImageInfo info = new ImageInfo("drawable"+R.drawable.contract_1,"");
//            imageInfoList.add(info);
//            info = new ImageInfo("drawable"+R.drawable.contract_2,"");
//            imageInfoList.add(info);
//            info = new ImageInfo("drawable"+R.drawable.contract_3,"");
//            imageInfoList.add(info);
//            info = new ImageInfo("drawable"+R.drawable.contract4,"");
//            imageInfoList.add(info);
//        }
//
//        DownloadImageAdapter adapter = new DownloadImageAdapter(R.layout.item_document,imageInfoList,this);
//        ListView lv = findViewById(R.id.cd_lv);
//        lv.setAdapter(adapter);
        String pictureUrl = getIntent().getStringExtra("pictureUrl");
        PhotoView imageView = findViewById(R.id.detail_iv);
        imageView.enable();
        Glide.with(this)
                .load(pictureUrl)
                .placeholder(R.drawable.placeholder_pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void goBack(View view) {
        finish();
    }
}
