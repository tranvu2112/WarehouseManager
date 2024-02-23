package com.tranvu1805.warehousemanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.Model.UserDTO;

import java.util.Objects;

public class AddAccount extends AppCompatActivity {
    TextInputEditText edtAccount, edtPass, edtConfirmPass, edtName, edtEmail;
    RadioButton rdoAdmin, rdoNv;
    RadioGroup rdoGroup;
    Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        findViews();
        setSelectOnRadioGroup();
        btnCancel.setOnClickListener(view -> this.finish());
        btnConfirm.setOnClickListener(view -> addUser());
    }

    private void addUser() {
        String account = Objects.requireNonNull(edtAccount.getText()).toString();
        String pass = Objects.requireNonNull(edtPass.getText()).toString();
        String confirmPass = Objects.requireNonNull(edtConfirmPass.getText()).toString();
        int role = rdoAdmin.isChecked() ? 1 : 0;
        String name = Objects.requireNonNull(edtName.getText()).toString();
        String email = Objects.requireNonNull(edtEmail.getText()).toString();
        if (account.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || name.isEmpty() || email.isEmpty()) {
            CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i) -> {
            });
        } else if (!pass.equals(confirmPass)) {
            CustomDialog.dialogSingle(this, "Thông báo", "Xác nhận mật khẩu không khớp", "OK", (dialogInterface, i) -> {
            });
        } else {
            UserDTO userDTO = new UserDTO(account, pass, role, name, email);
            long check = MyDatabase.getInstance(this).userDAO().insertUser(userDTO);
            if (check > 0) {
                CustomDialog.dialogSingle(this, "Thông báo", "Thêm tài khoản thành công", "OK", (dialogInterface, i) -> {
                    edtAccount.setText("");
                    edtPass.setText("");
                    edtConfirmPass.setText("");
                    edtName.setText("");
                    edtEmail.setText("");
                    finish();
                });

            }
        }
    }

    private void setSelectOnRadioGroup() {
        rdoGroup.setOnCheckedChangeListener(((radioGroup, i) -> rdoNv.setChecked(!rdoAdmin.isChecked())));
    }

    private void findViews() {
        edtAccount = findViewById(R.id.edtUserAdd);
        edtPass = findViewById(R.id.edtPassAdd);
        edtConfirmPass = findViewById(R.id.edtConfirmPassAdd);
        edtName = findViewById(R.id.edtNameAdd);
        edtEmail = findViewById(R.id.edtEmailAdd);
        rdoAdmin = findViewById(R.id.rdoAdminAdd);
        rdoNv = findViewById(R.id.rdoNvAdd);
        rdoGroup = findViewById(R.id.rdoGroup);
        btnCancel = findViewById(R.id.btnCancelAdd);
        btnConfirm = findViewById(R.id.btnConfirmAdd);
    }

}