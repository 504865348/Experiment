package com.joshua.experiment.adapter.billboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.billboard.BillboardCraftsmanActivity;
import com.joshua.experiment.activity.billboard.BillboardMoreActivity;
import com.joshua.experiment.activity.billboard.joshua.HotActivity;
import com.joshua.experiment.activity.billboard.joshua.PayActivity;
import com.joshua.experiment.entity.Billboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/6/17.
 * 底部导航--榜单--适配器
 */

public class BillBoardAdapter extends RecyclerView.Adapter<BillBoardAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Billboard> data = new ArrayList<>();

    public BillBoardAdapter(Context context, List<Billboard> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.billboard_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_hot_program1.setText(data.get(position).getProHotNameTop1());
        holder.tv_hot_program2.setText(data.get(position).getProHotNameTop2());
        holder.tv_more_program1.setText(data.get(position).getProHotNameTop3());
        holder.tv_more_program2.setText(data.get(position).getProHotNameTop4());
        holder.tv_pay_program1.setText(data.get(position).getProHotNameTop5());
        holder.tv_pay_program2.setText(data.get(position).getProHotNameTop6());
        holder.tv_crafts1.setText(data.get(position).getProHotNameTop7());
        holder.tv_crafts2.setText(data.get(position).getProHotNameTop8());
        holder.rl_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, HotActivity.class));
            }
        });
        holder.rl_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BillboardMoreActivity.class));
            }
        });
        holder.rl_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PayActivity.class));
            }
        });
        holder.rl_craftsman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BillboardCraftsmanActivity.class));
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
        TextView tv_hot_program1;
        TextView tv_hot_program2;
        TextView tv_more_program1;
        TextView tv_more_program2;
        TextView tv_pay_program1;
        TextView tv_pay_program2;
        TextView tv_crafts1;
        TextView tv_crafts2;
        RelativeLayout rl_hot;
        RelativeLayout rl_more;
        RelativeLayout rl_pay;
        RelativeLayout rl_craftsman;
        MyViewHolder(View itemView) {
            super(itemView);
            tv_hot_program1 = (TextView) itemView.findViewById(R.id.billboard_hot_program1);
            tv_hot_program2 = (TextView) itemView.findViewById(R.id.billboard_hot_program2);
            tv_more_program1 = (TextView) itemView.findViewById(R.id.billboard_more_program1);
            tv_more_program2 = (TextView) itemView.findViewById(R.id.billboard_more_program2);
            tv_pay_program1 = (TextView) itemView.findViewById(R.id.billboard_pay_program1);
            tv_pay_program2 = (TextView) itemView.findViewById(R.id.billboard_pay_program2);
            tv_crafts1 = (TextView) itemView.findViewById(R.id.billboard_crafts1);
            tv_crafts2 = (TextView) itemView.findViewById(R.id.billboard_crafts2);
            rl_hot = (RelativeLayout) itemView.findViewById(R.id.billboard_hot);
            rl_more = (RelativeLayout) itemView.findViewById(R.id.billboard_more);
            rl_pay = (RelativeLayout) itemView.findViewById(R.id.billboard_pay);
            rl_craftsman = (RelativeLayout) itemView.findViewById(R.id.billboard_craftsman);
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
