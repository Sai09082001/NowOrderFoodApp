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
import com.example.noworderfoodapp.entity.Shop;
import com.example.noworderfoodapp.entity.User;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.UserHolder> {
    private List<User> listUser;
    private final Context mContext;
    private OnItemClick callBack;

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    public ListUserAdapter(List<User> listUser, Context context) {
        this.listUser = listUser;
        this.mContext = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_user, parent, false);

        return new UserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User data = listUser.get(position); // lấy vị trí gán data tương ứng cho từng data
        holder.tvUserName.setText(data.getName());
        holder.tvUserAddress.setText(data.getHomeAddress());
        holder.tvUserPhone.setText(data.getPhonenumber());
        holder.user = data;
        if ( App.getInstance().getUser().getAvatarUrl() != null){
            Glide.with(mContext).load(App.getInstance().getUser().getAvatarUrl()).into(holder.ivUser);
        } else {
            Glide.with(mContext).load(""+ApiClient.BASE_URL+"/user/download?filename="+
                    App.getInstance().getUser().getAvatarUrl()).into(holder.ivUser);
        }
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }


    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    public interface OnItemClick {
        void onItemClick(User user);
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName;
        private TextView tvUserAddress;
        private TextView tvUserPhone;
        private ImageView ivUser;
        private User user;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserAddress= itemView.findViewById(R.id.tv_address_user);
            tvUserPhone= itemView.findViewById(R.id.tv_phone_shop);
            ivUser = itemView.findViewById(R.id.iv_user_service);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_click));
                    callBack.onItemClick(user);
                }

            });
        }
    }
}

