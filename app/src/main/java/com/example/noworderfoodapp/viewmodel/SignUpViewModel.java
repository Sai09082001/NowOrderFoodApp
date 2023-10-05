package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.User;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();
    public SignUpViewModel(){

    }

    public void loginUsernameAndPassword(User user){
        Call<ResponseDTO<Void>> call = ApiService.apiService.createNewUser(user.getName(),user.getAge(),user.getUsername()
        ,user.getPassword(),user.getPhonenumber(),user.getHomeAddress(),user.getRoles());
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call, Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    // Phản hồi thành công
                    ResponseDTO<Void> responseDTO = response.body();
                    if (responseDTO != null && responseDTO.getStatus() == 200) {
                        // Xử lý phản hồi thành công
                        isLogin.postValue(true);
                        Log.i("KMFG", "onResponse: create done");
                    } else {
                        Log.i("KMFG", "onResponse: create fail");
                        // Xử lý phản hồi không thành công
                        isLogin.postValue(false);
                    }
                } else {
                    // Xử lý phản hồi không thành công
                    isLogin.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý lỗi
                isLogin.postValue(false);
                Log.i("KMFG", "onResponse: "+t.getMessage());
            }
        });
    }
}
