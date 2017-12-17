package com.joshua.experiment.adapter.info;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.entity.joshua.Order;
import com.joshua.experiment.entity.joshua.OrderType;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单-适配器
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Order> data = new ArrayList<>();

    public OrderAdapter(Context context, List<Order> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_order, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.buyTime.setText(data.get(position).getBuyTime());
        holder.orderId.setText(data.get(position).getOrderId());
        holder.price.setText(data.get(position).getPrice() + "元");
        if (data.get(position).getType().equals(OrderType.TYPE_ASK_QUE)) {
            holder.type.setText("提问回答");
        } else if (data.get(position).getType().equals(OrderType.TYPE_BYE_VIDEO)) {
            holder.type.setText("收看节目");
        } else if (data.get(position).getType().equals(OrderType.TYPE_BYE_MEDIA)) {
            holder.type.setText("旁听问题");
        }
        holder.itemView.setTag(position + "");
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
        TextView buyTime;
        TextView orderId;
        TextView price;
        TextView type;

        MyViewHolder(View itemView) {
            super(itemView);
            buyTime = (TextView) itemView.findViewById(R.id.buyTime);
            orderId = (TextView) itemView.findViewById(R.id.orderId);
            price = (TextView) itemView.findViewById(R.id.price);
            type = (TextView) itemView.findViewById(R.id.type);

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
