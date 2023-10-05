package com.example.noworderfoodapp.entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PlaceEntity {
    private final String name,address;
    private final String photoBG;
    private LatLng location;

    public PlaceEntity(LatLng location,String name, String address, String photoBG) {
        this.location=location;
        this.name = name;
        this.address = address;
        this.photoBG = photoBG;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public String getPhotoBG() {
        return photoBG;
    }

    public LatLng getLocation() {
        return location;
    }
}