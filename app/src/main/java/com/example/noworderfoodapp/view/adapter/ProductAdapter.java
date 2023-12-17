package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.api.ApiClient;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.User;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Products> productsList;
    private final Context mContext;
    private OnItemClick callBack;

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
        notifyDataSetChanged();
    }

    public List<Products> getListProducts() {
        return productsList;
    }


    public ProductAdapter(List<Products> productsList, Context context) {
        this.productsList = productsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_shoe, parent, false);

        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Products data = productsList.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvProductName.setText(data.getName());
        holder.tvProductPrice.setText(data.getPrice()+"");
        holder.products = data;
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(Products products);
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName;
        private TextView tvProductPrice;

        private Products products;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice= itemView.findViewById(R.id.tv_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_click));
                    callBack.onItemClick(products);
                }

            });
        }
    }
}

