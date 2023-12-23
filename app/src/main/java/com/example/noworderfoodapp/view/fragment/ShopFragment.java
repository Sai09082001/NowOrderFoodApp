package com.example.noworderfoodapp.view.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.CommonUtils;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.database.SQLiteHelper;
import com.example.noworderfoodapp.databinding.FragmentShopBinding;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.FavoriteShop;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.view.act.CartUserActivity;
import com.example.noworderfoodapp.view.act.ProductDetailActivity;
import com.example.noworderfoodapp.view.act.ShopDetailActivity;
import com.example.noworderfoodapp.view.adapter.CategoryAdapter;
import com.example.noworderfoodapp.view.adapter.ProductAdapter;
import com.example.noworderfoodapp.view.adapter.ShopAdapter;
import com.example.noworderfoodapp.view.dialog.FilterOptionDialog;
import com.example.noworderfoodapp.viewmodel.ShopViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShopFragment extends BaseFragment<FragmentShopBinding, ShopViewModel> implements ShopAdapter.OnItemClick, ProductAdapter.OnItemClick, CategoryAdapter.OnItemClick, FilterOptionDialog.OnItemClick {
    public static final String KEY_SHOW_SHOP_DETAIL = "KEY_SHOW_SHOP_DETAIL";
    private CategoryAdapter categoryAdapter;
    private List<Category> listCategory;

    private ProductAdapter productAdapter;
    private List<Products> listProduct;
    private List<Products> listAllProduct;
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
        listCategory = new ArrayList<>();
        listProduct = new ArrayList<>();
        listAllProduct = new ArrayList<>();
        mViewModel.getListShopServer();
        mViewModel.getListProducts();
        mViewModel.getCategoryMutableLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> shops) {
                listCategory.clear();
                listCategory.addAll(shops);
                categoryAdapter.notifyDataSetChanged();
                Log.i("KMFG", "initViews: "+listCategory.toString());
            }
        });
        mViewModel.getProductsMutableLiveData().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                listProduct.clear();
                listProduct.addAll(products);
                listAllProduct.addAll(products);
                productAdapter.notifyDataSetChanged();
            }
        });
        binding.topBarAction.ivCartUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartUserActivity.class);
                startActivity(intent);
            }
        });
      //  setCallBack((OnActionCallBack) getActivity());
        categoryAdapter = new CategoryAdapter(listCategory,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
        binding.rcvCategory.setLayoutManager(manager);
        binding.rcvCategory.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClick(this);

        productAdapter = new ProductAdapter(listProduct,getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.rcvWatchProduct.setLayoutManager(gridLayoutManager);
        binding.rcvWatchProduct.setAdapter(productAdapter);
        productAdapter.setOnItemClick(this);
        binding.tvAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProduct.clear();
                listProduct.addAll(listAllProduct);
                productAdapter.setProductsList(listProduct);
            }
        });
        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the search query from the EditText
                String searchQuery = binding.edSearch.getText().toString();

                // Filter products based on the search query
                filterProducts(searchQuery);
            }
        });
        binding.tbFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterOptionDialog filterOptionDialog = new FilterOptionDialog();
                filterOptionDialog.show(getActivity().getSupportFragmentManager(), FilterOptionDialog.TAG);
                filterOptionDialog.setOnItemClick(ShopFragment.this);
            }
        });
    }
        private void sortByPriceDescending(List<Products> productList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            productList.sort(Comparator.comparingDouble(Products::getPrice).reversed());
        } else {
            // Sắp xếp trên Android dưới 7.0
            Collections.sort(productList, new Comparator<Products>() {
                @Override
                public int compare(Products product1, Products product2) {
                    return Double.compare(product2.getPrice(), product1.getPrice());
                    // Đảo ngược giá trị để sắp xếp giảm dần
                }
            });
        }
    }
    private void sortByPriceAscending(List<Products> productList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Collections.sort(productList, Comparator.comparingDouble(Products::getPrice));
        } else {
            Collections.sort(productList, new Comparator<Products>() {
                @Override
                public int compare(Products product1, Products product2) {
                    return Double.compare(product1.getPrice(), product2.getPrice());
                }
            });
        }

    }
    private void filterProducts(String query) {
        List<Products> filteredList = new ArrayList<>();

        for (Products product : listAllProduct) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }

        // Update the product list in the adapter
        listProduct.clear();
        listProduct.addAll(filteredList);
        productAdapter.setProductsList(listProduct);
    }
    @Override
    public void onItemClick(Shop shop) {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra("category",(ArrayList<Category>) shop.getCategories());
        intent.putExtra("shop", shop);
        getActivity().startActivity(intent);
    }

    @Override
    public void onFavoriteShopClick(Shop shop) {
        if (checkFavoriteShop(shop)) {
             Toast.makeText(App.getInstance(),"Shop đã có trong danh sách yêu thích",Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle("Thêm shop vào danh sách yêu thích");
            alert.setMessage("Danh sách yêu thích + " + shop.getName());
            alert.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Hoàn thành", (dialog, which) -> addShopFavorite(shop));
            alert.show();
        }
    }

    private boolean checkFavoriteShop(Shop shop) {
        boolean isFavorite = false;
        for (FavoriteShop favoriteShop : CommonUtils.getInstance().getListFavoriteWithUserSession(App.getInstance().getUser().getId())) {
            if (shop.getId() == favoriteShop.getShopId()) {
                isFavorite = true;
            }
        }
        return isFavorite;
    }

    private void addShopFavorite(Shop shop) {
        SQLiteHelper db = new SQLiteHelper(App.getInstance());
        db.addShop(new FavoriteShop(App.getInstance().getUser().getId(),shop.getId()));
        Log.i("KMFG", "addShopFavorite: "+ CommonUtils.getInstance()
                .getListFavoriteWithUserSession(App.getInstance().getUser().getId()));
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Category> shopList = mViewModel.getCategoryMutableLiveData().getValue();
        if (shopList != null) {
            categoryAdapter.setListCategories(shopList);
        }
        categoryAdapter.setListCategories(listCategory);
        binding.lnShopList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(Products products) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product_detail",products);
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemClick(Category category) {
        listProduct.clear();
        listProduct.addAll(category.getProducts());
        productAdapter.setProductsList(listProduct);
    }

    @Override
    public void onItemClick(String key) {
        if (key.equals(FilterOptionDialog.KEY_REDUCE)) {
            listProduct.clear();
            sortByPriceDescending(listAllProduct);
            listProduct.addAll(listAllProduct);
            productAdapter.setProductsList(listProduct);
        } else {
            listProduct.clear();
            sortByPriceAscending(listAllProduct);
            listProduct.addAll(listAllProduct);
            productAdapter.setProductsList(listProduct);
        }
    }
}
