package com.buivanphuc.foody.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.HienThiHinhBinhLuanAdapter;
import com.buivanphuc.foody.controller.BinhLuanController;
import com.buivanphuc.foody.model.BinhLuanModel;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTxtTenQuanAn, mTxtDiaChi,mTxtDang;
    ImageView mImageBack;
    ImageButton mBtnHinhAnh;
    EditText mEdtTieuDe,mEdtNoiDung;
    String sTenQuanAn = "";
    String sDiaChi = "";
    String maQuanAn;
    RecyclerView recyclerChonHinhBinhLuan;
    HienThiHinhBinhLuanAdapter adapter;
    public static final int REQUEST_CHONHINH =100;
    BinhLuanModel binhLuanModel;
    BinhLuanController controller;
    SharedPreferences sharedPreferences;
    List<String > listHinhDuocChon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        addControls();
        listHinhDuocChon = new ArrayList<>();
    }

    private void addControls() {
        sTenQuanAn = getIntent().getStringExtra("TenQuanAn");
        sDiaChi = getIntent().getStringExtra("DiaChi");
        maQuanAn = getIntent().getStringExtra("MaQuanAn");

        Log.d("KiemTra",maQuanAn);

        mTxtDiaChi = findViewById(R.id.txtDiaChiQuanAn);
        mTxtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        mImageBack  = findViewById(R.id.imgBack);
        mTxtDang = findViewById(R.id.txtDangBinhLuan);
        mBtnHinhAnh = findViewById(R.id.btnChonHinh);
        recyclerChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);
        mEdtTieuDe = findViewById(R.id.edTieuDeBinhLuan);
        mEdtNoiDung = findViewById(R.id.edNoiDungBinhLuan);

        mTxtTenQuanAn.setText(sTenQuanAn);
        mTxtDiaChi.setText(sDiaChi);

        mImageBack.setOnClickListener(this);
        mBtnHinhAnh.setOnClickListener(this);
        mTxtDang.setOnClickListener(this);
        controller = new BinhLuanController();

        sharedPreferences = getSharedPreferences("LuuDangNhap",MODE_PRIVATE);
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
            case R.id.txtDangBinhLuan:
                binhLuanModel = new BinhLuanModel();
                String tieuDe = mEdtTieuDe.getText().toString().trim();
                String noiDung = mEdtNoiDung.getText().toString().trim();
                if(!noiDung.isEmpty()){
                    String maUser = sharedPreferences.getString("MaUser","");
                    binhLuanModel.setTieude(tieuDe);
                    binhLuanModel.setNoidung(noiDung);
                    binhLuanModel.setMauser(maUser);

                    controller.themBinhLuan(maQuanAn,binhLuanModel,listHinhDuocChon);


                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listHinhDuocChon = new ArrayList<>();
        if(requestCode == REQUEST_CHONHINH)
        {
            if(resultCode == RESULT_OK && data!=null){
                listHinhDuocChon = data.getStringArrayListExtra("ListHinhDuocChon");
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
                adapter = new HienThiHinhBinhLuanAdapter(this,R.layout.custom_layout_hienthibinhluanduocchon,listHinhDuocChon);

                recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
                recyclerChonHinhBinhLuan.setAdapter(adapter);

            }
        }else {
            Toast.makeText(getApplicationContext(),"Nội dung không được để trống",Toast.LENGTH_LONG).show();
        }

    }
}
