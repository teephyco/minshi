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
import com.yalianxun.mingshi.beans.Notification;

import java.util.ArrayList;

public class LifeInfoRecyclerAdapter extends RecyclerView.Adapter<LifeInfoRecyclerAdapter.ViewHolder> {
    private ArrayList<Notification> dataList;
    private LayoutInflater mInflater;
    private LifeInfoRecyclerAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(LifeInfoRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public LifeInfoRecyclerAdapter(ArrayList<Notification> dataList, Context context) {
        this.dataList = dataList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LifeInfoRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.item_life_info_recycler,

                viewGroup, false);

        return new LifeInfoRecyclerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final LifeInfoRecyclerAdapter.ViewHolder viewHolder, final int position) {
        Notification message = dataList.get(position);
        if (message != null){
            viewHolder.title.setText(message.getTitle());
            viewHolder.imageView.setImageResource(message.getResID());
            String num = "" + message.getCountNum();
            viewHolder.browseNum.setText(num);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
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
        TextView browseNum;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            imageView = view.findViewById(R.id.recycler_iv);
            browseNum = view.findViewById(R.id.browse_num);
        }

    }
}
