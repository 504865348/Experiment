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
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.entity.AlbumHomePro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/7/22.
 * 专辑主页--节目
 */

public class AlbumProgramAdapter extends android.support.v7.widget.RecyclerView.Adapter<AlbumProgramAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<AlbumHomePro> data = new ArrayList<>();
    private String proPic = AlbumHomeActivity.albumPic;

    public AlbumProgramAdapter(Context context,List<AlbumHomePro> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.album_home_program_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_proName.setText(data.get(position).getRecordTitle());
        //Glide.with(mContext).load(data.get(position).getRecordImage()).into(holder.iv_pic);
        Glide.with(mContext).load(proPic).into(holder.iv_pic);
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
        TextView tv_proName;
        TextView tv_playNumber;
        TextView tv_duration;
        TextView tv_publishTime;
        ImageView iv_pic;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_proName = (TextView) itemView.findViewById(R.id.album_program_name);
            //tv_playNumber = (TextView) itemView.findViewById(R.id.album_program_pv);
            //tv_duration = (TextView) itemView.findViewById(R.id.album_program_duration);
            //tv_publishTime = (TextView) itemView.findViewById(R.id.album_program_publish_time);
            iv_pic= (ImageView) itemView.findViewById(R.id.album_program_cover);
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
