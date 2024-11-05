package phdhtl.khoa63.doan;

import static androidx.core.view.WindowCompat.getInsetsController;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import phdhtl.khoa63.doan.model.DatPhong;
import phdhtl.khoa63.doan.model.Phong;


public class MainActivity extends AppCompatActivity {

    TextView edtNgayChon, edtChonGio;
    DBHelperDatabse dbh;
    Cursor cs;
    List<Phong> phongList;
    List<DatPhong> datphongList;
    RoomAdapter adapter;
    RecyclerView rvDeluxe, rvVip, rvStand;
    Button btnHienTai;
    public  int nam, thang, ngay,gio,phut;
    String day;

    @Override
    protected void onResume() {
        super.onResume();

        showDataListView(rvDeluxe,"1");
        showDataListView(rvVip,"2");
        showDataListView(rvStand,"3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowInsetsControllerCompat windowInsetsController =
                getInsetsController(getWindow(), getWindow().getDecorView());

        ViewCompat.setOnApplyWindowInsetsListener(
                getWindow().getDecorView(),
                (view, windowInsets) -> {
                    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
                    return ViewCompat.onApplyWindowInsets(view, windowInsets);
                });

        rvDeluxe = findViewById(R.id.rvDeluxe);
        rvVip = findViewById(R.id.rvVip);
        rvStand = findViewById(R.id.rvStand);
        edtNgayChon = findViewById(R.id.edtNgayChon);
        edtChonGio = findViewById(R.id.edtChonGio);
        btnHienTai = findViewById(R.id.btnHienTai);

        dbh = new DBHelperDatabse(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("QUẢN LÝ PHÒNG");
        //toolbar.setBackgroundColor(Color.parseColor("#FFFF00"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        /*Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
        edtNgayChon.setText(simpleDateFormat1.format(calendar.getTime()));
        edtChonGio.setText(simpleDateFormat2.format(calendar.getTime()));


        showDataListView(rvDeluxe,"1");
        showDataListView(rvVip,"2");
        showDataListView(rvStand,"3");*/

        SetNgayGioHienTai();

        edtNgayChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();

            }
        });

        edtChonGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonGio();

            }
        });
        btnHienTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetNgayGioHienTai();
            }
        });
    }

    public void SetNgayGioHienTai(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
        edtNgayChon.setText(simpleDateFormat1.format(calendar.getTime()));
        edtChonGio.setText(simpleDateFormat2.format(calendar.getTime()));



        showDataListView(rvDeluxe,"1");
        showDataListView(rvVip,"2");
        showDataListView(rvStand,"3");
    }

    public  void ChonGio(){
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(0,0,0,hourOfDay, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        edtChonGio.setText(simpleDateFormat.format(calendar.getTime()));
                        gio = hourOfDay;
                        phut = minute;

                        showDataListView(rvDeluxe,"1");
                        showDataListView(rvVip,"2");
                        showDataListView(rvStand,"3");
                    }
                },h,m,true);
        timePickerDialog.show();
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
                edtNgayChon.setText(simpleDateFormat.format(calendar.getTime()));

                ngay = dayOfMonth;
                thang = month;
                nam = year;

                SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
                day = simpleDateFormat3.format(calendar.getTime());
                Log.d("simpleDateFormat3 changed", simpleDateFormat3.format(calendar.getTime()));

                showDataListView(rvDeluxe,"1");
                showDataListView(rvVip,"2");
                showDataListView(rvStand,"3");
            }
        },y,m,d);

        dataPickerDialog.show();
    }

    public void showDataListView(RecyclerView v,String s){

        String ngayChon = edtNgayChon.getText().toString() + " " + edtChonGio.getText().toString() + ":00";
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        v.setLayoutManager(gridLayoutManager);

        SQLiteDatabase db = dbh.ketNoiDocData();

        phongList = new ArrayList<>();
        datphongList = new ArrayList<>();

        Cursor cs = db.rawQuery("SELECT * FROM PHONG WHERE maloaiphong = '" + s + "'", null);

        while(cs.moveToNext()){
            phongList.add(new Phong(Integer.parseInt(cs.getString(0)),Integer.parseInt(cs.getString(1)),cs.getString(2), cs.getString(3), Integer.parseInt(cs.getString(0))));
            Log.d("Chay",cs.getString(0));
        }

        cs = db.rawQuery("SELECT * FROM DATPHONG WHERE '"+ ngayChon +"' BETWEEN NGAYDATPHONG AND NGAYTRAPHONG", null);

        ArrayList<Integer> maPhongDaDat = new ArrayList<>();

        while(cs.moveToNext()){
            int maPhong = Integer.parseInt(cs.getString(1));
            int maDatPhong = Integer.parseInt(cs.getString(0));
            String loaiDatPhong = cs.getString(2);
            String tenKhachHang = cs.getString(3);
            String soDienThoaiKhachHang = cs.getString(4);
            String soCCCD = cs.getString(5);
            String ngayDatPhong = cs.getString(6);
            String ngayTraPhong = cs.getString(7);
            int soGioDat = Integer.parseInt(cs.getString(8));
            float gia = Float.parseFloat(cs.getString(9));
            int trangThaiDatPhong = Integer.parseInt(cs.getString(10));

            DatPhong datPhong = new DatPhong(maPhong, maDatPhong, loaiDatPhong, tenKhachHang, soDienThoaiKhachHang, soCCCD, ngayDatPhong, ngayTraPhong, soGioDat, gia, trangThaiDatPhong);
            datphongList.add(datPhong);

            Log.d("Chay cs2", cs.getString(0));
            maPhongDaDat.add(maPhong);

        }
        // Cập nhật trạng thái của các phòng trong phongList
        for (Phong phong : phongList) {
            if (maPhongDaDat.contains(phong.getMaPhong())) {
                phong.setTrangThai(1); // Chuyển trạng thái sang 1 nếu phòng đã được đặt
                Log.d("Trang thai: ", "1");
            } else {
                phong.setTrangThai(0); // Ngược lại chuyển trạng thái sang 0
                Log.d("Trang thai: ", "0");

            }
        }

        adapter = new RoomAdapter(phongList,datphongList ,this);
        v.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_quanlyphong, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == R.id.action_dat_phong) {

            /*SharedPreferences settings = getSharedPreferences("login", MODE_PRIVATE);
            settings.edit().clear().commit();
            finish();*/

            Intent in = new Intent(this, DatPhongActivity.class);
            startActivity(in);
        }

        if (item.getItemId() == R.id.action_thong_ke) {
            Intent in = new Intent(this, ThongKe.class);
            startActivity(in);
        }

        if (item.getItemId() == R.id.action_quan_ly_dat_phong) {
            Intent in = new Intent(this, QuanLyDatPhongActivity.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }


}