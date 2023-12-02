package com.tranvu1805.warehousemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tranvu1805.warehousemanager.DTO.ProductDTO;
import com.tranvu1805.warehousemanager.R;

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
        View row;
        if(view!=null){
            row = view;
        }else {
            row = View.inflate(context, R.layout.product_row_spin_layout,null   );
        }
        TextView txtName = row.findViewById(R.id.txtNameProSpin);
        TextView txtPrice = row.findViewById(R.id.txtPriceProSpin);
        ImageView imgPro = row.findViewById(R.id.imgProductSpin);
        ProductDTO productDTO = productDTOS.get(i);
        txtName.setText(productDTO.getName());
        txtPrice.setText("Giá: "+ productDTO.getPrice());
        imgPro.setImageURI(Uri.parse(productDTO.getUriImg()));
        return row;
    }
}
