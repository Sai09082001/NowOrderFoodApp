package com.example.noworderfoodapp.view.fragment;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.FragmentHomeBinding;
import com.example.noworderfoodapp.view.adapter.HomePagerAdapter;
import com.example.noworderfoodapp.view.fragment.BaseFragment;
import com.example.noworderfoodapp.viewmodel.LoginViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, LoginViewModel> {

    @Override
    protected Class<LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        setUpAdapter();
        binding.bottomNaviHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        binding.vpHome.setCurrentItem(0);
                        break;
                    case R.id.action_shop:
                        binding.vpHome.setCurrentItem(1);
                        break;
                    case R.id.action_order:
                        binding.vpHome.setCurrentItem(2);
                        break;
                    case R.id.action_profile:
                        binding.vpHome.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    private void setUpAdapter() {
        HomePagerAdapter adapter = new HomePagerAdapter(getActivity().getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.vpHome.setAdapter(adapter);
        binding.vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0 :
                        binding.bottomNaviHome.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1 :
                        binding.bottomNaviHome.getMenu().findItem(R.id.action_shop).setChecked(true);
                        break;
                    case 2 :
                        binding.bottomNaviHome.getMenu().findItem(R.id.action_order).setChecked(true);
                        break;
                    case 3 :
                        binding.bottomNaviHome.getMenu().findItem(R.id.action_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}