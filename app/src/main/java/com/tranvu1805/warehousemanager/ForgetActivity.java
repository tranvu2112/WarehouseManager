package com.tranvu1805.warehousemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;

import java.util.Objects;

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
        if (Objects.requireNonNull(edtUser.getText()).toString().isEmpty() ||
                Objects.requireNonNull(edtEmail.getText()).toString().isEmpty()) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            UserDAO userDAO = new UserDAO(this);
            String user = edtUser.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            UserDTO u = userDAO.getLogin(user);
            if (u.getAccount().equals(user)) {
                if (u.getEmail().equals(email)) {
                    u.setPass("1111");
                    int check = userDAO.update(u);
                    if (check > 0) {
                        CustomDialog.dialogSingle(this, "Thông báo", "Mật khẩu đã đổi về mặc định là 1111", "OK", ((dialogInterface, i) -> {
                        }));
                    } else {
                        CustomDialog.dialogSingle(this, "Thông báo", "Đổi mật khẩu thất bại", "OK", ((dialogInterface, i) -> {
                        }));
                    }
                } else {
                    CustomDialog.dialogSingle(this, "Thông báo", "Email không chính xác", "OK", ((dialogInterface, i) -> {
                    }));

                }
            } else {
                CustomDialog.dialogSingle(this, "Thông báo", "Không tồn tại user", "OK", ((dialogInterface, i) -> {
                }));
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