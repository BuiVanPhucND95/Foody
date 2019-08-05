package com.buivanphuc.foody.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.BinhLuanAdapter;
import com.buivanphuc.foody.controller.WifiQuanAnController;
import com.buivanphuc.foody.model.BinhLuanModel;
import com.buivanphuc.foody.model.QuanAnModel;
import com.buivanphuc.foody.model.TienIchModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    TextView mTxtTenQuanAn, mTxtDiaChi, mTxtThoiGianHoatDong, mTxtTrangThaiHoatDong, txtTieuDeToolbar;
    TextView mTxtTongSoHinhAnh, mTxtTongSoCheckIn, mTxtTongSoLuuLai, mTxtTongSoBinhLuan, mTxtGioiHanGia;
    TextView mTxtTenWifi, mTxtMatKhauWifi, mTxtNgayDangWifi;
    ImageView mImageHinhQuanAn, mImageBack;
    RecyclerView recyclerBinhLuan;
    Button mBtnBinhLuan;
    ProgressBar progressBarChiTiet;
    NestedScrollView nestedScrollViewChiTiet;
    LinearLayout khungTienTich, khungWifi;
    View khungTinhNang;

    QuanAnModel quanAnModel;
    BinhLuanAdapter binhLuanAdapter;
    List<BinhLuanModel> binhLuanModelList;
    GoogleMap googleMap;
    MapFragment mapFragment;

    WifiQuanAnController wifiQuanAnController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);
        addControls();
        addEvent();
        quanAnModel = getIntent().getParcelableExtra("quanan");
        binhLuanModelList = new ArrayList<>();
        wifiQuanAnController = new WifiQuanAnController();
        hienThiChiTietQuanAn();
    }

    private void addEvent() {
        mImageBack.setOnClickListener(this);
        khungWifi.setOnClickListener(this);
        khungTinhNang.setOnClickListener(this);
        mBtnBinhLuan.setOnClickListener(this);

    }

    private void addControls() {
        mTxtTenQuanAn = findViewById(R.id.txtTenQuanAnChiTiet);
        mTxtDiaChi = findViewById(R.id.txtDiaChiQuanAn);
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
        mTxtGioiHanGia = findViewById(R.id.txtGioiHanGia);
        recyclerBinhLuan = findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        khungTienTich = findViewById(R.id.khungTienTich);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mTxtTenWifi = findViewById(R.id.txtTenWifi);
        mTxtMatKhauWifi = findViewById(R.id.txtMatKhauWifi);
        mTxtNgayDangWifi = findViewById(R.id.txtNgayDangWifi);
        khungWifi = findViewById(R.id.khungWifi);
        khungTinhNang = findViewById(R.id.khungTinhNang);
        mBtnBinhLuan = findViewById(R.id.btnBinhLuan);

        nestedScrollViewChiTiet = findViewById(R.id.nestedScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0, 0);

        mapFragment.getMapAsync(this);


    }

    private void hienThiChiTietQuanAn() {
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
        binhLuanAdapter = new BinhLuanAdapter(this, binhLuanModelList, R.layout.custom_layout_binhluan);

        recyclerBinhLuan.setAdapter(binhLuanAdapter);
        recyclerBinhLuan.setLayoutManager(layoutManager);

        // Xu Ly gia toi d, gia toi thieu
        if (quanAnModel.getGiatoida() != 0 && quanAnModel.getGiatoithieu() != 0) {
            NumberFormat numberFormat = new DecimalFormat("###,###");

            String giaToiDa = numberFormat.format(quanAnModel.getGiatoida());
            String giaToiThieu = numberFormat.format(quanAnModel.getGiatoithieu());
            mTxtGioiHanGia.setText(giaToiThieu + "đ - " + giaToiDa + "đ");

        } else {
            mTxtGioiHanGia.setVisibility(View.INVISIBLE);
        }
        // dowload Hình Tiện ích
        dowloadHinhTienIch();

        // xử lý wifi
        wifiQuanAnController.hienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(), mTxtTenWifi, mTxtMatKhauWifi, mTxtNgayDangWifi);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.khungWifi:
                Intent intent = new Intent(this, DanhSachWifiActivity.class);
                intent.putExtra("MaQuanAn", quanAnModel.getMaquanan());
                startActivity(intent);
                break;
            case R.id.khungTinhNang:
                Intent iGoogleMap = new Intent(getApplicationContext(), GoogleMapActivity.class);
                iGoogleMap.putExtra("latitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                iGoogleMap.putExtra("longitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                iGoogleMap.putExtra("TenQuanAn", quanAnModel.getTenquanan());
                startActivity(iGoogleMap);
                break;
            case R.id.btnBinhLuan:
                Intent iBinhLuan = new Intent(this,BinhLuanActivity.class);
                iBinhLuan.putExtra("MaQuanAn",quanAnModel.getMaquanan());
                iBinhLuan.putExtra("TenQuanAn",quanAnModel.getTenquanan());
                iBinhLuan.putExtra("DiaChi",quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
                startActivity(iBinhLuan);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latiude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
        double longtiude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();
        LatLng latLng = new LatLng(latiude, longtiude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());
        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
    }

    private void dowloadHinhTienIch() {
        for (String maTienIch : quanAnModel.getTienich()) {
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(maTienIch);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);
                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());

                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                            layoutParams.setMargins(10, 5, 10, 0);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setImageBitmap(bitmap);
                            imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            //imageTienIch.setPadding(5, 5, 5, 5);

                            khungTienTich.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
