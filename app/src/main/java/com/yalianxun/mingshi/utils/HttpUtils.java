package com.yalianxun.mingshi.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.yalianxun.mingshi.beans.UserInfo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    static public String URL = "https://szaucent.com/api/";

    //判断手机号格式是否正确
    public static boolean checkPhoneNumber(@NotNull String phone){
        String telRegex = "[1][34578]\\d{9}";
        return phone.matches(telRegex);
    }

    public static void updatePassword(String userID, String oldPassword,String newPassword,String openId,OnNetResponseListener listener){
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = HttpUtils.URL + "finger/auth/updatePwdAfterLogin";
        RequestBody requestBody = new FormBody.Builder()
                .add("userId", userID)
                .add("oldpassword", oldPassword)
                .add("newpassword", newPassword)
                .build();
        Request request = new Request.Builder()
                .addHeader("openId",openId)
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
                    try {
                        JSONObject jsonObjectALL = new JSONObject(string);
                        if(jsonObjectALL.optString("code","").equals("200"))
                            listener.onNetResponseSuccess(string);
                        else if(jsonObjectALL.optString("code","").equals("401")){
                            listener.onNetResponseSuccess("登录已过期，请重新登录");
                        }else {
                            listener.onNetResponseSuccess("修改失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onNetResponseSuccess("修改失败");
                    }
                }else {
                    listener.onNetResponseError("修改失败");
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

    public static void uploadPicture(String filePath,String openId,OnNetResponseListener listener){
        String url = HttpUtils.URL + "common/file/uploadFileToOss";
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder()
                .addHeader("openId",openId)
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
                    String URL = "";
                    try {
                        //图片上传成功
                        JSONObject jsonObjectALL = new JSONObject(response.body().string());
                        if(jsonObjectALL.optString("code","").equals("200"))
                            URL = jsonObjectALL.optString("data","");
                        else if(jsonObjectALL.optString("code","").equals("401"))
                            URL = "登录已过期，请重新登录";
                        else
                            URL = "数据异常";
                    } catch (JSONException e) {
                        e.printStackTrace();
                        URL = "数据异常";
                    }
                    listener.onNetResponseSuccess(URL);
                }

            }
        });
    }


    public static void submitEvent(String content, String title,UserInfo userInfo, ArrayList<String> list, OnNetResponseListener listener){
        String url = HttpUtils.URL + "report/save";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", userInfo.getProjectId())
                .add("houseNum", userInfo.getHouseNum())
                .add("title",title)
                .add("content", content)
                .add("status","1")
                .add("imageOne",list.size()>0?list.get(0):"")
                .add("imageTwo",list.size()>1?list.get(1):"")
                .add("imageThree",list.size()>2?list.get(2):"")
                .add("creatorId",userInfo.getName())
                .build();

        Request request = new Request.Builder()
                .addHeader("openId",userInfo.getUserId())
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
                            listener.onNetResponseSuccess("报事成功");
                        else if(jsonObjectALL.optString("code","").equals("401")){
                            listener.onNetResponseError("登录已过期，请重新登录");
                        }else {
                            listener.onNetResponseError("报事失败");
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        listener.onNetResponseError("网络异常");
                    }
                }else
                    listener.onNetResponseError("网络异常");

            }
        });
    }

    public static void getReportList(String projectId,String houseNum,String page, String rows,String openId,OnNetResponseListener listener){
        String url = HttpUtils.URL + "report/list";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", projectId)
                .add("houseNum", houseNum)
                .add("page", page)
                .add("rows", rows)
                .build();

        Request request = new Request.Builder()
                .addHeader("openId",openId)
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
                listener.onNetResponseError("获取列表失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {

                if(response.body() != null) {
                    try {
                        String str = response.body().string();
                        JSONObject jsonObjectALL = new JSONObject(str);
                        if(jsonObjectALL.optString("code","").equals("200")){
                            listener.onNetResponseSuccess(str);
                        }else if(jsonObjectALL.optString("code","").equals("401")){
                            listener.onNetResponseError("登录已过期，请重新登录");
                        }else {
                            listener.onNetResponseError("获取列表失败");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        listener.onNetResponseError("获取列表失败");
                    }
                }

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
                            JSONObject data = jsonObjectALL.optJSONObject("data");
                            assert data != null;
                            JSONArray jsonArray = data.getJSONArray("dataList");
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
                listener.onNetResponseError("加载详情失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.body() != null){
                    try {
                        String str = response.body().string();
                        JSONObject jsonObjectALL = new JSONObject(str);
                        if(jsonObjectALL.optString("code","").equals("200")){
                            listener.onNetResponseSuccess(str);
                        }else {
                            listener.onNetResponseError("加载详情失败");
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        listener.onNetResponseError("加载详情失败");
                    }
                }
            }
        });
    }

    public static void getDocumentList(String projectID,String openId,OnNetResponseListener listener){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("projectId", projectID)
                .build();
        String url = URL + "user/projectExtra";
        Request request = new Request.Builder()
                .addHeader("openId",openId)
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("http", "onFailure: " + e.getMessage());
                listener.onNetResponseError("获取物业档案失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.body() != null){
                    try {
                        String responseValue = response.body().string();
                        JSONObject jsonObjectALL = new JSONObject(responseValue);
                        if(jsonObjectALL.optString("code","").equals("200"))
                            listener.onNetResponseSuccess(responseValue);
                        else if(jsonObjectALL.optString("code","").equals("401")){
                            listener.onNetResponseError("登录已过期，请重新登录");
                        }else {
                            listener.onNetResponseError("获取物业档案失败");
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        listener.onNetResponseError("网络异常");
                    }
                }
            }
        });
    }

    public interface OnNetResponseListener {

        void onNetResponseError(String msg);

        void onNetResponseSuccess(String string);
    }

}
