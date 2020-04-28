package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.ImageInfo;

import java.util.List;

public class DownloadImageAdapter extends BaseAdapter {
    private int resourceId;
    private List<ImageInfo> imageInfoList;

    private Context mContext;

    public DownloadImageAdapter(int resourceId, List<ImageInfo> imageInfoList, Context mContext) {
        this.resourceId = resourceId;
        this.imageInfoList = imageInfoList;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return imageInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View item;

        ViewHolder vh;
        if (view == null) {
            item = LayoutInflater.from(mContext).inflate(resourceId, viewGroup, false);
            vh = new ViewHolder();
            if(resourceId == R.layout.gv_image_item){
                vh.iv = item.findViewById(R.id.gv_image);
                vh.tv = item.findViewById(R.id.gv_text_view);
            }else if(resourceId == R.layout.item_document){
                vh.iv = item.findViewById(R.id.document_iv);
                vh.tv = item.findViewById(R.id.document_tv);
            }else {
                vh.iv = item.findViewById(R.id.gv_pd_image);
                vh.tv = item.findViewById(R.id.gv_pd_text);
            }

            //找到复选框
            //让item和ViewHolder绑定在一起
            item.setTag(vh);
        } else {
            //复用ListView给的View
            item = view;
            //拿出ViewHolder
            vh = (ViewHolder) item.getTag();
        }
        ImageInfo info = imageInfoList.get(position);
        if(info != null){
            if(info.getImgPath().equals("")){
                vh.iv.setImageResource(R.drawable.license);
            }else {

                if(info.getImgPath().contains("drawable")){
                    String str = info.getImgPath().substring(8);

                    int resource = Integer.parseInt(str);
                    vh.iv.setImageResource(resource);
//                    Glide.with(mContext)
//                            .load(resource)
//                            .placeholder(R.drawable.placeholder_pic)
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(vh.iv);
                }else {
                    Glide.with(mContext)
                            .load(info.getImgPath())
                            .placeholder(R.drawable.placeholder_pic)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(vh.iv);
                }
            }
            if(info.getImgName().equals("")){
                vh.tv.setVisibility(View.GONE);
            }else {
                vh.tv.setVisibility(View.VISIBLE);
                vh.tv.setText(info.getImgName());
            }
        }
        return item;
    }

    static class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
