package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> userMutableLiveData;

    public MutableLiveData<List<User>> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setShopMutableLiveData(List<User> userMutableLiveData) {
        this.userMutableLiveData.postValue(userMutableLiveData);
    }

    public UserViewModel(){
        userMutableLiveData = new MutableLiveData<>();
    }

    public void getListUserServer(){
        Call<ResponseDTO<List<User>>> call = ApiService.apiService.
                getListUser();
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<User>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<User>>> call,
                                   Response<ResponseDTO<List<User>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<User>> apiResponse = response.body();
                    List<User> listShop = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (listShop != null) {
                        userMutableLiveData.postValue(listShop);

                    }
                } else {
                    // Xử lý khi có lỗi từ API

                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<User>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }
}
