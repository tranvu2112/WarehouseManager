package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.tranvu1805.warehousemanager.AddInvoice;
//import com.tranvu1805.warehousemanager.DAO.InvoiceDAO;
//import com.tranvu1805.warehousemanager.Model.InvoiceDTO;
//import com.tranvu1805.warehousemanager.adapter.InvoiceAdapter;
import com.tranvu1805.warehousemanager.databinding.FragmentInvoiceBinding;

import java.util.ArrayList;

public class InvoiceFragment extends Fragment {
    FragmentInvoiceBinding binding;
    //    InvoiceAdapter adapter;
//    InvoiceDAO invoiceDAO;
    //ArrayList<InvoiceDTO> invoiceDTOS;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        invoiceDAO = new InvoiceDAO(requireContext());
//        invoiceDTOS = (ArrayList<InvoiceDTO>) invoiceDAO.getList();
//        adapter = new InvoiceAdapter(requireContext(), invoiceDTOS);
//        binding.rvInvoice.setAdapter(adapter);
//        binding.btnAdd.setOnClickListener(v -> startActivity(new Intent(requireContext(), AddInvoice.class)));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
//        invoiceDTOS.clear();
//        invoiceDTOS.addAll(invoiceDAO.getList());
//        adapter.notifyDataSetChanged();
    }
}