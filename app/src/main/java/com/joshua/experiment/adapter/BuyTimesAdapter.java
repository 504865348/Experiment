package com.joshua.experiment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.entity.joshua.VideoDetailPlus;

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
 * 创建日期 ： 2017/12/17 17:04
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class BuyTimesAdapter extends RecyclerView.Adapter<BuyTimesAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<VideoDetailPlus> data = new ArrayList<>();

    public BuyTimesAdapter(Context context,List<VideoDetailPlus> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.buy_times_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(data.get(position).getRecordTitle());
        holder.tv_buy_times.setText("购买次数:"+data.get(position).getBuyTimes());
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
        TextView tv_buy_times;
        ImageView iv_pic;
        TextView tv_price;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.recommend_program_name);
            tv_buy_times = (TextView) itemView.findViewById(R.id.recommend_program_author_name);
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
