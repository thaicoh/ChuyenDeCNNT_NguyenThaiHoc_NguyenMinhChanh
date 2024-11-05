package phdhtl.khoa63.doan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import phdhtl.khoa63.doan.model.DatPhong;

public class QuanLyDatPhongActivity extends AppCompatActivity {

    private TextView txtNgayNhanPhong;
    private Button btnNgayHomNay;
    private Button btnTatCaNgay;
    private Button btnReset;
    private Spinner spinnerLoaiPhong;
    private Spinner spinnerLoaiDP;
    private Spinner spinnerPhong;
    private SearchView edtSearch;
    private ListView lvDanhSachDatPhong;
    DBHelperDatabse dbh;
    CustomAdapter_QLDP customAdapter_qldp;
    ArrayList<DatPhong> filteredDatPhong;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_dat_phong);
        dbh = new DBHelperDatabse(this);

        Toolbar toolbar = findViewById(R.id.toolbar4);
        toolbar.setTitle("QUẢN LÝ ĐẶT PHÒNG");
        //toolbar.setBackgroundColor(Color.parseColor("#FFFF00"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtNgayNhanPhong = findViewById(R.id.txtNgayNhanPhong);
        btnNgayHomNay = findViewById(R.id.btnNgayHomNay);
        btnReset = findViewById(R.id.btnReset);
        btnTatCaNgay = findViewById(R.id.btnTatCaNgay);
        spinnerLoaiPhong = findViewById(R.id.spinnerLoaiPhong);
        spinnerLoaiDP = findViewById(R.id.spinnerLoaiDP);
        spinnerPhong = findViewById(R.id.spinnerPhong);
        edtSearch = findViewById(R.id.edtSearch);
        lvDanhSachDatPhong = findViewById(R.id.lvDanhSachDatPhong);

        txtNgayNhanPhong.setText("Tất cả");

        txtNgayNhanPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        btnTatCaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNgayNhanPhong.setText("Tất cả");
                showResult();
            }
        });

        btnNgayHomNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                //SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
                //day = simpleDateFormat3.format(calendar.getTime());
                //Log.d("simpleDateFormat3", edtNgayChon.getText().toString() + " " + edtChonGio.getText().toString() + ":00");
                txtNgayNhanPhong.setText(simpleDateFormat1.format(calendar.getTime()));
                showResult();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNgayNhanPhong.setText("Tất cả");
                spinnerPhong.setSelection(0);
                spinnerLoaiPhong.setSelection(0);
                spinnerLoaiDP.setSelection(0);
                edtSearch.setQuery("", false);
                edtSearch.clearFocus();
                showResult();

            }
        });


        setSpiner();

        // Thiết lập sự kiện chọn item cho spinnerPhong
        spinnerPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Thiết lập sự kiện chọn item cho spinnerPhong
        spinnerLoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerLoaiDP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Khi người dùng thực hiện tìm kiếm
                showResult();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Khi văn bản trong SearchView thay đổi
                showResult();
                return false;
            }
        });

        showResult();

    }


    public void showResult(){
        String dauNgay = txtNgayNhanPhong.getText().toString() + " 00:00:00";
        String cuoiNgay = txtNgayNhanPhong.getText().toString() + " 23:59:59";
        String loaiPhong = spinnerLoaiPhong.getSelectedItem().toString();
        String phong = spinnerPhong.getSelectedItem().toString();
        String loaiDP = spinnerLoaiDP.getSelectedItem().toString();
        String seach = edtSearch.getQuery().toString();

        //Toast.makeText(this, "S : " + seach, Toast.LENGTH_SHORT).show();

        String loaidatphong = "";
        switch (spinnerLoaiDP.getSelectedItem().toString()){
            case "Ngày":
                loaidatphong = "ngay";
                break;
            case "Đêm":
                loaidatphong = "dem";
                break;
            case "Giờ":
                loaidatphong = "gio";
                break;
            case "All":
                loaidatphong = "All";
                break;
        }

        ArrayList<DatPhong> arrDatPhong = new ArrayList<>();

        SQLiteDatabase db = dbh.ketNoiDocData();

        String sql = "";


        sql = "SELECT dp.*, p.*, lp.* FROM DATPHONG dp JOIN PHONG p ON dp.MAPHONG = p.MAPHONG JOIN LOAIPHONG lp ON p.MALOAIPHONG = lp.MALOAIPHONG WHERE 1=1";

        if (!txtNgayNhanPhong.getText().toString().equals("Tất cả")) {
            sql += String.format(" AND dp.ngaydatphong BETWEEN '%s' AND '%s'", dauNgay, cuoiNgay);
        }
        if (!loaiPhong.equals("All")) {
            sql += String.format(" AND lp.TENLOAIPHONG = '%s'", loaiPhong);
        }
        if (!phong.equals("All")) {
            sql += String.format(" AND dp.MAPHONG = %s", phong);
        }
        if (!loaidatphong.equals("All")) {
            sql += String.format(" AND dp.loaidatphong = '%s'", loaidatphong);
        }

        Cursor cs = db.rawQuery(sql, null);

        while (cs.moveToNext()){
            Log.d("Ma dat phong", cs.getString(0));

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
            arrDatPhong.add(datPhong);
        }

        filteredDatPhong = new ArrayList<>();

        for (DatPhong datPhong : arrDatPhong) {

            if (datPhong.getTenKhachHang().trim().toLowerCase().contains(seach.trim().toLowerCase()) ||
                    datPhong.getSoDienThoaiKhachHang().trim().toLowerCase().contains(seach.trim().toLowerCase()) ||
                    String.valueOf(datPhong.getMaDatPhong()).contains(seach.trim().toLowerCase())) {
                filteredDatPhong.add(datPhong);
            }
        }

        customAdapter_qldp = new CustomAdapter_QLDP(this, filteredDatPhong);
        lvDanhSachDatPhong.setAdapter(customAdapter_qldp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 200 && resultCode == RESULT_OK && data != null){
            showResult();
            Toast.makeText(this, "Đã xóa thành công mã đặt phòng " + data.getStringExtra("MaDatPhongDaXoa"), Toast.LENGTH_SHORT).show();
        }else if(requestCode == 200 && resultCode == 333 && data != null){
            showResult();
            Toast.makeText(this, "Update thành công ", Toast.LENGTH_SHORT).show();
        } else{
            //Toast.makeText(this, ""), Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
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
                txtNgayNhanPhong.setText(simpleDateFormat.format(calendar.getTime()));

                //SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
                //day = simpleDateFormat3.format(calendar.getTime());
                //Log.d("simpleDateFormat3 changed", simpleDateFormat3.format(calendar.getTime()));

                //Log.d("Dau ngay", getDauNgay());
                //Log.d("Cuoi ngay", getCuoiNgay());

                showResult();
            }
        },y,m,d);
        dataPickerDialog.show();
    }

    public void setSpiner() {
        SQLiteDatabase db = dbh.ketNoiDocData();
        Cursor cs = db.rawQuery("SELECT * FROM LOAIPHONG", null);

        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("All");

        while (cs.moveToNext()) {
            String loaiPhong = cs.getString(1);
            spinnerArray.add(loaiPhong);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiPhong.setAdapter(adapter);


        cs = db.rawQuery("SELECT * FROM PHONG", null);

        List<String> spinnerArray2 = new ArrayList<>();
        spinnerArray2.add("All");

        String maPhong = null;
        while (cs.moveToNext()) {
            maPhong = cs.getString(0);
            spinnerArray2.add(maPhong);
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhong.setAdapter(adapter2);

        List<String> spinnerArray3 = new ArrayList<>();
        spinnerArray3.add("All");
        spinnerArray3.add("Giờ");
        spinnerArray3.add("Đêm");
        spinnerArray3.add("Ngày");

        while (cs.moveToNext()) {
            maPhong = cs.getString(0);
            spinnerArray2.add(maPhong);
        }

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiDP.setAdapter(adapter3);

        // Tìm vị trí của maPhong trong danh sách spinnerArray2
        //int position = adapter2.getPosition(maPhongFinal);

        // Nếu vị trí được tìm thấy (khác -1), thiết lập giá trị đã chọn của spinnerFN
        /*if (position != -1) {
            spinnerFN.setSelection(position);
        }else{
            spinnerFN.setSelection(0);
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.menu_quanlydatphong, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_xuatfile){
            showDiaLog(filteredDatPhong);
        }else{
            finish();
        }
        // handle arrow click here
        return super.onOptionsItemSelected(item);
    }

    private void showDiaLog(ArrayList<DatPhong> filteredDatPhong ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xuất file");
        builder.setMessage("Xuất danh sách tìm kiếm");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Nhâp tên file");
        builder.setView(input);

        builder.setPositiveButton("Xuất file", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String fileName = input.getText().toString();

                try {
                    if (fileName.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập tên file", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    buttonCreateExcelFile(fileName.trim());

                } catch (Exception e) {

                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void buttonCreateExcelFile(String s) {
        // Tạo một Workbook mới
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        // Tạo một Sheet mới với tên là "MySheet"
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("MySheet");

        // Tạo một Row đầu tiên để làm header (tùy chọn)
        HSSFRow headerRow = hssfSheet.createRow(0);

        // Tạo các header cho các cột (tùy chọn)
        String[] headers = {"MADATPHONG", "MAPHONG", "LOAIDATPHONG", "TENKHACHHANG", "SODIENTHOAIKHACHHANG", "SOCCCD", "NGAYDATPHONG", "NGAYTRAPHONG", "SOGIODAT", "GIA", "TRANGTHAIDATPHONG"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
        }

        // Lặp qua danh sách filteredDatPhong và điền dữ liệu vào Workbook
        for (int rowIndex = 0; rowIndex < filteredDatPhong.size(); rowIndex++) {
            DatPhong datPhong = filteredDatPhong.get(rowIndex);
            HSSFRow hssfRow = hssfSheet.createRow(rowIndex + 1); // Bắt đầu từ hàng thứ 2 (vì hàng đầu tiên là header)

            // Lấy các giá trị của DatPhong và ghi vào từng cell
            hssfRow.createCell(0).setCellValue(datPhong.getMaDatPhong());
            hssfRow.createCell(1).setCellValue(datPhong.getMaPhong());
            hssfRow.createCell(2).setCellValue(datPhong.getLoaiDatPhong());
            hssfRow.createCell(3).setCellValue(datPhong.getTenKhachHang());
            hssfRow.createCell(4).setCellValue(datPhong.getSoDienThoaiKhachHang());
            hssfRow.createCell(5).setCellValue(datPhong.getSoCCCD());
            hssfRow.createCell(6).setCellValue(datPhong.getNgayDatPhong());
            hssfRow.createCell(7).setCellValue(datPhong.getNgayTraPhong());
            hssfRow.createCell(8).setCellValue(datPhong.getSoGioDat());
            hssfRow.createCell(9).setCellValue(datPhong.getGia());
            hssfRow.createCell(10).setCellValue(datPhong.getTrangThaiDatPhong());
        }

        saveWorkBook(hssfWorkbook, s);
    }


    private void saveWorkBook(HSSFWorkbook hssfWorkbook,String s){
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0); // internal storage

        File fileOutput = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            fileOutput = new File(storageVolume.getDirectory().getPath() +"/Download/ "+ s +".xls");
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            hssfWorkbook.close();
            Toast.makeText(this, "Tạo file thành công", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Tạo file thất bại", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
    }




}