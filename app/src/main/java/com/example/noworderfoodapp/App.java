package com.example.noworderfoodapp;

import android.app.Application;

import com.example.noworderfoodapp.entity.User;

public class App extends Application {
    private static App instance;
    private boolean isOrder;
    private User user;

    private Storage storage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    public static App getInstance() {
        if(instance == null) instance = new App();
        return instance;
    }
    public Storage getStorage(){
        return storage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        storage = new Storage();
        instance = this;
    }
}
