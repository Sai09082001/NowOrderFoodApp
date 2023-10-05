package com.example.noworderfoodapp.entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class OrderAddressEntity {
    private String name,address;
    private List<Orders> ordersList;

    public OrderAddressEntity(String name, String address, List<Orders> ordersList) {
        this.name = name;
        this.address = address;
        this.ordersList = ordersList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public String toString() {
        return "OrderAddressEntity{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", ordersList=" + ordersList +
                '}';
    }
}
