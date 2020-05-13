package com.yalianxun.mingshi.utils;

import android.util.Log;

import com.yalianxun.mingshi.beans.MonthFee;
import com.yalianxun.mingshi.beans.PayRecord;
import com.yalianxun.mingshi.beans.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static volatile JsonUtil jsonUtil = null;

    private JsonUtil(){}

    public static JsonUtil getJsonUtil(){
        synchronized (JsonUtil.class){
            if(jsonUtil == null){
                jsonUtil = new JsonUtil();
            }
        }
        return jsonUtil;
    }

    public List<UserInfo> getUserInfoList(String response){
        List<UserInfo> list = new ArrayList<>();
        try {
            JSONObject jsonObjectALL = new JSONObject(response);
            JSONObject jsonData = jsonObjectALL.getJSONObject("data");
            JSONArray jsonArray = jsonData.getJSONArray("houseInfo");
            for (int i = 0; i < jsonArray.length(); i++) {
                // JSON数组里面的具体-JSON对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String globalId = jsonData.optString("globalId", "");
                String userId = jsonData.optString("userId", "");
                String name = jsonData.optString("nickName", "");
                String phone = jsonData.optString("mobile", "");
                String houseNum = jsonObject.optString("houseNum", "");
                String projectId = jsonObject.optString("projectId", "");
                String projectName = jsonObject.optString("projectName", "");
                String buildingName = jsonObject.optString("buildingName", "");
                String houseType = jsonObject.optString("houseType", "");
                UserInfo info = new UserInfo(globalId,userId,name,phone,houseNum,projectId,projectName,buildingName,houseType);
                list.add(info);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<MonthFee> getMonthFeeList(String response,String location,String name,String phone){
        List<MonthFee> list = new ArrayList<>();
        try {
            JSONObject jsonObjectALL = new JSONObject(response);
            JSONArray jsonArray = jsonObjectALL.getJSONArray("dataList");
            for (int i = 0; i < jsonArray.length(); i++) {
                // JSON数组里面的具体-JSON对象
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String date = jsonObject.optString("yearofmonth", "");
                String amountTotal = jsonObject.optString("amountTotal", "").equals("null") ? "0.00":jsonObject.optString("amountTotal", "");
                String amountNo = jsonObject.optString("amountNo", "").equals("null") ? "0.00":jsonObject.optString("amountNo", "");
                String arrears = jsonObject.optString("arrears", "").equals("null") ? "0.00":jsonObject.optString("arrears", "");
                String year = date.substring(0,4);
                String month = date.substring(4);
                MonthFee  pr= new MonthFee(month,year,amountTotal,amountNo,arrears,location,name,phone);
                list.add(pr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
