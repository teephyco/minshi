package com.yalianxun.mingshi.open;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.TraceAdapter;
import com.yalianxun.mingshi.beans.TripRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TripRecordActivity extends BaseActivity {
    private List<TripRecord> listViewData = new ArrayList<>();
    private TextView selectedTv;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    final String[] DOORS = new String[]{"10栋2单元大门", "10栋4单元大门",
            "南雅豪庭东门", "南雅豪庭北门",
            "6栋2单元大门", "2栋5单元大门"};//数据来源后台
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_record);
        TextView tv = findViewById(R.id.av_title);
        tv.setText(R.string.trace_recording);
        selectedTv = findViewById(R.id.selected_date);
        String str = "请选择需要查询的日期";
        selectedTv.setText(str);
        TimePicker tp1 = findViewById(R.id.start_time_pick);
        tp1.setIs24HourView(true);
        tp1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
             public void onTimeChanged(TimePicker view, int hour, int minute) {
                startHour = hour;
                startMinute = minute;
            }
        });
        TimePicker tp2 = findViewById(R.id.end_time_pick);
        tp2.setIs24HourView(true);
        tp2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int minute) {
                endHour = hour;
                endMinute = minute;
            }
        });


        refreshView("","","");
        TraceAdapter adapter = new TraceAdapter(listViewData,this);
        ListView lv = findViewById(R.id.tr_lv);
        lv.setAdapter(adapter);
    }

    public void goBack(View view) {
        finish();
    }


    public void refreshView(String location,String date,String time){

        //从后台获取数据
        TripRecord tr = new TripRecord("15:43:61","王刚","13622382338",0);
        listViewData.add(tr);
        tr = new TripRecord("16:43:61","王刚","13622382338",1);
        listViewData.add(tr);
        tr = new TripRecord("18:43:61","王刚","13622382338",0);
        listViewData.add(tr);
        tr = new TripRecord("21:43:61","王刚","13622382338",1);
        listViewData.add(tr);
        tr = new TripRecord("22:43:61","王刚","13622382338",0);
        listViewData.add(tr);

    }


    public void showCalendar(View view) {
        Calendar c=Calendar.getInstance();
        // 点击“确定”时触发
        DatePickerDialog dpd= new DatePickerDialog(this, (view1, year, monthOfYear, dayOfMonth) -> {

             String str = changeDate(year,monthOfYear+1,dayOfMonth);
             selectedTv.setText(str);
          },c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        dpd.show();
    }

    private String changeDate(int year, int monthOfYear, int dayOfMonth){
        return "" + year +"年" + monthOfYear +"月"+ dayOfMonth +"日";
    }


    public void showMySelector(View view) {
        findViewById(R.id.tr_dialog).setVisibility(View.VISIBLE);
        findViewById(R.id.tr_my_selector).setVisibility(View.VISIBLE);
    }

    public void sure(View view) {
        if(endHour < startHour){
            Toast.makeText(this, "时间选择不对", Toast.LENGTH_SHORT).show();
        }else {
            if (endHour == startHour && endMinute <= startMinute){
                Toast.makeText(this, "时间选择不对", Toast.LENGTH_SHORT).show();
            }else {
                @SuppressLint("DefaultLocale") String str = String.format("%02d", startHour) + ":" +
                                                            String.format("%02d", startMinute) + "-" +
                                                            String.format("%02d", endHour) +":" +
                                                            String.format("%02d", endMinute);
                ((TextView)findViewById(R.id.tr_time_quantum)).setText(str);
            }
        }
        findViewById(R.id.tr_my_selector).setVisibility(View.GONE);
        findViewById(R.id.tr_dialog).setVisibility(View.GONE);
    }


    public void cancel(View view) {
        findViewById(R.id.tr_dialog).setVisibility(View.GONE);
        findViewById(R.id.tr_my_selector).setVisibility(View.GONE);
    }

    public void chooseDoor(View view) {
        findViewById(R.id.tr_dialog).setVisibility(View.VISIBLE);
        findViewById(R.id.tr_dialog_lv).setVisibility(View.VISIBLE);

        ListView lv = findViewById(R.id.tr_dialog_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DOORS);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, v, position, id) -> {
            ((TextView)findViewById(R.id.tr_my_door)).setText(DOORS[position]);
            hideAlert(v);
        });
    }

    public void hideAlert(View view) {
        findViewById(R.id.tr_dialog).setVisibility(View.GONE);
        findViewById(R.id.tr_dialog_lv).setVisibility(View.GONE);
        findViewById(R.id.tr_my_selector).setVisibility(View.GONE);
    }
}
