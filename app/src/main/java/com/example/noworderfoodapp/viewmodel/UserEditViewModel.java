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

public class UserEditViewModel extends ViewModel {
    private MutableLiveData<Boolean> isEdit;

    public UserEditViewModel(){
        isEdit = new MutableLiveData<>();
    }

    public void editUserSession(String name,String phoneNumber,String homeAddress){
        Call<ResponseDTO<Void>> call = ApiService.apiService.
                editUser(App.getInstance().getUser().getId(),
                        App.getInstance().getUser().getAge(),name,phoneNumber,homeAddress);
        //   Call<ResponseDTO<List<User>>> call = ApiService.apiService.getListUser();
        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call,
                                   Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Void> apiResponse = response.body();
                    // Xử lý dữ liệu User...
                    if (apiResponse.getStatus() == 200) {
                        isEdit.postValue(true);
                        //App.getInstance().setUser(user);
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    isEdit.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                isEdit.postValue(false);
                Log.i("KMFG", "onFailure: "+t.getMessage());
            }
        });
    }

    public MutableLiveData<Boolean> getIsEdit() {
        return isEdit;
    }

    public void setIsLogin(boolean isEdit) {
        this.isEdit.setValue(isEdit);
    }

    public void editUserSession() {
        Call<ResponseDTO<User>> call = ApiService.apiService.getUserById(App.getInstance().getUser().getId());
        call.enqueue(new Callback<ResponseDTO<User>>() {
            @Override
            public void onResponse(Call<ResponseDTO<User>> call, Response<ResponseDTO<User>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<User> apiResponse = response.body();
                    User user = apiResponse.getData();
                    if (user != null) {
                        App.getInstance().setUser(user);
                        // Xử lý dữ liệu userDTO
                    } else {
                        // Xử lý phản hồi không thành công
                    }
                } else {
                    // Xử lý phản hồi không thành công
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<User>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }
}
