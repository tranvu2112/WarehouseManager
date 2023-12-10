package com.tranvu1805.warehousemanager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.adapter.ProductAdapter;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ArrayList<ProductDTO> productDTOS;
    ProductAdapter adapter;
    ProductDAO productDAO;
    RecyclerView rvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        findViews();
        rvProduct.setAdapter(adapter);
    }

    private void findViews() {
        rvProduct = findViewById(R.id.rvProduct);
        productDAO = new ProductDAO(this);
        productDTOS = (ArrayList<ProductDTO>) productDAO.getList();
        adapter = new ProductAdapter(this, productDTOS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addProduct) {
            startActivity(new Intent(this, AddProduct.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        productDTOS.clear();
        productDTOS.addAll(productDAO.getList());
        adapter.notifyDataSetChanged();
    }

}