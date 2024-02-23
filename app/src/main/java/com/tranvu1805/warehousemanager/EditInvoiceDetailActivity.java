package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DbHelper.MyDatabase;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.Model.Invoice;
import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.Model.ProductDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class EditInvoiceDetailActivity extends AppCompatActivity {
    TextInputEditText edtNumber, edtDate;
    RadioButton rdoImport, rdoExport;
    Button btnSave;
    ArrayList<ProductDTO> listProductToBuy;

    int idInvoice = 0;

    @SuppressLint("SetTextI18n")
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
            edtDate.setText(dateInvoice);
//            spinPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @SuppressLint("NotifyDataSetChanged")
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    ProductDTO selectedProduct = (ProductDTO) spinPro.getSelectedItem();
//                    if (selectedProduct != null) {
//                        boolean isProductExist = false;
//                        for (ProductDTO p : listProductToBuy) {
//                            if (p.getId() == selectedProduct.getId()) {
//                                CustomDialog.dialogSingle(EditInvoiceDetailActivity.this, "Thông báo", "Sản phẩm đã có trong hóa đơn, vui lòng chọn sản phẩm khác", "OK", (dialogInterface, i1) -> {
//                                });
//                                isProductExist = true;
//                                break;
//                            }
//                        }
//                        if (!isProductExist) {
//                            listProductToBuy.add(selectedProduct);
//                            productInvoiceAdapter = new ProductInvoiceAdapter(EditInvoiceDetailActivity.this, listProductToBuy);
//                            productInvoiceAdapter.notifyDataSetChanged();
//                            rvProduct.setVisibility(View.VISIBLE);
//                            rvProduct.setAdapter(productInvoiceAdapter);
//                        }
//                    } else {
//                        rvProduct.setVisibility(View.GONE);
//                    }
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                }
//            });
            edtDate.setOnClickListener(view -> {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    calendar.set(i, i1, i2);
                    edtDate.setText(dateFormat.format(calendar.getTime()));
                }, year, month, day);
                dialog.show();
            });
            btnSave.setOnClickListener(view -> {
                String numberInvoice = Objects.requireNonNull(edtNumber.getText()).toString();
                String date = Objects.requireNonNull(edtDate.getText()).toString();

                if (numberInvoice.isEmpty() || date.isEmpty()) {
                    CustomDialog.dialogSingle(this, "Thông báo", "Nhập đầy đủ thông tin và sản phẩm", "OK", (dialogInterface, i) -> {
                    });
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                    int idUser = sharedPreferences.getInt("idAccount", 0);
                    int type = rdoImport.isChecked() ? 0 : 1;
                    Invoice invoice = new Invoice(idInvoice, numberInvoice, idUser, type, date, "");
                    try {
                        int idNew = MyDatabase.getInstance(this).invoiceDAO().update(invoice);
                        if (idNew > 0) {
                            for (ProductDTO p : listProductToBuy) {
                                InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO(p.getId(), idInvoice, p.getQuantity(), p.getPrice());
                                MyDatabase.getInstance(this).invoiceDetailDAO().update(invoiceDetailDTO);
                            }
                            CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật hóa đơn thành công", "OK", (dialogInterface, i) -> finish());
                        }
                    } catch (Exception e) {
                        CustomDialog.dialogSingle(this, "Thông báo", "Cập nhật hóa đơn thất bại", "OK", (dialogInterface, i) -> {
                        });
                    }

                }
            });
        }
    }

    private void findViews() {
        edtNumber = findViewById(R.id.edtInvoiceDetailNumberEdit);
        edtDate = findViewById(R.id.edtInvoiceDetailDateEdit);
        rdoExport = findViewById(R.id.rdoInvoiceDetailExportEdit);
        rdoImport = findViewById(R.id.rdoInvoiceDetailImportEdit);
        btnSave = findViewById(R.id.btnSaveInvoiceDetailEdit);
//        spinPro = findViewById(R.id.spProductInvoiceDetailEdit);
//        rvProduct = findViewById(R.id.rvProductInvoiceDetailEdit);
//        productDTOS = (ArrayList<ProductDTO>) MyDatabase.getInstance(this).productDAO().getAll();
//        spinProAdapter = new SpinProAdapter(this, productDTOS);
//        spinPro.setAdapter(spinProAdapter);
        listProductToBuy = new ArrayList<>();
    }
}