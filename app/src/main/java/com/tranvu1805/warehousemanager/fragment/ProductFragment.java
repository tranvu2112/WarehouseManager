package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.tranvu1805.warehousemanager.AddProduct;
//import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.AddProduct;
import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
//import com.tranvu1805.warehousemanager.adapter.ProductAdapter;
import com.tranvu1805.warehousemanager.adapter.ProductAdapter;
import com.tranvu1805.warehousemanager.databinding.FragmentProductBinding;

import java.util.ArrayList;


public class ProductFragment extends Fragment {
    ArrayList<ProductDTO> productDTOS;
    ProductAdapter adapter;
    FragmentProductBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productDTOS = (ArrayList<ProductDTO>) MyDatabase.getInstance(requireActivity()).productDAO().getAll();
        adapter = new ProductAdapter(requireActivity(), productDTOS);
        binding.rvProduct.setAdapter(adapter);
        binding.btnAdd.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AddProduct.class)));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        productDTOS.clear();
        productDTOS.addAll(MyDatabase.getInstance(requireActivity()).productDAO().getAll());
        adapter.notifyDataSetChanged();
    }
}