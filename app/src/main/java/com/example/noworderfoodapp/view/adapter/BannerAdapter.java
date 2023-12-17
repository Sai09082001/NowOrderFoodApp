package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.Banner;
import com.example.noworderfoodapp.entity.Promotion;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerHolder> {
    private List<Banner> listBanner;
    private final Context mContext;
    private OnItemClick callBack;

    public void setListBanner(List<Banner> listPromotion) {
        this.listBanner = listPromotion;
        notifyDataSetChanged();
    }

    public List<Banner> getListBanner() {
        return listBanner;
    }

    public BannerAdapter(List<Banner> listPromotion, Context context) {
        this.listBanner = listPromotion;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_banner, parent, false);

        return new BannerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {
        Banner data = listBanner.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvBannerName.setText(data.getName()); // lấy vị trí gán data tương ứng cho từng data
        holder.banner = data;
    }

    @Override
    public int getItemCount() {
        return listBanner.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(Banner promotion);
    }

    public class BannerHolder extends RecyclerView.ViewHolder {
        private TextView tvBannerName;
        private Banner banner;
        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            tvBannerName = itemView.findViewById(R.id.tv_name_banner);
            tvBannerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onItemClick(banner);
                }

            });
        }
    }
}

