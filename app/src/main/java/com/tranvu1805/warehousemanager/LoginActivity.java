package com.tranvu1805.warehousemanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUser, edtPass;
    TextView txtForget;
    Button btnLogin;
    boolean isPassVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        invisiblePassword();
        txtForget.setOnClickListener(view ->
                startActivity(new Intent(this,ForgetActivity.class)));
    }

    //ẩn hiện mật khẩu
    @SuppressLint("ClickableViewAccessibility")
    private void invisiblePassword() {
        edtPass.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                int drawableWidth = edtPass.getCompoundDrawables()[2].getBounds().width();
                if (motionEvent.getRawX() >= (edtPass.getRight() - drawableWidth)) {
                    isPassVisible = !isPassVisible;
                    if (isPassVisible) {
                        edtPass.setTransformationMethod(SingleLineTransformationMethod.getInstance());
                        edtPass.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                ContextCompat.getDrawable(this, R.drawable.invisible), null);
                    } else {
                        edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        edtPass.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                ContextCompat.getDrawable(this, R.drawable.visible), null);
                    }
                    return true;
                }
            }
            return false;
        });
    }

    private void findViews() {
        edtUser = findViewById(R.id.edtUserLogin);
        edtPass = findViewById(R.id.edtPassLogin);
        txtForget = findViewById(R.id.txtForgetLogin);
        btnLogin = findViewById(R.id.btnLogin);
    }
}