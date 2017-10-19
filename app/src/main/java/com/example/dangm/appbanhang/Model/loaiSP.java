package com.example.dangm.appbanhang.Model;

/**
 * Created by dangm on 9/14/2017.
 */

public class loaiSP {
    private int idLoai;
    private String tenLoai;
    private String hinhloai;

    public loaiSP(int idLoai, String tenLoai, String hinhloai) {
        this.idLoai = idLoai;
        this.tenLoai = tenLoai;
        this.hinhloai = hinhloai;
    }

    public int getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(int idLoai) {
        this.idLoai = idLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getHinhloai() {
        return hinhloai;
    }

    public void setHinhloai(String hinhloai) {
        this.hinhloai = hinhloai;
    }
}
