package com.example.noworderfoodapp.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Promotion;
import com.example.noworderfoodapp.entity.ResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionViewModel extends ViewModel {
    private MutableLiveData<List<Promotion>> promotionMutableLiveData;

    public MutableLiveData<List<Promotion>> getPromotionMutableLiveData() {
        return promotionMutableLiveData;
    }

    public void setPromotionMutableLiveData(List<Promotion> promotionMutableLiveData) {
        this.promotionMutableLiveData.postValue(promotionMutableLiveData);
    }

    public PromotionViewModel(){
        promotionMutableLiveData = new MutableLiveData<>();
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
