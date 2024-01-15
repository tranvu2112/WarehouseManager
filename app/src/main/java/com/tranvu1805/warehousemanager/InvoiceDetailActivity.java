package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tranvu1805.warehousemanager.DAO.InvoiceDetailDAO;
import com.tranvu1805.warehousemanager.Model.InvoiceDetailDTO;
import com.tranvu1805.warehousemanager.adapter.ProductInvoiceDetailAdapter;

import java.util.ArrayList;

public class InvoiceDetailActivity extends AppCompatActivity {
    TextView txtNumber, txtDate, txtType, txtSum;
    RecyclerView rvProductBuy;
    InvoiceDetailDAO invoiceDetailDAO;
    ArrayList<InvoiceDetailDTO> invoiceDetailDTOS;
    int idInvoice = 1;
    ProductInvoiceDetailAdapter invoiceDetailAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        findViews();
        Intent intent = getIntent();
        if (intent != null) {
            idInvoice = intent.getIntExtra("idInvoice", 0);
            String num = intent.getStringExtra("number");
            String date = intent.getStringExtra("date");
            int typeInt = intent.getIntExtra("type", -1);
            String type = typeInt == 0 ? "Nhập" : "Xuất";
            invoiceDetailDTOS = invoiceDetailDAO.getListInvoice(idInvoice);
            invoiceDetailAdapter = new ProductInvoiceDetailAdapter(this, invoiceDetailDTOS);
            txtNumber.setText("Số hóa đơn: " + num);
            txtDate.setText("Ngày tạo: " + date);
            txtType.setText("Loại hóa đơn:" + type);
            txtSum.setText(getSum() + "  vnđ");
        }
        rvProductBuy.setAdapter(invoiceDetailAdapter);

    }

    private int getSum() {
        int sum = 0;
        for (InvoiceDetailDTO a : invoiceDetailDTOS
        ) {
            sum = sum + (a.getPrice() * a.getQuantity());
        }
        return sum;
    }

    private void findViews() {
        txtNumber = findViewById(R.id.txtInvoiceDetailNumber);
        txtDate = findViewById(R.id.txtInvoiceDetailDate);
        txtType = findViewById(R.id.txtInvoiceDetailType);
        rvProductBuy = findViewById(R.id.rvProductInInvoiceDetail);
        invoiceDetailDAO = new InvoiceDetailDAO(this);
        txtSum = findViewById(R.id.txtSumInvoiceDetail);
    }

}