package com.buivanphuc.foody.controller;

import android.widget.TextView;

import com.buivanphuc.foody.controller.Interfaces.IWifiQuanAn;
import com.buivanphuc.foody.model.WifiQuanAnModel;

import java.util.ArrayList;
import java.util.List;

public class WifiQuanAnController {

    private WifiQuanAnModel wifiQuanAnModel;
    private List<WifiQuanAnModel> wifiQuanAnModelList;
    public WifiQuanAnController(){
        wifiQuanAnModel = new WifiQuanAnModel();
        wifiQuanAnModelList = new ArrayList<>();
    }
    public void hienThiDanhSachWifiQuanAn(String maQuanAn, final TextView txtTenWifi, final TextView txtMatKhau, final TextView txtNgayDang){

        IWifiQuanAn wifiQuanAn = new IWifiQuanAn() {
            @Override
            public void hienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                txtTenWifi.setText(wifiQuanAnModel.getTen());
                txtMatKhau.setText(wifiQuanAnModel.getMatkhau());
                txtNgayDang.setText(wifiQuanAnModel.getNgaydang());
            }
        };
        wifiQuanAnModel.layDanhSachWifiQuanAn(maQuanAn,wifiQuanAn);
    }
}
