package com.buivanphuc.foody.controller;

import com.buivanphuc.foody.model.BinhLuanModel;

import java.util.List;

public class BinhLuanController {
    private BinhLuanModel binhLuanModel;
    public BinhLuanController(){
        binhLuanModel = new BinhLuanModel();
    }
    public void themBinhLuan(String maQuanAn, BinhLuanModel model, List<String > listHinh){
        this.binhLuanModel.themBinhLuan(maQuanAn,model,listHinh);
    }

}
