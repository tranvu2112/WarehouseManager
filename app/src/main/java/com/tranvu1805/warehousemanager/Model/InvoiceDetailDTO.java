package com.tranvu1805.warehousemanager.Model;

public class InvoiceDetailDTO {
    int idProduct, idInvoice, quantity, price;

    public InvoiceDetailDTO() {
    }

    public InvoiceDetailDTO(int idProduct, int idInvoice, int quantity, int price) {
        this.idProduct = idProduct;
        this.idInvoice = idInvoice;
        this.quantity = quantity;
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
