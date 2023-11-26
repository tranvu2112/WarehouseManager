package com.tranvu1805.warehousemanager.DTO;

public class InvoiceDTO {
    int id;
    String number,date;
    int idUser, type ;
    String detail;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String number, int type, String date, String detail) {
        this.number = number;
        this.type = type;
        this.date = date;
        this.detail = detail;
    }

    public InvoiceDTO(String number, int idUser, int type, String date, String detail) {
        this.number = number;
        this.idUser = idUser;
        this.type = type;
        this.date = date;
        this.detail = detail;
    }

    public InvoiceDTO(int id, String number, int idUser, int type, String date, String detail) {
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
