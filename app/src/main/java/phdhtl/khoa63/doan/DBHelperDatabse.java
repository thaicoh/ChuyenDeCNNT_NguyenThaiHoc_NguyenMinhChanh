package phdhtl.khoa63.doan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelperDatabse extends SQLiteOpenHelper {
    public static String DATABASE = "ManagerRoom.db";
    private static final int DATABASE_VERSION = 12;

    public DBHelperDatabse(@Nullable Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Bảng phòng
        String sqltext = "create table PHONG(MAPHONG integer primary key,MALOAIPHONG integer,MOTA text,ANHPHONG text,TRANGTHAI integer);\n"
                + "INSERT INTO PHONG VALUES(101, 1, 'Phòng tầng 1, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(201, 1, 'Phòng tầng 2, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(202, 2, 'Phòng tầng 2, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(501, 1, 'Phòng tầng 5, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(502, 2, 'Phòng tầng 5, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(301, 2, 'Phòng tầng 3, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(103, 3, 'Phòng tầng 1, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(203, 3, 'Phòng tầng 2, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(302, 3, 'Phòng tầng 3, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(401, 1, 'Phòng tầng 4, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p101', 1);\n"
                + "INSERT INTO PHONG VALUES(102, 2, 'Phòng tầng 1, có tivi, bồn tắm, máy lạnh, cửa sổ', 'p102', 1);";

        for (String sql : sqltext.split("\n")) {
            db.execSQL(sql);
        }

        // Bảng Loại Phòng
        String sqltext2 = "create table LOAIPHONG(MALOAIPHONG integer primary key,TENLOAIPHONG text,MOTA text,GIAPHONGNGAY float,GIAPHONGDEM float,GIAGIODAUTIEN float, GIAGIOTIEPTHEO float, ANHLOAIPHONG text);\n"
                + "INSERT INTO LOAIPHONG VALUES(1, 'Deluxe', 'Phòng thường không có ban công và bồn tắm',500,300,150,50, 'p101');\n"
                + "INSERT INTO LOAIPHONG VALUES(2, 'Deluxe with balcony', 'Phòng thường nhưng có ban công không có bồn tắm',520,320,170,50, 'p101');\n"
                + "INSERT INTO LOAIPHONG VALUES(3, 'VIP', 'Phòng Vip có ban công và bồn tắm',550,350,190,50, 'p102');";
        for (String sql : sqltext2.split("\n")) {
            db.execSQL(sql);
        }

        // Bảng Đặt Phòng
        String sqltext3 = "create table DATPHONG(" +
                "MADATPHONG integer primary key autoincrement," +
                "MAPHONG integer," +
                "LOAIDATPHONG text," +
                "TENKHACHHANG text," +
                "SODIENTHOAIKHACHHANG text," +
                "SOCCCD text," +
                "NGAYDATPHONG datetime," +
                "NGAYTRAPHONG datetime," +
                "SOGIODAT integer, " +
                "GIA float, " +
                "TRANGTHAIDATPHONG integer);";

        String insertData = "INSERT INTO DATPHONG (MAPHONG, LOAIDATPHONG, TENKHACHHANG, SODIENTHOAIKHACHHANG, SOCCCD, NGAYDATPHONG, NGAYTRAPHONG, SOGIODAT, GIA, TRANGTHAIDATPHONG) VALUES" +
                "(101, 'dem', 'Nguyen Thai Hoc', '090900900', '12312312312323', '2024-06-15 21:00:00', '2024-06-16 12:00:00', 16, 221, 2)," +
                "(102, 'ngay', 'Nguyen Thai Huy', '0721234212', '2312312312323', '2024-06-17 14:00:00', '2024-06-18 12:00:00', 22, 122, 1)," +
                "(302, 'gio', 'Nguyen Thai Tho', '0721234212', '2312312312323', '2024-06-17 14:00:00', '2024-06-17 16:00:00', 2, 122, 2)," +
                "(102, 'gio', 'Nguyen Thai Tho', '0721234212', '2312312312323', '2024-06-17 12:00:00', '2024-06-17 13:00:00', 2, 122, 1);";

        db.execSQL(sqltext3);
        db.execSQL(insertData);


        //String sqlLogin = "CREATE TABLE login(username text PRIMARY KEY, password text, fullname text, gener text, int yob);";
        //db.execSQL(sqlLogin);
        //String sqlInsert = "INSERT INTO login VALUES('hoc', '1');";
        //db.execSQL(sqlInsert);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS PHONG");
        db.execSQL("DROP TABLE IF EXISTS LOAIPHONG");
        db.execSQL("DROP TABLE IF EXISTS DATPHONG");
        //db.execSQL("DROP TABLE IF EXISTS login");

        onCreate(db);

    }

    Cursor initRecordFirstDB(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cs = db.rawQuery("SELECT * FROM PHONG", null);
            cs.moveToFirst();
            return cs;
        } catch (Exception e) {
        }
        return null;
    }



    SQLiteDatabase ketNoiDocData(){
        return getReadableDatabase();
    }

    SQLiteDatabase ketNoiWriteData(){
        return getWritableDatabase();
    }


    // Hàm lấy tổng số lượng phòng
    public int getTongSoPhong() {
        SQLiteDatabase db = ketNoiDocData();
        Cursor cs = db.rawQuery("SELECT COUNT(*) FROM PHONG", null);
        if (cs.moveToFirst()) {
            int totalRooms = cs.getInt(0);
            cs.close();
            return totalRooms;
        }
        cs.close();
        return 0;
    }

    // Hàm lấy tổng số lượng loại phòng
    public int getTongLoaiPhong() {
        SQLiteDatabase db = ketNoiDocData();

        Cursor cs = db.rawQuery("SELECT COUNT(*) FROM LOAIPHONG", null);
        if (cs.moveToFirst()) {
            int totalRoomTypes = cs.getInt(0);
            cs.close();
            return totalRoomTypes;
        }
        cs.close();
        return 0;
    }

    // Hàm lấy tên các loại phòng
    public ArrayList<String> getTenLoaiPhong() {
        SQLiteDatabase db = ketNoiDocData();

        ArrayList<String> roomTypeNames = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT TENLOAIPHONG FROM LOAIPHONG", null);
        while (cs.moveToNext()) {
            roomTypeNames.add(cs.getString(0));
        }
        cs.close();
        return roomTypeNames;
    }

    // Hàm lấy tổng số lượng loại đặt phòng
    public int getTongLoaiDatPhong() {
        SQLiteDatabase db = ketNoiDocData();
        Cursor cs = db.rawQuery("SELECT COUNT(DISTINCT LOAIDATPHONG) FROM DATPHONG", null);
        if (cs.moveToFirst()) {
            int totalBookingTypes = cs.getInt(0);
            cs.close();
            return totalBookingTypes;
        }
        cs.close();
        return 0;
    }

    // Hàm lấy tên các loại đặt phòng
    public String getTenLoaiDatPhong() {
        SQLiteDatabase db = ketNoiDocData();

        String s = "(";
        Cursor cs = db.rawQuery("SELECT DISTINCT LOAIDATPHONG FROM DATPHONG", null);
        while (cs.moveToNext()) {
            s = s + cs.getString(0) + ((!cs.isLast())?",   ":")");
        }
        cs.close();
        return s;
    }

    public float getTongDoanhThuTheoLoaiDatPhong_homnay(String loaidatphong) {
        SQLiteDatabase db = ketNoiDocData();
        float totalRevenue = 0;
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Truy vấn để tính tổng doanh thu của loaidatphong trong ngày hôm nay
        Cursor cs = db.rawQuery("SELECT SUM(GIA) FROM DATPHONG " +
                        "WHERE LOAIDATPHONG = ? AND DATE(NGAYDATPHONG) = ?",
                new String[]{loaidatphong, today});

        if (cs.moveToFirst()) {
            totalRevenue = cs.getFloat(0);
        }
        cs.close();
        return totalRevenue;
    }
    public int getTongDatPhongTheoLoaiDatPhong_homnay(String loaidatphong) {
        SQLiteDatabase db = ketNoiDocData();
        int totalBookings = 0;
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Truy vấn để tính tổng doanh thu của loaidatphong trong ngày hôm nay
        Cursor cs = db.rawQuery("SELECT COUNT(*) FROM DATPHONG " +
                        "WHERE LOAIDATPHONG = ? AND DATE(NGAYDATPHONG) = ?",
                new String[]{loaidatphong, today});

        if (cs.moveToFirst()) {
            totalBookings = cs.getInt(0);
        }
        cs.close();
        return totalBookings;
    }

    public float getTongDoanhThuTheoLoaiPhong_homnay(int maloaiphong) {
        SQLiteDatabase db = ketNoiDocData();
        float totalRevenue = 0;
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Truy vấn để tính tổng doanh thu của maloaiphong trong ngày hôm nay
        Cursor cs = db.rawQuery("SELECT SUM(GIA) FROM DATPHONG " +
                        "INNER JOIN PHONG ON DATPHONG.MAPHONG = PHONG.MAPHONG " +
                        "WHERE PHONG.MALOAIPHONG = ? AND DATE(DATPHONG.NGAYDATPHONG) = ?",
                new String[]{String.valueOf(maloaiphong), today});

        if (cs.moveToFirst()) {
            totalRevenue = cs.getFloat(0);
        }
        cs.close();
        return totalRevenue;
    }

    public int getTongSoDatPhongTheoLoaiPhong_homnay(int maloaiphong) {
        SQLiteDatabase db = ketNoiDocData();
        int totalBookings = 0;
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Truy vấn để tính tổng số đặt phòng của maloaiphong trong ngày hôm nay
        Cursor cs = db.rawQuery("SELECT COUNT(*) FROM DATPHONG " +
                        "INNER JOIN PHONG ON DATPHONG.MAPHONG = PHONG.MAPHONG " +
                        "WHERE PHONG.MALOAIPHONG = ? AND DATE(DATPHONG.NGAYDATPHONG) = ?",
                new String[]{String.valueOf(maloaiphong), today});

        if (cs.moveToFirst()) {
            totalBookings = cs.getInt(0);
        }
        cs.close();
        return totalBookings;
    }

    public int getTongDoanhThu() {
        SQLiteDatabase db = ketNoiDocData();
        int totalRevenue = 0;

        // Truy vấn để tính tổng doanh thu của maloaiphong trong ngày hôm nay
        Cursor cs = db.rawQuery("SELECT SUM(GIA) FROM DATPHONG " ,null);

        if (cs.moveToFirst()) {
            totalRevenue = (int) cs.getFloat(0);
        }
        cs.close();
        return totalRevenue;
    }

    public int getTongDoanhThu_LoaiPhong(int maloaiphong) {
        SQLiteDatabase db = ketNoiDocData();
        int totalRevenue = 0;
        //String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Sử dụng ? cho tham số maloaiphong và today
        Cursor cs = db.rawQuery("SELECT SUM(GIA) FROM DATPHONG " +
                        "INNER JOIN PHONG ON DATPHONG.MAPHONG = PHONG.MAPHONG " +
                        "WHERE PHONG.MALOAIPHONG = ?",
                new String[]{String.valueOf(maloaiphong)}); // Cung cấp giá trị cho ? tại đây

        if (cs.moveToFirst()) {
            totalRevenue = (int) cs.getFloat(0);
        }
        cs.close();
        return totalRevenue;
    }

    public int getTongDoanhThu_LoaiDatPhong(String loaidatphong) {
        SQLiteDatabase db = ketNoiDocData();
        int totalRevenue = 0;

        Cursor cs = db.rawQuery("SELECT SUM(GIA) FROM DATPHONG " +
                        "WHERE LOAIDATPHONG = ?",
                new String[]{loaidatphong});

        if (cs.moveToFirst()) {
            totalRevenue = (int) cs.getFloat(0);
        }
        cs.close();
        return totalRevenue;
    }
}
