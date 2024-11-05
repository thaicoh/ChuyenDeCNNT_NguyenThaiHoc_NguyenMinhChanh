package phdhtl.khoa63.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThongKe extends AppCompatActivity {

    DBHelperDatabse dbh;

    private TextView txtTongSoPhong, txtTongLoaiPhong, txtTongSoLoaiDatPhong, txtTongDatPhongGio,
            txtTongDatPhongNgay, txtTongDatPhongDem, txtTongDatPhongD, txtTongDatPhongDwB,
            txtTongDatPhongV, txtTongDoanhThu, txtTongDoanhThuDem, txtTongDoanhThuNgay,
            txtTongDoanhThuGio, txtTongDoanhThuD, txtTongDoanhThuDwB, txtTongDoanhThuV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);

        Toolbar toolbar = findViewById(R.id.toolbar6);
        toolbar.setTitle("Thống kê của khách sạn");
        //toolbar.setBackgroundColor(Color.parseColor("#FFFF00"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dbh = new DBHelperDatabse(this);

        // Ánh xạ các TextView
        txtTongSoPhong = findViewById(R.id.txtTongSoPhong);
        txtTongLoaiPhong = findViewById(R.id.txtTongLoaiPhong);
        txtTongSoLoaiDatPhong = findViewById(R.id.txtTongSoLoaiDatPhong);
        txtTongDatPhongGio = findViewById(R.id.txtTongDatPhongGio);
        txtTongDatPhongNgay = findViewById(R.id.txtTongDatPhongNgay);
        txtTongDatPhongDem = findViewById(R.id.txtTongDatPhongDem);
        txtTongDatPhongD = findViewById(R.id.txtTongDatPhongD);
        txtTongDatPhongDwB = findViewById(R.id.txtTongDatPhongDwB);
        txtTongDatPhongV = findViewById(R.id.txtTongDatPhongV);
        txtTongDoanhThu = findViewById(R.id.txtTongDoanhThu);
        txtTongDoanhThuDem = findViewById(R.id.txtTongDoanhThuDem);
        txtTongDoanhThuNgay = findViewById(R.id.txtTongDoanhThuNgay);
        txtTongDoanhThuGio = findViewById(R.id.txtTongDoanhThuGio);
        txtTongDoanhThuD = findViewById(R.id.txtTongDoanhThuD);
        txtTongDoanhThuDwB = findViewById(R.id.txtTongDoanhThuDwB);
        txtTongDoanhThuV = findViewById(R.id.txtTongDoanhThuV);

        txtTongSoPhong.setText(dbh.getTongSoPhong() + "");

        txtTongLoaiPhong.setText(dbh.getTongLoaiPhong() + "");
        txtTongSoLoaiDatPhong.setText(dbh.getTongLoaiDatPhong() + ",     Gồm " + dbh.getTenLoaiDatPhong());

        txtTongDatPhongGio.setText(dbh.getTongDatPhongTheoLoaiDatPhong_homnay("gio") + ",   Doanh thu:  " + dbh.getTongDoanhThuTheoLoaiDatPhong_homnay("gio"));
        txtTongDatPhongNgay.setText(dbh.getTongDatPhongTheoLoaiDatPhong_homnay("ngay") + ",   Doanh thu:  " + dbh.getTongDoanhThuTheoLoaiDatPhong_homnay("ngay"));
        txtTongDatPhongDem.setText(dbh.getTongDatPhongTheoLoaiDatPhong_homnay("dem") + ",   Doanh thu:  " + dbh.getTongDoanhThuTheoLoaiDatPhong_homnay("dem"));
        txtTongDatPhongD.setText(dbh.getTongSoDatPhongTheoLoaiPhong_homnay(1) + ",   Doanh thu:  " + dbh.getTongDoanhThuTheoLoaiPhong_homnay(1));
        txtTongDatPhongDwB.setText(dbh.getTongSoDatPhongTheoLoaiPhong_homnay(2) + ",   Doanh thu:  " + dbh.getTongDoanhThuTheoLoaiPhong_homnay(2));
        txtTongDatPhongV.setText(dbh.getTongSoDatPhongTheoLoaiPhong_homnay(3) + ",   Doanh thu:  " + dbh.getTongDoanhThuTheoLoaiPhong_homnay(3));

        txtTongDoanhThu.setText(dbh.getTongDoanhThu() + "000 vnd");
        txtTongDoanhThuDem.setText(dbh.getTongDoanhThu_LoaiDatPhong("dem") + "000 vnd");
        txtTongDoanhThuNgay.setText(dbh.getTongDoanhThu_LoaiDatPhong("ngay") + "000 vnd");
        txtTongDoanhThuGio.setText(dbh.getTongDoanhThu_LoaiDatPhong("gio") + "000 vnd");

        txtTongDoanhThuD.setText(dbh.getTongDoanhThu_LoaiPhong(1) + "000 vnd");
        txtTongDoanhThuDwB.setText(dbh.getTongDoanhThu_LoaiPhong(2) + "000 vnd");
        txtTongDoanhThuV.setText(dbh.getTongDoanhThu_LoaiPhong(3) + "000 vnd");
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_chitietphong, menu);
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

        finish();
        return super.onOptionsItemSelected(item);
    }
}