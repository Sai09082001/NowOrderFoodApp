package com.example.noworderfoodapp.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.User;

import java.util.List;
import java.util.stream.Collectors;

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
    @RequiresApi(api = Build.VERSION_CODES.N)
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
                    List<User> listUser = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (listUser != null) {
                        List<User> listData = filterWithUser(listUser);
                        userMutableLiveData.postValue(listData);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<User>  filterWithUser(List<User> listUser) {
        List<User> admins = listUser.stream()
                .filter(user -> user.getRoles() != null && user.getRoles().contains("ADMIN"))
                .collect(Collectors.toList());
        return admins;
    }
}
