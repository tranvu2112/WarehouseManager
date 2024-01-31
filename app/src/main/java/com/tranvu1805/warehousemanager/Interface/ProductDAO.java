package com.tranvu1805.warehousemanager.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.tranvu1805.warehousemanager.Model.ProductDTO;

import java.util.List;
@Dao
public interface ProductDAO {
    @Query("select * from SanPham")
    List<ProductDTO> getAll();

    @Query("select name from SanPham where id = :idProduct")
    String getNameById(int idProduct);

    @Insert
    long insertProduct(ProductDTO productDTO);
    @Update
    int updateProduct(ProductDTO productDTO);
    @Delete
    int delete(ProductDTO productDTO);
}
