package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.EditInvoiceDetailActivity;
import com.tranvu1805.warehousemanager.InvoiceDetailActivity;
import com.tranvu1805.warehousemanager.Model.Invoice;
import com.tranvu1805.warehousemanager.databinding.InvoiceRowLayoutBinding;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {
    Context context;
    ArrayList<Invoice> invoiceDTOS;


    public InvoiceAdapter(Context context, ArrayList<Invoice> invoiceDTOS) {
        this.context = context;
        this.invoiceDTOS = invoiceDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InvoiceRowLayoutBinding binding = InvoiceRowLayoutBinding.inflate(((Activity) context).getLayoutInflater(), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Invoice invoice = invoiceDTOS.get(position);
        holder.binding.txtNum.setText("Số: " + invoice.getNumber());
        holder.binding.txtUser.setText("Id Người tạo: " + invoice.getIdUser());
        String type = invoice.getType() == 0 ? "Nhập" : "Xuất";
        holder.binding.txtType.setText("Loại: " + type);
        holder.binding.txtDate.setText("Ngày: " + invoice.getDate());
        holder.binding.btnEdit.setOnClickListener(view -> updateInvoice(invoice));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, InvoiceDetailActivity.class);
            intent.putExtra("idInvoice", invoice.getId());
            intent.putExtra("number", invoice.getNumber());
            intent.putExtra("date", invoice.getDate());
            intent.putExtra("type", invoice.getType());
            context.startActivity(intent);
        });
        holder.itemView.setOnLongClickListener(view -> {
            CustomDialog.dialogDouble(context, "Thông báo", "Bạn có muốn xóa không",
                    "Có", (dialogInterface, i) -> {
                        int check = MyDatabase.getInstance(context).invoiceDAO().delete(invoice);
                        if (check > 0) {
                            invoiceDTOS.remove(invoice);
                            CustomDialog.dialogSingle(context, "Thông báo", "Xóa thành công", "OK", (dialogInterface1, i1) -> notifyDataSetChanged());
                        }
                    }, "Không", (dialogInterface, i) -> {
                    });
            return true;
        });
    }

    private void updateInvoice(Invoice invoice) {
        Intent intent = new Intent(context, EditInvoiceDetailActivity.class);
        intent.putExtra("idInvoice", invoice.getId());
        intent.putExtra("number", invoice.getNumber());
        intent.putExtra("date", invoice.getDate());
        intent.putExtra("type", invoice.getType());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return invoiceDTOS.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        InvoiceRowLayoutBinding binding;

        public ViewHolder(@NonNull InvoiceRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
