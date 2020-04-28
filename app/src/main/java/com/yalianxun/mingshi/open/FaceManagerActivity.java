package com.yalianxun.mingshi.open;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.utils.GlideCacheEngine;
import com.yalianxun.mingshi.utils.GlideEngine;

import java.util.List;

public class FaceManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_manager);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.face_manager);
    }

    public void goBack(View view) {
        finish();
    }


    public void choosePicture(View view) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())
                .maxSelectNum(1)
                .minSelectNum(1)
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .selectionMode(PictureConfig.SINGLE)
                .isCamera(true)// 是否显示拍照按钮
                .compress(true)// 是否压缩
                .enableCrop(true)
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)
                .forResult(new OnResultCallbackListener<LocalMedia>() {

                    @Override

                    public void onResult(List<LocalMedia> result) {

                        for (LocalMedia media : result) {
                            LinearLayout ll = findViewById(R.id.face_manager_default);
                            ll.setVisibility(View.GONE);
                            ll = findViewById(R.id.fm_manager_show);
                            ll.setVisibility(View.VISIBLE);
                            String path = media.getCompressPath();
                            //改变布局 同时上传照片到服务器
                            TextView tv = findViewById(R.id.fm_status);
                            tv.setText("审核中");
                            tv.setTextColor(Color.parseColor("#ff0000"));
                            tv = findViewById(R.id.upload_status);
                            tv.setText("已上传照片");
                            ImageView headIv = findViewById(R.id.fm_picture);
                            Glide.with(getContext())
                                    .load(path)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(headIv);
                            ll = findViewById(R.id.fm_show_reason);
                            ll.setVisibility(View.GONE);
                            ll = findViewById(R.id.fm_show_button);
                            ll.setVisibility(View.GONE);

                        }

                    }


                    @Override

                    public void onCancel() {

                    }

                });
    }


    public void uploadPhoto(View view) {
        choosePicture(view);
    }

    public Context getContext(){
        return this;
    }
}
