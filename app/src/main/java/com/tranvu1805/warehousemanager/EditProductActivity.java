package com.tranvu1805.warehousemanager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Model.CategoryDTO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.SpinCatAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class EditProductActivity extends AppCompatActivity {
    TextInputEditText edtName, edtPrice, edtQuan, edtDetail;
    Spinner spCat;
    Button btnConfirm, btnCancel;
    ImageButton btnSelectImg;
    SpinCatAdapter spinCatAdapter;
    ArrayList<CategoryDTO> categoryDTOS;

    Uri uriImg;
    ActivityResultLauncher<Intent> getImg;
    private int id;
    private byte[] imgBlob;

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
        String name = Objects.requireNonNull(edtName.getText()).toString();
        String priceString = Objects.requireNonNull(edtPrice.getText()).toString();
        String quanString = Objects.requireNonNull(edtQuan.getText()).toString();
        String detail = Objects.requireNonNull(edtDetail.getText()).toString();
        int idCat = (int) spinCatAdapter.getItemId(spCat.getSelectedItemPosition());
        if (name.isEmpty() || priceString.isEmpty() || quanString.isEmpty() || detail.isEmpty() || imgBlob == null) {
            CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i) -> {
            });
        } else {
            try {
                int price = Integer.parseInt(priceString);
                int quan = Integer.parseInt(quanString);
                if (price <= 0 || quan <= 0) {
                    CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng>0", "OK", (dialogInterface, i) -> {
                    });
                } else {
                    if(uriImg!=null){
                        InputStream inputStream = getContentResolver().openInputStream(uriImg);
                        assert inputStream != null;
                        imgBlob = getBytes(inputStream);
                    }
                    ProductDTO productDTO = new ProductDTO(idCat, name, price, quan, detail,imgBlob);
                    productDTO.setId(id);
                    int check = MyDatabase.getInstance(this).productDAO().updateProduct(productDTO);
                    if (check > 0) {
                        CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật thành công", "OK", (dialogInterface, i) -> finish());
                    } else {
                        CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật thất bại", "OK", (dialogInterface, i) -> finish());
                    }
                }
            } catch (Exception e) {
                CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng đúng định dạng", "OK", (dialogInterface, i) -> {
                });
            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @SuppressLint("SetTextI18n")
    private void getDataAndSetToViews() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String name = bundle.getString("Name");
                imgBlob = bundle.getByteArray("imgBlob");
                String detail = bundle.getString("Detail");
                int idCat = bundle.getInt("IdCat");
                id = bundle.getInt("Id");
                int price = bundle.getInt("Price");
                int quantity = bundle.getInt("Quantity");
                int pos = 0;
                edtName.setText(name);
                edtQuan.setText(String.valueOf(quantity));
                edtPrice.setText(String.valueOf(price));
                edtDetail.setText(detail);
                if(imgBlob!=null){
                    Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgBlob,0,imgBlob.length);
                    btnSelectImg.setImageBitmap(imgBitmap);
                }
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
        categoryDTOS = (ArrayList<CategoryDTO>) MyDatabase.getInstance(this).categoryDAO().getAll();
        spinCatAdapter = new SpinCatAdapter(this, categoryDTOS);
        spCat.setAdapter(spinCatAdapter);
    }

    private void getImgForProduct() {
        getImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Intent intent = o.getData();
                if (intent != null) {
                    uriImg = intent.getData();
                    btnSelectImg.setImageURI(uriImg);
                } else {
                    CustomDialog.dialogSingle(this, "Thông báo", "Bạn chưa chọn hình", "OK", (dialogInterface, i) -> {
                    });
                }
            } else {
                CustomDialog.dialogSingle(this, "Thông báo", "Bạn chưa chọn hình", "OK", (dialogInterface, i) -> {
                });
            }
        });
    }
}