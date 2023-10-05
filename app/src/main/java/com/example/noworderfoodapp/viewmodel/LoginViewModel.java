package com.example.noworderfoodapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends ViewModel {
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();
    private MutableLiveData<String> roleUser = new MutableLiveData<>();
    public LoginViewModel(){
        userMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getRoleUser() {
        return roleUser;
    }

    public void loginUsernameAndPassword(String username, String password){
        Call<ResponseDTO<User>> call = ApiService.apiService.
                getUserNameAndPassword(username, password);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<User>>() {
            @Override
            public void onResponse(Call<ResponseDTO<User>> call,
                                   Response<ResponseDTO<User>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<User> apiResponse = response.body();
                    User user = apiResponse.getData();
                    // Xử lý dữ liệu User...
                    if (user != null) {
                       userMutableLiveData.postValue(user);
                       if (user.getRoles().get(0).isEmpty() || user.getRoles().get(0) == null) {
                           roleUser.postValue("USER");
                       } else {
                           roleUser.postValue(user.getRoles().get(0));
                       }
                       App.getInstance().setUser(user);
                       isLogin.postValue(true);
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    isLogin.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<User>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                isLogin.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin.setValue(isLogin);
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setUserMutableLiveData(User user) {
        this.userMutableLiveData.setValue(user);
    }
}
