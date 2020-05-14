package com.yalianxun.mingshi.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    static public String URL = "http://192.168.0.110:8088/api/";

    //判断手机号格式是否正确
    public static boolean checkPhoneNumber(@NotNull String phone){
        String telRegex = "[1][34578]\\d{9}";
        return phone.matches(telRegex);
    }

    public static void updatePassword(String userID, String oldPassword,String newPassword,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = HttpUtils.URL + "finger/auth/updatePwdAfterLogin";
        RequestBody requestBody = new FormBody.Builder()
                .add("userId", userID)
                .add("oldpassword", oldPassword)
                .add("newpassword", newPassword)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
