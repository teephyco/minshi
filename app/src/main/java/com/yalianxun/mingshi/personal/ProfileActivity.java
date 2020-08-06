package com.yalianxun.mingshi.personal;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    private CircleImageView headIv;
    private String picturePath = "";
    private EditText et;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.my_profile);
        headIv = findViewById(R.id.profile_head);
        et = findViewById(R.id.profile_nickname);
        sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        String nickName = sharedPreferences.getString("nickName","");
        if(!nickName.equals("")){
            et.setHint(nickName);
        }
        String path = sharedPreferences.getString("profilePicture","");
        if(!path.equals("")){
            Glide.with(getContext())
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_pic)
                    .into(headIv);
        }
    }

    public void goBack(View view) {
        finish();
    }

    public void chosePicture(View view) {
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
                .compress(false)// 是否压缩
                .enableCrop(true)
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)
                .forResult(new OnResultCallbackListener<LocalMedia>() {

                    @Override

                    public void onResult(List<LocalMedia> result) {

                        for (LocalMedia media : result) {


                            String path = media.getCutPath();
                            Glide.with(getContext())
                                    .load(path)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(headIv);
                            picturePath = path;

                        }

                    }


                    @Override

                    public void onCancel() {

//                        Log.i(TAG, "PictureSelector Cancel");
//                        iv.setImageResource(R.drawable.pic);
                    }

                });
    }

    public void save(View view) {

        Intent intent = new Intent();

        intent.putExtra("path",picturePath);
        Log.i("xph"," picturePath is " + picturePath);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nickName",et.getText().toString());
        if(!picturePath.equals("")){
            editor.putString("profilePicture",picturePath);
        }
        editor.apply();
        setResult(1001,intent);
        finish();
    }

    public Context getContext(){
        return this;
    }
}
