package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tranvu1805.warehousemanager.AddAccount;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.DTO.UserDTO;
import com.tranvu1805.warehousemanager.R;
import com.tranvu1805.warehousemanager.adapter.UserAdapter;

import java.util.ArrayList;


public class UserFragment extends Fragment {
    RecyclerView rvUser;
    UserDAO userDAO;
    ArrayList<UserDTO> userDTOS;
    UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUser = view.findViewById(R.id.rvAccount);
        userDAO = new UserDAO(requireActivity());
        userDTOS = (ArrayList<UserDTO>) userDAO.getList();
        adapter = new UserAdapter(requireActivity(), userDTOS);
        rvUser.setAdapter(adapter);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.account_option_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.addAccount){
            startActivity(new Intent(requireActivity(), AddAccount.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        userDTOS.clear();
        userDTOS.addAll(userDAO.getList());
        adapter.notifyDataSetChanged();
    }

}