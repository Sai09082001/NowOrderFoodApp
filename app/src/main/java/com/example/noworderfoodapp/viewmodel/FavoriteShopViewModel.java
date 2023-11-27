package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.CommonUtils;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.FavoriteShop;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.Shop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteShopViewModel extends ViewModel {
    private MutableLiveData<List<Shop>> shopMutableLiveData;

    public MutableLiveData<List<Shop>> getShopMutableLiveData() {
        return shopMutableLiveData;
    }

    public void setShopMutableLiveData(List<Shop> shopMutableLiveData) {
        this.shopMutableLiveData.postValue(shopMutableLiveData);
    }

    public FavoriteShopViewModel(){
        shopMutableLiveData = new MutableLiveData<>();
    }

    public void getListShopServer(){
        Call<ResponseDTO<List<Shop>>> call = ApiService.apiService.
                getListShop();
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<Shop>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Shop>>> call,
                                   Response<ResponseDTO<List<Shop>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Shop>> apiResponse = response.body();
                    List<Shop> listShop = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (listShop != null) {
                        List<Shop> listShopFavorite = filterShopFavoriteWithUser(listShop);
                        shopMutableLiveData.postValue(listShopFavorite);

                    }
                } else {
                    // Xử lý khi có lỗi từ API

                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<Shop>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

    private List<Shop> filterShopFavoriteWithUser(List<Shop> listShop) {
        List<Shop> filteredDataList = new ArrayList<>();
        for (Shop shop : listShop) {
            boolean isFavorite = false;
            for (FavoriteShop favoriteShop : CommonUtils.getInstance().getListFavoriteWithUserSession(App.getInstance().getUser().getId())) {
                if (shop.getId() == favoriteShop.getShopId()) {
                    isFavorite = true;
                    break;
                }
            }
            if (isFavorite) {
                filteredDataList.add(shop);
            }
        }
        return filteredDataList;
    }
}
