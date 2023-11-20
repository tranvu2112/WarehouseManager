package com.tranvu1805.warehousemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;

import java.util.List;

public class ForgetActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtEmail;
    TextView txtForget;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        findViews();
        btnSubmit.setOnClickListener(view -> forgetPassword());
        txtForget.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private void forgetPassword() {
        if (edtUser.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            UserDAO userDAO = new UserDAO(this);
            List<UserDTO> userDTOS = userDAO.getList();
            String user = edtUser.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            for (UserDTO u : userDTOS) {
                if (u.getAccount().equals(user) && u.getEmail().equals(email)) {
                    u.setPass("111111");
                    userDAO.update(u);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Mật khẩu đã đặt về mặc định là 111111");
                    builder.setNegativeButton("OK", (dialogInterface, i) -> {

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Tài khoản, email không chính xác");
                    builder.setNegativeButton("OK", (dialogInterface, i) -> {

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }

    private void findViews() {
        edtUser = findViewById(R.id.edtUserForget);
        edtEmail = findViewById(R.id.edtPassForget);
        txtForget = findViewById(R.id.txtBackForget);
        btnSubmit = findViewById(R.id.btnSubmitForget);
    }
}