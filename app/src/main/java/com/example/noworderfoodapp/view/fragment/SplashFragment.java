package com.example.noworderfoodapp.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentSplashBinding;
import com.example.noworderfoodapp.viewmodel.SplashViewModel;

public class SplashFragment extends BaseFragment<FragmentSplashBinding, SplashViewModel> {
    public static final String KEY_SHOW_LOGIN_FRAGMENT = "KEY_SHOW_LOGIN_FRAGMENT";
    @Override
    protected Class<SplashViewModel> getViewModelClass() {
        return SplashViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initViews() {
        new Handler().postDelayed(this::gotoSignIn, 2000);

    }

    private void gotoSignIn(){
        callBack.callBack(KEY_SHOW_LOGIN_FRAGMENT, null);
    }
}
