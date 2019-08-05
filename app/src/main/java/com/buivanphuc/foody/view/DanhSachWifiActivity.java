package com.buivanphuc.foody.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.controller.DanhSachWifiController;

public class DanhSachWifiActivity extends AppCompatActivity {

    private Button btnCapNhat;
    private RecyclerView recyclerView;
    private DanhSachWifiController controller;
    private String maQuanAn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatdanhsachwifi);
        btnCapNhat = findViewById(R.id.btnCapNhatWifi);
        recyclerView = findViewById(R.id.recyclerDanhSachWifi);

        maQuanAn = getIntent().getStringExtra("MaQuanAn");



        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachWifiActivity.this,PopupCapNhatWifiActivity.class);
                intent.putExtra("MaQuanAn",maQuanAn);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        controller = new DanhSachWifiController(this);
        controller.hienThiDanhSachWifi(maQuanAn, recyclerView);
    }
}
