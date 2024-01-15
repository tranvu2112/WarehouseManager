package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.Dialog.CustomDialog;
import com.tranvu1805.warehousemanager.fragment.FragmentCollection;
import com.tranvu1805.warehousemanager.fragment.HomeFragment;
import com.tranvu1805.warehousemanager.fragment.InvoiceFragment;
import com.tranvu1805.warehousemanager.fragment.NoticeFragment;
import com.tranvu1805.warehousemanager.fragment.ProductFragment;
import com.tranvu1805.warehousemanager.fragment.StatiscalFragment;
import com.tranvu1805.warehousemanager.fragment.UserFragment;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bnvHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bnvHome = findViewById(R.id.bnvHome);
        bnvHome.setOnItemSelectedListener(item -> {
            Fragment fragment;
            if(item.getItemId()==R.id.home){
                fragment = new HomeFragment();
            } else if (item.getItemId()==R.id.user) {
                if(checkRole()){
                    fragment = new UserFragment();
                }
                else {
                   fragment = new NoticeFragment();
                }
            }else if (item.getItemId()==R.id.product){
                fragment = new FragmentCollection();
            }else if (item.getItemId()==R.id.bill){
                fragment = new InvoiceFragment();
            }
            else {
                fragment = new StatiscalFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragHome,fragment).commit();
            return true;
        });
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                CustomDialog.dialogDouble(HomeActivity.this,"Thông báo","Bạn muốn đăng xuất?",
                        "Có",((dialog, which) -> {
                            finish();
                        }),"Không",(dialog, which) -> {

                        });
            }
        };
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);
    }
    private boolean checkRole() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", 1);
        return role == 1;
    }

}