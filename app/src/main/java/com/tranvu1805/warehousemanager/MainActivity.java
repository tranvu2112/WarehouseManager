package com.tranvu1805.warehousemanager;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.tranvu1805.warehousemanager.Dialog.CustomDialog;

public class MainActivity extends AppCompatActivity {
    Button btnStart;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btnStartWel);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            btnStart.setOnClickListener(v ->
                    startActivity(new Intent(this, LoginActivity.class))
            );
        } else {
            ActivityCompat.requestPermissions(((Activity) this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnStart.setOnClickListener(v ->
                        startActivity(new Intent(this, LoginActivity.class))
                );
            } else {
                CustomDialog.dialogSingle(this, "Thông báo", "Bạn phải cập quyền đọc bộ nhớ?",
                        "Đồng Ý", ((dialogInterface, i) -> {
                            ActivityCompat.requestPermissions(((Activity) this), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
                        }));
            }
        }
    }
}