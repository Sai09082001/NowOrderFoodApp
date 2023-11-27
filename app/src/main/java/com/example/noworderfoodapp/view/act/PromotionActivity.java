package com.example.noworderfoodapp.view.act;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.databinding.ActivityPromotionBinding;
import com.example.noworderfoodapp.entity.Promotion;
import com.example.noworderfoodapp.view.adapter.PromotionAdapter;
import com.example.noworderfoodapp.viewmodel.PromotionViewModel;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends BaseActivity<ActivityPromotionBinding, PromotionViewModel> implements PromotionAdapter.OnItemClick {
        private PromotionAdapter promotionAdapter;
        private List<Promotion> listPromotion;

        @Override
        protected Class<PromotionViewModel> getViewModelClass() {
                return PromotionViewModel.class;
        }

        @Override
        protected void initViews() {
                listPromotion = new ArrayList<>();
                viewModel.getListPromotionServer();
                viewModel.getPromotionMutableLiveData().observe(this, new Observer<List<Promotion>>() {
                        @Override
                        public void onChanged(List<Promotion> promotions) {
                                listPromotion.clear();
                                listPromotion.addAll(promotions);
                                promotionAdapter.notifyDataSetChanged();
                                Log.i("KMFG", "initViews: "+listPromotion.toString());
                        }
                });
                //  setCallBack((OnActionCallBack) getActivity());
                promotionAdapter = new PromotionAdapter(listPromotion,this);
                LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
                binding.rcvPromotion.setLayoutManager(manager);
                binding.rcvPromotion.setAdapter(promotionAdapter);
                promotionAdapter.setOnItemClick(this);

        }


        @Override
        protected int getLayoutId() {
                return R.layout.activity_promotion;
        }


        @Override
        public void callBack(String key, Object data) {

        }

        @Override
        public void onItemClick(Promotion promotion) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("key_promotion", promotion); // Đặt dữ liệu kết quả vào Intent
                setResult(RESULT_OK, resultIntent); // Đặt kết quả và Intent
                finish();
        }
}

