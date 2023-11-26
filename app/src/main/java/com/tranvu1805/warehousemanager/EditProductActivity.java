package com.tranvu1805.warehousemanager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.CategoryDAO;
import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.SpinCatAdapter;

import java.util.ArrayList;

public class EditProductActivity extends AppCompatActivity {
    TextInputEditText edtName, edtPrice, edtQuan, edtDetail;
    Spinner spCat;
    Button btnConfirm, btnCancel;
    ImageButton btnSelectImg;
    SpinCatAdapter spinCatAdapter;
    ArrayList<CategoryDTO> categoryDTOS;
    CategoryDAO categoryDAO;
    Uri uriImg;
    ActivityResultLauncher<Intent> getImg;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        findViews();
        getImgForProduct();
        getDataAndSetToViews();
        btnConfirm.setOnClickListener(view -> updateProduct());
        btnSelectImg.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            getImg.launch(intent);
        });

    }

    private void updateProduct() {
        ProductDAO productDAO = new ProductDAO(this);
        ArrayList<ProductDTO> productDTOS= (ArrayList<ProductDTO>) productDAO.getList();
        String name = edtName.getText().toString();
        String priceString = edtPrice.getText().toString();
        String quanString = edtQuan.getText().toString();
        String detail = edtDetail.getText().toString();
        int idCat = (int) spinCatAdapter.getItemId(spCat.getSelectedItemPosition());
        if(name.isEmpty()||priceString.isEmpty()||quanString.isEmpty()||detail.isEmpty()||uriImg==null){
            CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i)->{});
        }else{
            try {
                int price = Integer.parseInt(priceString);
                int quan = Integer.parseInt(quanString);
                if(price<=0||quan<=0){
                    CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng>0", "OK", (dialogInterface, i)->{});
                }else {
                    ProductDTO productDTO = new ProductDTO(id,idCat,uriImg.toString(),name,price,quan,detail);
                    int check = productDAO.update(productDTO);
                    if(check>0){
                        CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật thành công", "OK", (dialogInterface, i)->{finish();});
                    }else {
                        CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật thất bại", "OK", (dialogInterface, i)->{finish();});
                    }
                }
            }catch (Exception e){
                CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng đúng định dạng", "OK", (dialogInterface, i)->{});
            }
        }
    }

    private void getDataAndSetToViews() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String name = bundle.getString("Name");
                String uriImg = bundle.getString("UriImg");
                String detail = bundle.getString("Detail");
                id = bundle.getInt("Id");
                int idCat = bundle.getInt("IdCat");
                int price = bundle.getInt("Price");
                int quantity = bundle.getInt("Quantity");
                int pos = 0;
                edtName.setText(name);
                edtQuan.setText(quantity + "");
                edtPrice.setText(price + "");
                edtDetail.setText(detail);
                btnSelectImg.setImageURI(Uri.parse(uriImg));
                for (int i = 0; i < categoryDTOS.size(); i++) {
                    if (categoryDTOS.get(i).getId() == idCat) {
                        pos = i;
                    }
                }
                spCat.setSelection(pos);
            }

        }
    }

    private void findViews() {
        edtDetail = findViewById(R.id.edtDetailProEdit);
        edtQuan = findViewById(R.id.edtQuantityEdit);
        edtPrice = findViewById(R.id.edtPriceEdit);
        edtName = findViewById(R.id.edtNameProEdit);
        spCat = findViewById(R.id.spCatProEdit);
        btnCancel = findViewById(R.id.btnCancelProEdit);
        btnConfirm = findViewById(R.id.btnConfirmProEdit);
        btnSelectImg = findViewById(R.id.btnImgProEdit);

        categoryDAO = new CategoryDAO(this);
        categoryDTOS = (ArrayList<CategoryDTO>) categoryDAO.getList();
        spinCatAdapter = new SpinCatAdapter(this, categoryDTOS);
        spCat.setAdapter(spinCatAdapter);
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