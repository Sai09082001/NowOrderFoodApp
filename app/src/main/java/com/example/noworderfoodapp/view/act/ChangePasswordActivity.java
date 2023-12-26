package com.example.noworderfoodapp.view.act;

import android.view.View;

import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.api.ApiClient;
import com.example.noworderfoodapp.databinding.ActivityChangePasswordBinding;
import com.example.noworderfoodapp.databinding.ActivityEditUserBinding;
import com.example.noworderfoodapp.viewmodel.UserEditViewModel;

public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding, UserEditViewModel> {

    @Override
    protected Class<UserEditViewModel> getViewModelClass() {
        return UserEditViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initViews() {

       binding.tvEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               viewModel.changePasswordSession(binding.tvOldpassword.getText().toString(),binding.tvNewpassword.getText().toString()
               );
           }
       });
        viewModel.getIsChangePassword().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    finish();
                }
            }
        });
    }

    @Override
    public void callBack(String key, Object data) {

    }
}
