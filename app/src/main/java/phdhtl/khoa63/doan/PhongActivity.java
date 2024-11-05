package phdhtl.khoa63.doan;

import static androidx.core.view.WindowCompat.*;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import phdhtl.khoa63.doan.model.DatPhong;
import phdhtl.khoa63.doan.model.LoaiPhong;
import phdhtl.khoa63.doan.model.Phong;

public class PhongActivity extends AppCompatActivity {

    EditText edtMaPhongCt, edtLoaiPhong, edtGiaNgay, edtGiaDem, edtGiaGio, edtGiaGioThem,edtmMoTa;
    TextView txtChonNgay;
    Button btnDatPhong, btnSuaPhong;
    ListView lvDatPhong;
    ImageView imageView;
    SearchView txtTimKiem;
    DBHelperDatabse dbh;
    Cursor cs;
    ArrayList<LoaiPhong> loaiPhongList;
    ArrayList<DatPhong> datPhongList;

    CustomAdapterDatPhong customAdapterDatPhong;

    private int maphong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phong);

        WindowInsetsControllerCompat windowInsetsController =
                getInsetsController(getWindow(), getWindow().getDecorView());


        ViewCompat.setOnApplyWindowInsetsListener(
                getWindow().getDecorView(),
                (view, windowInsets) -> {

                    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
                    return ViewCompat.onApplyWindowInsets(view, windowInsets);
                });

        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Chi tiết phòng");
        //toolbar.setBackgroundColor(Color.parseColor("#FFFF00"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dbh = new DBHelperDatabse(this);

        // Ánh xạ các biến với các ID trong layout
        edtMaPhongCt = findViewById(R.id.edtMaPhong);
        edtLoaiPhong = findViewById(R.id.edtLoaiPhong);
        edtGiaNgay = findViewById(R.id.edtGiaNgay);
        edtGiaDem = findViewById(R.id.edtGiaDem);
        edtGiaGio = findViewById(R.id.edtGiaGio);
        edtGiaGioThem = findViewById(R.id.edtGiaGioThem);
        edtmMoTa = findViewById(R.id.edtmMoTa);
        txtChonNgay = findViewById(R.id.txtChonNgay);
        btnDatPhong = findViewById(R.id.btnDatPhong);
        btnSuaPhong = findViewById(R.id.btnSuaPhong);
        lvDatPhong = findViewById(R.id.vlDatPhong);
        imageView = findViewById(R.id.imageView);
        txtTimKiem = findViewById(R.id.txtTimKiem);

        setEnableEdt();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

        //SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

        //day = simpleDateFormat3.format(calendar.getTime());

        //Log.d("simpleDateFormat3", edtNgayChon.getText().toString() + " " + edtChonGio.getText().toString() + ":00");

        txtChonNgay.setText(simpleDateFormat1.format(calendar.getTime()));


        Intent in = getIntent();
        Phong phong = (Phong) in.getSerializableExtra("Phong");
        maphong = phong.getMaPhong();
        SetThongTinPhong(phong);
        showListView();

        txtChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(PhongActivity.this,DatPhongActivity.class);
                in.putExtra("maPhong", edtMaPhongCt.getText().toString());
                startActivity(in);

            }
        });

        txtTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi người dùng thực hiện tìm kiếm
                showListView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Khi văn bản trong SearchView thay đổi
                showListView();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 200 && resultCode == RESULT_OK && data != null){
            showListView();
            Toast.makeText(this, "Đã xóa thành công mã đặt phòng " + data.getStringExtra("MaDatPhongDaXoa"), Toast.LENGTH_SHORT).show();
        }else if(requestCode == 200 && resultCode == 333 && data != null){
            showListView();
            Toast.makeText(this, "Update thành công ", Toast.LENGTH_SHORT).show();
        } else{
            //Toast.makeText(this, ""), Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showListView();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_chitietphong, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void ChonNgay(){

        Calendar calendar = Calendar.getInstance();
        int d = calendar.get(Calendar.DATE);
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);


        DatePickerDialog dataPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                txtChonNgay.setText(simpleDateFormat.format(calendar.getTime()));

                //SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
                //day = simpleDateFormat3.format(calendar.getTime());
                //Log.d("simpleDateFormat3 changed", simpleDateFormat3.format(calendar.getTime()));

                Log.d("Dau ngay", getDauNgay());
                Log.d("Cuoi ngay", getCuoiNgay());

                showListView();
            }
        },y,m,d);

        dataPickerDialog.show();
    }

    public void SetThongTinPhong(Phong phong){
        SQLiteDatabase db = dbh.ketNoiDocData();
        Cursor cs = db.rawQuery("SELECT * FROM LOAIPHONG WHERE maloaiphong = '" + phong.getMaLoaiPhong() + "'", null);
        cs.moveToFirst();
        edtMaPhongCt.setText(phong.getMaPhong() + "");
        edtLoaiPhong.setText(cs.getString(1) + "");
        edtGiaNgay.setText(cs.getString(3) + "");
        edtGiaDem.setText(cs.getString(4) + "");
        edtGiaGio.setText(cs.getString(5) + "");
        edtGiaGioThem.setText(cs.getString(6) + "");
        edtmMoTa.setText(phong.getMoTa() + "");

    }

    public String getDauNgay(){
        String dateString = txtChonNgay.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            Date date = sdf.parse(dateString);
            calendar.setTime(date);


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return sdf.format(calendar.getTime()) + " 00:00:00";
    }
    public String getCuoiNgay(){
        String dateString = txtChonNgay.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            Date date = sdf.parse(dateString);
            calendar.setTime(date);


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return sdf.format(calendar.getTime()) + " 23:59:00";
    }

    private void setEnableEdt() {
        edtMaPhongCt.setFocusable(false);
        edtMaPhongCt.setClickable(false);

        edtLoaiPhong.setFocusable(false);
        edtLoaiPhong.setClickable(false);

        edtGiaNgay.setFocusable(false);
        edtGiaNgay.setClickable(false);

        edtGiaDem.setFocusable(false);
        edtGiaDem.setClickable(false);

        edtGiaGio.setFocusable(false);
        edtGiaGio.setClickable(false);

        edtGiaGioThem.setFocusable(false);
        edtGiaGioThem.setClickable(false);

        edtmMoTa.setFocusable(false);
        edtmMoTa.setClickable(false);
    }

    public  void showListView(){
        String search = txtTimKiem.getQuery().toString();

        SQLiteDatabase db = dbh.ketNoiDocData();
        datPhongList = new ArrayList<>();

        Cursor cs = db.rawQuery("SELECT * FROM DATPHONG WHERE MAPHONG = " + maphong + " AND NGAYDATPHONG BETWEEN '"+getDauNgay()+"' AND '"+getCuoiNgay()+"'", null);
        Log.d("SQL" , "SELECT * FROM DATPHONG WHERE MAPHONG = " + maphong + " AND NGAYDATPHONG BETWEEN '"+getDauNgay()+"' AND '"+getCuoiNgay()+"'");

        if (cs != null && cs.moveToFirst()) {
            do {
                int maDatPhong = Integer.parseInt(cs.getString(0));
                int maPhong = Integer.parseInt(cs.getString(1));
                String loaiDatPhong = cs.getString(2);
                String tenKhachHang = cs.getString(3);
                String soDienThoaiKhachHang = cs.getString(4);
                String soCCCD = cs.getString(5);
                String ngayDatPhong = cs.getString(6);
                String ngayTraPhong = cs.getString(7);
                int soGioDat = Integer.parseInt(cs.getString(8));
                float gia = Float.parseFloat(cs.getString(9));
                int trangThaiDatPhong = Integer.parseInt(cs.getString(10));

                DatPhong datPhong = new DatPhong( maPhong, maDatPhong,loaiDatPhong, tenKhachHang, soDienThoaiKhachHang, soCCCD, ngayDatPhong, ngayTraPhong, soGioDat, gia, trangThaiDatPhong);
                datPhongList.add(datPhong);
            } while (cs.moveToNext());
        }

        // Đóng Cursor sau khi hoàn thành
        if (cs != null) {
            cs.close();
        }

        ArrayList<DatPhong> filteredDatPhong = new ArrayList<>();

        for (DatPhong datPhong : datPhongList) {

            if (datPhong.getTenKhachHang().trim().toLowerCase().contains(search.trim().toLowerCase()) ||
                    datPhong.getSoDienThoaiKhachHang().trim().toLowerCase().contains(search.trim().toLowerCase()) ||
                    String.valueOf(datPhong.getMaDatPhong()).contains(search.trim().toLowerCase())) {
                filteredDatPhong.add(datPhong);
            }
        }


        customAdapterDatPhong = new CustomAdapterDatPhong(this, filteredDatPhong);

        lvDatPhong.setAdapter(customAdapterDatPhong);
    }


}