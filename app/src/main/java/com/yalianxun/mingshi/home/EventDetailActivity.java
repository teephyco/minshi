package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.DownloadImageAdapter;
import com.yalianxun.mingshi.beans.Event;
import com.yalianxun.mingshi.beans.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends BaseActivity {
    private List<ImageInfo> imageInfoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        TextView tv = findViewById(R.id.av_title);
        tv.setText("报事详情");
        Intent intent = getIntent();
        Event event = intent.getParcelableExtra("event");
        if (event != null){
            if(event.getStatus() == 0){
                ((TextView)findViewById(R.id.ed_status)).setText("未处理");
                findViewById(R.id.ed_bottom).setVisibility(View.VISIBLE);
            }else if(event.getStatus() == 1){
                ((TextView)findViewById(R.id.ed_status)).setText("处理中");
                findViewById(R.id.feedback_layout).setVisibility(View.VISIBLE);
            }else if(event.getStatus() == 2){
                ((TextView)findViewById(R.id.ed_status)).setText("已完成");
                findViewById(R.id.feedback_layout).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.ed_feedback)).setText(event.getFeedback());
            }
            ((TextView)findViewById(R.id.ed_timestamp)).setText(event.getStartTime());
            ((TextView)findViewById(R.id.ed_event_type)).setText(event.getCurrentStatus());
            ((TextView)findViewById(R.id.ed_content)).setText(event.getContent());
            if(event.getPictureNum() >0){
                //加载图片
                findViewById(R.id.ed_show_picture).setVisibility(View.VISIBLE);

                for(int i = 0;i<event.getPictureNum();i++){
                    ImageInfo info = new ImageInfo("","");
                    imageInfoList.add(info);
                }
                DownloadImageAdapter adapter = new DownloadImageAdapter(R.layout.gv_image_item,imageInfoList,this);
                GridView gv = findViewById(R.id.ed_gv);
                gv.setAdapter(adapter);
            }
            String location =SP_MANAGER.getValue("buildingName","") + SP_MANAGER.getValue("houseNum","");;
            ((TextView)findViewById(R.id.ed_location)).setText(location);
            ImageView iv1 = findViewById(R.id.ed_pic1);
            ImageView iv2 = findViewById(R.id.ed_pic2);
            ImageView iv3 = findViewById(R.id.ed_pic3);
            if(!event.getImageOne().equals("")){
                findViewById(R.id.ed_show_picture).setVisibility(View.VISIBLE);
                loadPicture(iv1,event.getImageOne());
                loadPicture(iv2,event.getImageTwo());
                loadPicture(iv3,event.getImageThree());
            }
        }

    }

    private void loadPicture(ImageView imageView,String url){
        if(url.equals("")){
            imageView.setVisibility(View.INVISIBLE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.placeholder_pic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

    }
    public void goBack(View view) {
        finish();
    }
}
