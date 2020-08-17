package com.yalianxun.mingshi.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    static public String URL = "http://192.168.0.124:8083/api/";
//    http://124.71.113.203:8000/app/login

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
        String url = HttpUtils.URL + "user/login";
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
//        Log.d("http","path :" + filePath);
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
                if(response.body() != null){
//                   Log.d("http", "onResponse: " + response.body().string());
                    String URL = "";
                    try {
                        //图片上传成功
                        JSONObject jsonObjectALL = new JSONObject(response.body().string());
                        if(jsonObjectALL.optString("code","").equals("200"))
                            URL = jsonObjectALL.optString("data","");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        URL = "数据异常";
                    }
                    listener.onNetResponseSuccess(URL);
                }

            }
        });
    }


    public static void submitEvent(String content,OnNetResponseListener listener){
        String url = HttpUtils.URL + "report/save";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", "10010345712344")
                .add("location", "GD")
                .add("houseNum", "1001")
                .add("content", content)
                .add("picUrl","http://pic1.win4000.com/wallpaper/2020-04-13/5e93d620d04a6.jpg")
                .add("phone", "13923745307")
                .add("reporterName", "xph")
                .add("proprietorName", "xy")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
                listener.onNetResponseError("提交失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {

                if(response.body() != null){
                    try {
                        JSONObject jsonObjectALL = new JSONObject(response.body().string());
                        if(jsonObjectALL.optString("code","").equals("200"))
                            listener.onNetResponseError("报事成功");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        listener.onNetResponseError("网络异常");
                    }
                }else
                    listener.onNetResponseError("网络异常");

            }
        });
    }

    public static void getEventReportList(String projectId, String houseNum,String phone,OnNetResponseListener listener){
        String url = URL + "finger/event/getEventReportList?projectId=" + projectId +"&houseNum=" + houseNum + "&phone=" + phone;
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if(response.body() != null){
                        try {
                            String str = response.body().string();
                            JSONObject jsonObjectALL = new JSONObject(str);
                            JSONArray jsonArray = jsonObjectALL.getJSONArray("dataList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // JSON数组里面的具体-JSON对象
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String content = jsonObject.optString("content", "");
                                long reportTime = jsonObject.optLong("reportTime", 0);
                                String picUrl = jsonObject.optString("picUrl", "");

                                // 日志打印结果：
                                String timestamp = DateUtil.getTime(reportTime,1);
                                Log.d("http", "解析的结果：content" + content + " reportTime: " + timestamp + " picUrl:" + picUrl);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                } catch (IOException e) {
                    Log.d("ok http","Fail reason : "+ e.getMessage());
                    e.printStackTrace();
                    listener.onNetResponseError("网络异常");
                }
            }
        }).start();
    }

    public static void getMonthFeeDetail(String projectID,String houseNum,String date,String openId,OnNetResponseListener listener){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", projectID)
                .add("houseNum", houseNum)
                .add("yearofmonth",date)
                .build();
        String url = URL + "finger/fee/getMonthFeeItemList";
        Request request = new Request.Builder()
                .addHeader("openId",openId)
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.body() != null)
                    Log.d("http", "success: " + response.body().string());
            }
        });
    }

    public interface OnNetResponseListener {

        void onNetResponseError(String msg);

        void onNetResponseSuccess(String string);
    }

}
