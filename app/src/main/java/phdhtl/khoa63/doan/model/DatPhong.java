package phdhtl.khoa63.doan.model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


public class DatPhong implements Serializable {
    private int maPhong;
    private int maDatPhong;
    private String loaiDatPhong;
    private String tenKhachHang;
    private String soDienThoaiKhachHang;
    private String soCCCD;
    private String ngayDatPhong;
    private String ngayTraPhong;
    private int soGioDat;
    private float gia;
    private int trangThaiDatPhong;

    // Constructor
    public DatPhong(int maPhong, int maDatPhong,String loaiDatPhong, String tenKhachHang, String soDienThoaiKhachHang, String soCCCD, String ngayDatPhong, String ngayTraPhong, int soGioDat, float gia, int trangThaiDatPhong) {
        this.maPhong = maPhong;
        this.loaiDatPhong = loaiDatPhong;
        this.maDatPhong = maDatPhong;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoaiKhachHang = soDienThoaiKhachHang;
        this.soCCCD = soCCCD;
        this.ngayDatPhong = ngayDatPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.soGioDat = soGioDat;
        this.gia = gia;
        this.trangThaiDatPhong = trangThaiDatPhong;
    }

    public String getLoaiDatPhong() {
        return loaiDatPhong;
    }

    public void setLoaiDatPhong(String loaiDatPhong) {
        this.loaiDatPhong = loaiDatPhong;
    }

    // Getters and Setters
    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public int getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(int maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoaiKhachHang() {
        return soDienThoaiKhachHang;
    }

    public void setSoDienThoaiKhachHang(String soDienThoaiKhachHang) {
        this.soDienThoaiKhachHang = soDienThoaiKhachHang;
    }

    public String getSoCCCD() {
        return soCCCD;
    }

    public void setSoCCCD(String soCCCD) {
        this.soCCCD = soCCCD;
    }

    public String getNgayDatPhong() {
        return ngayDatPhong;
    }

    public void setNgayDatPhong(String ngayDatPhong) {
        this.ngayDatPhong = ngayDatPhong;
    }

    public String getNgayTraPhong() {
        return ngayTraPhong;
    }

    public void setNgayTraPhong(String ngayTraPhong) {
        this.ngayTraPhong = ngayTraPhong;
    }

    public int getSoGioDat() {
        return soGioDat;
    }

    public void setSoGioDat(int soGioDat) {
        this.soGioDat = soGioDat;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public int getTrangThaiDatPhong() {
        return trangThaiDatPhong;
    }

    public void setTrangThaiDatPhong(int trangThaiDatPhong) {
        this.trangThaiDatPhong = trangThaiDatPhong;
    }

    @Override
    public String toString() {
        return "" + maDatPhong + "," + maPhong + "," + loaiDatPhong+"," +tenKhachHang+","+soDienThoaiKhachHang+","+soCCCD+","+ngayDatPhong+","+ngayTraPhong+","+soGioDat+","+gia+","+trangThaiDatPhong;
    }
}
