package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.R;

import java.util.ArrayList;

public class ProductInvoiceDetailAdapter extends RecyclerView.Adapter<ProductInvoiceDetailAdapter.ViewHolder> {
    Context context;
    ArrayList<InvoiceDetailDTO> invoiceDetailDTOS;


    public ProductInvoiceDetailAdapter(Context context, ArrayList<InvoiceDetailDTO> invoiceDetailDTOS) {
        this.context = context;
        this.invoiceDetailDTOS = invoiceDetailDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.product_row_invoice_detail, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvoiceDetailDTO invoiceDetailDTO = invoiceDetailDTOS.get(position);
        holder.txtPrice.setText("Giá: " + invoiceDetailDTO.getPrice());
        holder.txtQuantity.setText("Số lượng: " + invoiceDetailDTO.getQuantity());
        int sum = invoiceDetailDTO.getPrice() * invoiceDetailDTO.getQuantity();
        holder.txtSum.setText("=   " + sum + "  VND");
        ProductDAO productDAO = new ProductDAO(context);
        holder.txtName.setText(productDAO.getName(invoiceDetailDTO.getIdProduct()));
    }


    @Override
    public int getItemCount() {
        return invoiceDetailDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtQuantity, txtSum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameProductInvoice);
            txtPrice = itemView.findViewById(R.id.txtPriceProductInvoice);
            txtQuantity = itemView.findViewById(R.id.txtQuantityProductInvoice);
            txtSum = itemView.findViewById(R.id.txtSumProductInvoice);
        }
    }
}
