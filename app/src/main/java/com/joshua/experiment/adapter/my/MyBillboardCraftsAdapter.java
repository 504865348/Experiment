package com.joshua.experiment.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.entity.MyBillboardCrafts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/8/4.
 * 我的榜单-最大国工匠榜适配器
 */

public class MyBillboardCraftsAdapter extends android.support.v7.widget.RecyclerView.Adapter<MyBillboardCraftsAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MyBillboardCrafts> data = new ArrayList<>();

    public MyBillboardCraftsAdapter(Context context,List<MyBillboardCrafts> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_billboard_crafts_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_rank.setText(data.get(position).getCraftsRank());
        holder.tv_name.setText(data.get(position).getCraftsName());
        holder.tv_info.setText(data.get(position).getIntroduction());
        holder.tv_classify.setText(data.get(position).getClassifyCrafts());
        holder.tv_hot_greed.setText(data.get(position).getHotDegree());
        Glide.with(mContext).load(data.get(position).getCraftsImage()).placeholder(R.drawable.load_error).into(holder.iv_pic);
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
        TextView tv_rank;
        TextView tv_name;
        TextView tv_info;
        TextView tv_classify;
        TextView tv_hot_greed;
        ImageView iv_pic;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_rank = (TextView) itemView.findViewById(R.id.my_billboard_crafts_rank);
            tv_name = (TextView) itemView.findViewById(R.id.my_billboard_crafts_name);
            tv_info = (TextView) itemView.findViewById(R.id.my_billboard_crafts_info);
            tv_classify = (TextView) itemView.findViewById(R.id.my_billboard_crafts_classify);
            tv_hot_greed = (TextView) itemView.findViewById(R.id.my_billboard_crafts_hot_greed);
            iv_pic= (ImageView) itemView.findViewById(R.id.my_billboard_crafts_pic);
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
