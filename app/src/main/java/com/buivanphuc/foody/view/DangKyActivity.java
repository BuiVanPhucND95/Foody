package com.buivanphuc.foody.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.controller.DangKyController;
import com.buivanphuc.foody.model.ThanhVienModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edEmail, edPassword, edNhapLaiPassword;
    Button btnDangKy;
    FirebaseAuth mFirebaseAuth;
    String mEmail, mPassWord, mNhapLaiPass;
    String mThongBaoLoi = "";
    Dialog mDialog;
    DangKyController dangKyController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        addControls();
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        mDialog.show();
        mEmail = edEmail.getText().toString().trim();
        mPassWord = edPassword.getText().toString().trim();
        mNhapLaiPass = edNhapLaiPassword.getText().toString().trim();
        mThongBaoLoi = getString(R.string.banChuaNhap);

        if (mEmail.isEmpty()) {
            mThongBaoLoi += getString(R.string.email);
            Toast.makeText(this, mThongBaoLoi, Toast.LENGTH_LONG).show();

        } else if (mPassWord.isEmpty()) {
            mThongBaoLoi += getString(R.string.matkhau);
            Toast.makeText(this, mThongBaoLoi, Toast.LENGTH_LONG).show();
        } else if (!mNhapLaiPass.equals(mPassWord)) {
            Toast.makeText(this, getString(R.string.thongBaoNhapLaiMatKhau), Toast.LENGTH_LONG).show();
        } else {
            mFirebaseAuth.createUserWithEmailAndPassword(mEmail, mPassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mDialog.cancel();
                        Toast.makeText(DangKyActivity.this, getString(R.string.thongBaoDangKyThanhCong), Toast.LENGTH_LONG).show();
                        ThanhVienModel thanhVienModel = new ThanhVienModel();
                        thanhVienModel.setHinhanh("user.png");
                        thanhVienModel.setHoten(edEmail.getText().toString());

                        String uid = task.getResult().getUser().getUid();
                        dangKyController = new DangKyController();
                        dangKyController.themThongTinThanhVien(thanhVienModel,uid);

                    } else {
                        Toast.makeText(DangKyActivity.this, getString(R.string.thongBaoDangKyThatBai), Toast.LENGTH_LONG).show();
                        mDialog.cancel();
                    }
                }
            });
        }
    }

    private void addControls() {
        edEmail = findViewById(R.id.edEmailDangKy);
        edPassword = findViewById(R.id.edPassWordDangKy);
        edNhapLaiPassword = findViewById(R.id.edNhapLaiPassWord);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangKy.setOnClickListener(this);

        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_dangxuly);
        mDialog.setCanceledOnTouchOutside(false);
    }
}
