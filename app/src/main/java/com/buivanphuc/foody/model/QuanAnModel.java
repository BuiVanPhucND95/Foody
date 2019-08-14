package com.buivanphuc.foody.model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
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

public class QuanAnModel implements Parcelable {
    private boolean giaohang;
    private String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    private long luotthich,giatoida,giatoithieu;
    private List<String> tienich, hinhanhquanan;
    private List<BinhLuanModel> binhLuanModelList;
    private List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    private List<Bitmap> bitmapList;
    private List<ThucDonModel> thucDonModelList;

    private DatabaseReference nodeRoot;

    public QuanAnModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public List<ThucDonModel> getThucDonModelList() {
        return thucDonModelList;
    }

    public void setThucDonModelList(List<ThucDonModel> thucDonModelList) {
        this.thucDonModelList = thucDonModelList;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
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

    private DataSnapshot dataRoot;

    // Xử lý code
    public void getDanhSachQuanAn(final IODauInterface ioDauInterface, final Location viTriHienTai, final int itemTiepTheo, final int itemDaCo) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachQuanAn(dataSnapshot, ioDauInterface, viTriHienTai, itemTiepTheo, itemDaCo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        if (dataRoot != null) {
            LayDanhSachQuanAn(dataRoot, ioDauInterface, viTriHienTai, itemTiepTheo, itemDaCo);

        } else {
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    private void LayDanhSachQuanAn(DataSnapshot dataSnapshot, IODauInterface ioDauInterface, Location viTriHienTai, int itemTiepTheo, int itemDaCo) {
        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");
        int i = 0;

        // Lấy danh sách quán ăn
        for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()) {
            if (i == itemTiepTheo) {
                break;
            }
            if (i < itemDaCo) {
                i++;
                continue;
            }
            i++;
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
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();

            // Lấy danh sách bình luận của quán ăn
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

            // Lấy chi nhánh quán ăn
            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModels = new ArrayList<>();
            DataSnapshot snapshotChiNhanhQuanAn = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
            for (DataSnapshot valueChiNhanhQuanAn : snapshotChiNhanhQuanAn.getChildren()) {
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);

                Location viTriQuanAn = new Location("");
                viTriQuanAn.setLatitude(chiNhanhQuanAnModel.getLatitude());
                viTriQuanAn.setLongitude(chiNhanhQuanAnModel.getLongitude());

                double khoangCach = viTriHienTai.distanceTo(viTriQuanAn) / 1000;
                chiNhanhQuanAnModel.setKhoangcach(khoangCach);
                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }
            // Kết thúc lấy chi nhánh quán ăn

            quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);

            ioDauInterface.getDanhSachQuanAnModel(quanAnModel);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        dest.writeLong(luotthich);
        dest.writeLong(giatoida);
        dest.writeLong(giatoithieu);

        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        dest.writeTypedList(chiNhanhQuanAnModelList);
        dest.writeTypedList(binhLuanModelList);

      //  dest.writeTypedList(bitmapList);
    }
    protected QuanAnModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        luotthich = in.readLong();
        giatoida = in.readLong();
        giatoithieu = in.readLong();

        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
       // bitmapList = in.createTypedArrayList(Bitmap.CREATOR);

        chiNhanhQuanAnModelList = new ArrayList<>();
        in.readTypedList(chiNhanhQuanAnModelList,ChiNhanhQuanAnModel.CREATOR);

        binhLuanModelList = new ArrayList<>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };
}
