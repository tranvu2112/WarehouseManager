package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.CategoryDAO;
import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView rvCategory;
    CategoryDAO categoryDAO;
    CategoryAdapter adapter;
    ArrayList<CategoryDTO> categoryDTOS;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        rvCategory = findViewById(R.id.rvCategory);
        categoryDAO = new CategoryDAO(this);
        categoryDTOS = (ArrayList<CategoryDTO>) categoryDAO.getList();
        adapter = new CategoryAdapter(this,categoryDTOS);
        rvCategory.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addCategory) {
            dialog = CustomDialog.builderSetView(this, R.layout.add_category_layout, view -> {
                TextInputEditText edtName = view.findViewById(R.id.edtNameAddCatRow);
                Button btnConfirm = view.findViewById(R.id.btnConfirmAddCatRow);
                Button btnCancel = view.findViewById(R.id.btnCancelAddCatRow);

                btnConfirm.setOnClickListener(view1 -> {
                    if(Objects.requireNonNull(edtName.getText()).toString().isEmpty()){
                        CustomDialog.dialogSingle(CategoryActivity.this, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i)->{});
                    } else {
                        CategoryDTO c = new CategoryDTO(edtName.getText().toString());
                        int check = categoryDAO.addRow(c);
                        if(check>0){
                            categoryDTOS.clear();
                            categoryDTOS.addAll(categoryDAO.getList());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(CategoryActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }else {
                            Toast.makeText(CategoryActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });
                btnCancel.setOnClickListener(view1 -> dialog.cancel());
            });
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}