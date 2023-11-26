package com.tranvu1805.warehousemanager.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    public CategoryDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Recycle")
    public List<CategoryDTO> getList(){
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        Cursor c = db.rawQuery("select * from TheLoai",null);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                categoryDTOList.add(new CategoryDTO(c.getInt(0),c.getString(1)));
            }while (c.moveToNext());
        }
        return categoryDTOList;
    }
    public int addRow(CategoryDTO c){
        ContentValues values = new ContentValues();
        values.put("TenLoai",c.getName());
        return (int) db.insert("TheLoai",null,values);
    }
    public int update(CategoryDTO c){
        String[] id = new String[]{String.valueOf(c.getId())};
        ContentValues values = new ContentValues();
        values.put("TenLoai",c.getName());
        return db.update("TheLoai",values,"MaLoai=?",id);
    }
    public int delete(CategoryDTO c){
        String[] id = new String[]{String.valueOf(c.getId())};
        db.delete("ChiTietHoaDon","MaSanPham in (select MaSanPham from SanPham where MaLoai=?)",id);
        db.delete("SanPham","MaSanPham = ?",id);
        return db.delete("TheLoai","MaLoai=?",id);
    }
}
