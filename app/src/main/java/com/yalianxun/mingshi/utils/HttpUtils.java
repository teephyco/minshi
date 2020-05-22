package com.yalianxun.mingshi.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.yalianxun.mingshi.SettingPasswordActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    static public String URL = "http://192.168.1.100:8088/api/";

    //判断手机号格式是否正确
    public static boolean checkPhoneNumber(@NotNull String phone){
        String telRegex = "[1][34578]\\d{9}";
        return phone.matches(telRegex);
    }

    public static void updatePassword(String userID, String oldPassword,String newPassword,OnNetResponseListener listener){
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

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
                listener.onNetResponseError("网络访问出现异常");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.body() != null){
                    String string = response.body().string();
                    if(string.contains("success")){
                        listener.onNetResponseSuccess(string);
                    }
                }
            }
        });
    }

    public static void gotoLogin(String mobile, String password,String mobileId,String mobileModel,OnNetResponseListener listener){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = HttpUtils.URL + "finger/auth/login";
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile", mobile)
                .add("password", password)
                .add("mobileId", mobileId)
                .add("mobileModel", mobileModel)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
//                String str = e.getMessage();
                listener.onNetResponseError("网络异常");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.body() != null){
                    String string = response.body().string();
                    listener.onNetResponseSuccess(string);
                }
            }
        });
    }

    public static void uploadPicture(String filePath,OnNetResponseListener listener){
        String url = HttpUtils.URL + "common/file/uploadFileToOss";
        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d("http","path :" + filePath);
        File file = new File(filePath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
                listener.onNetResponseError("图片上传失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.body() != null)
                    Log.d("http", "onResponse: " + response.body().string());
                //图片上传成功

            }
        });
    }

    public interface OnNetResponseListener {

        void onNetResponseError(String msg);

        void onNetResponseSuccess(String string);
    }

}
