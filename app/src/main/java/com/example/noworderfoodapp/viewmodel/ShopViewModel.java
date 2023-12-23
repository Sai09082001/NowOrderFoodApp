package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopViewModel extends ViewModel {
    private MutableLiveData<List<Category>> categoryMutableLiveData;

    public MutableLiveData<List<Category>> getCategoryMutableLiveData() {
        return categoryMutableLiveData;
    }

    public void setShopMutableLiveData(List<Category> shopMutableLiveData) {
        this.categoryMutableLiveData.postValue(shopMutableLiveData);
    }
    private MutableLiveData<List<Products>> productsMutableLiveData;

    public MutableLiveData<List<Products>> getProductsMutableLiveData() {
        return productsMutableLiveData;
    }
    public ShopViewModel(){
        categoryMutableLiveData = new MutableLiveData<>();
        productsMutableLiveData = new MutableLiveData<>();
    }

    public void getListShopServer(){
        Call<ResponseDTO<List<Category>>> call = ApiService.apiService.
                getListCategory();
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<Category>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Category>>> call,
                                   Response<ResponseDTO<List<Category>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Category>> apiResponse = response.body();
                    List<Category> listShop = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (listShop != null) {
                        categoryMutableLiveData.postValue(listShop);

                    }
                } else {
                    // Xử lý khi có lỗi từ API

                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<Category>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

    public void getListProducts(){
        Call<ResponseDTO<List<Products>>> call = ApiService.apiService.
                getListProducts();
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<Products>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Products>>> call,
                                   Response<ResponseDTO<List<Products>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Products>> apiResponse = response.body();
                    List<Products> orders = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (orders != null) {
                        productsMutableLiveData.postValue(orders);
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    Log.i("KMFG", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<Products>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }
}
