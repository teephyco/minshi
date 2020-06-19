package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.YLXCallback;
import com.yalianxun.mingshi.beans.ImageInfo;

import java.util.ArrayList;

public class YLXPagerAdapter extends PagerAdapter {
    private Context context;

    public void setCallback(YLXCallback callback) {
        this.callback = callback;
    }

    private YLXCallback callback;
    private ArrayList<ImageInfo> imageList;

    public YLXPagerAdapter(Context context, ArrayList<ImageInfo> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //getView
        View view = View.inflate(context, R.layout.vp_item, null);

        ImageView imageView = view.findViewById(R.id.vp_iv);
        TextView tv = view.findViewById(R.id.welcome_text);
        ImageInfo info = imageList.get(position);
        if(info != null){
            tv.setText(info.getImgName());
            if(info.getImgPath().equals("1")){
                imageView.setImageResource(R.drawable.welcome1);
            }else if(info.getImgPath().equals("2")){
                imageView.setImageResource(R.drawable.welcome2);
            }else {
                imageView.setImageResource(R.drawable.welcome3);
                view.findViewById(R.id.experience).setVisibility(View.VISIBLE);
                view.findViewById(R.id.experience).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.callback();
                    }
                });
            }
        }

        //添加到容器中
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
