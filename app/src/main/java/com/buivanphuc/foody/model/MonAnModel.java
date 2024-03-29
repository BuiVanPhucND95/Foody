package com.buivanphuc.foody.model;

public class MonAnModel {
    private String mamon;
    private long giatien;
    private String hinhanh;
    private String tenmon;
    public MonAnModel(){}
    public MonAnModel(String mamon, long giatien, String hinhanh, String tenmon) {
        this.mamon = mamon;
        this.giatien = giatien;
        this.hinhanh = hinhanh;
        this.tenmon = tenmon;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }
}
