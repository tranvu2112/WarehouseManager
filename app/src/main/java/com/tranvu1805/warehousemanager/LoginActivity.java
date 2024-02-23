package com.tranvu1805.warehousemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.Model.UserDTO;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtPass;
    TextView txtForget;
    Button btnLogin;
    CheckBox chkRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        checkRemember();
        btnLogin.setOnClickListener(view -> login());
        txtForget.setOnClickListener(view -> startActivity(new Intent(this, ForgetActivity.class)));
    }

    private void login() {
        if (Objects.requireNonNull(edtUser.getText()).toString().isEmpty() ||
                Objects.requireNonNull(edtPass.getText()).toString().isEmpty()) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();
            UserDTO userDTO = MyDatabase.getInstance(this).userDAO().checkLogin(user);
            if (userDTO == null) {
                CustomDialog.dialogSingle(this, "Thông báo", "Không tồn tại user","OK",((dialogInterface, i) -> {}));

            } else {
                if (userDTO.getPass().equals(pass)) {
                    saveToSharedPreferences(userDTO);
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeActivity.class));
                } else {
                    CustomDialog.dialogSingle(this, "Thông báo", "Mật khẩu sai","OK",((dialogInterface, i) -> {}));
                }
            }

        }
    }

    private void saveToSharedPreferences(UserDTO userDTO) {
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("account", userDTO.getAccount());
        editor.putInt("idAccount", userDTO.getId());
        editor.putString("name", userDTO.getName());
        editor.putString("email", userDTO.getEmail());
        editor.putInt("role", userDTO.getRole());
        editor.putString("password", userDTO.getPass());
        editor.putBoolean("checked",chkRemember.isChecked());
        editor.apply();
    }

    private void findViews() {
        edtUser = findViewById(R.id.edtUserLogin);
        edtPass = findViewById(R.id.edtPassLogin);
        txtForget = findViewById(R.id.txtForgetLogin);
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chkRemember);
    }
    //check lưu tài khoản
    public void checkRemember(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String user = sharedPreferences.getString("account","");
        String pass = sharedPreferences.getString("password","");
        boolean isSave = sharedPreferences.getBoolean("checked",false);
        chkRemember.setChecked(isSave);
        if(chkRemember.isChecked()){
            edtUser.setText(user);
            edtPass.setText(pass);
        }
        editor.apply();
    }
}