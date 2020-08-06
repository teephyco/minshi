package com.yalianxun.mingshi.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.open.OpenDoorActivity;
import com.yalianxun.mingshi.personal.PersonalActivity;
import com.yalianxun.mingshi.utils.JsonUtil;
import com.yalianxun.mingshi.view.AutoScrollTextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private List<String> locations = new ArrayList<>();
    private List<UserInfo> list;
    private UserInfo userInfo;
    private String CUSTOMER_SERVICE_PHONE = "075528716473";
    private TextView userLocationTV;
    private AutoScrollTextView autoScrollTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView(){
        SharedPreferences sharedPreferences = getSharedPreferences("YLX", Context.MODE_PRIVATE);
        String response = sharedPreferences.getString("loginResponse","");
        UserInfo use = new UserInfo("","","","","","","","","");
        List<UserInfo> array = new ArrayList<>();
        array.add(use);
//        list = JsonUtil.getJsonUtil().getUserInfoList(response);
        list = array;
        for(UserInfo userInfo : list){
            String location = userInfo.getProjectName() + userInfo.getBuildingName() + userInfo.getHouseNum();
            locations.add(location);
        }
        userLocationTV = findViewById(R.id.user_location);
        TextView userTV = findViewById(R.id.user_name);
        userInfo = list.get(0);
        String name = "Hello "+ userInfo.getName();
        userTV.setText(name);
        String currentLocation = userInfo.getProjectName() + userInfo.getBuildingName() + userInfo.getHouseNum();
        userLocationTV.setText(currentLocation);
        autoScrollTextView = findViewById(R.id.first_notify);
        autoScrollTextView.init(getWindowManager());
        autoScrollTextView.startScroll();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void translation(View view) {
        String tag = (String) view.getTag();
        if(tag.contains("self")){
            startActivity(new Intent(this, PersonalActivity.class));
        }else if(tag.contains("openDoor")){
            startActivity(new Intent(this, OpenDoorActivity.class));
        }
    }


    public void goToNext(View view) {
        String tag = (String) view.getTag();
        Intent intent = new Intent();
        if(tag.contains("charge")) {
            intent = new Intent(this, PaymentActivity.class);
        }else if(tag.contains("report")){
            intent = new Intent(this, ReportEventActivity.class);
        }else if(tag.contains("call")){
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+CUSTOMER_SERVICE_PHONE));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }else if(tag.contains("notify")){
            intent = new Intent(this, PropertyNotifyActivity.class);
        }else if(tag.contains("develop")){
            intent = new Intent(this, PropertyDevelopActivity.class);
        }else if(tag.contains("life")){
            intent = new Intent(this, LifeInfoActivity.class);
        }else if(tag.contains("document")){
            intent = new Intent(this, PropertyDocumentActivity.class);
        }
        intent.putExtra("userInfo",userInfo);
        startActivity(intent);
    }


    public void showLocations(View view) {

        findViewById(R.id.home_dialog).setVisibility(View.VISIBLE);
        findViewById(R.id.home_dialog_lv).setVisibility(View.VISIBLE);

        ListView lv = findViewById(R.id.home_dialog_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locations);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, v, position, id) -> {
            userLocationTV.setText(locations.get(position));
            userInfo = list.get(position);
            CUSTOMER_SERVICE_PHONE = "075522671374";
            hideAlert(v);
        });
    }

    public void hideAlert(View view) {
        findViewById(R.id.home_dialog).setVisibility(View.GONE);
        findViewById(R.id.home_dialog_lv).setVisibility(View.GONE);
    }
}
