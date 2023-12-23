package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noworderfoodapp.App;
import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.api.ApiClient;
import com.example.noworderfoodapp.entity.Category;
import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private List<Object> items;
    private OnItemClick callBack;

    public void setCallBack(OnItemClick callBack) {
        this.callBack = callBack;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public ShopDetailAdapter(List<Object> items, Context context) {
        this.items = items;
        this.mContext = context;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView tvHeader;
        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_category_header);
        }
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName;
        private TextView tvDescription;
        private TextView tvPrice;
        private ImageView ivProduct, ivAddProduct;
        private Products products;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivAddProduct = itemView.findViewById(R.id.iv_add_product);
            ivProduct = itemView.findViewById(R.id.iv_product);
            ivAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onItemClick(products);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        if (item instanceof Category) {
            return 0;
        } else if (item instanceof Products) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View vHeader = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_category_header, parent, false);
                return new HeaderHolder(vHeader);
            case 1:
                View vProduct = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_product, parent, false);
                return new ProductHolder(vProduct);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Object item = items.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                Category data = (Category) item; // lấy vị trí gán data tương ứng cho từng data
                headerHolder.tvHeader.setText(data.getName());
                break;

            case 1:
                ProductHolder productHolder = (ProductHolder) holder;
                // lấy vị trí gán data tương ứng cho từng data
                Products dataProduct = (Products) item;
                productHolder.tvProductName.setText(dataProduct.getName());
                productHolder.tvDescription.setText(dataProduct.getDescription());
                productHolder.tvPrice.setText(dataProduct.getPrice()+"");
                Glide.with(mContext).load(dataProduct.getImageUrl()).into(productHolder.ivProduct);
                productHolder.products = dataProduct;
                break;
        }
    }

    public interface OnItemClick {
        void onItemClick(Products products);
    }
}

