package com.joshua.experiment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.entity.HotCraftsman;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/7/28.
 * 首页-热门-更多(工匠)
 */

public class HotMoreCraftAdapter extends RecyclerView.Adapter<HotMoreCraftAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<HotCraftsman> data = new ArrayList<>();

    public HotMoreCraftAdapter(Context context, List<HotCraftsman> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.hot_more_crafts_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getCraftsmanName());
        holder.tv_classify.setText(data.get(position).getClassifyCrafts());
        holder.tv_hotDegree.setText(data.get(position).getHotDegree());
        holder.tv_info.setText(data.get(position).getIntroduction());
        Glide.with(mContext).load(data.get(position).getImageUrl()).placeholder(R.drawable.load_error).into(holder.iv_pic);
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
        TextView tv_classify;
        TextView tv_hotDegree;
        TextView tv_info;
        ImageView iv_pic;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.hot_more_crafts_name);
            tv_classify = (TextView) itemView.findViewById(R.id.hot_more_crafts_classify);
            tv_hotDegree = (TextView) itemView.findViewById(R.id.hot_more_crafts_hot_greed);
            tv_info = (TextView) itemView.findViewById(R.id.hot_more_crafts_info);
            iv_pic= (ImageView) itemView.findViewById(R.id.hot_more_crafts_cover);
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
