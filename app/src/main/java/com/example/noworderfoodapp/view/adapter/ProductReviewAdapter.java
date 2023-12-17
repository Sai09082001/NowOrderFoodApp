package com.example.noworderfoodapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noworderfoodapp.R;
import com.example.noworderfoodapp.entity.ProductReview;

import java.util.List;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ProductReviewHolder> {
    private List<ProductReview> productReviewList;
    private final Context mContext;
    private OnItemClick callBack;

    public void setListProductReview(List<ProductReview> listProductReview) {
        this.productReviewList = listProductReview;
        notifyDataSetChanged();
    }

    public List<ProductReview> getListProductReview() {
        return productReviewList;
    }

    public ProductReviewAdapter(List<ProductReview> listProductReview, Context context) {
        this.productReviewList = listProductReview;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ProductReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_product_comment, parent, false);

        return new ProductReviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductReviewHolder holder, int position) {
        ProductReview data = productReviewList.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvRate.setText(data.getRating()+""); // lấy vị trí gán data tương ứng cho từng data
        holder.tvComment.setText(data.getComment()); // lấy vị trí gán data tương ứng cho từng data
        holder.banner = data;
    }

    @Override
    public int getItemCount() {
        return productReviewList.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(ProductReview promotion);
    }

    public class ProductReviewHolder extends RecyclerView.ViewHolder {
        private TextView tvRate;
        private TextView tvComment;
        private ProductReview banner;
        public ProductReviewHolder(@NonNull View itemView) {
            super(itemView);
            tvRate = itemView.findViewById(R.id.tv_rate_promotion);
            tvComment = itemView.findViewById(R.id.tv_comment_product);
            tvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onItemClick(banner);
                }

            });
        }
    }
}

