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
import com.joshua.experiment.entity.MyBillboardHot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/8/4.
 * 我的榜单-最火节目飙升榜适配器
 */

public class MyBillboardHotAdapter extends android.support.v7.widget.RecyclerView.Adapter<MyBillboardHotAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MyBillboardHot> data = new ArrayList<>();

    public MyBillboardHotAdapter(Context context,List<MyBillboardHot> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_billboard_hot_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_rank.setText(data.get(position).getProRank());
        holder.tv_name.setText(data.get(position).getRecordTitle());
        Glide.with(mContext).load(data.get(position).getRecordImage()).placeholder(R.drawable.load_error).into(holder.iv_pic);
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
        ImageView iv_pic;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_rank = (TextView) itemView.findViewById(R.id.my_billboard_hot_rank);
            tv_name = (TextView) itemView.findViewById(R.id.my_billboard_hot_name);
            iv_pic= (ImageView) itemView.findViewById(R.id.my_billboard_hot_pic);
        }
    }

    private onRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    public interface onRecyclerViewItemClickListener {
        void onItemClick(View view, String position);
    }

    public void setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
