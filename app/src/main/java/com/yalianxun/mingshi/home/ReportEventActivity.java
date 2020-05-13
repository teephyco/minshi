package com.yalianxun.mingshi.home;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReportEventActivity extends BaseActivity {

    private GridImageAdapter mAdapter;
    private LinearLayout mLinearLayout;
    private TextView tempTv;
    String filePath;
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

            // 进入相册 以下是例子：不需要的api可以不写

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

                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg

                    //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置

                    .compress(true)// 是否压缩

                    .compressQuality(80)// 图片压缩后输出质量 0~ 100

                    .selectionMedia(mAdapter.getData())// 是否传入已选图片

                    .cutOutQuality(90)// 裁剪输出质量 默认100

                    .minimumCompressSize(100)// 小于100kb的图片不压缩


                    .forResult(new OnResultCallbackListener<LocalMedia>() {

                        @Override

                        public void onResult(List<LocalMedia> result) {

                            for (LocalMedia media : result) {
                                filePath = media.getCompressPath();

                            }

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

            //PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());

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

//        postHttps();
        uploadPicture();
        Toast.makeText(this, "提交成功" , Toast.LENGTH_SHORT).show();
    }

    public void showHistory(View view) {
        //go to history
        Intent intent = new Intent(this,EventListActivity.class);
        startActivity(intent);
    }

    private void postHttps(){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", "10010345712344")
                .add("location", "GD")
                .add("houseNum", "1001")
                .add("content", ((EditText) findViewById(R.id.et)).getText().toString())
                .add("picUrl","http://pic1.win4000.com/wallpaper/2020-04-13/5e93d620d04a6.jpg")
                .add("phone", "13923745307")
                .add("reporterName", "xph")
                .add("proprietorName", "xy")
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.108:8088/api/finger/event/createEventReport")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("okhttp", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {

                if(response.body() != null)
                    Log.d("okhttp", "onResponse: " + response.body().toString());
                    //加载真实的数据

            }
        });
    }

    private void uploadPicture(){
        String url = HttpUtils.URL + "common/file/uploadFileToOss";
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d("okhttp","path :" + filePath);
        File file = new File(filePath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("okhttp", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.body() != null)
                    Log.d("okhttp", "onResponse: " + response.body().string());
                //图片上传成功

            }
        });
    }
}
