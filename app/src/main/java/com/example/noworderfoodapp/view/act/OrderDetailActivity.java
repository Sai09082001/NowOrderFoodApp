package com.example.noworderfoodapp.view.act;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
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
//        if (orders.getStates().equals("Delivery")) binding.tvStateOrder.setText("Shop đã nhận đơn");
        if (orders.getStates().equals("Receiver")) {
            binding.tvStateOrder.setText("Đang giao hàng");
        }else if (orders.getStates().equals("Done")) {
            binding.tvStateOrder.setText("Hoàn thành");
        }else if (orders.getStates().equals("Cancel")) {
            binding.tvStateOrder.setText("Đã hủy đơn");
        } else {
            binding.tvStateOrder.setText("Đang xác nhận");
        }
        binding.tvShopName.setText(orders.getShopName());
        binding.tvAddressShop.setText(orders.getAddress());
        binding.tvSumOrder.setText(""+orders.getOrderItems().size()+" món");
        binding.tvOrderIndex.setText(""+orders.getOrderItems().size()+" món");
        binding.tvMoneyOrder.setText(calculateTotalPrice()+"");
        binding.tvPriceTop.setText((calculateTotalPrice()+15000)+"đ");
        //binding.tvPriceBottom.setText((calculateTotalPrice()+15000)+"");
        binding.tvUserNameOrder.setText("Đơn hàng của "+App.getInstance().getUser().getUsername());
        binding.tvAddressUser.setText(App.getInstance().getUser().getHomeAddress());
        binding.tvDateOrder.setText(CommonUtils.getInstance().convertDateToString(orders.getCreatedAt()));
        orderItemsAdapter = new OrderItemsAdapter(orders.getOrderItems(), this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rcvOrderDetail.setLayoutManager(manager);
        binding.rcvOrderDetail.setAdapter(orderItemsAdapter);
        binding.tvPriceBottom.setText(orders.getPrice()+"");
        binding.ivBack.setOnClickListener(view -> {
            onBackPressed();
        });
        if (orders.getStates().equals("Delivery")) {
            binding.btCancel.setText("Hủy đơn");
        } else if (orders.getStates().equals("Receiver")){
            binding.btCancel.setText("Đang giao hàng");
        } else {
            binding.btCancel.setText("Hoàn thành");
        }
        binding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orders.getStates().equals("Delivery")) {
                    orders.setStates("Cancel");
                    viewModel.updateOrder(orders);
                }
            }
        });
        viewModel.getIsUpdate().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(App.getInstance(),"Đã hủy đơn",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
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
