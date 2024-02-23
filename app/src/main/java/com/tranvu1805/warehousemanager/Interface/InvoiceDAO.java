package com.tranvu1805.warehousemanager.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranvu1805.warehousemanager.Model.Invoice;

import java.util.List;

@Dao
public interface InvoiceDAO {
    @Query("select * from HoaDon")
    List<Invoice> getAll();

    @Insert
    long insert(Invoice invoice);

    @Update
    int update(Invoice invoice);

    @Delete
    int delete(Invoice invoice);
}
