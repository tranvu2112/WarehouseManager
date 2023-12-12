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

import com.tranvu1805.warehousemanager.AddInvoice;
import com.tranvu1805.warehousemanager.DAO.InvoiceDAO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDTO;
import com.tranvu1805.warehousemanager.R;
import com.tranvu1805.warehousemanager.adapter.InvoiceAdapter;

import java.util.ArrayList;

public class InvoiceFragment extends Fragment {
    RecyclerView rvInvoice;
    InvoiceAdapter adapter;
    InvoiceDAO invoiceDAO;
    ArrayList<InvoiceDTO> invoiceDTOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_invoice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvInvoice = view.findViewById(R.id.rvInvoice);
        invoiceDAO = new InvoiceDAO(requireContext());
        invoiceDTOS = (ArrayList<InvoiceDTO>) invoiceDAO.getList();
        adapter = new InvoiceAdapter(requireContext(), invoiceDTOS);
        rvInvoice.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.invoice_option_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addInvoice){
            startActivity(new Intent(requireContext(), AddInvoice.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        invoiceDTOS.clear();
        invoiceDTOS.addAll(invoiceDAO.getList());
        adapter.notifyDataSetChanged();
    }
}