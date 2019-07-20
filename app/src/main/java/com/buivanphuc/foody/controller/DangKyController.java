package com.buivanphuc.foody.controller;

import com.buivanphuc.foody.model.ThanhVienModel;

public class DangKyController {

    private ThanhVienModel thanhVienModel;
    public DangKyController(){
        thanhVienModel = new ThanhVienModel();

    }
    public void themThongTinThanhVien(ThanhVienModel thanhVienModel,String uid){
        thanhVienModel.themThongTinThanhVien(thanhVienModel,uid);
    }
}
