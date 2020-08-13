package com.yalianxun.mingshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.beans.ImageInfo;

import java.util.ArrayList;


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {
    private ArrayList<ImageInfo> dataList;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public CustomRecyclerAdapter(ArrayList<ImageInfo> dataList, Context context) {
        this.dataList = dataList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.item_recycler,

                viewGroup, false);

        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        ImageInfo imageInfo = dataList.get(position);
        if (imageInfo != null){
            viewHolder.title.setText(imageInfo.getImgName());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            imageView = view.findViewById(R.id.recycler_iv);
        }

    }


}
