package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.MonAnModel;

import java.util.List;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<MonAnModel> list;

    public MonAnAdapter(Context context, int layout, List<MonAnModel> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenMonAn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
        }
    }

    @NonNull
    @Override
    public MonAnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnAdapter.ViewHolder holder, int position) {
        MonAnModel monAnModel = list.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
