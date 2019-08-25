package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.DatMonModel;
import com.buivanphuc.foody.model.MonAnModel;

import java.util.ArrayList;
import java.util.List;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<MonAnModel> list;
    public static List<DatMonModel> datMonModelList = new ArrayList<>();

    MonAnAdapter(Context context, int layout, List<MonAnModel> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenMonAn, txtSoLuong;
        ImageView imgTang, imgGiam;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgGiam = itemView.findViewById(R.id.imgGiamSoLuong);
            imgTang = itemView.findViewById(R.id.imgTangSoLuong);
        }
    }

    @NonNull
    @Override
    public MonAnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MonAnAdapter.ViewHolder holder, int position) {
        final MonAnModel monAnModel = list.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());

        holder.txtSoLuong.setTag(0);
        holder.imgTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = (int) holder.txtSoLuong.getTag();
                dem++;

                holder.txtSoLuong.setText(String.valueOf(dem));
                holder.txtSoLuong.setTag(dem);
                DatMonModel datMonTag = (DatMonModel) holder.imgGiam.getTag();
                if (datMonTag != null) {
                    datMonModelList.remove(datMonTag);
                }

                DatMonModel datMonModel = new DatMonModel();
                datMonModel.setSoLuong(dem);
                datMonModel.setTenMonAn(monAnModel.getTenmon());

                holder.imgGiam.setTag(datMonModel);


                datMonModelList.add(datMonModel);
            }
        });
        holder.imgGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = (int) holder.txtSoLuong.getTag();
                if (dem != 0) {
                    dem--;
                    if (dem == 0) {
                        DatMonModel datMonModel = (DatMonModel) v.getTag();
                        datMonModelList.remove(datMonModel);
                    }
                }

                holder.txtSoLuong.setText(String.valueOf(dem));
                holder.txtSoLuong.setTag(dem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
