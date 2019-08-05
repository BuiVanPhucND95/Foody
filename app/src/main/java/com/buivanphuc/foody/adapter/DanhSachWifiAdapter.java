package com.buivanphuc.foody.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.model.WifiQuanAnModel;

import java.util.List;

public class DanhSachWifiAdapter extends RecyclerView.Adapter<DanhSachWifiAdapter.ViewHolder> {

    private Context context;
    private int layout;
    private List<WifiQuanAnModel> wifiQuanAnModelList;

    public DanhSachWifiAdapter(Context context, int layout, List<WifiQuanAnModel> wifiQuanAnModelList) {
        this.context = context;
        this.layout = layout;
        this.wifiQuanAnModelList = wifiQuanAnModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenWifi, txtMatKhau, txtNgayDang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenWifi = itemView.findViewById(R.id.txtTenWifi);
            txtMatKhau = itemView.findViewById(R.id.txtMatKhauWifi);
            txtNgayDang = itemView.findViewById(R.id.txtNgayDangWifi);
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
        WifiQuanAnModel wifiQuanAnModel = wifiQuanAnModelList.get(position);
        holder.txtTenWifi.setText(wifiQuanAnModel.getTen());
        holder.txtNgayDang.setText(wifiQuanAnModel.getNgaydang());
        holder.txtMatKhau.setText(wifiQuanAnModel.getMatkhau());
    }

    @Override
    public int getItemCount() {
        return wifiQuanAnModelList.size();
    }


}
