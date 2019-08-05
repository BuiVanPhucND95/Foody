package com.buivanphuc.foody.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.HinhAnhBinhLuanAdapter;
import com.buivanphuc.foody.model.BinhLuanModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ChiTietBinhLuanActivity extends AppCompatActivity {

    ImageView mImageUser;
    TextView mTxtTieuDe,mTxtNoiDung,mTxtSoDiem;
    RecyclerView recyclerViewHinhBinhLuan;
    List<Bitmap> bitmapList;
    BinhLuanModel binhLuanModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);
        mImageUser = findViewById(R.id.cicleImageUser);
        mTxtTieuDe = findViewById(R.id.txtTieudebinhluan);
        mTxtNoiDung = findViewById(R.id.txtNodungbinhluan);
        mTxtSoDiem = findViewById(R.id.txtChamDiemBinhLuan);
        recyclerViewHinhBinhLuan = findViewById(R.id.recyclerHinhBinhLuan);
        bitmapList = new ArrayList<>();

        binhLuanModel = getIntent().getParcelableExtra("BINHLUANMODEL");

        mTxtTieuDe.setText(binhLuanModel.getTieude());
        mTxtNoiDung.setText(binhLuanModel.getNoidung());
        mTxtSoDiem.setText(binhLuanModel.getChamdiem()+"");
        setHinhAnhBinhLuan(mImageUser,binhLuanModel.getThanhVienModel().getHinhanh());

        // set hình ảnh bình luận
        for (String linkHinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkHinh);
            long ONE_MEGABYTE = 1024 * 1024;

            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()){
                        HinhAnhBinhLuanAdapter hinhAnhBinhLuanAdapter = new HinhAnhBinhLuanAdapter(ChiTietBinhLuanActivity.this,R.layout.custom_layout_hinhbinhluan,bitmapList,binhLuanModel,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);

                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(hinhAnhBinhLuanAdapter);
                    }
                }
            });
        }


    }


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
}
