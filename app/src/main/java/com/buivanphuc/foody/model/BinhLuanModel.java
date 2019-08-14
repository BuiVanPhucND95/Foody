package com.buivanphuc.foody.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class BinhLuanModel implements Parcelable {
    private double chamdiem;
    private long luotthich;
    private String noidung, tieude, mauser;
    private ThanhVienModel thanhVienModel;
    private List<String> hinhanhBinhLuanList;
    private String mabinhluan;

    public BinhLuanModel() {
    }


    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhBinhLuanList) {
        this.hinhanhBinhLuanList = hinhanhBinhLuanList;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(noidung);
        dest.writeString(tieude);
        dest.writeString(mauser);
        dest.writeStringList(hinhanhBinhLuanList);
        dest.writeString(mabinhluan);

        dest.writeParcelable(thanhVienModel, flags);
    }

    protected BinhLuanModel(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        hinhanhBinhLuanList = in.createStringArrayList();
        mabinhluan = in.readString();

        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public void themBinhLuan(String maquanan, BinhLuanModel binhLuanModel, final List<String> listHinhAnh) {
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String maBinhLuan = nodeBinhLuan.child(maquanan).push().getKey();
        nodeBinhLuan.child(maquanan).child(maBinhLuan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (listHinhAnh.size() > 0) {
                        for (String valueHinh : listHinhAnh) {
                            Uri uri = Uri.fromFile(new File(valueHinh));
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/" + uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                }
                            });
                        }
                    }
                }


            }
        });
        if(listHinhAnh.size()>0){
            for (String valueHinh : listHinhAnh) {
                Uri uri = Uri.fromFile(new File(valueHinh));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(maBinhLuan).push().setValue(uri.getLastPathSegment());

            }
        }



    }


}
