package com.buivanphuc.foody.view;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.ChonHinhBinhLuanAdapter;
import com.buivanphuc.foody.model.ChonHinhBinhLuanModel;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhBinhLuanActivity extends AppCompatActivity {
    List<ChonHinhBinhLuanModel > listDuongDan;
    RecyclerView recyclerView;
    ChonHinhBinhLuanAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chonhinhbinhluan);
        recyclerView = findViewById(R.id.recyclerChonHinhBinhLuan);
        listDuongDan = new ArrayList<>();
        getAllImage();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        adapter = new ChonHinhBinhLuanAdapter(this,R.layout.custom_layout_chonhinhbinhluan,listDuongDan);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void getAllImage() {

        String[] projection = {MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String duongDan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ChonHinhBinhLuanModel chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongDan,false);

            listDuongDan.add(chonHinhBinhLuanModel);
            cursor.moveToNext();
        }
    }
}
