package com.tranvu1805.warehousemanager.DbHelper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tranvu1805.warehousemanager.Interface.CategoryDAO;
import com.tranvu1805.warehousemanager.Interface.ProductDAO;
import com.tranvu1805.warehousemanager.Model.CategoryDTO;

import com.tranvu1805.warehousemanager.Model.Invoice;
import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.Model.UserDTO;

@Database(entities = {CategoryDTO.class, InvoiceDetailDTO.class, UserDTO.class,
        ProductDTO.class, Invoice.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public static volatile MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "khohang.db").allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract CategoryDAO categoryDAO();
    public abstract ProductDAO productDAO();

}
