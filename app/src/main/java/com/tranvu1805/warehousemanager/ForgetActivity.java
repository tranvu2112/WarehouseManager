package com.tranvu1805.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ForgetActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtPass;
    TextView txtForget;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        findViews();
        txtForget.setOnClickListener(view ->
                startActivity(new Intent(this, LoginActivity.class)));
    }

    private void findViews() {
        edtUser = findViewById(R.id.edtUserForget);
        edtPass = findViewById(R.id.edtPassForget);
        txtForget = findViewById(R.id.txtBackForget);
        btnLogin = findViewById(R.id.btnSubmitForget);
    }
}