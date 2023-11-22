package com.tranvu1805.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import com.tranvu1805.warehousemanager.Dialog.CustomDialog;

public class HomeActivity extends AppCompatActivity {
    ImageButton btnTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findView();
        btnTaiKhoan.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            int role = sharedPreferences.getInt("role", 1);
            if (role == 1) {
                startActivity(new Intent(this, AccountHome.class));
            } else {
                CustomDialog.dialogSingle(this, "Thông báo", "Bạn không đủ quyền truy cập", "OK", (dialogInterface, i) -> {});
            }
        });
    }

    private void findView() {
        btnTaiKhoan = findViewById(R.id.btnUser);
    }
}