package com.tranvu1805.warehousemanager.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tranvu1805.warehousemanager.InvoiceStatiscal;
import com.tranvu1805.warehousemanager.ProductStatiscal;
import com.tranvu1805.warehousemanager.R;
import com.tranvu1805.warehousemanager.databinding.FragmentStatiscalBinding;


public class StatiscalFragment extends Fragment {
    FragmentStatiscalBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStatiscalBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnProduct.setOnClickListener(
                v -> startActivity(new Intent(requireActivity(), ProductStatiscal.class)));
        binding.btnInvoice.setOnClickListener(
                v -> startActivity(new Intent(requireActivity(), InvoiceStatiscal.class)));
    }
}