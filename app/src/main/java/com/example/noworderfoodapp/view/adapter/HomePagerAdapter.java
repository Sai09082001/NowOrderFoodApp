package com.example.noworderfoodapp.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.noworderfoodapp.view.fragment.FavoriteShopFragment;
import com.example.noworderfoodapp.view.fragment.HomeShoeFragment;
import com.example.noworderfoodapp.view.fragment.OrderFragment;
import com.example.noworderfoodapp.view.fragment.ProfileFragment;
import com.example.noworderfoodapp.view.fragment.ShopFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    public HomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return 4;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return new HomeShoeFragment();
            case 1 :
                return new ShopFragment();
            case 2 :
                return new OrderFragment();
            case 3 :
                return new ProfileFragment();
            default:
                return new ShopFragment();
        }
    }

}