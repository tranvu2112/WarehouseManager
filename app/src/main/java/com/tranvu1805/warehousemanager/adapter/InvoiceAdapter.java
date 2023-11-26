package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranvu1805.warehousemanager.DAO.InvoiceDAO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDTO;
import com.tranvu1805.warehousemanager.R;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder>{
    Context context;
    ArrayList<InvoiceDTO> invoiceDTOS;
    InvoiceDAO invoiceDAO;



    public InvoiceAdapter(Context context, ArrayList<InvoiceDTO> invoiceDTOS) {
        this.context = context;
        this.invoiceDTOS = invoiceDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.invoice_row_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvoiceDTO invoiceDTO = invoiceDTOS.get(position);
        holder.txtNumber.setText("Số hóa đơn: "+invoiceDTO.getNumber());
        holder.txtUser.setText("Id Người tạo: "+invoiceDTO.getIdUser());
        holder.txtType.setText("Loại hóa đơn: "+invoiceDTO.getType());
        holder.txtDate.setText("Ngày tháng: "+invoiceDTO.getDate());
        holder.txtDetail.setText("Mô tả: "+invoiceDTO.getDetail());
    }

    @Override
    public int getItemCount() {
        return invoiceDTOS.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumber, txtUser, txtType, txtDate, txtDetail;
        ImageButton btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.txtInvoiceNumRow);
            txtUser = itemView.findViewById(R.id.txtUserInvoiceRow);
            txtType = itemView.findViewById(R.id.txtInvoiceTypeRow);
            txtDate = itemView.findViewById(R.id.txtInvoiceDateRow);
            txtDetail = itemView.findViewById(R.id.txtDetailInvoiceRow);
            btnEdit = itemView.findViewById(R.id.btnEditInvoiceRow);
        }
    }
}
