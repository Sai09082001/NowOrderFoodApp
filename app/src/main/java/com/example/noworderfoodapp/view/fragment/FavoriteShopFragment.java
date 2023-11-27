package com.example.noworderfoodapp.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.CommonUtils;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.database.SQLiteHelper;
import com.example.noworderfoodapp.databinding.FragmentFavoriteShopBinding;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.FavoriteShop;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.view.act.ShopDetailActivity;
import com.example.noworderfoodapp.view.adapter.ShopAdapter;
import com.example.noworderfoodapp.viewmodel.FavoriteShopViewModel;
import com.example.noworderfoodapp.viewmodel.ShopViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteShopFragment extends BaseFragment<FragmentFavoriteShopBinding, FavoriteShopViewModel> implements ShopAdapter.OnItemClick {
    public static final String KEY_SHOW_SHOP_DETAIL = "KEY_SHOW_SHOP_DETAIL";
    private ShopAdapter shopAdapter;
    private List<Shop> listShop;

    @Override
    protected Class<FavoriteShopViewModel> getViewModelClass() {
        return FavoriteShopViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite_shop;
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
                Log.i("KMFG", "initViews: " + listShop.toString());
            }
        });
        //  setCallBack((OnActionCallBack) getActivity());
        shopAdapter = new ShopAdapter(listShop, getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rcvFavoriteShop.setLayoutManager(manager);
        binding.rcvFavoriteShop.setAdapter(shopAdapter);
        shopAdapter.setOnItemClick(this);
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
        AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
        alert.setTitle("Xóa shop khỏi danh sách yêu thích");
        alert.setMessage("Danh sách yêu thích - " + shop.getName());
        alert.setButton(DialogInterface.BUTTON_POSITIVE,
                "Hoàn thành", (dialog, which) -> deleteShopFavorite(shop));
        alert.show();
    }

    private void deleteShopFavorite(Shop shop) {
        SQLiteHelper db = new SQLiteHelper(App.getInstance());
        db.deleteShopWithUser(App.getInstance().getUser().getId(),shop.getId());
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
        binding.lnShopFavorite.setVisibility(View.VISIBLE);
    }

}


