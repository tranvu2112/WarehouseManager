package com.tranvu1805.warehousemanager.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranvu1805.warehousemanager.Model.CategoryDTO;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("select * from TheLoai")
    List<CategoryDTO> getAll();

    @Query("select name from TheLoai where id = :idCat")
    String getNameById(int idCat);
    @Update
    int updateCategory(CategoryDTO categoryDTO);
    @Insert
    long insertCategory(CategoryDTO categoryDTO);

    @Delete
    int delete(CategoryDTO categoryDTO);
}
