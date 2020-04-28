package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.PayAdapter;
import com.yalianxun.mingshi.beans.PayRecord;

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

public class PaymentActivity extends BaseActivity {

    private List<PayRecord> listData = new ArrayList<>();
    private ListView listView;
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

//        getHttps();
        postHttps();
//        Log.d("xph","start 111111111");
//        for (int i=0;i<15;i++){
//            PayRecord pr = new PayRecord("0"+(i+1),"","","","","",true);
//            listData.add(pr);
//        }
//        Log.d("xph","start 333333 " + listData.size());
    }
    private void postHttps(){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", "xiaopuhua")
                .add("houseNum", "1001")
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.105:8088/api/finger/fee/getMonthFeeSumList")
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
                    //Log.d("okhttp", "onResponse: " + response.body().string());
                    //加载真实的数据
                    runOnUiThread(() -> loadData());

            }
        });
    }

    private void getHttps(){
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder()
                .url("http://192.168.1.103:8088/api/finger/device/listFamilyDevices?projectId=sfaas&houseNum=1001&custGlobalId=sddfssd")
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    Log.d("okhttp",response.body().string()+"");

                } catch (IOException e) {
                    Log.d("okhttp","Fail reason : "+ e.getMessage());

                    e.printStackTrace();

                }
            }
        }).start();
    }

    public void goBack(View view) {
        finish();
    }

    public void payInfo(View view) {
        Toast.makeText(this, "支付功能暂不支持", Toast.LENGTH_SHORT).show();
    }

    private void loadData(){
        double[] var = new double[9];
        var[0] = 2010.7d;//unpaidCharge
        var[1] = 6480.1d;//accountBalance
        var[2] = 30d;//waterCharge
        var[3] = 108d;//electricityCharge
        var[4] = 68d;//gasCharge
        var[5] = 160d;//propertyCharge
        var[6] = 300d;//parkingCharge
        var[7] = 6.7d;//lateFeeNotPay
        var[8] = 110d;//grossArea
        ((TextView)findViewById(R.id.unpaidCharge)).setText(String.valueOf(var[0]));
        ((TextView)findViewById(R.id.accountBalance)).setText(String.valueOf(var[1]));
        PayRecord pr = new PayRecord("4","2020","","","","2010.70",false,var,"","");
        listData.add(pr);
        for(int i=3;i>0;i--){
            PayRecord pr1 = new PayRecord("" + i,"2020","","","","1980.00",true,var,"5","2020-"+i+"-22 15:47:48");
            listData.add(pr1);
        }

        for(int i=12;i>5;i--){
            PayRecord pr2 = new PayRecord("" + i,"2019","","","","1880.00",true,var,"5","2019-"+i+"-22 15:47:48");
            listData.add(pr2);
        }
        PayAdapter adapter = new PayAdapter(listData, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,DetailPayActivity.class);
            PayRecord temp = listData.get(position);
            intent.putExtra("pr",temp);
            startActivity(intent);
        });
    }
}
