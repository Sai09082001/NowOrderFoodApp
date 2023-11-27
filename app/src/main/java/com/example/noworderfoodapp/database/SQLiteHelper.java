package com.example.noworderfoodapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.noworderfoodapp.entity.FavoriteShop;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ShopFavorite.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE shop("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "userId INTEGER,shopId INTEGER)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    public List<FavoriteShop> getAll(){
        List<FavoriteShop> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "id DESC";
        Cursor rs = st.query("shop",null,null,
                null,null,null,order);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            int user = rs.getInt(1);
            int shop = rs.getInt(2);
            list.add(new FavoriteShop(id,user,shop));
        }
        return list;
    }

    public long addShop (FavoriteShop rg) {
        ContentValues values = new ContentValues();
        values.put("userId",rg.getUserId());
        values.put("shopId",rg.getShopId());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("shop",null,values);
    }

    public List<FavoriteShop> searchByUserId (String key) {
        List<FavoriteShop> list = new ArrayList<>();
        String whereClause = "userId like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("shop",null,whereClause,whereArgs,null,null,null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            int userId = rs.getInt(1);
            int shopId = rs.getInt(2);
            list.add(new FavoriteShop(id,userId,shopId));
        }
        return list;
    }

    public int update (FavoriteShop rg) {
        ContentValues values = new ContentValues();
        values.put("userId",rg.getUserId());
        values.put("shopId",rg.getShopId());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(rg.getId())};
        return sqLiteDatabase.update("shop",values,whereClause,whereArgs);
    }

    public int delete (int id) {
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("shop",whereClause,whereArgs);
    }

    public int deleteShopWithUser(int userId, int shopId) {
        String whereClause = "userId = ? AND shopId = ?";
        String[] whereArgs = {String.valueOf(userId), String.valueOf(shopId)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("shop", whereClause, whereArgs);
    }
}
