package com.joshua.experiment.adapter.albumhome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.entity.AlbumHomeDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/7/22.
 * 专辑主页--详情
 */

public class AlbumDetailsAdapter extends android.support.v7.widget.RecyclerView.Adapter<AlbumDetailsAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<AlbumHomeDetails> data = new ArrayList<>();

    public AlbumDetailsAdapter(Context context,List<AlbumHomeDetails> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.album_home_details_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_craftName.setText(data.get(position).getCraftsmanName());
        holder.tv_craftIntro.setText(data.get(position).getIntroduction());
        holder.tv_classify.setText(data.get(position).getClassifyCrafts());
        holder.tv_hotDegree.setText(data.get(position).getHotDegree());
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
        TextView tv_craftName;
        TextView tv_classify;
        TextView tv_hotDegree;
        TextView tv_craftIntro;
        ImageView iv_pic;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_craftName = (TextView) itemView.findViewById(R.id.particulars_crafts_account);
            tv_classify = (TextView) itemView.findViewById(R.id.particulars_crafts_classify);
            tv_hotDegree = (TextView) itemView.findViewById(R.id.particulars_crafts_hot_greed);
            tv_craftIntro = (TextView) itemView.findViewById(R.id.particulars_crafts_info);
            iv_pic= (ImageView) itemView.findViewById(R.id.particulars_crafts_cover);
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
