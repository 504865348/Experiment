package com.joshua.experiment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.joshua.experiment.R;
import com.joshua.experiment.entity.joshua.VideoDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/4/31.
 * 首页-推荐--适配器
 */

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<VideoDetail> data = new ArrayList<>();

    public HomeRecommendAdapter(Context context,List<VideoDetail> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_page_recommend_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(data.get(position).getRecordTitle());
        holder.tv_author.setText(data.get(position).getName());
        holder.tv_price.setText("价格："+data.get(position).getMoney()+"元");
//        Glide.with(mContext).load(data.get(position).getRecordImage()).into(holder.iv_pic);
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
        TextView tv_title;
        TextView tv_author;
        ImageView iv_pic;
        TextView tv_price;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.recommend_program_name);
            tv_author = (TextView) itemView.findViewById(R.id.recommend_program_author_name);
            iv_pic= (ImageView) itemView.findViewById(R.id.recommend_program_img);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

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
