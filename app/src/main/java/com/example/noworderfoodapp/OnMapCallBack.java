package com.example.noworderfoodapp;

import com.example.noworderfoodapp.entity.Orders;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface OnMapCallBack {
    void showAlert(String distance, LatLng start, LatLng end, String key);
}
