package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.EditProductActivity;
import com.tranvu1805.warehousemanager.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductDTO> productDTOS;
    ProductDAO productDAO;



    public ProductAdapter(Context context, ArrayList<ProductDTO> productDTOS) {
        this.context = context;
        this.productDTOS = productDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.product_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDTO p = productDTOS.get(position);
        holder.txtName.setText("Tên: " + p.getName());
        holder.txtQuantity.setText("Số lượng: " + p.getQuantity());
        holder.txtPrice.setText("Giá: " + p.getPrice());
        if (p.getUriImg() != null) {
            Picasso.get().load(p.getUriImg()).into(holder.imgProduct);
        }
        holder.txtCat.setText("Thể loại: " + p.getIdCat());
        holder.txtDetail.setText("Mô tả: " + p.getDetail());
        holder.itemView.setOnLongClickListener(view -> {
            CustomDialog.dialogDouble(context, "Thông báo", "Bạn có muốn xóa không",
                    "Có", (dialogInterface, i) -> {
                        productDAO = new ProductDAO(context);
                        int check = productDAO.delete(p);
                        if (check > 0) {
                            productDTOS.remove(p);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }, "Không", (dialogInterface, i) -> {

                    });
            return true;
        });
        holder.btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Id",p.getId());
            bundle.putInt("IdCat",p.getIdCat());
            bundle.putString("Name",p.getName());
            bundle.putString("UriImg",p.getUriImg());
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
        TextView txtName, txtQuantity, txtPrice, txtCat, txtDetail;
        ImageView imgProduct;
        ImageButton btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameProductRow);
            txtQuantity = itemView.findViewById(R.id.txtQuantityProductRow);
            txtPrice = itemView.findViewById(R.id.txtPriceProductRow);
            txtCat = itemView.findViewById(R.id.txtCategoryProductRow);
            txtDetail = itemView.findViewById(R.id.txtDetailProductRow);
            imgProduct = itemView.findViewById(R.id.imgProductRow);
            btnEdit = itemView.findViewById(R.id.btnEditProductRow);
        }
    }
}
