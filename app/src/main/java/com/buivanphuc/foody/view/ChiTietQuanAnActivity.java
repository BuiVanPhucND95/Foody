package com.buivanphuc.foody.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.BinhLuanAdapter;
import com.buivanphuc.foody.model.BinhLuanModel;
import com.buivanphuc.foody.model.QuanAnModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTxtTenQuanAn, mTxtDiaChi, mTxtThoiGianHoatDong, mTxtTrangThaiHoatDong, txtTieuDeToolbar;
    TextView mTxtTongSoHinhAnh, mTxtTongSoCheckIn, mTxtTongSoLuuLai, mTxtTongSoBinhLuan, txtTrangThaiHoatDong;
    ImageView mImageHinhQuanAn, mImageBack;
    RecyclerView recyclerBinhLuan;
    ProgressBar progressBarChiTiet;
    NestedScrollView nestedScrollViewChiTiet;

    QuanAnModel quanAnModel;
    BinhLuanAdapter binhLuanAdapter;
    List<BinhLuanModel> binhLuanModelList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);
        addControls();
        addEvent();
        quanAnModel = getIntent().getParcelableExtra("quanan");
        binhLuanModelList = new ArrayList<>();

    }

    private void addEvent() {
        mImageBack.setOnClickListener(this);
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
        txtTieuDeToolbar = findViewById(R.id.txtTieuDeToolbar);
        progressBarChiTiet = findViewById(R.id.progressBarChiTiet);
        mImageBack = findViewById(R.id.imgBack);
        recyclerBinhLuan = findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        nestedScrollViewChiTiet = findViewById(R.id.nestedScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTxtTenQuanAn.setText(quanAnModel.getTenquanan());
        mTxtDiaChi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        mTxtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());
        mTxtTongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        mTxtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        txtTieuDeToolbar.setText(quanAnModel.getTenquanan());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String gioHienTai = dateFormat.format(calendar.getTime());
        String gioMoCua = quanAnModel.getGiomocua();
        String gioDongCua = quanAnModel.getGiodongcua();

        try {
            Date dateHienTai = dateFormat.parse(gioHienTai);
            Date dateMoCua = dateFormat.parse(gioMoCua);
            Date dateDongCua = dateFormat.parse(gioDongCua);

            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                // Giờ mở cửa
                mTxtTrangThaiHoatDong.setText(getResources().getString(R.string.dang_mo_cua));
                mTxtTrangThaiHoatDong.setTextColor(Color.RED);
            } else {
                // Giở đóng cửa
                mTxtTrangThaiHoatDong.setText(getResources().getString(R.string.da_dong_cua));
                mTxtTrangThaiHoatDong.setTextColor(Color.GREEN);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));

        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                mImageHinhQuanAn.setImageBitmap(bitmap);
                progressBarChiTiet.setVisibility(View.GONE);
                mImageHinhQuanAn.setAlpha(1.0f);
            }
        });
        // Load Danh sách bình luận quán ăn
        binhLuanModelList = quanAnModel.getBinhLuanModelList();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binhLuanAdapter = new BinhLuanAdapter(this,binhLuanModelList,R.layout.custom_layout_binhluan);

        recyclerBinhLuan.setAdapter(binhLuanAdapter);
        recyclerBinhLuan.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
        }
    }
}
