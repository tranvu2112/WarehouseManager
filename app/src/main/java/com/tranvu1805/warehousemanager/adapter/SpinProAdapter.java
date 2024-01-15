package com.tranvu1805.warehousemanager.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tranvu1805.warehousemanager.Model.ProductDTO;
import com.tranvu1805.warehousemanager.databinding.ProductRowSpinLayoutBinding;

import java.util.ArrayList;

public class SpinProAdapter extends BaseAdapter {
    Context context;
    ArrayList<ProductDTO> productDTOS;

    public SpinProAdapter(Context context, ArrayList<ProductDTO> productDTOS) {
        this.context = context;
        this.productDTOS = productDTOS;
    }

    @Override
    public int getCount() {
        return productDTOS.size();
    }

    @Override
    public Object getItem(int i) {
        return productDTOS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productDTOS.get(i).getId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ProductRowSpinLayoutBinding binding;
        if (view != null) {
            binding = ProductRowSpinLayoutBinding.bind(view);
        } else {
            binding = ProductRowSpinLayoutBinding.inflate(LayoutInflater.from(context),viewGroup, false);
        }

        ProductDTO productDTO = productDTOS.get(i);
        binding.txtNameProSpin.setText(productDTO.getName());
        binding.txtPriceProSpin.setText("Giá: " + productDTO.getPrice());
        if(productDTO.getImgBlob()!=null){
            Bitmap imgBitmap = BitmapFactory.decodeByteArray(productDTO.getImgBlob(),0,productDTO.getImgBlob().length);
            binding.imgProductSpin.setImageBitmap(imgBitmap);
        }
        return binding.getRoot();
    }
}
