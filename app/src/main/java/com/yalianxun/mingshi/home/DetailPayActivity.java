package com.yalianxun.mingshi.home;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.MonthFee;
import com.yalianxun.mingshi.beans.PayRecord;


public class DetailPayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.detail_pay);
        String str;
        MonthFee pr = getIntent().getParcelableExtra("pr");

        TextView tv1 = findViewById(R.id.pay_date_month);
        TextView tv2 = findViewById(R.id.pay_date_year);
        TextView tv3 = findViewById(R.id.pay_cost);
        TextView tv4 = findViewById(R.id.pay_status);
        TextView myLocation = findViewById(R.id.pay_location);
        TextView userInfo = findViewById(R.id.pay_user_info);
        if(pr != null){
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
                tv4.setText("未缴费");
                tv1.setTextColor(Color.parseColor("#199ED8"));
                tv2.setTextColor(Color.parseColor("#199ED8"));
                tv3.setTextColor(Color.parseColor("#199ED8"));
                tv4.setTextColor(Color.parseColor("#199ED8"));
            }
            ((TextView)findViewById(R.id.area_property)).setText(pr.getArrears());
            ((TextView)findViewById(R.id.area_repair)).setText(pr.getArrears());
            setTextViewValue(findViewById(R.id.electricity_charge),pr.getArrears());
            setTextViewValue(findViewById(R.id.water_charge),pr.getArrears());
            setTextViewValue(findViewById(R.id.property_charge),pr.getArrears());
            setTextViewValue(findViewById(R.id.total_fee),pr.getAmountTotal());
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
