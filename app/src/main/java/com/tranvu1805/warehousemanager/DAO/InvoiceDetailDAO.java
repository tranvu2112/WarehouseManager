package com.tranvu1805.warehousemanager.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    public InvoiceDetailDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Recycle")
    public List<InvoiceDetailDTO> getList() {
        List<InvoiceDetailDTO> invoiceDetailDTOS = new ArrayList<>();
        Cursor c = db.rawQuery("select * from ChiTietHoaDon", null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                int idPro = c.getInt(0);
                int idInv = c.getInt(1);
                int quantity = c.getInt(2);
                int price = c.getInt(3);
                invoiceDetailDTOS.add(new InvoiceDetailDTO(idPro, idInv, quantity, price));
            } while (c.moveToNext());
        }
        return invoiceDetailDTOS;
    }

    public ArrayList<InvoiceDetailDTO> getListInvoice(int idInvoice) {
        ArrayList<InvoiceDetailDTO> invoiceDetailDTOS2 = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from ChiTietHoaDon where MaHoaDon=?", new String[]{String.valueOf(idInvoice)});
        Log.d("zzzz", "cursor: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int idPro = cursor.getInt(0);
                int idInv = cursor.getInt(1);
                int quantity = cursor.getInt(2);
                int price = cursor.getInt(3);
                invoiceDetailDTOS2.add(new InvoiceDetailDTO(idPro, idInv, quantity, price));
            } while (cursor.moveToNext());
        }
        return invoiceDetailDTOS2;
    }

    public int addRow(InvoiceDetailDTO u) {
        ContentValues values = new ContentValues();
        values.put("MaSanPham", u.getIdProduct());
        values.put("MaHoaDon", u.getIdInvoice());
        values.put("SoLuong", u.getQuantity());
        values.put("DonGia", u.getPrice());
        return (int) db.insert("ChiTietHoaDon", null, values);
    }

    @SuppressLint("Recycle")
    public int getSumMoney(String dateStart,String dateEnd, int type) {
        int sum=0;
        Cursor c = db.rawQuery("SELECT SUM ( thanh_tien )FROM  (SELECT soluong*dongia as thanh_tien FROM chitiethoadon \n" +
                "where MaHoaDon in (select ct.MaHoaDon from chitiethoadon ct join hoadon hd on hd.MaHoaDon = ct.MaHoaDon where hd.LoaiHoaDon=? and hd.ngaythang>=? and hd.ngaythang <=?))",
                new String[]{String.valueOf(type), dateStart,dateEnd});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                int a = c.getInt(0);
                sum+=a;
            }while (c.moveToNext());
            return sum;
        }
        return sum;
    }

    public int update(InvoiceDetailDTO u) {
        String[] id = new String[]{String.valueOf(u.getIdProduct()), String.valueOf(u.getIdInvoice())};
        ContentValues values = new ContentValues();
        values.put("MaSanPham", u.getIdProduct());
        values.put("MaHoaDon", u.getIdInvoice());
        values.put("SoLuong", u.getQuantity());
        values.put("DonGia", u.getPrice());
        return db.update("ChiTietHoaDon", values, "MaHoaDon=? and MaSanPham= ?", id);
    }

    public int delete(ProductDTO u) {
        String[] id = new String[]{String.valueOf(u.getId())};
        return db.delete("HoaDon", "MaSanPham=?", id);
    }
}
