package com.tranvu1805.warehousemanager.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tranvu1805.warehousemanager.Model.UserDTO;
import com.tranvu1805.warehousemanager.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    public UserDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Recycle")
    public List<UserDTO> getList(){
        List<UserDTO> userDTOS = new ArrayList<>();
        Cursor c = db.rawQuery("select * from TaiKhoan",null);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                int id = c.getInt(0);
                String account = c.getString(1);
                String pass = c.getString(2);
                int role = c.getInt(3);
                String name = c.getString(4);
                String email =c.getString(5);
                userDTOS.add(new UserDTO(id,account,pass, role,name,email));
            }while (c.moveToNext());
        }
        return userDTOS;
    }
    public int addRow(UserDTO u){
        ContentValues values = new ContentValues();
        values.put("TaiKhoan",u.getAccount());
        values.put("MatKhau",u.getPass());
        values.put("VaiTro",u.getRole());
        values.put("HoTen",u.getName());
        values.put("Email",u.getEmail());
        return (int) db.insert("TaiKhoan",null,values);
    }
    public int update(UserDTO u){
        String[] id = new String[]{String.valueOf(u.getId())};
        ContentValues values = new ContentValues();
        values.put("TaiKhoan",u.getName());
        values.put("MatKhau",u.getPass());
        return db.update("TaiKhoan",values,"MaTaiKhoan=?",id);
    }
    public int delete(UserDTO u){
        String[] id = new String[]{String.valueOf(u.getId())};
        return db.delete("TaiKhoan","MaTaiKhoan=?",id);
    }
    @SuppressLint("Recycle")
    public UserDTO getLogin(String userName){
         Cursor c = db.rawQuery("select * from TaiKhoan where TaiKhoan=?",new String[]{userName});
        if(c!=null&&c.getCount()==1){
            c.moveToFirst();
            int id = c.getInt(0);
            String account = c.getString(1);
            String pass = c.getString(2);
            int role = c.getInt(3);
            String name = c.getString(4);
            String email =c.getString(5);
            return new UserDTO(id,account,pass, role,name,email);
        }else {
            return null;
        }
    }
}
