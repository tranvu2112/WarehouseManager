package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.databinding.ProductRowInvoiceDetailBinding;

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
        ProductRowInvoiceDetailBinding binding = ProductRowInvoiceDetailBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvoiceDetailDTO invoiceDetailDTO = invoiceDetailDTOS.get(position);
        holder.binding.txtPrice.setText("Giá: " + invoiceDetailDTO.getPrice());
        holder.binding.txtQuantity.setText("Số lượng: " + invoiceDetailDTO.getQuantity());
        int sum = invoiceDetailDTO.getPrice() * invoiceDetailDTO.getQuantity();
        holder.binding.txtSum.setText("=   " + sum + "  VND");
        ProductDAO productDAO = new ProductDAO(context);
        holder.binding.txtName.setText(productDAO.getName(invoiceDetailDTO.getIdProduct()));
    }


    @Override
    public int getItemCount() {
        return invoiceDetailDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ProductRowInvoiceDetailBinding binding;

        public ViewHolder(@NonNull ProductRowInvoiceDetailBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
