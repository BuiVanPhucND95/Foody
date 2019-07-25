package com.buivanphuc.foody.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.QuanAnModel;

public class ChiTietQuanAnActivity extends AppCompatActivity {
    TextView mTxtTenQuanAn,mTxtDiaChi,mTxtThoiGianHoatDong,mTxtTrangThaiHoatDong;
    TextView mTxtTongSoHinhAnh,mTxtTongSoCheckIn,mTxtTongSoLuuLai,mTxtTongSoBinhLuan;
    ImageView mImageHinhQuanAn;
    QuanAnModel quanAnModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);
        addControls();

        quanAnModel = getIntent().getParcelableExtra("quanan");

        Log.d("KiemTra", quanAnModel.getTenquanan());
    }

    private void addControls() {
        mTxtTenQuanAn = findViewById(R.id.txtTenQuanAnChiTiet);
        mTxtDiaChi = findViewById(R.id.txtDiaChiQuanAnChiTiet);
        mTxtThoiGianHoatDong = findViewById(R.id.txtThoiGianHoatDong);
        mTxtTrangThaiHoatDong = findViewById(R.id.txtTrangThaiHoatDong);
        mImageHinhQuanAn = findViewById(R.id.imgHinhQuanAnChiTiet);
        mTxtTongSoHinhAnh = findViewById(R.id.tongSoHinhAnh);
        mTxtTongSoCheckIn = findViewById(R.id.tongSoCheckIn);
        mTxtTongSoLuuLai = findViewById(R.id.tongSoLuuLai);
        mTxtTongSoBinhLuan = findViewById(R.id.tongSoBinhLuan);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTxtTenQuanAn.setText(quanAnModel.getTenquanan());
        mTxtDiaChi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        mTxtThoiGianHoatDong.setText(quanAnModel.getGiomocua()+"-"+quanAnModel.getGiodongcua());
        mTxtTongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size()+"");
        mTxtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");

    }
}
