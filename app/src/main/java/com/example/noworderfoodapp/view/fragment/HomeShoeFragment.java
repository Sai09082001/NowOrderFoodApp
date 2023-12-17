package com.example.noworderfoodapp.view.fragment;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentHomeShoeBinding;
import com.example.noworderfoodapp.entity.Banner;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.view.act.ShopDetailActivity;
import com.example.noworderfoodapp.view.adapter.BannerAdapter;
import com.example.noworderfoodapp.view.adapter.ShopAdapter;
import com.example.noworderfoodapp.viewmodel.HomeShoeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeShoeFragment extends BaseFragment<FragmentHomeShoeBinding, HomeShoeViewModel> implements ShopAdapter.OnItemClick {
    private ShopAdapter shopAdapter;
    private ShopAdapter shopMasterAdapter;
    private BannerAdapter bannerAdapter;
    private List<Shop> listShop;
    private List<Shop> listMaster;
    private List<Banner> listBanner;
    @Override
    protected Class<HomeShoeViewModel> getViewModelClass() {
        return HomeShoeViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_shoe;
    }

    @Override
    protected void initViews() {
        Log.i("KMFG", "initViews:fragment_order ");
        listShop = new ArrayList<>();
        listMaster = new ArrayList<>();
        listBanner = new ArrayList<>();
        mViewModel.getListBanner();
        mViewModel.getListShopServer();
        mViewModel.getBannerMutableLiveData().observe(this, new Observer<List<Banner>>() {
            @Override
            public void onChanged(List<Banner> categories) {
                listBanner.clear();
                listBanner.addAll(categories);
                bannerAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getMasterShopMutableLiveData().observe(this, new Observer<List<Shop>>() {
            @Override
            public void onChanged(List<Shop> products) {
                listMaster.clear();
                listMaster.addAll(products);
                shopMasterAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getShopMutableLiveData().observe(this, new Observer<List<Shop>>() {
            @Override
            public void onChanged(List<Shop> products) {
                listShop.clear();
                listShop.addAll(products);
                shopAdapter.notifyDataSetChanged();
            }
        });
        bannerAdapter = new BannerAdapter(listBanner,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),  RecyclerView.HORIZONTAL,false);
        binding.rcvBannerShoe.setLayoutManager(manager);
        binding.rcvBannerShoe.setAdapter(bannerAdapter);
       // bannerAdapter.setOnItemClick(this);
        shopMasterAdapter = new ShopAdapter(listMaster,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvShopMaster.setLayoutManager(layoutManager);
        binding.rcvShopMaster.setAdapter(shopMasterAdapter);
        shopMasterAdapter.setOnItemClick(this);
        shopAdapter = new ShopAdapter(listShop,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rcvShopBranch.setLayoutManager(linearLayoutManager);
        binding.rcvShopBranch.setAdapter(shopAdapter);
        shopAdapter.setOnItemClick(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getListBanner();
        mViewModel.getListShopServer();
        mViewModel.getBannerMutableLiveData().observe(this, new Observer<List<Banner>>() {
            @Override
            public void onChanged(List<Banner> categories) {
                listBanner.clear();
                listBanner.addAll(categories);
                bannerAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getMasterShopMutableLiveData().observe(this, new Observer<List<Shop>>() {
            @Override
            public void onChanged(List<Shop> products) {
                listMaster.clear();
                listMaster.addAll(products);
                shopMasterAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getShopMutableLiveData().observe(this, new Observer<List<Shop>>() {
            @Override
            public void onChanged(List<Shop> products) {
                listShop.clear();
                listShop.addAll(products);
                shopAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(Shop shop) {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra("category", (ArrayList<Category>) shop.getCategories());
        intent.putExtra("shop", shop);
        getActivity().startActivity(intent);
    }

    @Override
    public void onFavoriteShopClick(Shop shop) {

    }
}
