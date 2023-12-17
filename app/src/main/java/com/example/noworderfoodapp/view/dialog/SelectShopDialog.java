package com.example.noworderfoodapp.view.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.view.adapter.ShopAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectShopDialog extends DialogFragment implements ShopAdapter.OnItemClick {

    public static String TAG = SelectShopDialog.class.getName();
    private RecyclerView rcvShop;
    private ShopAdapter shopAdapter;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_select_shop, container);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvShop = view.findViewById(R.id.rcv_shop);
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Lấy danh sách sản phẩm từ bundle
            Serializable serializable = bundle.getSerializable("shopList");
            String key = bundle.getString("key_product");
            if (serializable instanceof ArrayList) {
                ArrayList<Shop> shopList = (ArrayList<Shop>) serializable;
                List<Shop> dataShop = filterShopsByKey(shopList,key);
                shopAdapter = new ShopAdapter(dataShop,getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                rcvShop.setLayoutManager(linearLayoutManager);
                rcvShop.setAdapter(shopAdapter);
                shopAdapter.setOnItemClick(this);
            }
        }
    }

    private List<Shop> filterShopsByKey(List<Shop> shopList, String keyToFind) {
        List<Shop> result = new ArrayList<>();

        for (Shop shop : shopList) {
            if (shop.getCategories() != null) {
                for (Category category : shop.getCategories()) {
                    if (category.getProducts() != null) {
                        for (Products product : category.getProducts()) {
                            if (keyToFind.equals(product.getName())) {
                                result.add(shop);
                                break; // Thoát vòng lặp khi tìm thấy một sản phẩm
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
    private OnItemClick callBack;

    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    @Override
    public void onItemClick(Shop shop) {
        callBack.onItemClick(shop);
    }

    @Override
    public void onFavoriteShopClick(Shop shop) {

    }

    public interface OnItemClick {
        void onItemClick(Shop key);
    }
}