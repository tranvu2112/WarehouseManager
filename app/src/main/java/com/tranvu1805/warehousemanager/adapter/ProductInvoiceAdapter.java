package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.tranvu1805.warehousemanager.R;

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
        View view = inflater.inflate(R.layout.product_to_buy_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productDTOS.size() > 0) {
            ProductDTO p = productDTOS.get(position);
            holder.txtName.setText(p.getName());
            holder.txtPrice.setText("Giá: " + p.getPrice());
            if (p.getUriImg() != null) {
                Picasso.get().load(p.getUriImg()).into(holder.imgProduct);
            }

            holder.btnMinus.setOnClickListener(view -> {
                if(holder.quantity>1){
                    holder.quantity--;
                    holder.txtQuantity.setText(holder.quantity+"");
                }
                notifyDataSetChanged();
            });
            holder.btnAdd.setOnClickListener(view -> {
                if(holder.quantity<99){
                    holder.quantity++;
                    holder.txtQuantity.setText(holder.quantity+"");
                }
                notifyDataSetChanged();
            });
            p.setQuantity(Integer.parseInt(holder.txtQuantity.getText().toString()));
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
        TextView txtName, txtPrice,txtQuantity;
        ImageView imgProduct;
        ImageButton btnAdd,btnMinus;
        int quantity=1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameProductBuy);
            txtPrice = itemView.findViewById(R.id.txtPriceProductBuy);
            txtQuantity=itemView.findViewById(R.id.txtQuantityProductBuy);
            imgProduct = itemView.findViewById(R.id.imgProductBuy);
            btnAdd = itemView.findViewById(R.id.btnAddProductBuy);
            btnMinus = itemView.findViewById(R.id.btnMinusProductBuy);
        }
    }
}
