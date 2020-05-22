package com.yalianxun.mingshi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yalianxun.mingshi.beans.UserInfo;

import java.util.List;

public class SharedPreferencesManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public SharedPreferencesManager(Context context, String name) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);//获取sharedPreference
        editor = sharedPreferences.edit();//生成editor
        editor.apply();
    }


    public void saveLoginData(String response,String mobile,String password){
        List<UserInfo> list = JsonUtil.getJsonUtil().getUserInfoList(response);
        if (list.size()>0){
            UserInfo userInfo = list.get(0);
            editor.putString("name",userInfo.getName());
            editor.putString("userID",userInfo.getUserId());
        }
        editor.putString("mobile",mobile);
        editor.putString("password",password);
        editor.putString("loginResponse",response);
        editor.putBoolean("login",true);
        editor.apply();
    }
    public void putValue(String key, Object value) {

        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }
    public String getValue(String key, String value) {
        return sharedPreferences.getString(key, value);
    }
}
