package com.example.dangm.appbanhang.Model;

import java.io.Serializable;

/**
 * Created by dangm on 9/27/2017.
 */

public class SanPham implements Serializable {
    private int idSP;
    private int idLoaiSP;
    private String tenSP;
    private int giaSP;
    private String mota;
    private String hinhSP;

    public SanPham(int idSP, int idLoaiSP, String tenSP, int giaSP, String mota, String hinhSP) {
        this.idSP = idSP;
        this.idLoaiSP = idLoaiSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.mota = mota;
        this.hinhSP = hinhSP;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public int getIdLoaiSP() {
        return idLoaiSP;
    }

    public void setIdLoaiSP(int idLoaiSP) {
        this.idLoaiSP = idLoaiSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }
}
