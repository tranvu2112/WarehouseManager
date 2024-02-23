package com.tranvu1805.warehousemanager.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "TaiKhoan",
        indices = {@Index(value = "user_name", unique = true)}
)
public class UserDTO {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "user_name")
    String account;
    String pass;
    int role;
    String name, email;

    @Ignore
    public UserDTO() {
    }

    @Ignore
    public UserDTO(String account, String pass, int role, String name, String email) {
        this.account = account;
        this.pass = pass;
        this.role = role;
        this.name = name;
        this.email = email;
    }

    public UserDTO(int id, String account, String pass, int role, String name, String email) {
        this.id = id;
        this.account = account;
        this.pass = pass;
        this.role = role;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
