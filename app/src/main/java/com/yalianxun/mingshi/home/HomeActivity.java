package com.yalianxun.mingshi.home;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.open.OpenDoorActivity;
import com.yalianxun.mingshi.personal.PersonalActivity;

public class HomeActivity extends BaseActivity {
    final String[] locations = new String[]{"南晶小区12栋302", "南晶小区6栋702",
            "南雅豪庭32栋1704", "南雅豪庭32栋1301",
            "南雅豪庭11栋1203", "南雅豪庭10栋1102",};//数据来源后台
    private String CUSTOMER_SERVICE_PHONE = "075528716473";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i("xph","onCreate " + this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("xph","onNewIntent " + this);
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
        if(tag.contains("charge")) {
            startActivity(new Intent(this, PaymentActivity.class));
        }else if(tag.contains("report")){
            startActivity(new Intent(this, ReportEventActivity.class));
        }else if(tag.contains("call")){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+CUSTOMER_SERVICE_PHONE));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(tag.contains("notify")){
            startActivity(new Intent(this, PropertyNotifyActivity.class));
        }else if(tag.contains("develop")){
            startActivity(new Intent(this, PropertyDevelopActivity.class));
        }else if(tag.contains("life")){
            startActivity(new Intent(this, LifeInfoActivity.class));
        }else if(tag.contains("document")){
            startActivity(new Intent(this, PropertyDocumentActivity.class));
        }
    }


    public void showLocations(View view) {

        findViewById(R.id.home_dialog).setVisibility(View.VISIBLE);
        findViewById(R.id.home_dialog_lv).setVisibility(View.VISIBLE);

        ListView lv = findViewById(R.id.home_dialog_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locations);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, v, position, id) -> {
            ((TextView)findViewById(R.id.user_location)).setText(locations[position]);
            CUSTOMER_SERVICE_PHONE = "075522671374";
            hideAlert(v);
        });
    }

    public void hideAlert(View view) {
        findViewById(R.id.home_dialog).setVisibility(View.GONE);
        findViewById(R.id.home_dialog_lv).setVisibility(View.GONE);
    }
}
