package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.databinding.ProductToBuyLayoutBinding;

import java.util.ArrayList;

public class ProductInvoiceAdapter extends RecyclerView.Adapter<ProductInvoiceAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductDTO> productDTOS;


    public ProductInvoiceAdapter(Context context, ArrayList<ProductDTO> productDTOS) {
        this.context = context;
        this.productDTOS = productDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
       ProductToBuyLayoutBinding binding = ProductToBuyLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productDTOS.size() > 0) {
            ProductDTO p = productDTOS.get(position);
            holder.binding.txtName1.setText("aaa");
            holder.binding.txtPrice1.setText("Giá: " + "aaa");
            if(p.getImgBlob()!=null){
                Bitmap imgBitmap = BitmapFactory.decodeByteArray(p.getImgBlob(),0,p.getImgBlob().length);
                holder.binding.imgProduct.setImageBitmap(imgBitmap);
            }

            holder.binding.btnMinus.setOnClickListener(view -> {
                if(holder.quantity>1){
                    holder.quantity--;
                    holder.binding.txtQuantity.setText(String.valueOf(holder.quantity));
                }
                notifyDataSetChanged();
            });
            holder.binding.btnAdd.setOnClickListener(view -> {
                if(holder.quantity<99){
                    holder.quantity++;
                    holder.binding.txtQuantity.setText(String.valueOf(holder.quantity));
                }
                notifyDataSetChanged();
            });
            p.setQuantity(Integer.parseInt(holder.binding.txtQuantity.getText().toString()));
            ProductDAO productDAO = new ProductDAO(context);
            productDAO.update(p);
            holder.itemView.setOnLongClickListener(view -> {
                CustomDialog.dialogDouble(context, "Thông báo", "Bạn có muốn xóa không",
                        "Có", (dialogInterface, i) -> {
                                productDTOS.remove(p);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }, "Không", (dialogInterface, i) -> {

                        });
                return true;
            });
        }
    }


    @Override
    public int getItemCount() {
        return productDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        int quantity=1;
        ProductToBuyLayoutBinding binding;

        public ViewHolder(@NonNull ProductToBuyLayoutBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
