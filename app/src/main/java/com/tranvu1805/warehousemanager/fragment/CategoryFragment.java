package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.CategoryDAO;
import com.tranvu1805.warehousemanager.Model.CategoryDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.R;
import com.tranvu1805.warehousemanager.adapter.CategoryAdapter;
import com.tranvu1805.warehousemanager.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryFragment extends Fragment {
    FragmentCategoryBinding binding;
    CategoryDAO categoryDAO;
    CategoryAdapter adapter;
    ArrayList<CategoryDTO> categoryDTOS;
    AlertDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryDAO = new CategoryDAO(requireContext());
        categoryDTOS = (ArrayList<CategoryDTO>) categoryDAO.getList();
        adapter = new CategoryAdapter(requireContext(), categoryDTOS);
        binding.rvCategory.setAdapter(adapter);
        binding.btnAddCategory.setOnClickListener(v -> addCategory());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addCategory() {
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
}