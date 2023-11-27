package com.example.noworderfoodapp.entity;

public class FavoriteShop {
    private int id;
    private int userId;
    private int shopId;

    public FavoriteShop() {

    }

    public FavoriteShop(int userId, int shopId) {
        this.userId = userId;
        this.shopId = shopId;
    }


    public FavoriteShop(int id, int userId, int shopId) {
        this.id = id;
        this.userId = userId;
        this.shopId = shopId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "FavoriteShop{" +
                "id=" + id +
                ", userId=" + userId +
                ", shopId=" + shopId +
                '}';
    }
}
