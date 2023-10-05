package com.example.noworderfoodapp.view.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentShopBinding;
import com.example.noworderfoodapp.databinding.FragmentUserBinding;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.entity.User;
import com.example.noworderfoodapp.view.act.ShopDetailActivity;
import com.example.noworderfoodapp.view.adapter.ListUserAdapter;
import com.example.noworderfoodapp.view.adapter.ShopAdapter;
import com.example.noworderfoodapp.viewmodel.ShopViewModel;
import com.example.noworderfoodapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends BaseFragment<FragmentUserBinding, UserViewModel> {
    public static final String KEY_SHOW_SHOP_DETAIL = "KEY_SHOW_SHOP_DETAIL";
    private ListUserAdapter usersAdapter;
    private List<User> listUser;
    @Override
    protected Class<UserViewModel> getViewModelClass() {
        return UserViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initViews() {
        listUser = new ArrayList<>();
        mViewModel.getListUserServer();
        mViewModel.getUserMutableLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                listUser.clear();
                listUser.addAll(users);
                usersAdapter.notifyDataSetChanged();
                Log.i("KMFG", "initViews: "+listUser.toString());
            }
        });
        //  setCallBack((OnActionCallBack) getActivity());
        usersAdapter = new ListUserAdapter(listUser,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvUsers.setLayoutManager(manager);
        binding.rcvUsers.setAdapter(usersAdapter);
       // usersAdapter.setOnItemClick(this);
    }

//    @Override
//    public void onItemClick(Shop shop) {
//        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
//        intent.putExtra("category",(ArrayList<Category>) shop.getCategories());
//        intent.putExtra("shop", shop);
//        getActivity().startActivity(intent);
//    }

    @Override
    public void onResume() {
        super.onResume();
        //
        List<User> usersList = mViewModel.getUserMutableLiveData().getValue();
        if (usersList != null) {
            usersAdapter.setListUser(usersList);
        }
        usersAdapter.setListUser(listUser);
        binding.lnUserList.setVisibility(View.VISIBLE);
    }

}
