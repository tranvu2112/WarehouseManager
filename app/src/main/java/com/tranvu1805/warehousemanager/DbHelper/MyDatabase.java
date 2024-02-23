package com.tranvu1805.warehousemanager.DbHelper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tranvu1805.warehousemanager.Interface.CategoryDAO;
import com.tranvu1805.warehousemanager.Interface.InvoiceDAO;
import com.tranvu1805.warehousemanager.Interface.InvoiceDetailDAO;
import com.tranvu1805.warehousemanager.Interface.ProductDAO;
import com.tranvu1805.warehousemanager.Interface.UserDAO;
import com.tranvu1805.warehousemanager.Model.CategoryDTO;
import com.tranvu1805.warehousemanager.Model.Invoice;
import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.Model.UserDTO;

import java.util.ArrayList;

@Database(entities = {CategoryDTO.class, InvoiceDetailDTO.class, UserDTO.class,
        ProductDTO.class, Invoice.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public static volatile MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "khohang.db").allowMainThreadQueries().build();
            ArrayList<UserDTO> userDTOs = (ArrayList<UserDTO>) MyDatabase.getInstance(context).userDAO().getAll();
            if (userDTOs.size() == 0) {
                UserDTO userDTO = new UserDTO("admin", "1", 1, "admin", "admin@gmail.com");
                MyDatabase.getInstance(context).userDAO().insertUser(userDTO);
            }
        }
        return instance;
    }
    //tăng version
//    static final Migration MIGRATION2_3 = new Migration(2,3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//        }
//    };

    public abstract CategoryDAO categoryDAO();

    public abstract ProductDAO productDAO();

    public abstract UserDAO userDAO();

    public abstract InvoiceDAO invoiceDAO();

    public abstract InvoiceDetailDAO invoiceDetailDAO();


}
