package com.yalianxun.mingshi.home;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.PayRecord;


public class DetailPayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.detail_pay);
        String str;
        PayRecord pr = getIntent().getParcelableExtra("pr");

        TextView tv1 = findViewById(R.id.pay_date_month);
        TextView tv2 = findViewById(R.id.pay_date_year);
        TextView tv3 = findViewById(R.id.pay_cost);
        TextView tv4 = findViewById(R.id.pay_status);
        if(pr != null){
            tv1.setText(pr.getDateMonth());
            tv2.setText(pr.getDateYear());
            str = "¥" + pr.getFee();
            tv3.setText(str);
            if(pr.isStatus()){
                tv4.setText("已缴费");
                findViewById(R.id.show_pay_detail).setVisibility(View.VISIBLE);
                if(pr.getPayType().equals("5")){
                    ((TextView)findViewById(R.id.pay_type)).setText("缴费方式：支付宝");
                }
                str = "缴费时间："+ pr.getPayTime();
                ((TextView)findViewById(R.id.pay_time)).setText(str);
            }else{
                tv4.setText("未缴费");
                tv1.setTextColor(Color.parseColor("#199ED8"));
                tv2.setTextColor(Color.parseColor("#199ED8"));
                tv3.setTextColor(Color.parseColor("#199ED8"));
                tv4.setTextColor(Color.parseColor("#199ED8"));
            }
            ((TextView)findViewById(R.id.area_property)).setText(String.valueOf(pr.getGrossArea()));
            ((TextView)findViewById(R.id.area_repair)).setText(String.valueOf(pr.getGrossArea()));
            setTextViewValue(findViewById(R.id.electricity_charge),String.valueOf(pr.getElectricityCharge()));
            setTextViewValue(findViewById(R.id.water_charge),String.valueOf(pr.getWaterCharge()));
            setTextViewValue(findViewById(R.id.property_charge),String.valueOf(pr.getPropertyCharge()));
            setTextViewValue(findViewById(R.id.total_fee),pr.getFee());
        }


    }
    public void goBack(View view) {
        finish();
    }

    public void setTextViewValue(TextView tv,String str){
        str += "元";
        tv.setText(str);
    }
}
