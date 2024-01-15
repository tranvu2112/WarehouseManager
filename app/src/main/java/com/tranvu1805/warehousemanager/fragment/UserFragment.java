package com.tranvu1805.warehousemanager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tranvu1805.warehousemanager.AddAccount;
import com.tranvu1805.warehousemanager.DAO.UserDAO;
import com.tranvu1805.warehousemanager.Model.UserDTO;
import com.tranvu1805.warehousemanager.adapter.UserAdapter;
import com.tranvu1805.warehousemanager.databinding.FragmentUserBinding;

import java.util.ArrayList;


public class UserFragment extends Fragment {
    UserDAO userDAO;
    ArrayList<UserDTO> userDTOS;
    UserAdapter adapter;
    FragmentUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDAO = new UserDAO(requireActivity());
        userDTOS = (ArrayList<UserDTO>) userDAO.getList();
        adapter = new UserAdapter(requireActivity(), userDTOS);
        binding.rvAccount.setAdapter(adapter);
        binding.btnAddUser.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AddAccount.class)));
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