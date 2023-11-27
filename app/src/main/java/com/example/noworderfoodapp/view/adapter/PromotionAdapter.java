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
import com.example.noworderfoodapp.entity.Promotion;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionHolder> {
    private List<Promotion> listPromotion;
    private final Context mContext;
    private OnItemClick callBack;

    public void setListPromotion(List<Promotion> listPromotion) {
        this.listPromotion = listPromotion;
        notifyDataSetChanged();
    }

    public List<Promotion> getListPromotion() {
        return listPromotion;
    }

    public PromotionAdapter(List<Promotion> listPromotion, Context context) {
        this.listPromotion = listPromotion;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PromotionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_promotion, parent, false);

        return new PromotionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionHolder holder, int position) {
        Promotion data = listPromotion.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvPromotionName.setText(data.getName());
        if (containsPercent(data.getValue())) {
            holder.ivPromotion.setImageResource(R.drawable.ic_promotion_percent);
        } else {
            holder.ivPromotion.setImageResource(R.drawable.ic_promotion_reduce);
        }
        holder.promotion = data;
    }

    private boolean containsPercent(String inputString) {
        return inputString != null && inputString.contains("%");
    }

    @Override
    public int getItemCount() {
        return listPromotion.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(Promotion promotion);
    }

    public class PromotionHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnPromotion;
        private TextView tvPromotionName;
        private TextView tvUsePromotion;
        private ImageView ivPromotion;
        private Promotion promotion;
        public PromotionHolder(@NonNull View itemView) {
            super(itemView);
            lnPromotion = itemView.findViewById(R.id.ln_promotion);
            tvPromotionName = itemView.findViewById(R.id.tv_name_promotion);
            tvUsePromotion= itemView.findViewById(R.id.tv_use_promotion);
            ivPromotion= itemView.findViewById(R.id.iv_promotion);
            tvUsePromotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onItemClick(promotion);
                }

            });
        }
    }
}

