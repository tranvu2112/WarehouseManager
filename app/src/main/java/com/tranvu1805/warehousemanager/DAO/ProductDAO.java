package com.tranvu1805.warehousemanager.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    public ProductDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Recycle")
    public List<ProductDTO> getList(){
        List<ProductDTO> productDTOS = new ArrayList<>();
        Cursor c = db.rawQuery("select * from SanPham",null);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                int id = c.getInt(0);
                int idCat = c.getInt(1);
                String name = c.getString(2);
                int price = c.getInt(3);
                int quantity = c.getInt(4);
                String detail =c.getString(5);
                productDTOS.add(new ProductDTO(id,idCat,name, price,quantity,detail));
            }while (c.moveToNext());
        }
        return productDTOS;
    }
    public int addRow(ProductDTO u){
        ContentValues values = new ContentValues();
        values.put("MaLoai",u.getIdCat());
        values.put("TenSanPham",u.getName());
        values.put("Gia",u.getPrice());
        values.put("SoLuong",u.getQuantity());
        values.put("MoTa",u.getDetail());
        return (int) db.insert("SanPham",null,values);
    }
    public int update(ProductDTO u){
        String[] id = new String[]{String.valueOf(u.getId())};
        ContentValues values = new ContentValues();
        values.put("MaLoai",u.getIdCat());
        values.put("TenSanPham",u.getName());
        values.put("Gia",u.getPrice());
        values.put("SoLuong",u.getQuantity());
        values.put("MoTa",u.getDetail());
        return db.update("SanPham",values,"MaSanPham=?",id);
    }


    public int delete(ProductDTO u){
        String[] id = new String[]{String.valueOf(u.getId())};
        db.delete("ChiTietHoaDon","MaSanPham=?",id);
        return db.delete("SanPham","MaSanPham=?",id);
    }
}
