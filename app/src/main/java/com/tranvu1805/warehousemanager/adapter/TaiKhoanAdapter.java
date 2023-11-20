package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.R;

import java.util.ArrayList;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder> {
    Context context;
    ArrayList<UserDTO> userDTOS;
    AlertDialog dialog;
    UserDAO userDAO;

    public TaiKhoanAdapter(Context context, ArrayList<UserDTO> userDTOS) {
        this.context = context;
        this.userDTOS = userDTOS;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.row_user_home, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDTO u = userDTOS.get(position);
        holder.txtName.setText(u.getName());
        holder.txtAccount.setText(u.getAccount());
        holder.itemView.setOnLongClickListener(view -> {
            CustomDialog.dialogDouble(context, "Thông báo", "Xác nhận xóa tài khoản này", "Có",
                    (dialogInterface, i) -> {
                        userDAO = new UserDAO(context);
                        int check = userDAO.delete(u);
                        if (check > 0) {
                            userDTOS.remove(u);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    },
                    "Không", (dialogInterface, i) -> {

                    });
            return true;
        });
        holder.btnEdit.setOnClickListener(view -> {
            displayDialog(u);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void displayDialog(UserDTO u) {
        dialog = CustomDialog.builderSetView(context, R.layout.edit_user_layout, view1 -> {
            TextInputEditText edtAccount = view1.findViewById(R.id.edtUserUpdate);
            TextInputEditText edtPass = view1.findViewById(R.id.edtPassUpdate);
            TextInputEditText edtName = view1.findViewById(R.id.edtNameUpdate);
            TextInputEditText edtEmail = view1.findViewById(R.id.edtEmailUpdate);
            RadioGroup rdoGroup = view1.findViewById(R.id.rdoGroupUpdate);
            RadioButton rdoAdmin = view1.findViewById(R.id.rdoAdminUpdate);
            RadioButton rdoNv = view1.findViewById(R.id.rdoNvUpdate);
            Button btnConfirm = view1.findViewById(R.id.btnConfirmUpdate);
            Button btnCancel = view1.findViewById(R.id.btnCancelUpdate);

            edtAccount.setText(u.getAccount());
            edtPass.setText(u.getPass());
            edtName.setText(u.getName());
            if (u.getRole() == 1) rdoAdmin.setChecked(true);
            else rdoNv.setChecked(true);
            rdoGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                rdoNv.setChecked(!rdoAdmin.isChecked());
            });

            btnConfirm.setOnClickListener(view -> {
                String account = edtAccount.getText().toString();
                String pass = edtPass.getText().toString();
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                int role = rdoAdmin.isChecked()?1:0;
                if(account.isEmpty()||pass.isEmpty()||name.isEmpty()||email.isEmpty()){
                    CustomDialog.dialogSingle(context, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i)->{});
                }else {
                    u.setAccount(account);
                    u.setPass(pass);
                    u.setName(name);
                    u.setEmail(email);
                    u.setRole(role);
                    int check = userDAO.update(u);
                    if(check>0){
                        userDTOS.clear();
                        userDTOS.addAll(userDAO.getList());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        CustomDialog.dialogSingle(context, "Thông báo", "Sửa thất bại", "OK", (dialogInterface, i)->{});
                    }
                }
            });
            btnCancel.setOnClickListener(view -> {
                dialog.cancel();
            });
            edtEmail.setText(u.getEmail());


        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return userDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAccount, txtName;
        ImageButton btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtFullNameRowHome);
            txtAccount = itemView.findViewById(R.id.txtAccountRowHome);
            btnEdit = itemView.findViewById(R.id.btnEditRow);
        }
    }
}
