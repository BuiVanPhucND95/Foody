package com.buivanphuc.foody.controller;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.buivanphuc.foody.R;
import com.buivanphuc.foody.adapter.DanhSachWifiAdapter;
import com.buivanphuc.foody.controller.Interfaces.IWifiQuanAn;
import com.buivanphuc.foody.model.WifiQuanAnModel;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DanhSachWifiController {
    private WifiQuanAnModel wifiQuanAnModel;
    private Context context;
    private List<WifiQuanAnModel> wifiQuanAnModelList;

    public DanhSachWifiController(Context context){
        wifiQuanAnModel = new WifiQuanAnModel();
        this.context = context;
        wifiQuanAnModelList = new ArrayList<>();
    }
    public void hienThiDanhSachWifi(String maQuanAn, final RecyclerView recyclerView){
        IWifiQuanAn iWifiQuanAn = new IWifiQuanAn() {
            @Override
            public void hienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                DanhSachWifiAdapter adapter = new DanhSachWifiAdapter(context, R.layout.layout_wifi_chitietquanan,wifiQuanAnModelList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        wifiQuanAnModel.layDanhSachWifiQuanAn(maQuanAn,iWifiQuanAn);
    }
    public void addWifi(Context context,WifiQuanAnModel wifiQuanAnModel1,String maQuanAn){
        wifiQuanAnModel.addWifiQuanAn(wifiQuanAnModel1,context,maQuanAn);
    }
}
