package com.tranvu1805.warehousemanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Model.Invoice;
import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.adapter.ProductInvoiceAdapter;
import com.tranvu1805.warehousemanager.adapter.SpinProAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddInvoice extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 123;
    TextInputEditText edtNumber, edtDate;
    RadioButton rdoImport, rdoExport;
    Button btnSave;
    Spinner spinPro;
    ArrayList<ProductDTO> productDTOS, listProductToBuy;
    SpinProAdapter spinProAdapter;
    RecyclerView rvProduct;
    ArrayList<Invoice> invoiceDTOS;
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
            String numberInvoice = Objects.requireNonNull(edtNumber.getText()).toString();
            String date = Objects.requireNonNull(edtDate.getText()).toString();

            if (numberInvoice.isEmpty() || date.isEmpty() || listProductToBuy.isEmpty()) {
                CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin và sản phẩm", "OK", (dialogInterface, i) -> {
                });
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                int idUser = sharedPreferences.getInt("idAccount", 0);
                int type = rdoImport.isChecked() ? 0 : 1;
                Invoice invoiceDTO = new Invoice(numberInvoice, idUser, type, date, "");
                long idNew = MyDatabase.getInstance(this).invoiceDAO().insert(invoiceDTO);
                if (idNew > 0) {
                    for (ProductDTO p : listProductToBuy) {
                        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO(p.getId(), (int) idNew, p.getQuantity(), p.getPrice());
                        MyDatabase.getInstance(this).invoiceDetailDAO().insert(invoiceDetailDTO);
                    }
                    CustomDialog.dialogSingle(this, "Thông báo", "Tạo hóa đơn thành công",
                            "OK", (dialogInterface, i) -> finish());
                } else {
                    CustomDialog.dialogSingle(this, "Thông báo", "Tạo hóa đơn thất bại",
                            "OK", (dialogInterface, i) -> {
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
        productDTOS = (ArrayList<ProductDTO>) MyDatabase.getInstance(this).productDAO().getAll();
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
        invoiceDTOS = (ArrayList<Invoice>) MyDatabase.getInstance(this).invoiceDAO().getAll();
    }

    @SuppressLint("SimpleDateFormat")
    private void setEdtDateNow() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = dateFormat.format(date);
        edtDate.setText(dateString);
        edtDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year= calendar.get(Calendar.YEAR);
            int month= calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            DatePickerDialog dialog =new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
                calendar.set(i,i1,i2);
                edtDate.setText(dateFormat.format(calendar.getTime()));
            },year,month,day);
            dialog.show();
        });
    }
}