package com.example.noworderfoodapp;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<CartSession> cartSessionList = new ArrayList<>();

    public List<CartSession> getCartSessionList() {
        return cartSessionList;
    }

    public void setCartSessionList(List<CartSession> cartSessionList) {
        this.cartSessionList = cartSessionList;
    }
}