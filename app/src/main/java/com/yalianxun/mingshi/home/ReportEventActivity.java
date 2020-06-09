package com.yalianxun.mingshi.home;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.GridImageAdapter;
import com.yalianxun.mingshi.utils.FullyGridLayoutManager;
import com.yalianxun.mingshi.utils.GlideCacheEngine;
import com.yalianxun.mingshi.utils.GlideEngine;
import com.yalianxun.mingshi.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReportEventActivity extends BaseActivity {

    private GridImageAdapter mAdapter;
    private LinearLayout mLinearLayout;
    private ArrayList<String> pictureURL = new ArrayList<String>();
    private TextView tempTv;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            clearCache();
        }
        setContentView(R.layout.activity_report);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.my_question);
        initEditTextAndTextView(findViewById(R.id.et),findViewById(R.id.change_tv),200);
        RecyclerView mRecyclerView = findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(getContext(), onAddPicClickListener);
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList") != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
        }
        mAdapter.setSelectMax(9);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void goBack(View view) {
        finish();
    }

    public void selectAction(View view) {
        if(tempTv != null && tempTv != view){
            setTextViewStyle(R.drawable.bolder_unselected,"#000000");
        }
        tempTv = (TextView)view;
        setTextViewStyle(R.drawable.bolder_selected,"#199ED8");
    }

    public void setTextViewStyle(int bolder,String color){
        tempTv.setBackgroundResource(bolder);
        tempTv.setTextColor(Color.parseColor(color));
    }

    protected void initEditTextAndTextView(final EditText et, final TextView tv, final int maxNum){
        et.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @SuppressLint("SetTextI18n")
            public void afterTextChanged(Editable s) {
                int number = s.length();
                tv.setText("" + number);
                selectionStart = et.getSelectionStart();
                selectionEnd = et.getSelectionEnd();
                if (temp.length() > maxNum) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    et.setText(s);
                    et.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            PictureSelector.create(ReportEventActivity.this)

                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()

                    .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项

                    .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用

                    .maxSelectNum(9)// 最大图片选择数量

                    .minSelectNum(1)// 最小选择数量

                    .imageSpanCount(4)// 每行显示个数

                    .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回

                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选

                    .isCamera(true)// 是否显示拍照按钮

                    .isZoomAnim(false)// 图片列表点击 缩放效果 默认true

                    .compress(true)// 是否压缩

                    .compressQuality(80)// 图片压缩后输出质量 0~ 100

                    .selectionMedia(mAdapter.getData())// 是否传入已选图片

                    .cutOutQuality(90)// 裁剪输出质量 默认100

                    .minimumCompressSize(100)// 小于100kb的图片不压缩

                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
//                            for (LocalMedia media : result) {
//                                filePath = media.getCompressPath();
//                            }
                            mAdapter.setList(result);
                            mAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancel() {
                            //Log.i(TAG, "PictureSelector Cancel");

                        }

                    });

        }};

    private void clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PictureFileUtils.deleteAllCacheDirFile(getContext());
        } else {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},

                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }

    }

    public Context getContext() {
        return this;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void commit(View view) {
        //处理提交事件
        if(tempTv == null){
            Toast.makeText(this, "未选择服务类别，请选择，谢谢！" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(((EditText) findViewById(R.id.et)).getText().toString().equals("")){
            Toast.makeText(this, "内容描述不能为空。" , Toast.LENGTH_SHORT).show();
            return;
        }
        findViewById(R.id.shadow_fl).setVisibility(View.VISIBLE);
        List<LocalMedia> list = mAdapter.getData();
        if(list.size()>0){
            for(int i = 0;i < list.size();i++){
                LocalMedia media = list.get(i);
                if(media.getCompressPath()!=null)
                    filePath = media.getCompressPath();
                else
                    filePath = media.getRealPath();
                HttpUtils.uploadPicture(filePath, new HttpUtils.OnNetResponseListener() {
                    @Override
                    public void onNetResponseError(String msg) {
                        runOnUiThread(()-> {
                            Toast.makeText(ReportEventActivity.this,msg,Toast.LENGTH_SHORT).show();
                            findViewById(R.id.shadow_fl).setVisibility(View.GONE);
                        });
                    }
                    @Override
                    public void onNetResponseSuccess(String string) {
                        if(string.contains("http")){
                            pictureURL.add(string);
                            if(pictureURL.size() == mAdapter.getData().size())
                                runOnUiThread(()-> submitMyEvent());
                        }else {
                            runOnUiThread(()-> {
                                Toast.makeText(ReportEventActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
                                findViewById(R.id.shadow_fl).setVisibility(View.GONE);
                            });
                        }

                    }
                });
            }
        }else {
            submitMyEvent();
        }
    }

    public void showHistory(View view) {
        //go to history
        Intent intent = new Intent(this,EventListActivity.class);
        startActivity(intent);
    }

    private void submitMyEvent(){
        HttpUtils.submitEvent(((EditText) findViewById(R.id.et)).getText().toString(), new HttpUtils.OnNetResponseListener() {
            @Override
            public void onNetResponseError(String msg) {
                runOnUiThread(()-> {
                    Toast.makeText(ReportEventActivity.this,msg,Toast.LENGTH_SHORT).show();
                    findViewById(R.id.shadow_fl).setVisibility(View.GONE);
                });
            }

            @Override
            public void onNetResponseSuccess(String string) {
                runOnUiThread(()-> {
                    Toast.makeText(ReportEventActivity.this,string,Toast.LENGTH_SHORT).show();
                    findViewById(R.id.shadow_fl).setVisibility(View.GONE);
                });
            }
        });
    }


}
