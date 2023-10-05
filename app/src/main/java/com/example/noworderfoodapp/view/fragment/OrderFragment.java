package com.example.noworderfoodapp.view.fragment;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.OnActionCallBack;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentOrderBinding;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.view.act.OrderDetailActivity;
import com.example.noworderfoodapp.view.adapter.OrderAdapter;
import com.example.noworderfoodapp.view.adapter.ShopAdapter;
import com.example.noworderfoodapp.viewmodel.OrderViewModel;
import com.example.noworderfoodapp.viewmodel.SplashViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment<FragmentOrderBinding, OrderViewModel> implements OrderAdapter.OnItemClick {
    private OrderAdapter orderAdapter;
    private List<Orders> listOrder;
    @Override
    protected Class<OrderViewModel> getViewModelClass() {
        return OrderViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initViews() {
        Log.i("KMFG", "initViews:fragment_order ");
        binding.tvOrderList.setText("Đơn hàng của "+App.getInstance().getUser().getUsername());
        listOrder = new ArrayList<>();
        mViewModel.getOrderByUser(App.getInstance().getUser().getId());
        mViewModel.getOrdersMutableLiveData().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> shops) {
                listOrder.clear();
                listOrder.addAll(shops);
                orderAdapter.notifyDataSetChanged();
                Log.i("KMFG", "initViews: "+listOrder.toString());
            }
        });
        orderAdapter = new OrderAdapter(listOrder,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvOrder.setLayoutManager(manager);
        binding.rcvOrder.setAdapter(orderAdapter);
        orderAdapter.setOnItemClick(this);
    }

    @Override
    public void onItemClick(Orders orders) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("order",orders);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getOrderByUser(App.getInstance().getUser().getId());
        mViewModel.getOrdersMutableLiveData().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> shops) {
                listOrder.clear();
                listOrder.addAll(shops);
                orderAdapter.notifyDataSetChanged();
                Log.i("KMFG", "initViews: "+listOrder.toString());
            }
        });
    }
}
