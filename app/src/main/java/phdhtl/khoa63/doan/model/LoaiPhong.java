package phdhtl.khoa63.doan.model;

public class LoaiPhong {
    private int maLoaiPhong;
    private String tenLoaiPhong;
    private String moTa;
    private float giaPhongDem;
    private float giaPhongNgay;
    private float giaGioDauTien;
    private float giaGioTiepTheo;
    private String anhLoaiPhong;

    // Constructor
    public LoaiPhong(int maLoaiPhong, String tenLoaiPhong, String moTa, float giaPhongDem, float giaPhongNgay, float giaGioDauTien, float giaGioTiepTheo, String anhLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.moTa = moTa;
        this.giaPhongDem = giaPhongDem;
        this.giaPhongNgay = giaPhongNgay;
        this.giaGioDauTien = giaGioDauTien;
        this.giaGioTiepTheo = giaGioTiepTheo;
        this.anhLoaiPhong = anhLoaiPhong;
    }

    // Getters and Setters
    public int getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(int maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getGiaPhongDem() {
        return giaPhongDem;
    }

    public void setGiaPhongDem(float giaPhongDem) {
        this.giaPhongDem = giaPhongDem;
    }

    public float getGiaPhongNgay() {
        return giaPhongNgay;
    }

    public void setGiaPhongNgay(float giaPhongNgay) {
        this.giaPhongNgay = giaPhongNgay;
    }

    public float getGiaGioDauTien() {
        return giaGioDauTien;
    }

    public void setGiaGioDauTien(float giaGioDauTien) {
        this.giaGioDauTien = giaGioDauTien;
    }

    public float getGiaGioTiepTheo() {
        return giaGioTiepTheo;
    }

    public void setGiaGioTiepTheo(float giaGioTiepTheo) {
        this.giaGioTiepTheo = giaGioTiepTheo;
    }

    public String getAnhLoaiPhong() {
        return anhLoaiPhong;
    }

    public void setAnhLoaiPhong(String anhLoaiPhong) {
        this.anhLoaiPhong = anhLoaiPhong;
    }
}
