package phdhtl.khoa63.doan.model;

import java.io.Serializable;

public class Phong implements Serializable {
    private int maPhong;
    private int maLoaiPhong;
    private String moTa;
    private String anhPhong;
    private int trangThai;

    // Constructor
    public Phong(int maPhong, int maLoaiPhong, String moTa, String anhPhong, int trangThai) {
        this.maPhong = maPhong;
        this.maLoaiPhong = maLoaiPhong;
        this.moTa = moTa;
        this.anhPhong = anhPhong;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public int getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(int maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnhPhong() {
        return anhPhong;
    }

    public void setAnhPhong(String anhPhong) {
        this.anhPhong = anhPhong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
