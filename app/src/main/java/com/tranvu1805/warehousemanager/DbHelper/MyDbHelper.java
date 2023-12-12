package com.tranvu1805.warehousemanager.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    static String name = "db_khohang";
    static int version = 2;

    public MyDbHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TheLoai = "CREATE TABLE TheLoai(MaLoai INTEGER PRIMARY KEY AUTOINCREMENT, TenLoai TEXT (30) NOT NULL UNIQUE);";
        String TaiKhoan = "CREATE TABLE TaiKhoan (MaTaiKhoan INTEGER PRIMARY KEY AUTOINCREMENT,TaiKhoan TEXT (50) UNIQUE NOT NULL,MatKhau TEXT (50) NOT NULL,VaiTro INTEGER (1) NOT NULL CHECK (VaiTro = 0 || VaiTro= 1),HoTen TEXT (150) NOT NULL,Email TEXT (100) NOT NULL UNIQUE);";
        String SanPham = "CREATE TABLE SanPham ( MaSanPham INTEGER PRIMARY KEY AUTOINCREMENT, MaLoai INTEGER REFERENCES TheLoai (MaLoai), TenSanPham TEXT (50) NOT NULL, Gia INTEGER NOT NULL CHECK (gia >= 0), SoLuong INTEGER NOT NULL CHECK (SoLuong >= 0), MoTa TEXT (150),imgBlob BLOB);";
        String HoaDon = "CREATE TABLE HoaDon ( MaHoaDon INTEGER PRIMARY KEY AUTOINCREMENT, SoHoaDon TEXT (100) UNIQUE NOT NULL, MaTaiKhoan INTEGER REFERENCES TaiKhoan (MaTaiKhoan) NOT NULL, LoaiHoaDon INTEGER (1) NOT NULL CHECK (LoaiHoaDon = 0 || LoaiHoaDon = 1), NgayThang TEXT (50) NOT NULL, MoTa TEXT (150));";
        String ChiTietHoaDon = "CREATE TABLE ChiTietHoaDon ( MaSanPham INTEGER NOT NULL REFERENCES SanPham (MaSanPham), MaHoaDon INTEGER NOT NULL REFERENCES HoaDon (MaHoaDon), SoLuong INTEGER NOT NULL, DonGia INTEGER NOT NULL, Primary key (MaSanPham, MaHoaDon));";
        sqLiteDatabase.execSQL(TheLoai);
        sqLiteDatabase.execSQL(TaiKhoan);
        sqLiteDatabase.execSQL(SanPham);
        sqLiteDatabase.execSQL(HoaDon);
        sqLiteDatabase.execSQL(ChiTietHoaDon);
        sqLiteDatabase.execSQL("insert into TaiKhoan values(null,'Admin','111111',1,'Admin','admin.com')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i<i1){
            sqLiteDatabase.execSQL("drop table if exists ChiTietHoaDon");
            sqLiteDatabase.execSQL("drop table if exists HoaDon");
            sqLiteDatabase.execSQL("drop table if exists SanPham");
            sqLiteDatabase.execSQL("drop table if exists TaiKhoan");
            sqLiteDatabase.execSQL("drop table if exists TheLoai");
            onCreate(sqLiteDatabase);
        }
    }
}
