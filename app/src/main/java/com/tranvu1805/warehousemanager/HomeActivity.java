package com.tranvu1805.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {
    ImageButton btnTaiKhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findView();
        btnTaiKhoan.setOnClickListener(view -> startActivity(new Intent(this, AccountHome.class)));
    }

    private void findView() {
        btnTaiKhoan = findViewById(R.id.btnUser);
    }
}