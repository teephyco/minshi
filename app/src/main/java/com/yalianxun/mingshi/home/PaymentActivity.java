package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.PayAdapter;
import com.yalianxun.mingshi.beans.MonthFee;
import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.JsonUtil;
import com.yalianxun.mingshi.view.LoadMoreListView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

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

public class PaymentActivity extends BaseActivity {

    private List<MonthFee> list = new ArrayList<>();
    private LoadMoreListView listView;
    private PayAdapter adapter;
    private LinearLayout pay_l;
    private LinearLayout progress_l;
    private ProgressBar progressBar;
    private UserInfo userInfo;
    private TextView loadTv;
    private TextView unpaidChargeTV;
    private TextView accountBalanceTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.cost_of_property);
        initData();
        listView = findViewById(R.id.pay_lv);
        //将适配器中数据添加到ListView中

    }
    private void initData(){
        pay_l = findViewById(R.id.pay_layout);
        progress_l = findViewById(R.id.pay_progress_layout);
        progressBar = findViewById(R.id.pay_progress);
        loadTv = findViewById(R.id.pay_load_state);
        unpaidChargeTV = findViewById(R.id.unpaidCharge);
        accountBalanceTV = findViewById(R.id.accountBalance);
        userInfo = getIntent().getParcelableExtra("userInfo");
        postHttps("finger/fee/getBalanceAndNotPay",false);
    }
    private void postHttps(String api,boolean finish){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", userInfo.getProjectId())
                .add("houseNum", userInfo.getHouseNum())
                .build();
        String url = HttpUtils.URL + api;
        Request request = new Request.Builder()
                .addHeader("openId",userInfo.getUserId())
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ok http", "onFailure: " + e.getMessage());
                runOnUiThread(() -> showLoading());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.body() != null){
                    //Log.d("ok http", "onResponse: " + response.body().string());
                    String str = response.body().string();
                    //加载真实的数据
                    if (finish){
                        runOnUiThread(() -> loadData(str));
                    }else {
                        if(str.contains("success")){
                            runOnUiThread(() -> updateBalanceAndNotPay(str));
                        }
                    }
                }
            }
        });
    }



    public void goBack(View view) {
        finish();
    }

    public void payInfo(View view) {
        Toast.makeText(this, "支付功能暂不支持", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String response){
        if(response.contains("success")){
            String currentLocation = userInfo.getProjectName() + userInfo.getBuildingName() + userInfo.getHouseNum().substring(4);
            String userName = userInfo.getName();
            String mobile = userInfo.getPhone();
            list = JsonUtil.getJsonUtil().getMonthFeeList(response,currentLocation,userName,mobile);
        }
        adapter = new PayAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,DetailPayActivity.class);
            MonthFee temp = list.get(position);
            intent.putExtra("userInfo",userInfo);
            intent.putExtra("pr",temp);
            startActivity(intent);
        });
        listView.setOnLoadMoreListener(this::loadMore);
        progress_l.setVisibility(View.GONE);
        pay_l.setVisibility(View.VISIBLE);
    }

    private void showLoading(){
        loadTv.setText("加载失败");
        progressBar.setVisibility(View.GONE);
    }

    private void loadMore(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                    listView.setLoadCompleted();
                });
            }
        }.start();
    }

    private void updateBalanceAndNotPay(String response){
        JSONObject jsonObjectALL;
        try {
            jsonObjectALL = new JSONObject(response);
            JSONObject jsonData = jsonObjectALL.getJSONObject("data");
            String str = jsonData.optString("balance", "0");
            if(str.equals("null")){
                accountBalanceTV.setText("0");
            }else {
                accountBalanceTV.setText(str);
            }
            str = jsonData.optString("notPayMoney", "0");
            if(str.equals("null")){
                unpaidChargeTV.setText("0");
            }else {
                unpaidChargeTV.setText(str);
            }
            postHttps("finger/fee/getMonthFeeSumList",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
