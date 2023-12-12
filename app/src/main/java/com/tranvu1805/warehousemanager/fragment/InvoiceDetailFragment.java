package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tranvu1805.warehousemanager.DAO.InvoiceDetailDAO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.R;
import com.tranvu1805.warehousemanager.adapter.ProductInvoiceDetailAdapter;

import java.util.ArrayList;

public class InvoiceDetailFragment extends Fragment {
    TextView txtNumber, txtDate, txtType, txtSum;
    RecyclerView rvProductBuy;
    InvoiceDetailDAO invoiceDetailDAO;
    ArrayList<InvoiceDetailDTO> invoiceDetailDTOS;
    int idInvoice = 1;
    ProductInvoiceDetailAdapter invoiceDetailAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNumber =view.findViewById(R.id.txtInvoiceDetailNumber);
        txtDate = view.findViewById(R.id.txtInvoiceDetailDate);
        txtType = view.findViewById(R.id.txtInvoiceDetailType);
        rvProductBuy = view.findViewById(R.id.rvProductInInvoiceDetail);
        invoiceDetailDAO = new InvoiceDetailDAO(requireContext());
        txtSum = view.findViewById(R.id.txtSumInvoiceDetail);
        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            idInvoice = intent.getIntExtra("idInvoice", 0);
            String num = intent.getStringExtra("number");
            String date = intent.getStringExtra("date");
            int typeInt = intent.getIntExtra("type", -1);
            String type = typeInt == 0 ? "Nhập" : "Xuất";
            invoiceDetailDTOS = invoiceDetailDAO.getListInvoice(idInvoice);
            invoiceDetailAdapter = new ProductInvoiceDetailAdapter(requireContext(), invoiceDetailDTOS);
            txtNumber.setText("Số hóa đơn: " + num);
            txtDate.setText("Ngày tạo: " + date);
            txtType.setText("Loại hóa đơn:" + type);
            txtSum.setText(getSum() + "  vnđ");
        }
        rvProductBuy.setAdapter(invoiceDetailAdapter);
    }
    private int getSum() {
        int sum = 0;
        for (InvoiceDetailDTO a : invoiceDetailDTOS
        ) {
            sum = sum + (a.getPrice() * a.getQuantity());
        }
        return sum;
    }
}