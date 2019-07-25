package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.BinhLuanModel;
import com.buivanphuc.foody.model.ChiNhanhQuanAnModel;
import com.buivanphuc.foody.model.QuanAnModel;
import com.buivanphuc.foody.view.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerODauAdapter extends RecyclerView.Adapter<RecyclerODauAdapter.ViewHolderODau> {

    private Context context;
    private List<QuanAnModel> quanAnModelList;
    private int layout;

    public RecyclerODauAdapter(Context context, List<QuanAnModel> quanAnModelList, int layout) {
        this.context = context;
        this.layout = layout;
        this.quanAnModelList = quanAnModelList;
    }

    class ViewHolderODau extends RecyclerView.ViewHolder {
        TextView txtTenQuanAn, txtTieudebinhluan, txtTieudebinhluan2;
        TextView txtNodungbinhluan, txtNodungbinhluan2, txtTongBinhLuan, txtTongHinhBinhLuan;
        TextView txtChamDiemBinhLuan, txtChamDiemBinhLuan2, txtDiemTrungBinhQuanAn, txtDiaChiQuanAnODau;
        TextView txtKhoangCachQuanAnODau;
        Button btnDatMon;
        ImageView imageHinhQuanAnOdau;
        LinearLayout containerBinhLuan2, containerBinhLuan;
        CircleImageView cicleImageUser, cicleImageUser2;

        CardView cardViewOdau;

        ViewHolderODau(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAn = itemView.findViewById(R.id.txtTenQuanQuanOdau);
            btnDatMon = itemView.findViewById(R.id.btnDatMonOdau);
            imageHinhQuanAnOdau = itemView.findViewById(R.id.imageHinhQuanAnOdau);
            cicleImageUser = itemView.findViewById(R.id.cicleImageUser);
            cicleImageUser2 = itemView.findViewById(R.id.cicleImageUser2);
            txtTieudebinhluan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtTieudebinhluan2 = itemView.findViewById(R.id.txtTieudebinhluan2);
            txtNodungbinhluan = itemView.findViewById(R.id.txtNodungbinhluan);
            txtNodungbinhluan2 = itemView.findViewById(R.id.txtNodungbinhluan2);

            containerBinhLuan2 = itemView.findViewById(R.id.containerBinhLuan2);
            containerBinhLuan = itemView.findViewById(R.id.containerBinhLuan);
            txtChamDiemBinhLuan = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            txtChamDiemBinhLuan2 = itemView.findViewById(R.id.txtChamDiemBinhLuan2);
            txtTongBinhLuan = itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhBinhLuan = itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiemTrungBinhQuanAn = itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtDiaChiQuanAnODau = itemView.findViewById(R.id.txtDiaChiQuanAnODau);
            txtKhoangCachQuanAnODau = itemView.findViewById(R.id.txtKhoangCachQuanAnODau);
            cardViewOdau = itemView.findViewById(R.id.cardViewOdau);

        }
    }

    @NonNull
    @Override
    public ViewHolderODau onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);

        return new ViewHolderODau(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderODau holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);


        if (quanAnModel.isGiaohang()) {
            holder.btnDatMon.setVisibility(View.VISIBLE);
        }
        holder.txtTenQuanAn.setText(quanAnModel.getTenquanan());
        // Load ảnh hình quán ăn
//        if (quanAnModel.getBitmapList().size() > 0) {
////
////            holder.imageHinhQuanAnOdau.setImageBitmap(quanAnModel.getBitmapList().get(0));
////        }

        if (quanAnModel.getHinhanhquanan().size() > 0) {

            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").
                    child(quanAnModel.getHinhanhquanan().get(0));
            long ONE_MEGABYTE = 1024 * 1024;

            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    holder.imageHinhQuanAnOdau.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("KiemTra", e.toString());
                }
            });
        }

        // Kết thúc load hình ảnh quán ăn

        // Load Bình Luận quán ăn
        if (quanAnModel.getBinhLuanModelList().size() > 0) {

            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
            holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
            setHinhAnhBinhLuan(holder.cicleImageUser, binhLuanModel.getThanhVienModel().getHinhanh());

            if (quanAnModel.getBinhLuanModelList().size() >= 2) {
                BinhLuanModel binhLuanModel1 = quanAnModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel1.getTieude());
                holder.txtNodungbinhluan2.setText(binhLuanModel1.getNoidung());
                holder.txtChamDiemBinhLuan2.setText(binhLuanModel1.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.cicleImageUser2, binhLuanModel1.getThanhVienModel().getHinhanh());
            } else {
                holder.containerBinhLuan2.setVisibility(View.GONE);
            }
            holder.txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
            int tongSoHinhBinhLuan = 0;
            double tongDiem = 0;
            double diemTrungBinhQuanAn = 0;
            for (BinhLuanModel bl : quanAnModel.getBinhLuanModelList()) {
                tongSoHinhBinhLuan += bl.getHinhanhBinhLuanList().size();
                tongDiem += bl.getChamdiem();
            }
            diemTrungBinhQuanAn = tongDiem / quanAnModel.getBinhLuanModelList().size();
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f", diemTrungBinhQuanAn));

            if (tongSoHinhBinhLuan != 0) {
                holder.txtTongHinhBinhLuan.setText(tongSoHinhBinhLuan + "");
            } else {
                holder.txtTongHinhBinhLuan.setText("0");
            }


        } else {
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
            holder.txtTongHinhBinhLuan.setText("0");
        }

        // Kết thúc xử lý bình luận

        // Lấy chi nhánh quán ăn, và hiển thị km
        if (quanAnModel.getChiNhanhQuanAnModelList().size() > 0) {
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()) {
                if (chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()) {
                    chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                }
            }
            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
            holder.txtKhoangCachQuanAnODau.setText(String.format("%.2f", chiNhanhQuanAnModelTam.getKhoangcach()) + " Km");

        }

        // Xử lý sự kiện click
        holder.cardViewOdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);

                iChiTietQuanAn.putExtra("quanan", quanAnModel);
                context.startActivity(iChiTietQuanAn);

            }
        });

    }

    private void setHinhAnhBinhLuan(final CircleImageView imageView, String link) {
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

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }


}
