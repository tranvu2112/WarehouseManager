package com.tranvu1805.warehousemanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.tranvu1805.warehousemanager.adapter.FragmentAdapter;
import com.tranvu1805.warehousemanager.databinding.FragmentCollectionBinding;


public class FragmentCollection extends Fragment {

    FragmentCollectionBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCollectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(requireActivity());
        binding.vpProduct.setAdapter(fragmentAdapter);
        TabLayoutMediator mediator = new TabLayoutMediator(binding.tabLayoutProduct, binding.vpProduct, ((tab, position) -> {
            if (position == 0) tab.setText("Sản Phẩm");
            else tab.setText("Thể Loại");
        }));
        mediator.attach();
    }
}