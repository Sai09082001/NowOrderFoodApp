package com.example.noworderfoodapp.view.act;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.noworderfoodapp.OnActionCallBack;
import com.example.noworderfoodapp.R;

public abstract class BaseActivity<BD extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity
        implements OnActionCallBack {

    protected  BD binding;
    protected  VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initViews();
    }


    protected abstract Class<VM> getViewModelClass();

    protected  abstract void initViews();
    protected abstract int getLayoutId();

    protected void showFragment(int layoutID, Fragment fragment, boolean addToBackStack){

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_view,fragment);
        if(addToBackStack){
            transaction.addToBackStack("replace");
        }
        transaction.commit();
    }

    protected void addFragment(int layoutID, Fragment fragment, boolean addToBackStack){

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_view,fragment);
        if(addToBackStack){
            transaction.addToBackStack("add");
        }
        transaction.commit();
    }
}
