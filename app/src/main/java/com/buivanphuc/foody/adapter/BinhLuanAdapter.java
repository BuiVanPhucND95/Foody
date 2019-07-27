package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.BinhLuanModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHolderBinhLuan> {
    private Context context;
    private List<BinhLuanModel> binhLuanModelList;
    private int layout;
    private List<Bitmap> bitmapList;

    public BinhLuanAdapter(Context context, List<BinhLuanModel> binhLuanModelList, int layout) {
        this.context = context;
        this.binhLuanModelList = binhLuanModelList;
        this.layout = layout;

        bitmapList = new ArrayList<>();
    }

    class ViewHolderBinhLuan extends RecyclerView.ViewHolder {
        ImageView cicleImageUser;
        TextView txtTieuDe, txtNoiDung, txtChamDiemBinhLuan;
        RecyclerView recyclerHinhBinhLuan;

        ViewHolderBinhLuan(@NonNull View itemView) {
            super(itemView);
            cicleImageUser = itemView.findViewById(R.id.cicleImageUser);
            txtTieuDe = itemView.findViewById(R.id.txtTieudebinhluan);
            txtNoiDung = itemView.findViewById(R.id.txtNodungbinhluan);
            txtChamDiemBinhLuan = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            recyclerHinhBinhLuan = itemView.findViewById(R.id.recyclerHinhBinhLuan);
        }
    }

    @NonNull
    @Override
    public ViewHolderBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);

        return new ViewHolderBinhLuan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderBinhLuan holder, int position) {
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);

        holder.txtTieuDe.setText(binhLuanModel.getTieude());
        holder.txtNoiDung.setText(binhLuanModel.getNoidung());
        holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(holder.cicleImageUser, binhLuanModel.getThanhVienModel().getHinhanh());

        // set hình ảnh bình luận
        for (String linkHinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkHinh);
            long ONE_MEGABYTE = 1024 * 1024;

            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                   bitmapList.add(bitmap);
                   if(bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()){
                       HinhAnhBinhLuanAdapter hinhAnhBinhLuanAdapter = new HinhAnhBinhLuanAdapter(context,R.layout.custom_layout_hinhbinhluan,bitmapList);
                       RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);

                       holder.recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                       holder.recyclerHinhBinhLuan.setAdapter(hinhAnhBinhLuanAdapter);
                   }
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        int soBinhLuan = binhLuanModelList.size();
        if (soBinhLuan > 5) {
            return 5;
        } else {
            return soBinhLuan;
        }
    }

    private void setHinhAnhBinhLuan(final ImageView imageView, String link) {
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
}
