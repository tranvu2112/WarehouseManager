package com.tranvu1805.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

public class AddInvoice extends AppCompatActivity {
    TextInputEditText edtNumber,edtDate;
    RadioButton rdoImport, rdoExport;
    Button btnSave;
    Spinner spinPro;
    RecyclerView rvProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);
        findViews();
        rdoImport.setChecked(!rdoExport.isChecked());
    }

    private void findViews() {
        edtNumber = findViewById(R.id.edtInvoiceNumberAdd);
        edtDate = findViewById(R.id.edtInvoiceDateAdd);
        rdoExport = findViewById(R.id.rdoExport);
        rdoImport = findViewById(R.id.rdoImport);
        btnSave = findViewById(R.id.btnSaveInvoice);
        spinPro = findViewById(R.id.spProductInvoice);
        rvProduct = findViewById(R.id.rvProductInvoice);
    }
}