package com.tranvu1805.warehousemanager.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "SanPham",
        foreignKeys = @ForeignKey(
                entity = CategoryDTO.class,
                parentColumns = "id",
                childColumns = "idCat",
                onDelete = ForeignKey.CASCADE
        )
)
public class ProductDTO {
    @PrimaryKey(autoGenerate = true)
    int id;
    int idCat;
    String name;
    int price, quantity;
    String detail;
    byte[] imgBlob;
    @Ignore
    public ProductDTO() {
    }

    @Ignore
    public ProductDTO(int idCat, String name, int price, int quantity, String detail, byte[] imgBlob) {
        this.idCat = idCat;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.detail = detail;
        this.imgBlob = imgBlob;
    }

    public ProductDTO(int id, int idCat, String name, int price, int quantity, String detail, byte[] imgBlob) {
        this.id = id;
        this.idCat = idCat;
        this.imgBlob = imgBlob;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCat() {
        return idCat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getImgBlob() {
        return imgBlob;
    }

}
