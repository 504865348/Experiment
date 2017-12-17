package com.joshua.experiment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.entity.HotListen;

import java.util.ArrayList;
import java.util.List;

public class HotListenAdapter extends RecyclerView.Adapter<HotListenAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<HotListen> data = new ArrayList<>();

    public HotListenAdapter(Context context, List<HotListen> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.homepage_hot_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_name.setText(data.get(position).getProgramName());
        Glide.with(mContext).load(data.get(position).getImageUrl()).placeholder(R.drawable.load_error).into(holder.iv_pic);
        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumHomeActivity.class);
                intent.putExtra("albumId", data.get(position).getId());
                intent.putExtra("albumName", data.get(position).getProgramName());
                intent.putExtra("albumPic", data.get(position).getImageUrl());
                intent.putExtra("albumCrafts", data.get(position).getAuthor());
                intent.putExtra("albumIntroduction", data.get(position).getIntroduction());
                intent.putExtra("albumClassify", data.get(position).getClassify());
                intent.putExtra("albumModel", data.get(position).getModel());
                intent.putExtra("albumPlay", data.get(position).getPlay());
                intent.putExtra("albumSubscribe", data.get(position).getSubscribe());
                mContext.startActivity(intent);
            }
        });
        holder.itemView.setTag(position+"");
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewItemClickListener != null) {
            mOnRecyclerViewItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_pic;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.hot_item_text);
            iv_pic= (ImageView) itemView.findViewById(R.id.hot_item_image);
        }
    }

    private onRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    interface onRecyclerViewItemClickListener {
        void onItemClick(View view, String position);
    }

    public void setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
