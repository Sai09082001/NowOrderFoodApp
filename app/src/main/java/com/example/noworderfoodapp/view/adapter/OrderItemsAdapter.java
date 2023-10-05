package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.OrderItems;
import com.example.noworderfoodapp.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemHolder> {
    private List<OrderItems> orderItemsList;
    private final Context mContext;
    private OnItemClick callBack;

    public List<OrderItems> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItems> orderItemsList) {
        this.orderItemsList = orderItemsList;
        notifyDataSetChanged();
    }

    public OrderItemsAdapter(List<OrderItems> orderItemsList, Context context) {
        this.orderItemsList = orderItemsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_order_cart, parent, false);

        return new OrderItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        OrderItems data = orderItemsList.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvOrderName.setText(data.getProduct().getName());
        holder.tvOrderCount.setText(data.getQuantity()+"");
        holder.tvOrderPrice.setText(data.getPrice()+"");
        holder.orderItems = data;

    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(OrderItems orderItems);
    }

    public class OrderItemHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderName;
        private TextView tvOrderCount;
        private TextView tvOrderPrice;
        private OrderItems orderItems;
        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tv_order_item_name);
            tvOrderCount= itemView.findViewById(R.id.tv_order_count);
            tvOrderPrice= itemView.findViewById(R.id.tv_price_order);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onItemClick(orderItems);
                }

            });
        }
    }
}

