package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.ThucDonModel;

import java.util.List;

public class ThucDonAdapter extends RecyclerView.Adapter<ThucDonAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<ThucDonModel> list;

    public ThucDonAdapter(Context context, int layout, List<ThucDonModel> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenThucDon;
        RecyclerView recyclerMonAn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenThucDon = itemView.findViewById(R.id.txtTenThucDon);
            recyclerMonAn = itemView.findViewById(R.id.recyclerMonAn);

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
        ThucDonModel thucDonModel = list.get(position);
        holder.txtTenThucDon.setText(thucDonModel.getTenthucdon());
        holder.recyclerMonAn.setLayoutManager(new LinearLayoutManager(context));
        MonAnAdapter monAnAdapter = new MonAnAdapter(context,R.layout.custom_layout_monan,thucDonModel.getMonAnModelList());
        holder.recyclerMonAn.setAdapter(monAnAdapter);
        monAnAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
