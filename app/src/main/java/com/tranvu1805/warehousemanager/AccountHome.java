package com.tranvu1805.warehousemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.adapter.UserAdapter;

import java.util.ArrayList;

public class AccountHome extends AppCompatActivity {
    RecyclerView rvUser;
    UserDAO userDAO;
    ArrayList<UserDTO> userDTOS;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_home);
        findViews();
        rvUser.setAdapter(adapter);
    }

    private void findViews() {
        rvUser = findViewById(R.id.rvAccount);
        userDAO = new UserDAO(this);
        userDTOS = (ArrayList<UserDTO>) userDAO.getList();
        adapter = new UserAdapter(this,userDTOS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addAccount){
            startActivity(new Intent(this, AddAccount.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        userDTOS.clear();
        userDTOS.addAll(userDAO.getList());
        adapter.notifyDataSetChanged();
    }
}