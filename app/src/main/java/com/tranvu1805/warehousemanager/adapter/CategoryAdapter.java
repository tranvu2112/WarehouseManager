package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.CategoryDAO;
import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.R;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoryDTO> categoryDTOS;
    CategoryDAO categoryDAO;
    AlertDialog dialog;

    public CategoryAdapter(Context context, ArrayList<CategoryDTO> categoryDTOS) {
        this.context = context;
        this.categoryDTOS = categoryDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.cat_row_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDTO c = categoryDTOS.get(position);
        holder.txtName.setText(c.getName());
        holder.itemView.setOnLongClickListener(view -> {
            delCategory(c);
            return false;
        });
        holder.btnEdit.setOnClickListener(view -> updateCategory(c));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCategory(CategoryDTO c) {
        dialog = CustomDialog.builderSetView(context, R.layout.edit_category_layout, view -> {
            TextInputEditText edtName = view.findViewById(R.id.edtNameEditCatRow);
            Button btnConfirm = view.findViewById(R.id.btnConfirmEditCatRow);
            Button btnCancel = view.findViewById(R.id.btnCancelEditCatRow);
            edtName.setText(c.getName());
            btnConfirm.setOnClickListener(view1 -> {
                if (Objects.requireNonNull(edtName.getText()).toString().isEmpty()) {
                    CustomDialog.dialogSingle(context, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i) -> {
                    });
                } else {
                    c.setName(edtName.getText().toString());
                    categoryDAO = new CategoryDAO(context);
                    int check = categoryDAO.update(c);
                    if (check > 0) {
                        CustomDialog.dialogSingle(context, "Thông báo", "Sửa thành công", "OK", (dialogInterface, i) -> {
                            notifyDataSetChanged();
                            dialog.cancel();
                        });
                    } else {
                        CustomDialog.dialogSingle(context, "Thông báo", "Sửa thất bại", "OK", (dialogInterface, i) -> {
                            dialog.cancel();

                        });
                    }
                }
            });
            btnCancel.setOnClickListener(view1 -> dialog.cancel());
        });
        dialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void delCategory(CategoryDTO c) {
        CustomDialog.dialogDouble(context, "Thông báo", "Bạn muốn xóa loại sản phẩm này",
                "Có", (dialogInterface, i) -> {
                    categoryDAO = new CategoryDAO(context);
                    int check = categoryDAO.delete(c);
                    if (check > 0) {
                        categoryDTOS.remove(c);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }

                }, "Không", (dialogInterface, i) -> {

                });
    }

    @Override
    public int getItemCount() {
        return categoryDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageButton btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameCatRow);
            btnEdit = itemView.findViewById(R.id.btnEditCatRow);
        }
    }
}
