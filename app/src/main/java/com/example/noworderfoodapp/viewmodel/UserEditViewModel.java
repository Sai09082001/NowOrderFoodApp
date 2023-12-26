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
    private MutableLiveData<Boolean> isChangePassword;


    public UserEditViewModel(){
        isEdit = new MutableLiveData<>();
        isChangePassword = new MutableLiveData<>();
    }

    public void editUserSession(String name,int age,String phoneNumber,String homeAddress){
        Call<ResponseDTO<Void>> call = ApiService.apiService.
                editUser(App.getInstance().getUser().getId(),age,name,phoneNumber,homeAddress);
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


    //Change Password

    public void changePasswordSession(String oldPassword, String newPassword) {
        // Kiểm tra mật khẩu cũ trước khi gọi API
        if (!isOldPasswordCorrect(oldPassword)) {
            // Mật khẩu cũ không đúng, hiển thị thông báo hoặc thực hiện xử lý tương ứng
            isChangePassword.postValue(false);
            return;
        }

        Call<ResponseDTO<Void>> call = ApiService.apiService.changePassword(
                App.getInstance().getUser().getId(),
                newPassword
        );

        call.enqueue(new Callback<ResponseDTO<Void>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Void>> call, Response<ResponseDTO<Void>> response) {
                if (response.isSuccessful()) {
                    ResponseDTO<Void> apiResponse = response.body();
                    if (apiResponse.getStatus() == 200) {
                        // Xử lý khi thay đổi mật khẩu thành công
                        isChangePassword.postValue(true);
                    } else {
                        // Xử lý khi thay đổi mật khẩu không thành công
                        isChangePassword.postValue(false);
                    }
                } else {
                    // Xử lý khi có lỗi từ API
                    isChangePassword.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Void>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                isChangePassword.postValue(false);
                Log.i("KMFG", "onFailure: " + t.getMessage());
            }
        });
    }

    private boolean isOldPasswordCorrect(String enteredOldPassword) {
        // Kiểm tra mật khẩu cũ có trùng khớp với mật khẩu lưu trên máy chủ hay không
        // Đây là nơi bạn có thể thực hiện các xác nhận mật khẩu cũ
        String storedPassword = App.getInstance().getUser().getPassword();
        return enteredOldPassword.equals(storedPassword);
    }
    public MutableLiveData<Boolean> getIsChangePassword() {
        return isChangePassword;
    }


}
