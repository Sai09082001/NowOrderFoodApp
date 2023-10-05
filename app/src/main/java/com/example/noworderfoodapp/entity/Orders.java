package com.example.noworderfoodapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("orderItems")
    private List<OrderItems> orderItems;

    @JsonProperty("price")
    private double price;

    @JsonProperty("comment")
    private String comment;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("user")
    private User user;

    @JsonProperty("shipper")
    private User shipper;

    @JsonProperty("shopname")
    private String shopname;

    @JsonProperty("states")
    private String states;

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    @JsonProperty("address")
    private String address;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Orders(){

    }

    public Orders(int id, List<OrderItems> orderItems, double price, String comment, User user,User shipper,
                  String shopname, String states, String address) {
        this.id = id;
        this.orderItems = orderItems;
        this.price = price;
        this.comment = comment;
        this.user = user;
        this.shipper = shipper;
        this.shopname = shopname;
        this.states =states;
        this.address = address;
    }

    public Orders(List<OrderItems> orderItems, double price, String comment, User user,
                  String shopname, String states, String address) {
        this.orderItems = orderItems;
        this.price = price;
        this.comment = comment;
        this.user = user;
        this.shopname = shopname;
        this.states = states;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShopName() {
        return shopname;
    }

    public void setShopName(String shopName) {
        this.shopname = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getShipper() {
        return shipper;
    }

    public void setShipper(User shipper) {
        this.shipper = shipper;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", price=" + price +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", shipper=" + shipper +
                ", shopname='" + shopname + '\'' +
                ", states=" + states +
                ", address='" + address + '\'' +
                '}';
    }
}