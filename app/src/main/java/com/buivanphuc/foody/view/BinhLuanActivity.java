package com.buivanphuc.foody.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTxtTenQuanAn, mTxtDiaChi,mTxtDang;
    ImageView mImageBack;
    ImageButton mBtnHinhAnh;
    String sTenQuanAn = "";
    String sDiaChi = "";
    String maQuanAn;
    public static final int REQUEST_CHONHINH =100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        addControls();

    }

    private void addControls() {
        sTenQuanAn = getIntent().getStringExtra("TenQuanAn");
        sDiaChi = getIntent().getStringExtra("DiaChi");
        maQuanAn = getIntent().getStringExtra("MaQuanAn");

        mTxtDiaChi = findViewById(R.id.txtDiaChiQuanAn);
        mTxtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        mImageBack  = findViewById(R.id.imgBack);
        mTxtDang = findViewById(R.id.txtDangBinhLuan);
        mBtnHinhAnh = findViewById(R.id.btnChonHinh);

        mTxtTenQuanAn.setText(sTenQuanAn);
        mTxtDiaChi.setText(sDiaChi);

        mImageBack.setOnClickListener(this);
        mBtnHinhAnh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                finish();
                break;
            case R.id.btnChonHinh:
                Intent iChonHinhBL = new Intent(this,ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhBL,REQUEST_CHONHINH);
                break;
        }
    }
}
