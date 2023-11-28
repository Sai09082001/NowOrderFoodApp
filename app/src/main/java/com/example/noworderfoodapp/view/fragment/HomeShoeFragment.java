package com.example.noworderfoodapp.view.fragment;

import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentHomeShoeBinding;
import com.example.noworderfoodapp.databinding.FragmentOrderBinding;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.view.act.OrderDetailActivity;
import com.example.noworderfoodapp.view.adapter.CategoryAdapter;
import com.example.noworderfoodapp.view.adapter.OrderAdapter;
import com.example.noworderfoodapp.view.adapter.ProductAdapter;
import com.example.noworderfoodapp.viewmodel.HomeShoeViewModel;
import com.example.noworderfoodapp.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeShoeFragment extends BaseFragment<FragmentHomeShoeBinding, HomeShoeViewModel> implements CategoryAdapter.OnItemClick , ProductAdapter.OnItemClick{
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private List<Products> listProduct;
    private List<Category> listCategory;
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
        listProduct = new ArrayList<>();
        listCategory = new ArrayList<>();
        mViewModel.getListCategory();
        mViewModel.getListProducts();
        mViewModel.getCategoryMutableLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                listCategory.clear();
                listCategory.addAll(categories);
                categoryAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getProductsMutableLiveData().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                listProduct.clear();
                listProduct.addAll(products);
                productAdapter.notifyDataSetChanged();
            }
        });
        categoryAdapter = new CategoryAdapter(listCategory,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),  RecyclerView.HORIZONTAL,false);
        binding.rcvCategoryShoe.setLayoutManager(manager);
        binding.rcvCategoryShoe.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClick(this);

        productAdapter = new ProductAdapter(listProduct,getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rcvHomeShoe.setLayoutManager(gridLayoutManager);
        binding.rcvHomeShoe.setAdapter(productAdapter);
        productAdapter.setOnItemClick(this);
    }

    @Override
    public void onItemClick(Category orders) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("order",orders);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Products products) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("order",products);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getListProducts();
        mViewModel.getListCategory();
        mViewModel.getCategoryMutableLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> shops) {
                listCategory.clear();
                listCategory.addAll(shops);
                categoryAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getProductsMutableLiveData().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                listProduct.clear();
                listProduct.addAll(products);
                productAdapter.notifyDataSetChanged();
            }
        });
    }
}
