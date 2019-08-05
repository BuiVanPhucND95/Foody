package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.BinhLuanModel;
import com.buivanphuc.foody.view.ChiTietBinhLuanActivity;
import com.buivanphuc.foody.view.FullScreenHinhAnhBinhLuanActivity;

import java.util.List;

public class HinhAnhBinhLuanAdapter extends RecyclerView.Adapter<HinhAnhBinhLuanAdapter.ViewHolder> {

    private Context context;
    private int layout;
    private List<Bitmap> listHinhAnh;
    private BinhLuanModel binhLuanModel;
    private boolean isChiTietBinhLuan;

    public HinhAnhBinhLuanAdapter(Context context, int layout, List<Bitmap> listHinhAnh, BinhLuanModel binhLuanModel, boolean isChiTietBinhLuan) {
        this.context = context;
        this.layout = layout;
        this.listHinhAnh = listHinhAnh;
        this.binhLuanModel = binhLuanModel;
        this.isChiTietBinhLuan = isChiTietBinhLuan;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout khungSoHinhBinhLuan;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhBinhLuan = itemView.findViewById(R.id.imageBinhLuan);
            txtSoHinhBinhLuan = itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khungSoHinhBinhLuan = (FrameLayout) itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imgHinhBinhLuan.setImageBitmap(listHinhAnh.get(position));
        if (!isChiTietBinhLuan) {
            if (position == 3) {
                holder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                int soHinhConLai = listHinhAnh.size() - 4;
                if (soHinhConLai > 0) {
                    holder.txtSoHinhBinhLuan.setText("+" + soHinhConLai + "");
                }
                holder.imgHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChiTietBinhLuanActivity.class);
                        intent.putExtra("BINHLUANMODEL", binhLuanModel);
                        context.startActivity(intent);
                    }
                });
            }
        } else {
            holder.imgHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FullScreenHinhAnhBinhLuanActivity.class);
                    intent.putExtra("BINHLUANMODEL", binhLuanModel);
                    intent.putExtra("ViTri", position);
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (!isChiTietBinhLuan) {
            return 4;
        } else {
            return listHinhAnh.size();
        }
    }


}
