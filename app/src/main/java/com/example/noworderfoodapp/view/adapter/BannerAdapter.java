package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.Banner;

import java.util.List;
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerHolder> {
    private List<Banner> listBanner;
    private final Context mContext;
    private OnItemClick callBack;

    private Handler handler;
    private int currentPosition;


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
        handler = new Handler(Looper.getMainLooper());
        currentPosition = 0;
        startBannerChange();
    }

    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_banner, parent, false);

        return new BannerHolder(v);
    }

    private void startBannerChange() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listBanner != null && listBanner.size() > 1) {
                    currentPosition = (currentPosition + 1) % listBanner.size();
                    notifyDataSetChanged();
                    handler.postDelayed(this, 5000); // Change banner every 5 seconds
                }
            }
        }, 5000); // Initial delay before the first banner change
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {
        Banner data = listBanner.get(currentPosition); // lấy vị trí gán data tương ứng cho từng data
        holder.tvBannerName.setText(data.getName()); // lấy vị trí gán data tương ứng cho từng data
        Glide.with(mContext).load(data.getAvatarUrl()).into(holder.ivBanner);
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
        private ImageView ivBanner;
        private Banner banner;
        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            tvBannerName = itemView.findViewById(R.id.tv_name_banner);
            ivBanner = itemView.findViewById(R.id.iv_banner);
            tvBannerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onItemClick(banner);
                }

            });
        }
    }
}