package com.joshua.experiment.adapter.find;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.entity.FindFriendsAttention;

import java.util.ArrayList;
import java.util.List;


public class FindFriendsAdapter extends android.support.v7.widget.RecyclerView.Adapter<FindFriendsAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<FindFriendsAttention> data = new ArrayList<>();

    public FindFriendsAdapter(Context context,List<FindFriendsAttention> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }
    @Override
    public FindFriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.find_friends_recommend_item, parent, false);
        view.setOnClickListener(this);
        return new FindFriendsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FindFriendsAdapter.MyViewHolder holder, final int position) {
        holder.tv_name.setText(data.get(position).getCraftsmanName());
        holder.tv_introduction.setText(data.get(position).getIntroduction());
        if(data.get(position).getClassifyCrafts()==null||data.get(position).getClassifyCrafts().equals("null")){
            holder.tv_classify.setText("普通用户");
        }else {
            holder.tv_classify.setText(data.get(position).getClassifyCrafts());
        }

        holder.tv_hot_degree.setText(data.get(position).getHotDegree());
        Glide.with(mContext).load(data.get(position).getImageUrl()).placeholder(R.drawable.load_error).into(holder.iv_pic);
        holder.btn_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "暂未开放工匠关注功能", Toast.LENGTH_SHORT).show();
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_pic;
        TextView tv_name;
        TextView tv_introduction;
        TextView tv_classify;
        TextView tv_hot_degree;
        Button btn_attention;
        MyViewHolder(View itemView) {
            super(itemView);
            iv_pic= (ImageView) itemView.findViewById(R.id.friends_recommend_img);
            tv_name = (TextView) itemView.findViewById(R.id.friends_recommend_name);
            tv_introduction = (TextView) itemView.findViewById(R.id.friends_recommend_introduction);
            tv_classify = (TextView) itemView.findViewById(R.id.friends_recommend_classify);
            tv_hot_degree = (TextView) itemView.findViewById(R.id.friends_recommend_hot_greed);
            btn_attention = (Button) itemView.findViewById(R.id.friends_recommend_plus);
        }
    }

    private FindFriendsAdapter.onRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    public interface onRecyclerViewItemClickListener {
        void onItemClick(View view, String position);
    }

    public void setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
