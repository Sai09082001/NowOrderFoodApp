package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.Promotion;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Boolean> isSuccess;

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess.postValue(isSuccess);
    }

    public CartViewModel(){
        isSuccess = new MutableLiveData<>();
    }

    public void postOrdersData(Orders orders){
        Call<ResponseDTO<Void>> call = ApiService.apiService.
                postOrderData(orders);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call,
                                   Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Void> apiResponse = response.body();
                    // Xử lý dữ liệu User...
                    if (apiResponse.getStatus() == 200 || apiResponse.getStatus() ==201) {
                        isSuccess.postValue(true);
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                     isSuccess.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                isSuccess.postValue(false);
                Log.i("KMFG", "onResponse: fail");
            }
        });
    }

    public void updatePromotion(Promotion promotion){
        Call<ResponseDTO<Void>> call = ApiService.apiService.
                updatePromotion(promotion);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call,
                                   Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Void> apiResponse = response.body();
                    // Xử lý dữ liệu User...
                    if (apiResponse.getStatus() == 200 || apiResponse.getStatus() ==201) {
                        Log.i("KMFG", "updatePromotion: oke");
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    Log.i("KMFG", "updatePromotion: fail");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                isSuccess.postValue(false);
                Log.i("KMFG", "onResponse: fail");
            }
        });
    }

}
