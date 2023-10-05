package com.example.noworderfoodapp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.OnActionCallBack;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentShopBinding;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.view.act.ShopDetailActivity;
import com.example.noworderfoodapp.view.adapter.ShopAdapter;
import com.example.noworderfoodapp.viewmodel.ShopViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends BaseFragment<FragmentShopBinding, ShopViewModel> implements ShopAdapter.OnItemClick {
    public static final String KEY_SHOW_SHOP_DETAIL = "KEY_SHOW_SHOP_DETAIL";
    private ShopAdapter shopAdapter;
    private List<Shop> listShop;
    @Override
    protected Class<ShopViewModel> getViewModelClass() {
        return ShopViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void initViews() {
        listShop = new ArrayList<>();
        mViewModel.getListShopServer();
        mViewModel.getShopMutableLiveData().observe(this, new Observer<List<Shop>>() {
            @Override
            public void onChanged(List<Shop> shops) {
                listShop.clear();
                listShop.addAll(shops);
                shopAdapter.notifyDataSetChanged();
                Log.i("KMFG", "initViews: "+listShop.toString());
            }
        });
      //  setCallBack((OnActionCallBack) getActivity());
        shopAdapter = new ShopAdapter(listShop,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvShop.setLayoutManager(manager);
        binding.rcvShop.setAdapter(shopAdapter);
        shopAdapter.setOnItemClick(this);
    }

    @Override
    public void onItemClick(Shop shop) {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra("category",(ArrayList<Category>) shop.getCategories());
        intent.putExtra("shop", shop);
        getActivity().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
      //
        List<Shop> shopList = mViewModel.getShopMutableLiveData().getValue();
        if (shopList != null) {
            shopAdapter.setListShop(shopList);
        }
        shopAdapter.setListShop(listShop);
        binding.lnShopList.setVisibility(View.VISIBLE);
    }

}
