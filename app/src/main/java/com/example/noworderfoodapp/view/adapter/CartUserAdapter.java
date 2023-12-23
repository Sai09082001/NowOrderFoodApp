package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.CartSession;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.OrderItems;
import com.example.noworderfoodapp.entity.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartUserAdapter extends RecyclerView.Adapter<CartUserAdapter.CartUserHolder> {
    private List<CartSession> cartSessionList;
    private final Context mContext;
    private OnItemClick callBack;
    private List<OrderItems> orderItems;
    public void setListCartUser(List<CartSession> cartSessionList) {
        this.cartSessionList = cartSessionList;
        notifyDataSetChanged();
    }

    public List<CartSession> getListCartUser() {
        return cartSessionList;
    }

    public CartUserAdapter(List<CartSession> cartSessionList, Context context) {
        this.cartSessionList = cartSessionList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CartUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_cart_user, parent, false);

        return new CartUserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartUserHolder holder, int position) {
        int sumOrder = 0;
        orderItems = new ArrayList<>();
        CartSession data = cartSessionList.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvShopName.setText(data.getShop().getName()); // lấy vị trí gán data tương ứng cho từng data
        holder.tvUsername.setText(App.getInstance().getUser().getName());
        holder.tvUserPhone.setText(App.getInstance().getUser().getPhonenumber());
        holder.tvShopAddress.setText(data.getShop().getAddress());
        for (Map.Entry<Integer, Integer> entry : data.getProductQuantityMap().entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            boolean isDuplicate = false;
            for (int i = 0; i < data.getProductsList().size(); i++) {
                if (productId == data.getProductsList().get(i).getId()) {
                    // Kiểm tra xem mục đã tồn tại trong danh sách orderItems chưa
                    for (OrderItems orderItem : orderItems) {
                        if (orderItem.getProduct().getId() == productId) {
                            // Mục đã tồn tại, không thêm vào orderItems nữa
                            isDuplicate = true;
                            break;
                        }
                    }

                    if (!isDuplicate) {
                        // Mục không tồn tại trong danh sách, thêm vào orderItems
                        orderItems.add(new OrderItems(data.getProductsList().get(i), quantity,
                                data.getProductsList().get(i).getPrice() * quantity));
                    }

                    // Đặt lại biến cờ
                    isDuplicate = false;
                }
            }
            // Tìm sản phẩm tương ứng trong danh sách sản phẩm
            Log.i("KMFG", "Cart productID: "+productId+"-quantity"+quantity);
            sumOrder++;
        }
        holder.tvSumOrder.setText(""+sumOrder+" món");
        holder.tvMoneyOrder.setText(calculateTotalPrice(data)+"");
        double sumMoney = calculateTotalPrice(data)+15000;
        holder.tvSumMoney.setText(sumMoney+"");
        holder.cartSession = data;
    }

    public double calculateTotalPrice(CartSession data) {
        double totalPrice = 0;

        // Duyệt qua các sản phẩm trong HashMap
        for (Map.Entry<Integer, Integer> entry : data.getProductQuantityMap().entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            // Tìm sản phẩm tương ứng trong danh sách sản phẩm
            for (Products product : data.getProductsList()) {
                if (product.getId() == productId) {
                    // Tính tổng giá tiền bằng cách nhân giá tiền của sản phẩm với số lượng
                    totalPrice += product.getPrice() * quantity;
                    break;
                }
            }
        }

        return totalPrice;
    }

    @Override
    public int getItemCount() {
        return cartSessionList.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(CartSession cartSession);
    }

    public class CartUserHolder extends RecyclerView.ViewHolder {
        private TextView tvShopName,tvUsername,tvUserPhone,tvShopAddress,tvSumOrder,tvMoneyOrder,tvSumMoney;
        private LinearLayout lnCartUser;
        private CartSession cartSession;
        public CartUserHolder(@NonNull View itemView) {
            super(itemView);
            tvShopName = itemView.findViewById(R.id.tv_shop_name);
            tvUsername = itemView.findViewById(R.id.tv_user_name);
            tvUserPhone = itemView.findViewById(R.id.tv_user_phone);
            tvShopAddress = itemView.findViewById(R.id.tv_shop_address);
            tvSumOrder = itemView.findViewById(R.id.tv_sum_order);
            tvMoneyOrder = itemView.findViewById(R.id.tv_money_order);
            tvSumMoney = itemView.findViewById(R.id.tv_sum_money);
            lnCartUser = itemView.findViewById(R.id.ln_cart_user);
            lnCartUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onItemClick(cartSession);
                }

            });
        }
    }
}

