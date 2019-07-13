package com.buivanphuc.foody.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTxtDangKy,mTxtQuenMK;
    Button mBtnDangNhap;
    EditText mEdtEmail, mEdtPass;
    String sEmail, sPassWord;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        mTxtDangKy = findViewById(R.id.txtDangKy);
        mBtnDangNhap = findViewById(R.id.btnDangNhap);
        mEdtEmail = findViewById(R.id.edtEmailDangNhap);
        mEdtPass = findViewById(R.id.edtPassWordDangNhap);
        mTxtQuenMK = findViewById(R.id.txtQuenMatKhau);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mTxtDangKy.setOnClickListener(this);
        mBtnDangNhap.setOnClickListener(this);
        mTxtQuenMK.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtDangKy:
                Intent iDangKy = new Intent(this, DangKyActivity.class);
                startActivity(iDangKy);
                break;
            case R.id.btnDangNhap:
                dangNhap();
                break;
            case R.id.txtQuenMatKhau:
                startActivity(new Intent(DangNhapActivity.this,KhoiPhucEmailActivity.class));
                break;
        }
    }

    private void dangNhap() {
        sEmail = mEdtEmail.getText().toString().trim();
        sPassWord = mEdtPass.getText().toString().trim();
        if (sEmail.isEmpty()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.chua_nhap_email), Toast.LENGTH_LONG).show();
        } else if (sPassWord.isEmpty()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.chua_nhap_pass), Toast.LENGTH_LONG).show();
        } else {
            mFirebaseAuth.signInWithEmailAndPassword(sEmail, sPassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(DangNhapActivity.this, TrangChuActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.dang_nhap_that_bai), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }
}
