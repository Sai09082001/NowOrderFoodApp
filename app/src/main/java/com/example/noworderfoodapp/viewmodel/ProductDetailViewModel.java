package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Banner;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.Promotion;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.Shop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailViewModel extends ViewModel {
    private MutableLiveData<Boolean> isUpdate = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsUpdate() {
        return isUpdate;
    }
    private MutableLiveData<List<Shop>> shopMutableLiveData;

    public MutableLiveData<List<Shop>> getShopMutableLiveData() {
        return shopMutableLiveData;
    }
    private MutableLiveData<List<Promotion>> promotionMutableLiveData;

    public MutableLiveData<List<Promotion>> getPromotionMutableLiveData() {
        return promotionMutableLiveData;
    }

    public void setPromotionMutableLiveData(List<Promotion> promotionMutableLiveData) {
        this.promotionMutableLiveData.postValue(promotionMutableLiveData);
    }

    public ProductDetailViewModel(){
        promotionMutableLiveData = new MutableLiveData<>();
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
                        shopMutableLiveData.postValue(listShop);

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

    public void getListPromotionServer(){
        Call<ResponseDTO<List<Promotion>>> call = ApiService.apiService.
                getListPromotion();
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<Promotion>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Promotion>>> call,
                                   Response<ResponseDTO<List<Promotion>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Promotion>> apiResponse = response.body();
                    List<Promotion> listPromotion = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (listPromotion != null) {
                        List<Promotion> listData = handleListPromotion(listPromotion);
                        promotionMutableLiveData.postValue(listData);

                    }
                } else {
                    // Xử lý khi có lỗi từ API

                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<Promotion>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }


    public void updateProductComment(Products products){
        Call<ResponseDTO<Void>> call = ApiService.apiService.
                updateProducts(products);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call,
                                   Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Void> apiResponse = response.body();
                    //List<Orders> orders = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (apiResponse.getStatus() == 201 || apiResponse.getStatus() == 200) {
                        isUpdate.postValue(true);
                        Log.i("KMFG", "Done: ");
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    isUpdate.postValue(false);
                    Log.i("KMFG", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                isUpdate.postValue(false);
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

    private List<Promotion> handleListPromotion(List<Promotion> listShop) {
        List<Promotion> filteredList = new ArrayList<>();
        for (Promotion item : listShop) {
            if (!item.getStatus().equals("used")) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }
}
