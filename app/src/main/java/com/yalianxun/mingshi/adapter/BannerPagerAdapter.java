package com.yalianxun.mingshi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.ImageInfo;
import com.yalianxun.mingshi.home.HomePageActivity;


import java.util.ArrayList;

public class BannerPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ImageInfo> dataList;
    private Handler handler;
    private OnItemClickListener listener;
    private boolean isMove;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public BannerPagerAdapter(Context context, ArrayList<ImageInfo> dataList, Handler handler) {
        this.context = context;
        this.dataList = dataList;
        this.handler = handler;
    }
    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //getView
        View view = View.inflate(context, R.layout.vp_image_item, null);

        final ImageView imageView = view.findViewById(R.id.vp_image);
        // 对ImageView设置触摸监听
        imageView.setOnTouchListener((view1, motionEvent) -> {

            switch (motionEvent.getAction()) {
                // 按下
                case MotionEvent.ACTION_DOWN:
                    // 取消handler任务
                    isMove = false;
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_MOVE:
                    isMove = true;
                    Log.d("xph"," move ");
                    break;
                case MotionEvent.ACTION_UP:
                    if(!isMove){
                        listener.onItemClick("");
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    //重新执行handler轮播任务
                    handler.sendEmptyMessageDelayed(HomePageActivity.AUTO_SCROLL,
                            2000);
                    break;

                default:
                    break;
            }
            return true;
        });
        ImageInfo imageInfo = dataList.get(position);
        if(imageInfo != null){
            Glide.with(context)
                    .load(imageInfo.getResID())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_pic)
                    .into(imageView);
        }

        //添加到容器中
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface OnItemClickListener {
        void onItemClick(String value);
    }
}
