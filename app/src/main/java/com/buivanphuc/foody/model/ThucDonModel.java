package com.buivanphuc.foody.model;

import android.util.Log;

import com.buivanphuc.foody.controller.Interfaces.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThucDonModel {
    private String mathucdon;
    private String tenthucdon;
    private List<MonAnModel> monAnModelList;

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModelList(List<MonAnModel> monAnModelList) {
        this.monAnModelList = monAnModelList;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public void getDanhSachThucDonQuanAn(String maQuanAn, final ThucDonInterface thucDonInterface) {
        DatabaseReference nodeThucDonQuanAn = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maQuanAn);
        nodeThucDonQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                final List<ThucDonModel> thucDonModelList = new ArrayList<>();
                for (DataSnapshot valueThucDon : dataSnapshot.getChildren()) {
                    final ThucDonModel thucDonModel = new ThucDonModel();
                    DatabaseReference nodeThucDon = FirebaseDatabase.getInstance().getReference().child("thucdons").child(valueThucDon.getKey());
                    nodeThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotThucDon) {
                            ///////////////
                            String maThucDon = dataSnapshotThucDon.getKey();
                            thucDonModel.setMathucdon(maThucDon);
                            thucDonModel.setTenthucdon(dataSnapshotThucDon.getValue(String.class));
                            List<MonAnModel> monAnModelList = new ArrayList<>();
                            for (DataSnapshot valueMonAn : dataSnapshot.child(maThucDon).getChildren()) {
                                MonAnModel monAnModel = valueMonAn.getValue(MonAnModel.class);
                                monAnModel.setMamon(valueMonAn.getKey());
                                monAnModelList.add(monAnModel);

                            }
                            thucDonModel.setMonAnModelList(monAnModelList);
                            thucDonModelList.add(thucDonModel);
                            thucDonInterface.getThucDonThanhCong(thucDonModelList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
