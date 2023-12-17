package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.CommonUtils;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Banner;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeShoeViewModel extends ViewModel {
    private MutableLiveData<List<Shop>> shopMutableLiveData;
    private MutableLiveData<List<Shop>> shopMasterMutableLiveData;
    private MutableLiveData<List<Banner>> bannerMutableLiveData;
    public MutableLiveData<List<Shop>> getMasterShopMutableLiveData() {
        return shopMasterMutableLiveData;
    }
    public MutableLiveData<List<Shop>> getShopMutableLiveData() {
        return shopMutableLiveData;
    }
    public MutableLiveData<List<Banner>> getBannerMutableLiveData() {
        return bannerMutableLiveData;
    }

    public void setBannerMutableLiveData(MutableLiveData<List<Banner>> bannerMutableLiveData) {
        this.bannerMutableLiveData = bannerMutableLiveData;
    }

    public HomeShoeViewModel(){
        bannerMutableLiveData = new MutableLiveData<>();
        shopMutableLiveData = new MutableLiveData<>();
        shopMasterMutableLiveData = new MutableLiveData<>();
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
                    List<Shop> listBranch = filterShopBranch(listShop);
                    List<Shop> masterShop = filterShopMaster(listShop);
                    // Xử lý dữ liệu User...
                    if (listShop != null) {
                        shopMutableLiveData.postValue(listBranch);
                        shopMasterMutableLiveData.postValue(masterShop);
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
    private List<Shop> filterShopMaster(List<Shop> listShop) {
        List<Shop> listData = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            listData = listShop.stream().filter(shop -> shop.getIsBranch() == 1).collect(Collectors.toList());
        } else {
            for (Shop shop : listShop) {
                if (shop.getIsBranch() == 1) {
                    listData.add(shop);
                }
            }
        }
        return listData;
    }

    private List<Shop> filterShopBranch(List<Shop> listShop) {
        List<Shop> listData = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
           listData = listShop.stream().filter(shop -> shop.getIsBranch() == 0).collect(Collectors.toList());
        } else {
            for (Shop shop : listShop) {
                if (shop.getIsBranch() == 0) {
                    listData.add(shop);
                }
            }
        }
        return listData;
    }


    public void getListBanner(){
        Call<ResponseDTO<List<Banner>>> call = ApiService.apiService.
                getListBanner();
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<Banner>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Banner>>> call,
                                   Response<ResponseDTO<List<Banner>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Banner>> apiResponse = response.body();
                    List<Banner> orders = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (orders != null) {
                        bannerMutableLiveData.postValue(orders);
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    Log.i("KMFG", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<Banner>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

}
