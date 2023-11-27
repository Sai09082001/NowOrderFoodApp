package com.example.noworderfoodapp;

import android.annotation.SuppressLint;

import com.example.noworderfoodapp.database.SQLiteHelper;
import com.example.noworderfoodapp.entity.FavoriteShop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtils {
    private static CommonUtils instance;
    private CommonUtils() {
        //for singleton
    }

    public static CommonUtils getInstance(){
        if(instance==null){
            instance = new CommonUtils();
        }
        return instance;
    }

    @SuppressLint("SimpleDateFormat")
    public String convertDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateString = dateFormat.format(date);
        return dateString;
    }
    @SuppressLint("SimpleDateFormat")
    public String convertDateTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);
        return dateString;
    }
    public List<FavoriteShop> getListFavoriteWithUserSession(int id){
        SQLiteHelper db = new SQLiteHelper(App.getInstance());
        List<FavoriteShop> list = db.searchByUserId(id+"");
        return list;
    }
    public String getCurrentDate(){
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(currentDate);
    }

}
