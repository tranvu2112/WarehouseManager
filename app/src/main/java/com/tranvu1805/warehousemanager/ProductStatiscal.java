package com.tranvu1805.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.databinding.ActivityProductStatiscalBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProductStatiscal extends AppCompatActivity {
    ActivityProductStatiscalBinding binding;
    ArrayList<String> listStatic;
    ArrayAdapter<String> spAdapter;
    ProductDAO productDAO;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductStatiscalBinding.inflate(getLayoutInflater());
        listStatic = new ArrayList<>();
        listStatic.add("Tiền hàng tồn kho");
        spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listStatic);
        binding.spType.setAdapter(spAdapter);
        setContentView(binding.getRoot());
        binding.btnSubmit.setOnClickListener(v -> {
            if(binding.spType.getSelectedItem().toString().equals("Tiền hàng tồn kho")){
                productDAO = new ProductDAO(this);
                binding.txtMoney.setText("Tiền hàng tồn kho là: " + productDAO.getProductInStore());
                binding.txtMoney.setVisibility(View.VISIBLE);
            }
        });
    }


}