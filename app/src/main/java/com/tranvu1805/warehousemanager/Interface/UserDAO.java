package com.tranvu1805.warehousemanager.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranvu1805.warehousemanager.Model.UserDTO;

import java.util.List;
@Dao
public interface UserDAO {
    @Query("select * from TaiKhoan")
    List<UserDTO> getAll();

    @Query("select * from TaiKhoan where user_name = :userName")
    UserDTO checkLogin(String userName);

    @Insert
    long insertUser(UserDTO userDTO);

    @Update
    int updateUser(UserDTO userDTO);

    @Delete
    int deleteUser(UserDTO userDTO);
}
