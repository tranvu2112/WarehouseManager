package com.tranvu1805.warehousemanager.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tranvu1805.warehousemanager.DTO.InvoiceDTO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;


public class InvoiceDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    public InvoiceDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Recycle")
    public List<InvoiceDTO> getList() {
        List<InvoiceDTO> invoiceDTOS = new ArrayList<>();
        Cursor c = db.rawQuery("select * from HoaDon", null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                int id = c.getInt(0);
                String number = c.getString(1);
                int idUser = c.getInt(2);
                int type = c.getInt(3);
                int quantity = c.getInt(4);
                String detail = c.getString(5);
                invoiceDTOS.add(new InvoiceDTO(id, number, idUser, type, quantity, detail));
            } while (c.moveToNext());
        }
        return invoiceDTOS;
    }

    public int addRow(InvoiceDTO u) {
        ContentValues values = new ContentValues();
        values.put("SoHoaDon", u.getNumber());
        values.put("MaTaiKhoan", u.getIdUser());
        values.put("LoaiHoaDon", u.getType());
        values.put("MoTa", u.getDetail());
        return (int) db.insert("HoaDon", null, values);
    }

    public int update(InvoiceDTO u) {
        String[] id = new String[]{String.valueOf(u.getId())};
        ContentValues values = new ContentValues();
        values.put("SoHoaDon", u.getNumber());
        values.put("MaTaiKhoan", u.getIdUser());
        values.put("LoaiHoaDon", u.getType());
        values.put("MoTa", u.getDetail());
        return db.update("HoaDon", values, "MaHoaDon=?", id);
    }

    public int delete(ProductDTO u) {
        String[] id = new String[]{String.valueOf(u.getId())};
        return db.delete("HoaDon", "MaSanPham=?", id);
    }
}


