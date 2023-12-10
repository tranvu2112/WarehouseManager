package com.tranvu1805.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.InvoiceDAO;
import com.tranvu1805.warehousemanager.DAO.InvoiceDetailDAO;
import com.tranvu1805.warehousemanager.DAO.ProductDAO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDTO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.ProductInvoiceAdapter;
import com.tranvu1805.warehousemanager.adapter.SpinProAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddInvoice extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 123;
    TextInputEditText edtNumber, edtDate;
    RadioButton rdoImport, rdoExport;
    Button btnSave;
    Spinner spinPro;
    ProductDAO productDAO;
    ArrayList<ProductDTO> productDTOS, listProductToBuy;
    SpinProAdapter spinProAdapter;
    RecyclerView rvProduct;
    InvoiceDAO invoiceDAO;
    ArrayList<InvoiceDTO> invoiceDTOS;
    ProductInvoiceAdapter productInvoiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);
        findViews();
        rdoImport.setChecked(!rdoExport.isChecked());
        setEdtDateNow();
        spinPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductDTO selectedProduct = (ProductDTO) spinPro.getSelectedItem();
                if (selectedProduct != null) {
                    boolean isProductExist = false;
                    for (ProductDTO p : listProductToBuy) {
                        if (p.getId() == selectedProduct.getId()) {
                            CustomDialog.dialogSingle(AddInvoice.this, "Thông báo", "Sản phẩm đã có trong hóa đơn, vui lòng chọn sản phẩm khác", "OK", (dialogInterface, i1) -> {
                            });
                            isProductExist = true;
                            break;
                        }
                    }

                    if (!isProductExist) {
                        listProductToBuy.add(selectedProduct);
                        productInvoiceAdapter = new ProductInvoiceAdapter(AddInvoice.this, listProductToBuy);
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
                InvoiceDTO invoiceDTO = new InvoiceDTO(numberInvoice, idUser, type, date, "");
                long idNew = invoiceDAO.addRowAndGetId(invoiceDTO);
                if (idNew > 0) {
                    for (ProductDTO p : listProductToBuy) {
                        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO(p.getId(), (int) idNew, p.getQuantity(), p.getPrice());
                        InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO(this);
                        invoiceDetailDAO.addRow(invoiceDetailDTO);
                    }
                    CustomDialog.dialogSingle(this, "Thông báo", "Tạo hóa đơn thành công", "OK", (dialogInterface, i) -> {
                    });
                    finish();
                } else {
                    CustomDialog.dialogSingle(this, "Thông báo", "Tạo hóa đơn thất bại", "OK", (dialogInterface, i) -> {
                    });
                }
            }
        });
    }

    private void findViews() {
        edtNumber = findViewById(R.id.edtInvoiceNumberAdd);
        edtDate = findViewById(R.id.edtInvoiceDateAdd);
        rdoExport = findViewById(R.id.rdoExport);
        rdoImport = findViewById(R.id.rdoImport);
        btnSave = findViewById(R.id.btnSaveInvoice);
        spinPro = findViewById(R.id.spProductInvoice);
        rvProduct = findViewById(R.id.rvProductInvoice);
        productDAO = new ProductDAO(this);
        productDTOS = (ArrayList<ProductDTO>) productDAO.getList();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        } else {
            spinProAdapter = new SpinProAdapter(this, productDTOS);
            spinPro.setAdapter(spinProAdapter);
        }

        listProductToBuy = new ArrayList<>();
        invoiceDAO = new InvoiceDAO(this);
        invoiceDTOS = (ArrayList<InvoiceDTO>) invoiceDAO.getList();
    }

    @SuppressLint("SimpleDateFormat")
    private void setEdtDateNow() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);
        edtDate.setText(dateString);
    }
}