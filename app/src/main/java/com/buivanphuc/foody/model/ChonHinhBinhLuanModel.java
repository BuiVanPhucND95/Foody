package com.buivanphuc.foody.model;

public class ChonHinhBinhLuanModel {
    private String duongDan;
    private boolean isCheck;

    public ChonHinhBinhLuanModel(String duongDan, boolean isCheck) {
        this.duongDan = duongDan;
        this.isCheck = isCheck;
    }

    public String getDuongDan() {
        return duongDan;
    }

    public void setDuongDan(String duongDan) {
        this.duongDan = duongDan;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
