package com.example.noworderfoodapp.view.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.OnActionCallBack;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.api.ApiClient;
import com.example.noworderfoodapp.api.ApiService;
import com.example.noworderfoodapp.databinding.FragmentOrderBinding;
import com.example.noworderfoodapp.databinding.FragmentProfileBinding;
import com.example.noworderfoodapp.databinding.FragmentSplashBinding;
import com.example.noworderfoodapp.view.act.CustomerServiceActivity;
import com.example.noworderfoodapp.view.act.EditUserActivity;
import com.example.noworderfoodapp.view.act.MainActivity;
import com.example.noworderfoodapp.view.act.PromotionActivity;
import com.example.noworderfoodapp.viewmodel.ProfileViewModel;
import com.example.noworderfoodapp.viewmodel.SplashViewModel;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {

    @Override
    protected Class<ProfileViewModel> getViewModelClass() {
        return ProfileViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initViews() {
       binding.tvNameProfile.setText(App.getInstance().getUser().getUsername());
       if ( App.getInstance().getUser().getAvatarUrl() == null){
           binding.profileImage.setImageResource(R.drawable.ic_user_receiver);
       } else {
           Glide.with(this).load(""+ApiClient.BASE_URL+"/user/download?filename="+
                   App.getInstance().getUser().getAvatarUrl()).into(binding.profileImage);
       }
       binding.frEditUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), EditUserActivity.class);
               startActivity(intent);
           }
       });
        binding.frPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PromotionActivity.class);
                startActivity(intent);
            }
        });
        binding.frCustomerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomerServiceActivity.class);
                startActivity(intent);
            }
        });
        binding.btLogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(),MainActivity.class);
               startActivity(intent);
               getActivity().finish();
           }
       });
    }
}
