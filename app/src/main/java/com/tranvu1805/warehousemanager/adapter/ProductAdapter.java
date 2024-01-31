package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.EditProductActivity;
import com.tranvu1805.warehousemanager.databinding.ProductRowLayoutBinding;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductDTO> productDTOS;



    public ProductAdapter(Context context, ArrayList<ProductDTO> productDTOS) {
        this.context = context;
        this.productDTOS = productDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRowLayoutBinding binding = ProductRowLayoutBinding.inflate(((Activity) context).getLayoutInflater(), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDTO p = productDTOS.get(position);
        holder.binding.txtName.setText("Tên: " + p.getName());
        holder.binding.txtQuantity.setText("Số lượng: " + p.getQuantity());
        holder.binding.txtPrice.setText("Giá: " + p.getPrice());
        if(p.getImgBlob()!=null){
            Bitmap imgBitmap = BitmapFactory.decodeByteArray(p.getImgBlob(),0,p.getImgBlob().length);
            holder.binding.imgProduct.setImageBitmap(imgBitmap);
        }
        holder.binding.txtCategory.setText("Thể loại: " + p.getIdCat());
        holder.binding.txtDetail.setText("Mô tả: " + p.getDetail());
        holder.itemView.setOnLongClickListener(view -> {
            CustomDialog.dialogDouble(context, "Thông báo", "Bạn có muốn xóa không",
                    "Có", (dialogInterface, i) -> {
                        int check = MyDatabase.getInstance(context).productDAO().delete(p);
                        if (check > 0) {
                            productDTOS.remove(p);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, "Không", (dialogInterface, i) -> {

                    });
            return true;
        });
        holder.binding.btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Id",p.getId());
            bundle.putInt("IdCat",p.getIdCat());
            bundle.putString("Name",p.getName());
            bundle.putByteArray("imgBlob",p.getImgBlob());
            bundle.putInt("Price",p.getPrice());
            bundle.putInt("Quantity",p.getQuantity());
            bundle.putString("Detail",p.getDetail());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

    }




    @Override
    public int getItemCount() {
        return productDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ProductRowLayoutBinding binding;

        public ViewHolder(@NonNull ProductRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
