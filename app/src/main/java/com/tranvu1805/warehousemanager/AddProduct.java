package com.tranvu1805.warehousemanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.Model.CategoryDTO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.adapter.SpinCatAdapter;
import com.tranvu1805.warehousemanager.databinding.ActivityAddProductBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class AddProduct extends AppCompatActivity {
    ActivityAddProductBinding binding;

    ActivityResultLauncher<Intent> getImg;
    ArrayList<CategoryDTO> categoryDTOS;
    SpinCatAdapter adapter;
    Uri uriImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getImgForProduct();
        findViews();
        binding.spCatProAdd.setAdapter(adapter);
        binding.btnImgProAdd.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            getImg.launch(intent);
        });
        binding.btnConfirmProAdd.setOnClickListener(view -> addToList());
        binding.btnCancelProAdd.setOnClickListener(v -> finish());
    }

    private void findViews() {
        categoryDTOS = (ArrayList<CategoryDTO>) MyDatabase.getInstance(this).categoryDAO().getAll();
        adapter = new SpinCatAdapter(this, categoryDTOS);
    }

    private void addToList() {
        String name = Objects.requireNonNull(binding.edtNameProAdd.getText()).toString();
        String priceString = Objects.requireNonNull(binding.edtPriceProAdd.getText()).toString();
        String quanString = Objects.requireNonNull(binding.edtQuantityProAdd.getText()).toString();
        String detail = Objects.requireNonNull(binding.edtDetailProAdd.getText()).toString();
        int idCat = (int) adapter.getItemId(binding.spCatProAdd.getSelectedItemPosition());
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
                            ProductDTO productDTO = new ProductDTO(idCat, name, price, quan, detail, imageData);
                            long check = MyDatabase.getInstance(this).productDAO().insertProduct(productDTO);
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
            binding.btnImgProAdd.setImageBitmap(bitmap);
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
