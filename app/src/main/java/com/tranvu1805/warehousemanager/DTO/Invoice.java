package com.tranvu1805.warehousemanager.DTO;

public class Invoice {
    int id;
    String number;
    int idUser, type, quantity;
    String detail;

    public Invoice() {
    }

    public Invoice(String number, int type, int quantity, String detail) {
        this.number = number;
        this.type = type;
        this.quantity = quantity;
        this.detail = detail;
    }

    public Invoice(String number, int idUser, int type, int quantity, String detail) {
        this.number = number;
        this.idUser = idUser;
        this.type = type;
        this.quantity = quantity;
        this.detail = detail;
    }

    public Invoice(int id, String number, int idUser, int type, int quantity, String detail) {
        this.id = id;
        this.number = number;
        this.idUser = idUser;
        this.type = type;
        this.quantity = quantity;
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

    public void setNumber(String number) {
        this.number = number;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
