package com.example.noworderfoodapp.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.databinding.FragmentLoginBinding;
import com.example.noworderfoodapp.entity.ResponseDTO;
import com.example.noworderfoodapp.entity.User;
import com.example.noworderfoodapp.view.act.MapActivity;
import com.example.noworderfoodapp.view.act.ShipActivity;
import com.example.noworderfoodapp.viewmodel.LoginViewModel;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {
    public static final String KEY_SHOW_HOME_FRAGMENT = "KEY_SHOW_HOME_FRAGMENT";
    public static final String KEY_SHOW_SIGN_UP_FRAGMENT = "KEY_SHOW_SIGN_UP_FRAGMENT";
    @Override
    protected Class<LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViews() {
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.loginUsernameAndPassword(binding.edtUsername.getText().toString(),
                        binding.edtPassword.getText().toString());
            }
        });

        mViewModel.getIsLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    if(mViewModel.getRoleUser().getValue().equals("SHIPPER")) {
                        startActivity(new Intent(getActivity(), ShipActivity.class));
                    } else {
                        callBack.callBack(KEY_SHOW_HOME_FRAGMENT,null);
                    }

                    mViewModel.setIsLogin(false);
                }
            }
        });
        binding.tvCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.callBack(KEY_SHOW_SIGN_UP_FRAGMENT,null);
            }
        });
    }


}
