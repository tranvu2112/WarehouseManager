package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tranvu1805.warehousemanager.AddProduct;
import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.R;
import com.tranvu1805.warehousemanager.adapter.ProductAdapter;

import java.util.ArrayList;


public class ProductFragment extends Fragment {
    ArrayList<ProductDTO> productDTOS;
    ProductAdapter adapter;
    ProductDAO productDAO;
    RecyclerView rvProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProduct = view.findViewById(R.id.rvProduct);
        productDAO = new ProductDAO(requireContext());
        productDTOS = (ArrayList<ProductDTO>) productDAO.getList();
        adapter = new ProductAdapter(requireActivity(), productDTOS);
        rvProduct.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.product_option_menu,menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addProduct) {
            startActivity(new Intent(requireActivity(), AddProduct.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        productDTOS.clear();
        productDTOS.addAll(productDAO.getList());
        adapter.notifyDataSetChanged();
    }
}