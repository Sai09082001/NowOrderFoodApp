package com.example.noworderfoodapp.view.act;

import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.ActivityShipDoneBinding;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.view.adapter.OrderAdapter;
import com.example.noworderfoodapp.viewmodel.MainViewModel;
import com.example.noworderfoodapp.viewmodel.ShipDoneViewModel;
import com.example.noworderfoodapp.viewmodel.ShippingOrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShipOrderDoneActivity extends BaseActivity<ActivityShipDoneBinding, ShipDoneViewModel> implements OrderAdapter.OnItemClick {
    private OrderAdapter orderAdapter;
    private List<Orders> listOrder;
    @Override
    protected Class<ShipDoneViewModel> getViewModelClass() {
        return ShipDoneViewModel.class;
    }

    @Override
    protected void initViews() {
        listOrder = new ArrayList<>();
        viewModel.getOrderByShipper(App.getInstance().getUser().getId());
        viewModel.getOrderShippingMutableLiveData().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> shops) {
                listOrder.clear();
                listOrder.addAll(shops);
                orderAdapter.notifyDataSetChanged();
                Log.i("KMFG", "initViews: "+listOrder.toString());
            }
        });
        orderAdapter = new OrderAdapter(listOrder,this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvShipDone.setLayoutManager(manager);
        binding.rcvShipDone.setAdapter(orderAdapter);
        orderAdapter.setOnItemClick(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_done;
    }


    @Override
    public void callBack(String key, Object data) {

    }

    @Override
    public void onItemClick(Orders orders) {

    }
}