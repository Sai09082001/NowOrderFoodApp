package com.example.noworderfoodapp.view.act;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.ActivityCustomerServiceBinding;
import com.example.noworderfoodapp.entity.User;
import com.example.noworderfoodapp.view.adapter.ListUserAdapter;
import com.example.noworderfoodapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceActivity extends BaseActivity<ActivityCustomerServiceBinding, UserViewModel> implements ListUserAdapter.OnItemClick {
    private ListUserAdapter usersAdapter;
    private List<User> listUser;
    @Override
    protected Class<UserViewModel> getViewModelClass() {
        return UserViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }


    @Override
    protected void initViews() {
        listUser = new ArrayList<>();
        if (App.getInstance().getUser().getRoles().get(0).equals("ADMIN")) {
            viewModel.getListUserServer();
        } else {
            viewModel.getListAdmin();
        }

        viewModel.getUserMutableLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                listUser.clear();
                listUser.addAll(users);
                usersAdapter.notifyDataSetChanged();
                Log.i("KMFG", "initViews: "+listUser.toString());
            }
        });
        //  setCallBack((OnActionCallBack) getActivity());
        usersAdapter = new ListUserAdapter(listUser,this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvUsers.setLayoutManager(manager);
        binding.rcvUsers.setAdapter(usersAdapter);
        usersAdapter.setOnItemClick(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        List<User> usersList = viewModel.getUserMutableLiveData().getValue();
        if (usersList != null) {
            usersAdapter.setListUser(usersList);
        }
        usersAdapter.setListUser(listUser);
        binding.lnUserList.setVisibility(View.VISIBLE);
    }

    @Override
    public void callBack(String key, Object data) {

    }


    @Override
    public void onItemClick(User user) {
        Intent intent=new Intent(this,ChatActivity.class);
        intent.putExtra("RECEIVER_USER", user);
        startActivity(intent);
    }
}
