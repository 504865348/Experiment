package com.joshua.experiment.activity.record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.entity.Album;

import java.util.ArrayList;
import java.util.List;


/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/4/24 13:56
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Album> data = new ArrayList<>();

    public AlbumAdapter(Context context, List<Album> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.select_album_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getTitle());
        holder.tv_info.setText(data.get(position).getIntroduction());
        holder.tv_type.setText(data.get(position).getClassifyName());
        holder.tv_model.setText(data.get(position).getModel());
        Glide.with(mContext).load(data.get(position).getAlbumImage()).placeholder(R.drawable.load_error).into(holder.iv_pic);
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewItemClickListener != null) {
            mOnRecyclerViewItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_info;
        TextView tv_type;
        TextView tv_model;
        ImageView iv_pic;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.album_item_name);
            tv_info = (TextView) itemView.findViewById(R.id.album_item_info);
            tv_type= (TextView) itemView.findViewById(R.id.album_item_type);
            tv_model= (TextView) itemView.findViewById(R.id.album_item_model);
            iv_pic= (ImageView) itemView.findViewById(R.id.album_item_cover);
        }
    }

    private onRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    interface onRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
