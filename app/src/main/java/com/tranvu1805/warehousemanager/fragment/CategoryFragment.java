package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.CategoryDAO;
import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.R;
import com.tranvu1805.warehousemanager.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryFragment extends Fragment {
    RecyclerView rvCategory;
    CategoryDAO categoryDAO;
    CategoryAdapter adapter;
    ArrayList<CategoryDTO> categoryDTOS;
    AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategory = view.findViewById(R.id.rvCategory);
        categoryDAO = new CategoryDAO(requireContext());
        categoryDTOS = (ArrayList<CategoryDTO>) categoryDAO.getList();
        adapter = new CategoryAdapter(requireContext(), categoryDTOS);
        rvCategory.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_option_menu, menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addCategory) {
            dialog = CustomDialog.builderSetView(requireContext(), R.layout.add_category_layout, view -> {
                TextInputEditText edtName = view.findViewById(R.id.edtNameAddCatRow);
                Button btnConfirm = view.findViewById(R.id.btnConfirmAddCatRow);
                Button btnCancel = view.findViewById(R.id.btnCancelAddCatRow);

                btnConfirm.setOnClickListener(view1 -> {
                    if (Objects.requireNonNull(edtName.getText()).toString().isEmpty()) {
                        CustomDialog.dialogSingle(requireActivity(), "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i) -> {
                        });
                    } else {
                        CategoryDTO c = new CategoryDTO(edtName.getText().toString());
                        int check = categoryDAO.addRow(c);
                        if (check > 0) {
                            categoryDTOS.clear();
                            categoryDTOS.addAll(categoryDAO.getList());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(requireActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        } else {
                            Toast.makeText(requireActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
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