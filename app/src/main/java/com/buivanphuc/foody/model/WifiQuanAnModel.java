package com.buivanphuc.foody.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.buivanphuc.foody.controller.Interfaces.IWifiQuanAn;
import com.buivanphuc.foody.view.PopupCapNhatWifiActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WifiQuanAnModel {

    private String ten;
    private String matkhau;
    private String ngaydang;

    public WifiQuanAnModel() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    private DatabaseReference nodeWifiQuanAn;

    public void layDanhSachWifiQuanAn(String maQuanAn, final IWifiQuanAn iWifiQuanAn) {
        //wifiquanans
        nodeWifiQuanAn = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maQuanAn);

        nodeWifiQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot valueWifi : dataSnapshot.getChildren()) {
                    WifiQuanAnModel wifiQuanAnModel = valueWifi.getValue(WifiQuanAnModel.class);
                    iWifiQuanAn.hienThiDanhSachWifi(wifiQuanAnModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addWifiQuanAn(WifiQuanAnModel wifiQuanAnModel, final Context context,String maQuanAn) {
        DatabaseReference nodeWifiQuanQan = FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maQuanAn);
        nodeWifiQuanQan.push().setValue(wifiQuanAnModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_LONG).show();
            }
        });
    }
}
