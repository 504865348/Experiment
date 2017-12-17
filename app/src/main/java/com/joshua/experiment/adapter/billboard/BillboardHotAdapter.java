package com.joshua.experiment.adapter.billboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.entity.BillboardHot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/5/31.
 * 底部导航--榜单--最火节目飙升榜--适配器
 */

public class BillboardHotAdapter extends RecyclerView.Adapter<BillboardHotAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<BillboardHot> data = new ArrayList<>();

    public BillboardHotAdapter(Context context, List<BillboardHot> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.billboard_hot_program_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_rank.setText(data.get(position).getId());
        holder.tv_title.setText(data.get(position).getRecordTitle());
        holder.tv_author.setText(data.get(position).getName());
        //Glide.with(mContext).load(data.get(position).getRecordImage()).into(holder.iv_pic);
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
        TextView tv_title;
        TextView tv_author;
        ImageView iv_pic;
        MyViewHolder(View itemView) {
            super(itemView);
            tv_rank = (TextView) itemView.findViewById(R.id.billboard_hot_program_rank);
            tv_title = (TextView) itemView.findViewById(R.id.billboard_hot_program_name);
            tv_author = (TextView) itemView.findViewById(R.id.billboard_hot_program_author_name);
            iv_pic= (ImageView) itemView.findViewById(R.id.billboard_hot_program_img);
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
