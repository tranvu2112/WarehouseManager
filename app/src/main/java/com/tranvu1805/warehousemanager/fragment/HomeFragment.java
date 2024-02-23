package com.tranvu1805.warehousemanager.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.Model.UserDTO;
import com.tranvu1805.warehousemanager.R;

import java.util.Objects;

public class HomeFragment extends Fragment {
    ImageButton btnAccount, btnProduct, btnChangePass, btnCategory, btnInvoice, btnStatiscal;
    AlertDialog dialog;
    Fragment fragment;
    BottomNavigationView bnvMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnProduct = view.findViewById(R.id.btnProduct);
        btnAccount = view.findViewById(R.id.btnUser);
        btnCategory = view.findViewById(R.id.btnCategory);
        btnChangePass = view.findViewById(R.id.btnPassChange);
        btnStatiscal = view.findViewById(R.id.btnStatiscal);
        btnInvoice = view.findViewById(R.id.btnInvoice);
        bnvMenu = requireActivity().findViewById(R.id.bnvHome);

        btnAccount.setOnClickListener(view1 -> accountOnClick());
        btnProduct.setOnClickListener(view1 -> replaceFragment(new ProductFragment(), R.id.product));
        btnCategory.setOnClickListener(view1 -> {
            fragment = new CategoryFragment();
            getParentFragmentManager().beginTransaction().replace(R.id.fragHome, fragment).commit();
        });
        btnChangePass.setOnClickListener(view1 -> changePass());
        btnInvoice.setOnClickListener(view1 -> replaceFragment(new InvoiceFragment(), R.id.bill));
        btnStatiscal.setOnClickListener(v -> replaceFragment(new StatiscalFragment(), R.id.status));
    }

    private void replaceFragment(Fragment fragmentReplace, int idItem) {
        fragment = fragmentReplace;
        getParentFragmentManager().beginTransaction().replace(R.id.fragHome, fragment).commit();
        bnvMenu.setSelectedItemId(idItem);
    }

    private void changePass() {
        dialog = CustomDialog.builderSetView(requireActivity(), R.layout.change_pass_dialog_layout, view -> {

            TextInputEditText edtOldPass = view.findViewById(R.id.edtOldPassChange);
            TextInputEditText edtNewPass = view.findViewById(R.id.edtNewPassChange);
            TextInputEditText edtConfirmNew = view.findViewById(R.id.edtConfirmNewPassChange);
            Button btnConfirm = view.findViewById(R.id.btnConfirmChange);
            Button btnCancel = view.findViewById(R.id.btnCancelChange);

            btnConfirm.setOnClickListener(view2 -> {
                String oldPass = Objects.requireNonNull(edtOldPass.getText()).toString();
                String newPass = Objects.requireNonNull(edtNewPass.getText()).toString();
                String confirmPass = Objects.requireNonNull(edtConfirmNew.getText()).toString();
                if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    CustomDialog.dialogSingle(requireActivity(), "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i) -> {
                    });
                } else if (!newPass.equals(confirmPass)) {
                    CustomDialog.dialogSingle(requireActivity(), "Thông báo", "Mật khẩu không khớp", "OK", (dialogInterface, i) -> {
                    });
                } else {
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", MODE_PRIVATE);
                    String nowPass = sharedPreferences.getString("password", "");
                    if (nowPass.equals(oldPass)) {
                        int id = sharedPreferences.getInt("idAccount", -1);
                        String account = sharedPreferences.getString("account", "");
                        int role = sharedPreferences.getInt("role", -1);
                        String name = sharedPreferences.getString("name", "");
                        String email = sharedPreferences.getString("email", "");
                        UserDTO userDTO = new UserDTO(id, account, newPass, role, name, email);
                        int check = MyDatabase.getInstance(requireActivity()).userDAO().updateUser(userDTO);
                        if (check > 0) {
                            CustomDialog.dialogSingle(requireActivity(), "Thông báo", "Đổi mật khẩu thành công", "OK", (dialogInterface, i) -> dialog.cancel());
                        } else {
                            CustomDialog.dialogSingle(requireActivity(), "Thông báo", "Đổi mật khẩu thất bại", "OK", (dialogInterface, i) -> {
                            });
                        }
                    }
                }
            });
            btnCancel.setOnClickListener(view2 -> dialog.cancel());
        });
        dialog.show();
    }

    private void accountOnClick() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", 1);
        if (role == 1) {
            replaceFragment(new UserFragment(), R.id.user);
        } else {
            replaceFragment(new NoticeFragment(), R.id.user);
        }
    }
}