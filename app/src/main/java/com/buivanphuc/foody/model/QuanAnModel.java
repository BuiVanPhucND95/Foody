package com.buivanphuc.foody.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.buivanphuc.foody.controller.Interfaces.IODauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanAnModel {
    private boolean giaohang;
    private String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    private long luotthich;
    private List<String> tienich, hinhanhquanan;
    private List<BinhLuanModel> binhLuanModelList;

    private DatabaseReference nodeRoot;

    public QuanAnModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich1) {
        this.luotthich = luotthich1;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    // Xử lý code
    public void getDanhSachQuanAn(final IODauInterface ioDauInterface) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");


                // Lấy danh sách quán ăn
                for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {
                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanAn.getKey());

                    // Lấy danh sách Hình quán ăn theo mã
                    DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
                    List<String> hinhanhList = new ArrayList<>();
                    for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()) {
                        hinhanhList.add(valueHinhQuanAn.getValue(String.class));
                    }
                    quanAnModel.setHinhanhquanan(hinhanhList);
                    // Kết Thúc lấy Hình ảnh quán ăn

                    // Lấy danh sách bình luận của quán ăn
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();
                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                        if (binhLuanModel != null) {
                            binhLuanModel.setMabinhluan(valueBinhLuan.getKey());
                        }

                        ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        binhLuanModel.setThanhVienModel(thanhVienModel);

                        List<String> hinhAnhBLList = new ArrayList<>();
                        DataSnapshot snapshotNodeHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                        for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBinhLuan.getChildren()) {
                            hinhAnhBLList.add(valueHinhBinhLuan.getValue(String.class));
                        }
                        binhLuanModel.setHinhanhBinhLuanList(hinhAnhBLList);

                        binhLuanModels.add(binhLuanModel);
                    }
                    quanAnModel.setBinhLuanModelList(binhLuanModels);
                    // Kết thúc lấy bình luận quán ăn


                    ioDauInterface.getDanhSachQuanAnModel(quanAnModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);

    }

}
