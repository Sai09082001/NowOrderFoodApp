package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.CommonUtils;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.OrderItems;
import com.example.noworderfoodapp.entity.Orders;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
    private List<Orders> ordersList;
    private final Context mContext;

    private Orders selectedOrders;

    private OnItemClick callBack;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setSelectedOrders(Orders orders) {
        if(selectedOrders!=null){
            selectedOrders.setSelected(false);
        }
        orders.setSelected(true);
        selectedOrders=orders;
        notifyDataSetChanged();
    }


    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public OrderAdapter(List<Orders> ordersList, Context context) {
        this.ordersList = ordersList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_orders, parent, false);

        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Orders data = ordersList.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvShopOrderName.setText(data.getShopName());
        holder.tvOrderCount.setText("("+data.getOrderItems().size()+"món)");
        holder.tvOrderPrice.setText(""+data.getPrice()+" đ");
        if (data.getStates().equals("Receiver")) {
            holder.tvStateOrder.setText("Đang giao hàng");
        }else if (data.getStates().equals("Done")) {
            holder.tvStateOrder.setText("Hoàn thành");
        }else if (data.getStates().equals("Cancel")) {
            holder.tvStateOrder.setText("Đã hủy đơn");
        } else {
            holder.tvStateOrder.setText("Đang xác nhận");
        }
        holder.tvShopAddress.setText(data.getAddress());
        holder.tvCreateOrder.setText(CommonUtils.getInstance().convertDateToString(data.getCreatedAt()).substring(0,10));
        holder.lnOrders.setBackgroundResource(
                data==selectedOrders &&
                        data.isSelected() ? R.color.gray_orange : R.color.white);
        holder.orders = data;
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(Orders orders);
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        private TextView tvShopOrderName;
        private TextView tvOrderCount;
        private TextView tvOrderPrice;
        private TextView tvShopAddress;
        private TextView tvCreateOrder;
        private TextView tvStateOrder;
        private LinearLayout lnOrders;
        private Orders orders;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            tvShopOrderName = itemView.findViewById(R.id.tv_shop_order_name);
            tvOrderPrice= itemView.findViewById(R.id.tv_order_price);
            tvOrderCount= itemView.findViewById(R.id.tv_count_order);
            tvShopAddress= itemView.findViewById(R.id.tv_shop_address);
            tvCreateOrder= itemView.findViewById(R.id.tv_create_order);
            tvStateOrder= itemView.findViewById(R.id.tv_state_order);
            lnOrders = itemView.findViewById(R.id.ln_orders);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedOrders != null) {
                        selectedOrders.setSelected(false);
                    }
                    orders.setSelected(true);

                    notifyDataSetChanged();
                    selectedOrders = orders;
                    callBack.onItemClick(orders);
                }

            });
        }
    }
}

