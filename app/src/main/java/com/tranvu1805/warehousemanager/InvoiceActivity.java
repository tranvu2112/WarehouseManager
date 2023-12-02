package com.tranvu1805.warehousemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tranvu1805.warehousemanager.DAO.InvoiceDAO;
import com.tranvu1805.warehousemanager.DTO.InvoiceDTO;
import com.tranvu1805.warehousemanager.adapter.InvoiceAdapter;

import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {
    RecyclerView rvInvoice;
    InvoiceAdapter adapter;
    InvoiceDAO invoiceDAO;
    ArrayList<InvoiceDTO> invoiceDTOS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        findViews();
    }

    private void findViews() {
        rvInvoice = findViewById(R.id.rvInvoice);
        invoiceDAO = new InvoiceDAO(this);
        invoiceDTOS = (ArrayList<InvoiceDTO>) invoiceDAO.getList();
        adapter = new InvoiceAdapter(this,invoiceDTOS);
        rvInvoice.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invoice_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addInvoice){
            startActivity(new Intent(this, AddInvoice.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        invoiceDTOS.clear();
        invoiceDTOS.addAll(invoiceDAO.getList());
        adapter.notifyDataSetChanged();
    }
}