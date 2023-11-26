package com.tranvu1805.warehousemanager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.CategoryDAO;
import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.SpinCatAdapter;

import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {
    TextInputEditText edtName, edtPrice, edtQuan, edtDetail;
    Button btnConfirm, btnCancel;
    ImageButton btnSelectImg;
    Spinner spCat;
    ActivityResultLauncher<Intent> getImg;
    CategoryDAO categoryDAO;
    ProductDAO productDAO;
    ArrayList<CategoryDTO> categoryDTOS;
    SpinCatAdapter adapter;
    Uri uriImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getImgForProduct();
        findViews();
        spCat.setAdapter(adapter);
        btnSelectImg.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            getImg.launch(intent);
        });
        btnConfirm.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            String priceString = edtPrice.getText().toString();
            String quanString = edtQuan.getText().toString();
            String detail = edtDetail.getText().toString();
            int idCat = (int) adapter.getItemId(spCat.getSelectedItemPosition());
            if(name.isEmpty()||priceString.isEmpty()||quanString.isEmpty()||detail.isEmpty()||uriImg==null){
                CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i)->{});
            }else{
                try {
                    int price = Integer.parseInt(priceString);
                    int quan = Integer.parseInt(quanString);
                    if(price<=0||quan<=0){
                        CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng>0", "OK", (dialogInterface, i)->{});
                    }else {
                        ProductDTO productDTO = new ProductDTO(idCat,uriImg.toString(),name,price,quan,detail);
                        int check = productDAO.addRow(productDTO);
                        if(check>0){
                            CustomDialog.dialogSingle(this, "Thông báo", "Thêm thành công", "OK", (dialogInterface, i)->{finish();});
                        }
                    }
                }catch (Exception e){
                    CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng đúng định dạng", "OK", (dialogInterface, i)->{});
                }
            }
        });
    }

    private void findViews() {
        btnConfirm = findViewById(R.id.btnConfirmProAdd);
        btnCancel = findViewById(R.id.btnCancelProAdd);
        btnSelectImg = findViewById(R.id.btnImgProAdd);
        edtName = findViewById(R.id.edtNameProAdd);
        edtPrice = findViewById(R.id.edtPriceProAdd);
        edtQuan = findViewById(R.id.edtQuantityProAdd);
        edtDetail = findViewById(R.id.edtDetailProAdd);
        spCat = findViewById(R.id.spCatProAdd);
        categoryDAO =new CategoryDAO(this);
        productDAO = new ProductDAO(this);
        categoryDTOS = (ArrayList<CategoryDTO>) categoryDAO.getList();
        adapter = new SpinCatAdapter(this,categoryDTOS);

    }
    private void getImgForProduct() {
        getImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if(o.getResultCode()== Activity.RESULT_OK){
                Intent intent = o.getData();
                if (intent != null) {
                    uriImg = intent.getData();
                    btnSelectImg.setImageURI(uriImg);
                }else {
                    CustomDialog.dialogSingle(this,"Thông báo","Bạn chưa chọn hình","OK",(dialogInterface, i) -> {});
                }
            }else {
                CustomDialog.dialogSingle(this,"Thông báo","Bạn chưa chọn hình","OK",(dialogInterface, i) -> {});
            }
        });
    }
}