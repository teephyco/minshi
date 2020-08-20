package com.yalianxun.mingshi.home;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.Event;
import com.yalianxun.mingshi.beans.FeeBean;
import com.yalianxun.mingshi.beans.MonthFee;
import com.yalianxun.mingshi.beans.PayRecord;
import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.utils.HttpUtils;
import com.yalianxun.mingshi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailPayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.detail_pay);
        String str;
        String date = "202010";
        MonthFee pr = getIntent().getParcelableExtra("pr");

        TextView tv1 = findViewById(R.id.pay_date_month);
        TextView tv2 = findViewById(R.id.pay_date_year);
        TextView tv3 = findViewById(R.id.pay_cost);
        TextView tv4 = findViewById(R.id.pay_status);
        TextView myLocation = findViewById(R.id.pay_location);
        TextView userInfo = findViewById(R.id.pay_user_info);
        if(pr != null){
            date = pr.getDateYear() + pr.getDateMonth();
            tv1.setText(pr.getDateMonth());
            tv2.setText(pr.getDateYear());
            myLocation.setText(pr.getLocation());
            String user = pr.getName() + " " + pr.getPhone();
            userInfo.setText(user);
            str = "¥" + pr.getAmountTotal();
            tv3.setText(str);
            if(pr.getAmountNo().equals("0.00")){
                tv4.setText("已缴费");
                findViewById(R.id.show_pay_detail).setVisibility(View.VISIBLE);
//                if(pr.getPayType().equals("5")){
//                    ((TextView)findViewById(R.id.pay_type)).setText("缴费方式：支付宝");
//                }
//                str = "缴费时间："+ pr.getPayTime();
//                ((TextView)findViewById(R.id.pay_time)).setText(str);
            }else{
                tv4.setText("待缴费");
                tv1.setTextColor(Color.parseColor("#1677ff"));
                tv2.setTextColor(Color.parseColor("#1677ff"));
                tv3.setTextColor(Color.parseColor("#1677ff"));
                tv4.setTextColor(Color.parseColor("#ff3100"));
            }
            setTextViewValue(findViewById(R.id.total_fee),pr.getAmountTotal());
        }
        UserInfo user = getIntent().getParcelableExtra("userInfo");
        if(user != null)
            HttpUtils.getMonthFeeDetail(user.getProjectId(), user.getHouseNum(), date, user.getUserId(), new HttpUtils.OnNetResponseListener() {
                @Override
                public void onNetResponseError(String msg) {
                    runOnUiThread(()-> {
                        ToastUtils.showTextToast(getContext(),msg);
//                        findViewById(R.id.shadow_fl).setVisibility(View.GONE);
                    });
                }

                @Override
                public void onNetResponseSuccess(String string) {
                    runOnUiThread(()-> {
                        loadDetailData(string);
                    });
                }
            });


    }

    public void goBack(View view) {
        finish();
    }

    public void setTextViewValue(TextView tv,String str){
        str += "元";
        tv.setText(str);
    }

    private Context getContext(){
        return this;
    }

    private void loadDetailData(String value){
        try {
            List<FeeBean> allData = new ArrayList<>();
            JSONObject jsonObjectALL = new JSONObject(value);
            JSONArray jsonArray = jsonObjectALL.getJSONArray("dataList");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FeeBean feeBean = new FeeBean();
                feeBean.setFeeTypeName(jsonObject.optString("feeTypeName",""));
                feeBean.setTotalFee(jsonObject.optString("totalFee","0"));
                feeBean.setUsageAmount(jsonObject.optString("usageAmount","0"));
                feeBean.setUnitPrice(jsonObject.optString("unitPrice","0"));
                feeBean.setStatus(jsonObject.optString("status","1"));
                allData.add(feeBean);
            }

            refreshView(allData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshView(List<FeeBean> data){
        for (FeeBean feeBean : data) {
            if (feeBean.getFeeTypeName().contains("电费")){
                ((TextView)findViewById(R.id.electricity_usage_amount)).setText(feeBean.getUsageAmount());
                setTextViewValue(findViewById(R.id.electricity_charge),feeBean.getTotalFee());
                String unitPrice = feeBean.getUnitPrice() + "元/度";
                ((TextView)findViewById(R.id.electricity_unit_price)).setText(unitPrice);
            }
            if (feeBean.getFeeTypeName().contains("水费")){
                ((TextView)findViewById(R.id.water_usage_amount)).setText(feeBean.getUsageAmount());
                setTextViewValue(findViewById(R.id.water_charge),feeBean.getTotalFee());
                String unitPrice = feeBean.getUnitPrice() + "元/立方";
                ((TextView)findViewById(R.id.water_unit_price)).setText(unitPrice);
            }
            if (feeBean.getFeeTypeName().contains("物业管理费")){
                ((TextView)findViewById(R.id.area_property)).setText(feeBean.getUsageAmount());
                setTextViewValue(findViewById(R.id.property_charge),feeBean.getTotalFee());
                String unitPrice = feeBean.getUnitPrice() + "元/平方";
                ((TextView)findViewById(R.id.property_unit_price)).setText(unitPrice);
            }
            if (feeBean.getFeeTypeName().contains("本体维修基金")){
                ((TextView)findViewById(R.id.area_repair)).setText(feeBean.getUsageAmount());
                setTextViewValue(findViewById(R.id.repair_total_fee),feeBean.getTotalFee());
                String unitPrice = feeBean.getUnitPrice() + "元/平方";
                ((TextView)findViewById(R.id.repair_unit_price)).setText(unitPrice);
            }
        }
    }
}
