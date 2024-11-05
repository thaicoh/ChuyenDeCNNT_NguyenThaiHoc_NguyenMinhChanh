package phdhtl.khoa63.doan;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import phdhtl.khoa63.doan.model.DatPhong;
import phdhtl.khoa63.doan.model.DateTimeUtils;
import phdhtl.khoa63.doan.model.Phong;

public class ChiTietDatPhongActivity extends AppCompatActivity {

    private Toolbar toolbar5;
    private EditText edtMaDatPhong;
    private EditText edtMaPhongctdp;
    private EditText edtLoaiPhongctdp;
    private EditText edtLoaiDatPhong;
    private EditText edtTenKhachHang;
    private EditText edtSDT;
    private EditText edtCCCD;
    private TextView edtTongTien;
    private EditText edtNgayNhan;
    private EditText edtNgayTra;
    private EditText edtTrangThai;
    private Button btnLuuSua;
    private Button btnHuySua;

    DBHelperDatabse dbh;

    DatPhong datPhong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_dat_phong);

        // Ánh xạ các biến tới các thành phần trong tệp XML
        toolbar5 = findViewById(R.id.toolbar5);
        edtMaDatPhong = findViewById(R.id.edtMaDatPhong);
        edtMaPhongctdp = findViewById(R.id.edtMaPhongctdp);
        edtLoaiPhongctdp = findViewById(R.id.edtLoaiPhongctdp);
        edtLoaiDatPhong = findViewById(R.id.edtLoaiDatPhong);
        edtTenKhachHang = findViewById(R.id.edtTenKhachHang);
        edtSDT = findViewById(R.id.edtSDT);
        edtCCCD = findViewById(R.id.edtCCCD);
        edtNgayNhan = findViewById(R.id.edtNgayNhan);
        edtNgayTra = findViewById(R.id.edtNgayTra);
        edtTrangThai = findViewById(R.id.edtTrangThai);
        btnLuuSua = findViewById(R.id.btnLuuSua);
        edtTongTien = findViewById(R.id.txtTongTien);
        btnHuySua = findViewById(R.id.btnHuySua);


        disableEditTexts();

        dbh = new DBHelperDatabse(this);

        toolbar5 = findViewById(R.id.toolbar5);
        toolbar5.setTitle("CHI TIẾT ĐẶT PHÒNG");
        //toolbar.setBackgroundColor(Color.parseColor("#FFFF00"));
        setSupportActionBar(toolbar5);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnLuuSua.setVisibility(View.GONE);
        btnHuySua.setVisibility(View.GONE);

        Intent in = getIntent();
        datPhong = (DatPhong) in.getSerializableExtra("DatPhong");

        String maDatPhong = datPhong.getMaDatPhong() + "";

        Toast.makeText(getApplicationContext(),"Ma phòng : " + maDatPhong, Toast.LENGTH_LONG).show();
        setChiTietDatPhong(datPhong);

        btnLuuSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(edtCCCD.getText().toString().trim()) && !TextUtils.isEmpty(edtSDT.getText().toString().trim())
                    && !TextUtils.isEmpty(edtTenKhachHang.getText().toString().trim())) {

                    if(!edtCCCD.getText().toString().trim().equals(datPhong.getSoCCCD().trim()) || !edtSDT.getText().toString().trim().equals(datPhong.getSoDienThoaiKhachHang().trim()) || !edtTenKhachHang.getText().toString().trim().equals(datPhong.getTenKhachHang().trim())){

                            SQLiteDatabase db =  dbh.ketNoiWriteData();
                            String sql = "UPDATE datphong SET TENKHACHHANG = ?, SODIENTHOAIKHACHHANG = ?, SOCCCD = ? WHERE madatphong = ?";

                            try {
                                db.execSQL(sql, new Object[]{edtTenKhachHang.getText().toString().trim(), edtSDT.getText().toString().trim(), edtCCCD.getText().toString().trim(), edtMaDatPhong.getText().toString().trim()});
                                db.close();
                                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();

                                edtCCCD.setBackgroundColor(Color.WHITE);
                                edtSDT.setBackgroundColor(Color.WHITE);
                                edtTenKhachHang.setBackgroundColor(Color.WHITE);
                                disableEditTexts();

                                // Gửi mã code 200 về activity trước đó
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("MaDatPhongDaSua",datPhong.getMaDatPhong());
                                setResult(333, resultIntent);

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Cập nhật không thành công, Lỗi Exception", Toast.LENGTH_LONG).show();
                                edtCCCD.setBackgroundColor(Color.WHITE);
                                edtSDT.setBackgroundColor(Color.WHITE);
                                edtTenKhachHang.setBackgroundColor(Color.WHITE);
                                disableEditTexts();
                            }
                    } else{
                        Toast.makeText(getApplicationContext(), "Cập nhật không thành công do bạn chưa update dữ liệu!", Toast.LENGTH_LONG).show();
                    }




                }else{
                    Toast.makeText(ChiTietDatPhongActivity.this, "Vui lòng không để trống thông tin!", Toast.LENGTH_SHORT).show();

                }


            }
        });

        btnHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChiTietDatPhong(datPhong);

                edtCCCD.setBackgroundColor(Color.WHITE);
                edtSDT.setBackgroundColor(Color.WHITE);
                edtTenKhachHang.setBackgroundColor(Color.WHITE);
                disableEditTexts();

                btnLuuSua.setVisibility(View.GONE);
                btnHuySua.setVisibility(View.GONE);
            }
        });

    }

    public void setChiTietDatPhong(DatPhong datPhong){

        DBHelperDatabse dbh = new DBHelperDatabse(this);
        SQLiteDatabase db = dbh.ketNoiDocData();

        String sql = String.format(" SELECT lp.TENLOAIPHONG FROM PHONG p INNER JOIN LOAIPHONG lp ON p.MALOAIPHONG = lp.MALOAIPHONG  WHERE p.MAPHONG = %d", datPhong.getMaPhong());

        Cursor cs = db.rawQuery(sql, null);
        if(cs.moveToFirst()){
            edtLoaiPhongctdp.setText(cs.getString(0) + "");
        }else{
            Toast.makeText(this, "Khong tim thay loai phong", Toast.LENGTH_SHORT).show();
        }

        if(datPhong != null){
            edtMaDatPhong.setText(datPhong.getMaDatPhong() + "");
            edtMaPhongctdp.setText(datPhong.getMaPhong() + "");
            edtTenKhachHang.setText(datPhong.getTenKhachHang() + "");
            edtSDT.setText(datPhong.getSoDienThoaiKhachHang() + "");
            edtCCCD.setText(datPhong.getSoCCCD() + "");
            edtNgayNhan.setText(DateTimeUtils.convertDateTimeFormat(datPhong.getNgayDatPhong()));
            edtNgayTra.setText(DateTimeUtils.convertDateTimeFormat(datPhong.getNgayTraPhong()));
            edtTrangThai.setText(datPhong.getTrangThaiDatPhong() + "");

            edtTongTien.setText("Tổng tiền: " + datPhong.getGia() + "00 vnd");

        }

        switch (datPhong.getLoaiDatPhong()){
            case "ngay":
                edtLoaiDatPhong.setText("Ngày");
                break;
            case "dem":
                edtLoaiDatPhong.setText("Đêm");
                break;
            case "gio":
                edtLoaiDatPhong.setText("Giờ");
                break;
        }
    }

    private void showDiaLogComfirm(String madatphong){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Xác nhận");
        b.setMessage("Bạn có đồng ý xóa đặt phòng có mã "+ madatphong +" không ?");
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
                String sql="delete from datphong where madatphong ='"+ madatphong +"'";
                try {
                    SQLiteDatabase  db = dbh.ketNoiWriteData();
                    db.execSQL(sql);
                    db.close();
                    //Toast.makeText(getApplicationContext(),"Xóa Thành công", Toast.LENGTH_LONG).show();

                    // Gửi mã code 200 về activity trước đó
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("MaDatPhongDaXoa", madatphong);
                    setResult(RESULT_OK, resultIntent);
                    finish();

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Không Thành công", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Nút Cancel
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_chitietdatphong, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if(item.getItemId() == R.id.action_edit){
            enableEditTexts();
            btnLuuSua.setVisibility(View.VISIBLE);
            btnHuySua.setVisibility(View.VISIBLE);

            edtCCCD.setBackgroundColor(Color.parseColor("#B3E5FC"));
            edtSDT.setBackgroundColor(Color.parseColor("#B3E5FC"));
            edtTenKhachHang.setBackgroundColor(Color.parseColor("#B3E5FC"));


        }else if(item.getItemId() == R.id.action_delete){
            showDiaLogComfirm(edtMaDatPhong.getText().toString());

        }else{
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void disableEditTexts() {
        disableEditText(edtMaDatPhong);
        disableEditText(edtMaPhongctdp);
        disableEditText(edtLoaiPhongctdp);
        disableEditText(edtLoaiDatPhong);
        disableEditText(edtTenKhachHang);
        disableEditText(edtSDT);
        disableEditText(edtCCCD);
        disableEditText(edtNgayNhan);
        disableEditText(edtNgayTra);
        disableEditText(edtTrangThai);
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setInputType(InputType.TYPE_NULL);
    }

    private void enableEditTexts() {
        //enableEditText(edtMaDatPhong);
        //enableEditText(edtMaPhongctdp);
        //enableEditText(edtLoaiPhongctdp);
        //enableEditText(edtLoaiDatPhong);
        enableEditText(edtTenKhachHang);
        enableEditText(edtSDT);
        enableEditText(edtCCCD);
        //enableEditText(edtNgayNhan);
        //enableEditText(edtNgayTra);
        enableEditText(edtTrangThai);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
    }
}