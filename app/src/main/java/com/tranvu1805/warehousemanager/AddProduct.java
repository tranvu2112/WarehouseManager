package com.tranvu1805.warehousemanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.CategoryDAO;
import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.SpinCatAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

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
        btnConfirm.setOnClickListener(view -> addToList());
        btnCancel.setOnClickListener(v -> finish());
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
        categoryDAO = new CategoryDAO(this);
        productDAO = new ProductDAO(this);
        categoryDTOS = (ArrayList<CategoryDTO>) categoryDAO.getList();
        adapter = new SpinCatAdapter(this, categoryDTOS);
    }

    private void addToList() {
        String name = Objects.requireNonNull(edtName.getText()).toString();
        String priceString = Objects.requireNonNull(edtPrice.getText()).toString();
        String quanString = Objects.requireNonNull(edtQuan.getText()).toString();
        String detail = Objects.requireNonNull(edtDetail.getText()).toString();
        int idCat = (int) adapter.getItemId(spCat.getSelectedItemPosition());
        if (name.isEmpty() || priceString.isEmpty() || quanString.isEmpty() || detail.isEmpty() || uriImg == null) {
            CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin", "OK", (dialogInterface, i) -> {
            });
        } else {
            try {
                int price = Integer.parseInt(priceString);
                int quan = Integer.parseInt(quanString);
                if (price <= 0 || quan <= 0) {
                    CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng > 0", "OK", (dialogInterface, i) -> {
                    });
                } else {
                    if (uriImg != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uriImg);
                            assert inputStream != null;
                            byte[] imageData = getBytes(inputStream);
                            ProductDTO productDTO = new ProductDTO(idCat, name, price, quan, detail,imageData);
                            int check = productDAO.addRow(productDTO);
                            if (check > 0) {
                                CustomDialog.dialogSingle(this, "Thông báo", "Thêm thành công", "OK", (dialogInterface, i) -> finish());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                CustomDialog.dialogSingle(this, "Thông báo", "Nhập giá, số lượng đúng định dạng", "OK", (dialogInterface, i) -> {
                });
            }
        }
    }

    private void getImgForProduct() {
        getImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Intent intent = o.getData();
                if (intent != null) {
                    uriImg = intent.getData();
                    loadImageFromUri(uriImg);
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

    private void loadImageFromUri(Uri uri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            btnSelectImg.setImageBitmap(bitmap);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            CustomDialog.dialogSingle(this, "Thông báo", "Đã xảy ra lỗi khi đọc hình ảnh", "OK", (dialogInterface, i) -> {
            });
        }
    }
}
