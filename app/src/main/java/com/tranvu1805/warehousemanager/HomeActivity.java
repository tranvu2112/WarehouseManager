package com.tranvu1805.warehousemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;

public class HomeActivity extends AppCompatActivity {
    ImageButton btnAccount, btnProduct, btnChangePass, btnCategory, btnInvoice, btnStatiscal;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findView();
        btnAccount.setOnClickListener(view -> accountOnClick());
        btnProduct.setOnClickListener(view -> {

        });
        btnChangePass.setOnClickListener(view1 -> {
            dialog = CustomDialog.builderSetView(this, R.layout.change_pass_dialog_layout, view -> {
                TextInputEditText edtOldPass = view.findViewById(R.id.edtOldPassChange);
                TextInputEditText edtNewPass = view.findViewById(R.id.edtNewPassChange);
                TextInputEditText edtConfirmNew = view.findViewById(R.id.edtConfirmNewPassChange);
                Button btnConfirm = view.findViewById(R.id.btnConfirmChange);
                Button btnCancel = view.findViewById(R.id.btnCancelChange);
                btnConfirm.setOnClickListener(view2 -> {
                    String oldPass = edtOldPass.getText().toString();
                    String newPass = edtNewPass.getText().toString();
                    String confirmPass = edtConfirmNew.getText().toString();
                    if(oldPass.isEmpty()||newPass.isEmpty()||confirmPass.isEmpty()){
                        CustomDialog.dialogSingle(HomeActivity.this, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i)->{});
                    }else if (!newPass.equals(confirmPass)){
                        CustomDialog.dialogSingle(HomeActivity.this, "Thông báo", "Mật khẩu không khớp", "OK", (dialogInterface, i)->{});
                    }else {
                        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        String nowPass = sharedPreferences.getString("password","");
                        if(nowPass.equals(oldPass)){
                            int id = sharedPreferences.getInt("idAccount",-1);
                            String account=sharedPreferences.getString("account","");
                            int role = sharedPreferences.getInt("role",-1);
                            String name=sharedPreferences.getString("name","");
                            String email=sharedPreferences.getString("email","");
                            UserDTO userDTO = new UserDTO(id,account,newPass,role,name,email);
                            UserDAO userDAO = new UserDAO(HomeActivity.this);
                            int check = userDAO.update(userDTO);
                            if(check>0){
                                CustomDialog.dialogSingle(HomeActivity.this, "Thông báo", "Đổi mật khẩu thành công", "OK", (dialogInterface, i)-> dialog.cancel());
                            }else {
                                CustomDialog.dialogSingle(HomeActivity.this, "Thông báo", "Đổi mật khẩu thất bại", "OK", (dialogInterface, i)->{});
                            }
                        }
                    }
                });
                btnCancel.setOnClickListener(view2 -> dialog.cancel());
            });
            dialog.show();
        });

    }

    private void accountOnClick() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", 1);
        if (role == 1) {
            startActivity(new Intent(this, AccountHome.class));
        } else {
            CustomDialog.dialogSingle(this, "Thông báo", "Bạn không đủ quyền truy cập", "OK", (dialogInterface, i) -> {
            });
        }
    }

    private void findView() {
        btnProduct = findViewById(R.id.btnProduct);
        btnAccount = findViewById(R.id.btnUser);
        btnCategory = findViewById(R.id.btnCategory);
        btnChangePass = findViewById(R.id.btnPassChange);
        btnStatiscal = findViewById(R.id.btnStatiscal);
        btnInvoice = findViewById(R.id.btnInvoice);
    }
}