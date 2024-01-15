package com.tranvu1805.warehousemanager.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tranvu1805.warehousemanager.fragment.CategoryFragment;
import com.tranvu1805.warehousemanager.fragment.ProductFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position ==0)return new ProductFragment();
        else return new CategoryFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
