package com.example.noworderfoodapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    public String getPassword() {
        return password;
    }

    @JsonProperty("id")
    private int id;

    public int getId() {
        return id;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("age")
    private int age;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("username")
    private String username;

    @JsonProperty("phonenumber")
    private String phonenumber;

    @JsonProperty("password")
    private String password;

    @JsonProperty("roles")
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    @JsonProperty("homeAddress")
    private String homeAddress;

    @JsonProperty("birthdate")
    private String birthdate;

    public User(){

    }

    public User(int id, int age, String name, String avatarUrl, String username,
                String phonenumber, String password, List<String> roles, String homeAddress,
                String birthdate) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.phonenumber = phonenumber;
        this.password = password;
        this.roles = roles;
        this.homeAddress = homeAddress;
        this.birthdate = birthdate;
    }

    public User(String name,  int age,String username,
                String password,  String phonenumber,  String homeAddress,
                List<String> roles) {
        this.age = age;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.phonenumber = phonenumber;
        this.password = password;
        this.roles = roles;
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", username='" + username + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", homeAddress='" + homeAddress + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}