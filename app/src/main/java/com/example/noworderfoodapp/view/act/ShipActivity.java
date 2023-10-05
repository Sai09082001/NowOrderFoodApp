package com.example.noworderfoodapp.view.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.api.ApiClient;
import com.example.noworderfoodapp.databinding.ActivityShipBinding;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.viewmodel.ShipViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShipActivity extends BaseActivity<ActivityShipBinding, ShipViewModel> {

    private ArrayList<Orders> listOrder;

    @Override
    protected Class<ShipViewModel> getViewModelClass() {
        return ShipViewModel.class;
    }

    @Override
    protected void initViews() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 101);
        }
        listOrder = new ArrayList<>();
        viewModel.getOrderList();
        viewModel.getOrderShippingMutableLiveData().observe(this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                listOrder.clear();
                listOrder.addAll(orders);
                Log.i("KMFG", "initViews: "+listOrder.toString());
            }
        });
      binding.tbShipWork.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(ShipActivity.this,MapActivity.class);
              intent.putExtra("list",listOrder);
              startActivity(intent);
          }
      });
        binding.tvShipName.setText("Tên : "+App.getInstance().getUser().getName());
        binding.tvShipUsername.setText(App.getInstance().getUser().getUsername());
        binding.tvShipPhone.setText("Số điện thoại : "+App.getInstance().getUser().getPhonenumber());
        if (App.getInstance().getUser().getBirthdate() == null) {
            binding.tvShipBirthdate.setText("Ngày sinh : ");
        } else {
            binding.tvShipBirthdate.setText("Ngày sinh : "+App.getInstance().getUser().getBirthdate());
        }
      //  binding.tvShipBirthdate.setText("Ngày sinh : "+App.getInstance().getUser().getBirthdate());
        binding.tvShipAddress.setText("Địa chỉ nhà : "+App.getInstance().getUser().getHomeAddress());
        if ( App.getInstance().getUser().getAvatarUrl() == null){
            binding.shipProfileImage.setImageResource(R.drawable.ic_user_receiver);
        } else {
            Glide.with(this).load(""+ApiClient.BASE_URL+"/user/download?filename="+
                    App.getInstance().getUser().getAvatarUrl()).into(binding.shipProfileImage);
        }
        binding.btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShipActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        binding.tbrShipDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShipActivity.this, ShipOrderDoneActivity.class);
                startActivity(intent);

            }
        });
        binding.tbrShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShipActivity.this, ShippingOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship;
    }


    @Override
    public void callBack(String key, Object data) {

    }
}