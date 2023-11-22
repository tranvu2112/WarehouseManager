package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtPass;
    TextView txtForget;
    Button btnLogin;
    CheckBox chkRemember;
    boolean isPassVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        invisiblePassword();
        checkRemember();
        btnLogin.setOnClickListener(view -> login());
        txtForget.setOnClickListener(view -> startActivity(new Intent(this, ForgetActivity.class)));
    }

    private void login() {
        if (edtUser.getText().toString().isEmpty() || edtPass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            UserDAO userDAO = new UserDAO(this);
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();
            UserDTO userDTO = userDAO.getLogin(user);
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
        editor.putInt("idUser", userDTO.getId());
        editor.putInt("role", userDTO.getRole());
        editor.putString("password", userDTO.getPass());
        editor.putBoolean("checked",chkRemember.isChecked());
        editor.apply();
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