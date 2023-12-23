package com.example.noworderfoodapp;

import com.example.noworderfoodapp.entity.Products;
import com.example.noworderfoodapp.entity.Shop;

import java.util.ArrayList;
import java.util.HashMap;

public class CartSession {
    private Shop shop;
    private HashMap<Integer, Integer> productQuantityMap;
    private ArrayList<Products> productsList;

    public CartSession(Shop shop, HashMap<Integer, Integer> productQuantityMap, ArrayList<Products> productsList) {
        this.shop = shop;
        this.productQuantityMap = productQuantityMap;
        this.productsList = productsList;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public HashMap<Integer, Integer> getProductQuantityMap() {
        return productQuantityMap;
    }

    public void setProductQuantityMap(HashMap<Integer, Integer> productQuantityMap) {
        this.productQuantityMap = productQuantityMap;
    }

    public ArrayList<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(ArrayList<Products> productsList) {
        this.productsList = productsList;
    }
}
