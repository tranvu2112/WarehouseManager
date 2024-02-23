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

import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.adapter.ProductInvoiceDetailAdapter;
import com.tranvu1805.warehousemanager.databinding.FragmentInvoiceDetailBinding;

import java.util.ArrayList;

public class InvoiceDetailFragment extends Fragment {
    FragmentInvoiceDetailBinding binding;

    ArrayList<InvoiceDetailDTO> invoiceDetailDTOS;
    int idInvoice = 1;
    ProductInvoiceDetailAdapter invoiceDetailAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = requireActivity().getIntent();
        if (intent != null) {
            idInvoice = intent.getIntExtra("idInvoice", 0);
            String num = intent.getStringExtra("number");
            String date = intent.getStringExtra("date");
            int typeInt = intent.getIntExtra("type", -1);
            String type = typeInt == 0 ? "Nhập" : "Xuất";
            invoiceDetailDTOS = (ArrayList<InvoiceDetailDTO>) MyDatabase.getInstance(requireContext())
                    .invoiceDetailDAO().getByIdInvoice(idInvoice);
            invoiceDetailAdapter = new ProductInvoiceDetailAdapter(requireContext(), invoiceDetailDTOS);
            binding.txtInvoiceDetailNumber.setText("Số hóa đơn: " + num);
            binding.txtInvoiceDetailDate.setText("Ngày tạo: " + date);
            binding.txtInvoiceDetailType.setText("Loại hóa đơn: " + type);
            binding.txtSumInvoiceDetail.setText(getSum() + "  vnđ");
        }
        binding.rvProductInInvoiceDetail.setAdapter(invoiceDetailAdapter);
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