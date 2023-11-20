package com.tranvu1805.warehousemanager.Dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.tranvu1805.warehousemanager.Interface.DialogBindView;

public class CustomDialog {

    public static void dialogSingle(Context context, String title, String message, String positive,
                                    DialogInterface.OnClickListener onClickListenerPositive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(positive, onClickListenerPositive);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void dialogDouble(Context context, String title, String message,
                                    String positive, DialogInterface.OnClickListener onClickListenerPositive,
                                    String negative, DialogInterface.OnClickListener onClickListenerNegative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(positive, onClickListenerPositive)
                .setNegativeButton(negative,onClickListenerNegative);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static AlertDialog builderSetView(Context context, int idLayout, DialogBindView findView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(idLayout,null);
        if(findView!=null){
            findView.onBindView(view);
        }
        builder.setView(view);
        return builder.create();
    }
}
