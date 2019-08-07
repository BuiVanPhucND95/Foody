package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class HienThiHinhBinhLuanAdapter extends RecyclerView.Adapter<HienThiHinhBinhLuanAdapter.ViewHolder> {

    private Context context;
    private int layout;
    private List<String> list;

    public HienThiHinhBinhLuanAdapter(Context context, int layout, List<String> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imgDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            imgDelete = itemView.findViewById(R.id.imgeDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = Uri.parse(list.get(position));
        File file = new File(uri.toString());
        Glide
                .with(context)
                .load(file)
                .centerCrop()
                .into(holder.imageView);
        holder.imgDelete.setTag(position);

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
