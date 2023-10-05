package com.example.noworderfoodapp.view.act;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.CommonUtils;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.ActivityOrderDetailBinding;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.view.adapter.OrderItemsAdapter;
import com.example.noworderfoodapp.viewmodel.ShopDetailViewModel;

public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailBinding, ShopDetailViewModel> {
    private Orders orders;
    private OrderItemsAdapter orderItemsAdapter;

    @Override
    protected Class<ShopDetailViewModel> getViewModelClass() {
        return ShopDetailViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews() {
        orders = (Orders) getIntent().getSerializableExtra("order");
        if (orders.getStates().equals("Delivery")) binding.tvStateOrder.setText("Đang giao hàng");
        binding.tvShopName.setText(orders.getShopName());
        binding.tvAddressShop.setText(orders.getAddress());
        binding.tvSumOrder.setText(""+orders.getOrderItems().size()+" món");
        binding.tvOrderIndex.setText(""+orders.getOrderItems().size()+" món");
        binding.tvMoneyOrder.setText(calculateTotalPrice()+"");
        binding.tvPriceTop.setText((calculateTotalPrice()+15000)+"đ");
        binding.tvPriceBottom.setText((calculateTotalPrice()+15000)+"");
        binding.tvUserNameOrder.setText("Đơn hàng của "+App.getInstance().getUser().getUsername());
        binding.tvAddressUser.setText(App.getInstance().getUser().getHomeAddress());
        binding.tvDateOrder.setText(CommonUtils.getInstance().convertDateToString(orders.getCreatedAt()));
        orderItemsAdapter = new OrderItemsAdapter(orders.getOrderItems(), this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rcvOrderDetail.setLayoutManager(manager);
        binding.rcvOrderDetail.setAdapter(orderItemsAdapter);
        binding.ivBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private double calculateTotalPrice() {
        double sum = 0;
        for (int i = 0; i < orders.getOrderItems().size(); i++) {
            sum+= orders.getOrderItems().get(i).getPrice();
        }
        return sum;
    }
    
    @Override
    public void callBack(String key, Object data) {

    }
}
