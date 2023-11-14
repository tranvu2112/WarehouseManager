package com.tranvu1805.warehousemanager.DTO;

public class ProductDTO {
    int id, idCat;
    String name;
    int price,quantity;
    String detail;

    public ProductDTO() {
    }

    public ProductDTO(String name, int price, int quantity, String detail) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.detail = detail;
    }

    public ProductDTO(int idCat, String name, int price, int quantity, String detail) {
        this.idCat = idCat;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.detail = detail;
    }

    public ProductDTO(int id, int idCat, String name, int price, int quantity, String detail) {
        this.id = id;
        this.idCat = idCat;
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

    public void setIdCat(int idCat) {
        this.idCat = idCat;
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
}
