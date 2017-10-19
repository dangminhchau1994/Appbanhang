package com.example.dangm.appbanhang.Model;

/**
 * Created by dangm on 10/16/2017.
 */

public class GioHang {

    public int id;
    public String tensp;
    public long giapsp;
    public int soluong;
    public String hinhsp;

    public GioHang(int id, String tensp, long giapsp, int soluong, String hinhsp) {
        this.id = id;
        this.tensp = tensp;
        this.giapsp = giapsp;
        this.soluong = soluong;
        this.hinhsp = hinhsp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiapsp() {
        return giapsp;
    }

    public void setGiapsp(long giapsp) {

        this.giapsp = giapsp;
    }

    public int getSoluong() {

        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }
}
