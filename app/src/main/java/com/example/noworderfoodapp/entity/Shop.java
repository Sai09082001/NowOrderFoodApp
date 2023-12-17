package com.example.noworderfoodapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shop implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("isBranch")
    private int isBranch; // 0 is branch, 1 is manager
    @JsonProperty("categories")
    private List<Category> categories;

    public Shop(){

    }

    public Shop(int id, String name, String address, String phone,int isBranch, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isBranch='" + isBranch + '\'' +
                ", categories=" + categories +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(int isBranch) {
        this.isBranch = isBranch;
    }
}
