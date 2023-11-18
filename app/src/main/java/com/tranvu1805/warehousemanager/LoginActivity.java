package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtPass;
    TextView txtForget;
    Button btnLogin;
    boolean isPassVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        invisiblePassword();
        btnLogin.setOnClickListener(view -> login());
        txtForget.setOnClickListener(view -> startActivity(new Intent(this, ForgetActivity.class)));
    }

    private void login() {
        if (edtUser.getText().toString().isEmpty() || edtPass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            UserDAO userDAO = new UserDAO(this);
            List<UserDTO> userDTOS = userDAO.getList();
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();
            for (UserDTO u : userDTOS) {
                if (u.getName().equals(user) && u.getPass().equals(pass)) {
                    startActivity(new Intent(this, HomeActivity.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Tài khoản, mật khẩu không chính xác");
                    builder.setNegativeButton("OK", (dialogInterface, i) -> {

                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }

    //ẩn hiện mật khẩu
    @SuppressLint("ClickableViewAccessibility")
    private void invisiblePassword() {
        edtPass.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                int drawableWidth = edtPass.getCompoundDrawables()[2].getBounds().width();
                if (motionEvent.getRawX() >= (edtPass.getRight() - drawableWidth)) {
                    isPassVisible = !isPassVisible;
                    if (isPassVisible) {
                        edtPass.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                        edtPass.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                ContextCompat.getDrawable(this, R.drawable.invisible), null);
                    } else {
                        edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        edtPass.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                ContextCompat.getDrawable(this, R.drawable.visible), null);
                    }
                    return true;
                }
            }
            return false;
        });
    }

    private void findViews() {
        edtUser = findViewById(R.id.edtUserLogin);
        edtPass = findViewById(R.id.edtPassLogin);
        txtForget = findViewById(R.id.txtForgetLogin);
        btnLogin = findViewById(R.id.btnLogin);
    }
}