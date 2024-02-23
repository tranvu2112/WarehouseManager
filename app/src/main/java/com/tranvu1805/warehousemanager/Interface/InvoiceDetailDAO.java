package com.tranvu1805.warehousemanager.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;

import java.util.List;

@Dao
public interface InvoiceDetailDAO {
    @Query("select * from ChiTietHoaDon")
    List<InvoiceDetailDTO> getAll();

    @Query("select * from ChiTietHoaDon where idInvoice=:idProduct")
    List<InvoiceDetailDTO> getByIdInvoice(int idProduct);


    @Query("SELECT SUM ( thanh_tien )FROM  (SELECT quantity*price as thanh_tien FROM chitiethoadon" +
            " where idInvoice in (select ct.idInvoice from ChiTietHoaDon ct join HoaDon hd " +
            "on hd.id = ct.idInvoice " +
            "where hd.type=:typeHoaDon and hd.date>=:startDate and hd.date <=:endDate))")
    long getMoney(int typeHoaDon, String startDate, String endDate);

    @Insert
    long insert(InvoiceDetailDTO invoiceDetailDTO);

    @Update
    int update(InvoiceDetailDTO invoiceDetailDTO);

    @Delete
    int delete(InvoiceDetailDTO invoiceDetailDTO);
}
