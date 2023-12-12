package com.tranvu1805.warehousemanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tranvu1805.warehousemanager.DTO.CategoryDTO;
import com.tranvu1805.warehousemanager.R;

import java.util.ArrayList;

public class SpinCatAdapter extends BaseAdapter {
    Context context;
    ArrayList<CategoryDTO> categoryDTOS;

    public SpinCatAdapter(Context context, ArrayList<CategoryDTO> categoryDTOS) {
        this.context = context;
        this.categoryDTOS = categoryDTOS;
    }

    @Override
    public int getCount() {
        return categoryDTOS.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryDTOS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categoryDTOS.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row;
        if(view!=null){
            row = view;
        }else {
            row = View.inflate(context, R.layout.cat_row_spin_layout,null   );
        }
        TextView txtName = row.findViewById(R.id.txtNameCatRow);
        CategoryDTO categoryDTO = categoryDTOS.get(i);
        txtName.setText(categoryDTO.getName());
        return row;
    }
}
