package com.yalianxun.mingshi.utils;

import org.jetbrains.annotations.NotNull;

public class HttpUtils {
    static public String URL = "http://192.168.0.110:8088/api/";

    //判断手机号格式是否正确
    public static boolean checkPhoneNumber(@NotNull String phone){
        String telRegex = "[1][34578]\\d{9}";

        return phone.matches(telRegex);

    }
}
