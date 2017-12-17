package com.joshua.experiment.adapter.qacrafts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.entity.joshua.QuesAnsClassify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nzz on 2017/6/9.
 * 工匠-我的问答-未处理回答-适配器
 */

public class NotAnswerAdapter extends RecyclerView.Adapter<NotAnswerAdapter.MyViewHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<QuesAnsClassify> data = new ArrayList<>();
    public NotAnswerAdapter(Context context,List<QuesAnsClassify> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @Override
    public NotAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_ask_answer_crafts_undeal_item, parent, false);
        view.setOnClickListener(this);
        return new NotAnswerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotAnswerAdapter.MyViewHolder holder, int position) {
        holder.tv_asker.setText(data.get(position).getCraftsmanName());
        holder.tv_price.setText(data.get(position).getMoney()+"元");
        holder.tv_content.setText(data.get(position).getQuestionWord());
        holder.tv_time.setText(data.get(position).getQueTime());
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
        TextView tv_asker;
        TextView tv_price;
        TextView tv_content;
        TextView tv_time;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_asker = (TextView) itemView.findViewById(R.id.not_answer_tv_asker);
            tv_price = (TextView) itemView.findViewById(R.id.not_answer_tv_ask_price);
            tv_content = (TextView) itemView.findViewById(R.id.not_answer_tv_ask_content);
            tv_time = (TextView) itemView.findViewById(R.id.not_answer_tv_ask_time);
        }
    }

    private NotAnswerAdapter.onRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    public interface onRecyclerViewItemClickListener {
        void onItemClick(View view, String position);
    }

    public void setOnRecyclerViewItemClickListener(NotAnswerAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
