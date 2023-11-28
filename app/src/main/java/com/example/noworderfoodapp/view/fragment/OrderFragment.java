package com.example.noworderfoodapp.view.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentOrderBinding;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.view.act.OrderDetailActivity;
import com.example.noworderfoodapp.view.adapter.OrderAdapter;
import com.example.noworderfoodapp.viewmodel.OrderViewModel;

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
//        ArrayAdapter<CharSequence> adapterHk = ArrayAdapter.createFromResource(getContext(),
//                R.array.order_array, android.R.layout.simple_spinner_item);
//        adapterHk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.spFilterOrder.setAdapter(adapterHk);
//        binding.spFilterOrder.setOnItemSelectedListener(new InitDataEvent());
    }
//    private class InitDataEvent implements AdapterView.OnItemSelectedListener {
//        //Khi có chọn lựa thì vào hàm này
//        public void onItemSelected(AdapterView<?> arg0,
//                                   View arg1,
//                                   int arg2,
//                                   long arg3) {
//            //arg2 là phần tử được chọn trong data source
//            Log.i("KMFG", "onItemSelected: ok");
//            initDataOrderTime(arg2);
//        }
//        public void onNothingSelected(AdapterView<?> arg0) {
//
//        }
//    }
//    private void initDataOrderTime(int arg) {
//        if (arg==1) {
//            listOrder.clear();
//            listOrder.addAll(mViewModel.filterOrderByDate());
//            orderAdapter.notifyDataSetChanged();
//        } else if (arg ==0 ) {
//            mViewModel.getOrderByUser(App.getInstance().getUser().getId());
//            mViewModel.getOrdersMutableLiveData().observe(this, new Observer<List<Orders>>() {
//                @Override
//                public void onChanged(List<Orders> shops) {
//                    listOrder.clear();
//                    listOrder.addAll(shops);
//                    orderAdapter.notifyDataSetChanged();
//                }
//            });
//        }
//
//    }

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
