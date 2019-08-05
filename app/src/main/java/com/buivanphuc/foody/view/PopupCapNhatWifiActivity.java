package com.buivanphuc.foody.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.controller.DanhSachWifiController;
import com.buivanphuc.foody.model.WifiQuanAnModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopupCapNhatWifiActivity extends AppCompatActivity {
    EditText edtTenWifi, edtMatKhau;
    Button btnDongY;
    DanhSachWifiController controller;
    String maQuanAn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_catnhatwifi);
        edtTenWifi = findViewById(R.id.edTenWifi);
        edtMatKhau = findViewById(R.id.edNhatKhauWifi);
        setTitle("Cập nhật wifi");
        btnDongY = findViewById(R.id.btnDongYCatNhatWifi);
        controller = new DanhSachWifiController(getApplicationContext());
        maQuanAn = getIntent().getStringExtra("MaQuanAn");

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenWifi = edtTenWifi.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngayDang = simpleDateFormat.format(calendar.getTime());

                if (!tenWifi.isEmpty() || !matKhau.isEmpty()) {


                    WifiQuanAnModel wifiQuanAnModel = new WifiQuanAnModel();
                    wifiQuanAnModel.setTen(tenWifi);
                    wifiQuanAnModel.setMatkhau(matKhau);
                    wifiQuanAnModel.setNgaydang(ngayDang);
                    controller.addWifi(getApplicationContext(),wifiQuanAnModel,maQuanAn);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Thông tin k được để trống", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
