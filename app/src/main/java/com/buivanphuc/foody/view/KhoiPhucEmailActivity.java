package com.buivanphuc.foody.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuth;


public class KhoiPhucEmailActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEdtEmail;
    TextView mTxtDangKyMoi;
    Button mBtnSend;
    String sEmail;
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_khoiphuc);
        mEdtEmail = findViewById(R.id.edtEmailKP);
        mBtnSend = findViewById(R.id.btnSend);
        mTxtDangKyMoi = findViewById(R.id.txtDangKyMoi);
        firebaseAuth = FirebaseAuth.getInstance();

        mTxtDangKyMoi.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                sEmail = mEdtEmail.getText().toString().trim();
                if (kiemTraEmail(sEmail)) {
                    firebaseAuth.sendPasswordResetEmail(sEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.gui_emai_thanh_cong), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.email_k_hop_le), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txtDangKyMoi:
                startActivity(new Intent(KhoiPhucEmailActivity.this, DangKyActivity.class));
                break;
        }
    }

    private boolean kiemTraEmail(String s) {
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }
}
