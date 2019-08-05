package com.buivanphuc.foody.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.HinhAnhBinhLuanAdapter;
import com.buivanphuc.foody.adapter.ViewPagerHinhBinhLuan;
import com.buivanphuc.foody.model.BinhLuanModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FullScreenHinhAnhBinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPagerHinhBinhLuan;
    ViewPagerHinhBinhLuan adapter;
    BinhLuanModel binhLuanModel;
    int viTri = 0;
    List<Bitmap> bitmapList;
    ImageView mImageBack, mImageUser;
    TextView mTxtTitle, mTxtTieuDe, mTxtNoiDung, txtChamDiem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_hinhanhbinhluan);
        viewPagerHinhBinhLuan = findViewById(R.id.viewPagerHinhBinhLuan);
        mImageBack = findViewById(R.id.imgBack);
        mImageUser = findViewById(R.id.cicleImageUser);
        mTxtTieuDe = findViewById(R.id.txtTieudebinhluan);
        mTxtNoiDung = findViewById(R.id.txtNodungbinhluan);
        txtChamDiem = findViewById(R.id.txtChamDiemBinhLuan);
        mTxtTitle = findViewById(R.id.txtTieuDeToolbar);
        binhLuanModel = getIntent().getParcelableExtra("BINHLUANMODEL");
        viTri = getIntent().getIntExtra("ViTri", 0);
        bitmapList = new ArrayList<>();

        mImageBack.setOnClickListener(this);

        thread.start();

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            adapter = new ViewPagerHinhBinhLuan(FullScreenHinhAnhBinhLuanActivity.this, bitmapList);
            //      viewPagerHinhBinhLuan.setCurrentItem(viTri);
            viewPagerHinhBinhLuan.setAdapter(adapter);
            setHinhAnhBinhLuan(mImageUser, binhLuanModel.getThanhVienModel().getHinhanh());

            Log.d("KiemTra", binhLuanModel.getTieude());
            mTxtTitle.setText(binhLuanModel.getTieude());
            mTxtTieuDe.setText(binhLuanModel.getTieude());
            mTxtNoiDung.setText(binhLuanModel.getNoidung());
            txtChamDiem.setText(binhLuanModel.getChamdiem() + "");

        }
    };
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            for (String linkHinh : binhLuanModel.getHinhanhBinhLuanList()) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkHinh);
                long ONE_MEGABYTE = 1024 * 1024;

                storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        bitmapList.add(bitmap);
                        if (bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()) {
                            handler.post(runnable);
                        }
                    }
                });
            }
        }
    });

    private void setHinhAnhBinhLuan(final ImageView imageView, String link) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(link);
        long ONE_MEGABYTE = 1024 * 1024;

        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });

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
