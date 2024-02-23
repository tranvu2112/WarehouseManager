package com.tranvu1805.warehousemanager.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "HoaDon",
        foreignKeys = @ForeignKey(
                entity = UserDTO.class,
                parentColumns = "id",
                childColumns = "idUser",
                onDelete = ForeignKey.CASCADE
        )
)
public class Invoice {
    @PrimaryKey(autoGenerate = true)
    int id;
    String number, date;
    int idUser, type;
    String detail;

    @Ignore
    public Invoice() {
    }

    @Ignore
    public Invoice(String number, int type, String date, String detail) {
        this.number = number;
        this.type = type;
        this.date = date;
        this.detail = detail;
    }

    @Ignore
    public Invoice(String number, int idUser, int type, String date, String detail) {
        this.number = number;
        this.idUser = idUser;
        this.type = type;
        this.date = date;
        this.detail = detail;
    }

    public Invoice(int id, String number, int idUser, int type, String date, String detail) {
        this.id = id;
        this.number = number;
        this.idUser = idUser;
        this.type = type;
        this.date = date;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
