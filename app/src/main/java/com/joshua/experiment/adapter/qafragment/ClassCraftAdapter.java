package com.joshua.experiment.adapter.qafragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.ask.AskQuestionActivity;
import com.joshua.experiment.entity.ClassCraft;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/7/23.
 * 问答主界面--分类--工匠
 */

public class ClassCraftAdapter extends android.support.v7.widget.RecyclerView.Adapter<ClassCraftAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<ClassCraft> data = new ArrayList<>();

    public ClassCraftAdapter(Context context,List<ClassCraft> data) {
        mInflater = LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.question_answer_class_crafts_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_name.setText(data.get(position).getCraftsmanName());
        holder.tv_introduction.setText(data.get(position).getIntroduction());
        holder.tv_classify.setText(data.get(position).getClassifyCrafts());
        holder.tv_hot_degree.setText(data.get(position).getHotDegree());
        Glide.with(mContext).load(data.get(position).getImageUrl()).placeholder(R.drawable.load_error).into(holder.iv_pic);
        holder.btn_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AskQuestionActivity.class);
                intent.putExtra("answer", data.get(position).getCraftsmanName());
                intent.putExtra("craftsAccount", data.get(position).getCraftsAccount());
                mContext.startActivity(intent);
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
        TextView tv_name;
        TextView tv_introduction;
        TextView tv_classify;
        TextView tv_hot_degree;
        ImageView iv_pic;
        Button btn_ask;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.question_answer_crafts_name);
            tv_introduction = (TextView) itemView.findViewById(R.id.question_answer_crafts_introduction);
            tv_classify = (TextView) itemView.findViewById(R.id.question_answer_crafts_classify);
            tv_hot_degree = (TextView) itemView.findViewById(R.id.question_answer_crafts_hot_greed);
            iv_pic= (ImageView) itemView.findViewById(R.id.question_answer_crafts_img);
            btn_ask= (Button) itemView.findViewById(R.id.question_answer_crafts_ask);
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
