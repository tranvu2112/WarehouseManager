package com.tranvu1805.warehousemanager.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "TheLoai")
public class CategoryDTO {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;

    @Ignore
    public CategoryDTO() {
    }

    @Ignore
    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
