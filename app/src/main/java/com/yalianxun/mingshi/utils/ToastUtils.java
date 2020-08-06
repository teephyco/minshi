package com.yalianxun.mingshi.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yalianxun.mingshi.R;

public class ToastUtils {
    public static void showTextToast(Context context, String message, int duration){
        @SuppressLint("InflateParams") View toastView= LayoutInflater.from(context).inflate(R.layout.toast_view,null);
        TextView text = (TextView) toastView.findViewById(R.id.content);
        text.setText(message);
        Toast toast=new Toast(context);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(duration);
        toast.setView(toastView);
        toast.show();
    }
    public static void showTextToast(Context context, String message){
        @SuppressLint("InflateParams") View toastView= LayoutInflater.from(context).inflate(R.layout.toast_view,null);
        TextView text = (TextView) toastView.findViewById(R.id.content);
        text.setText(message);
        Toast toast=new Toast(context);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }
}
