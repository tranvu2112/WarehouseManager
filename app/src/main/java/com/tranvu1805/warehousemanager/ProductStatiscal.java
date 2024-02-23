package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.databinding.ActivityProductStatiscalBinding;

import java.util.ArrayList;

public class ProductStatiscal extends AppCompatActivity {
    ActivityProductStatiscalBinding binding;
    ArrayList<String> listStatic;
    ArrayAdapter<String> spAdapter;

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
            if (binding.spType.getSelectedItem().toString().equals("Tiền hàng tồn kho")) {
                binding.txtMoney.setText("Tiền hàng tồn kho là: " + MyDatabase.getInstance(this).productDAO().getMoneyInStore());
                binding.txtMoney.setVisibility(View.VISIBLE);
            }
        });
    }


}