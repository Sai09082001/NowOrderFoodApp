package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.CommonUtils;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.Orders;
import com.example.noworderfoodapp.entity.ResponseDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<List<Orders>> ordersMutableLiveData;

    public MutableLiveData<List<Orders>> getOrdersMutableLiveData() {
        return ordersMutableLiveData;
    }

    public void setOrdersMutableLiveData(MutableLiveData<List<Orders>> ordersMutableLiveData) {
        this.ordersMutableLiveData = ordersMutableLiveData;
    }

    public OrderViewModel(){
       ordersMutableLiveData = new MutableLiveData<>();
    }

    public void getOrderByUser(int id){
        Call<ResponseDTO<List<Orders>>> call = ApiService.apiService.
                searchOrderByUser(id);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<List<Orders>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Orders>>> call,
                                   Response<ResponseDTO<List<Orders>>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<List<Orders>> apiResponse = response.body();
                    List<Orders> orders = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (orders != null) {
                        ordersMutableLiveData.postValue(orders);
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    Log.i("KMFG", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<List<Orders>>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

    public List<Orders> filterOrderByDate(){
        List<Orders> listData = ordersMutableLiveData.getValue();
        List<Orders> filteredList = new ArrayList<>();
        for (Orders item : listData) {
            if (CommonUtils.getInstance().convertDateTime(item.getCreatedAt()).equals(CommonUtils.getInstance().getCurrentDate())) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

}
