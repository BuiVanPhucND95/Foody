package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.ChonHinhBinhLuanModel;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ChonHinhBinhLuanAdapter extends RecyclerView.Adapter<ChonHinhBinhLuanAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<ChonHinhBinhLuanModel> listDuongDan;

    public ChonHinhBinhLuanAdapter(Context context, int layout, List<ChonHinhBinhLuanModel> listDuongDan) {
        this.context = context;
        this.layout = layout;
        this.listDuongDan = listDuongDan;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView checkBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ChonHinhBinhLuanModel chonHinhBinhLuanModel = listDuongDan.get(position);
        Uri uri = Uri.parse(chonHinhBinhLuanModel.getDuongDan());
      //  holder.imageView.setImageURI(uri);
        File file = new File(uri.toString());
        Glide
                .with(context)
                .load(file)
                .centerCrop()
                .into(holder.imageView);

        if(listDuongDan.get(position).getIsCheck())
        {
            holder.checkBox.setImageResource(R.drawable.ic_selected);
        }else {
            holder.checkBox.setImageResource(R.drawable.ic_select);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CheckBox checkBox = (CheckBox) v;
//                listDuongDan.get(position).setIsCheck(checkBox.isChecked());
                if(listDuongDan.get(position).getIsCheck()){
                    listDuongDan.get(position).setIsCheck(false);
                    holder.checkBox.setImageResource(R.drawable.ic_select);
                }else  if(!listDuongDan.get(position).getIsCheck()) {
                    listDuongDan.get(position).setIsCheck(true);
                    holder.checkBox.setImageResource(R.drawable.ic_selected);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }


}
