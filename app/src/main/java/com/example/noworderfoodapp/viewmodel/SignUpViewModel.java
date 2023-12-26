package com.example.noworderfoodapp.viewmodel;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.Constants;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isLogin = new MutableLiveData<>();

    private DatabaseReference databaseReference;
    public SignUpViewModel(){

    }

    public void upDataUser(User user){


        databaseReference = firebaseDatabase.getReference().child("users-reg").child(user.getId()+"");
        databaseReference.child("name").setValue(user.getName());
        databaseReference.child("age").setValue(user.getAge());
        databaseReference.child("username").setValue(user.getUsername());
        databaseReference.child("phonenumber").setValue(user.getPhonenumber());
        databaseReference.child("homeAddress").setValue(user.getHomeAddress());
        databaseReference.child("roles").setValue(user.getRoles());


        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                 //   App.getInstance().setUser(user);
                } else {
                    errorMessage.postValue(task.getException().toString());
                }
            }
        });
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
                        upDataUser(user);
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
