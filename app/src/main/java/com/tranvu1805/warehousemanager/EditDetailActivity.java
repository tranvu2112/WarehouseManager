package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.InvoiceDAO;
import com.tranvu1805.warehousemanager.DAO.InvoiceDetailDAO;
import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDTO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.ProductInvoiceAdapter;
import com.tranvu1805.warehousemanager.adapter.SpinProAdapter;

import java.util.ArrayList;

public class EditDetailActivity extends AppCompatActivity {
    TextInputEditText edtNumber, edtDate;
    RadioButton rdoImport, rdoExport;
    Button btnSave;
    Spinner spinPro;
    ProductDAO productDAO;
    InvoiceDAO invoiceDAO;
    ArrayList<ProductDTO> productDTOS, listProductToBuy;
    SpinProAdapter spinProAdapter;
    RecyclerView rvProduct;

    int idInvoice = 0;
    ArrayList<InvoiceDetailDTO> invoiceDetailDTOS;
    InvoiceDetailDAO invoiceDetailDAO;
    ProductInvoiceAdapter productInvoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);
        findViews();
        Intent intent = getIntent();
        if (intent != null) {
            idInvoice = intent.getIntExtra("idInvoice", 0);
            String num = intent.getStringExtra("number");
            String dateInvoice = intent.getStringExtra("date");
            int typeInt = intent.getIntExtra("type", -1);
            if (typeInt == 0) {
                rdoImport.setChecked(true);
            } else {
                rdoExport.setChecked(true);
            }
            edtNumber.setText(num);
            edtDate.setText("Ngày tạo: " + dateInvoice);
            spinPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ProductDTO selectedProduct = (ProductDTO) spinPro.getSelectedItem();
                    if (selectedProduct != null) {
                        boolean isProductExist = false;
                        for (ProductDTO p : listProductToBuy) {
                            if (p.getId() == selectedProduct.getId()) {
                                CustomDialog.dialogSingle(EditDetailActivity.this, "Thông báo", "Sản phẩm đã có trong hóa đơn, vui lòng chọn sản phẩm khác", "OK", (dialogInterface, i1) -> {
                                });
                                isProductExist = true;
                                break;
                            }
                        }
                        if (!isProductExist) {
                            listProductToBuy.add(selectedProduct);
                            productInvoiceAdapter = new ProductInvoiceAdapter(EditDetailActivity.this, listProductToBuy);
                            productInvoiceAdapter.notifyDataSetChanged();
                            rvProduct.setVisibility(View.VISIBLE);
                            rvProduct.setAdapter(productInvoiceAdapter);
                        }
                    } else {
                        rvProduct.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            btnSave.setOnClickListener(view -> {
                String numberInvoice = edtNumber.getText().toString();
                String date = edtDate.getText().toString();

                if (numberInvoice.isEmpty() || date.isEmpty() || listProductToBuy.isEmpty()) {
                    CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin và sản phẩm", "OK", (dialogInterface, i) -> {
                    });
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                    int idUser = sharedPreferences.getInt("idAccount", 0);
                    int type = rdoImport.isChecked() ? 0 : 1;
                    InvoiceDTO invoiceDTO = new InvoiceDTO(idInvoice, numberInvoice, idUser, type, date, "");
                    invoiceDAO=new InvoiceDAO(this);
                    try {
                        int idNew = invoiceDAO.update(invoiceDTO);
                        if (idNew > 0) {
                            for (ProductDTO p : listProductToBuy) {
                                InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO(p.getId(), idInvoice, p.getQuantity(), p.getPrice());
                                InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO(this);
                                invoiceDetailDAO.update(invoiceDetailDTO);
                            }
                            CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật hóa đơn thành công", "OK", (dialogInterface, i) -> {
                            });
                            finish();
                        }
                    }catch (Exception e){
                        CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật hóa đơn thất bại", "OK", (dialogInterface, i) -> {
                        });
                    }

                }
            });
        }
    }
    //đổ listproductTobuy bằng cách lấy dữ liệu từ bên hóa đơn chi tiết
    private void findViews() {
        edtNumber = findViewById(R.id.edtInvoiceDetailNumberEdit);
        edtDate = findViewById(R.id.edtInvoiceDetailDateEdit);
        rdoExport = findViewById(R.id.rdoInvoiceDetailExportEdit);
        rdoImport = findViewById(R.id.rdoInvoiceDetailImportEdit);
        btnSave = findViewById(R.id.btnSaveInvoiceDetailEdit);
        spinPro = findViewById(R.id.spProductInvoiceDetailEdit);
        rvProduct = findViewById(R.id.rvProductInvoiceDetailEdit);
        productDAO = new ProductDAO(this);
        productDTOS = (ArrayList<ProductDTO>) productDAO.getList();
        spinProAdapter = new SpinProAdapter(this, productDTOS);
        spinPro.setAdapter(spinProAdapter);
        invoiceDetailDAO = new InvoiceDetailDAO(this);
        listProductToBuy = new ArrayList<>();
    }
}