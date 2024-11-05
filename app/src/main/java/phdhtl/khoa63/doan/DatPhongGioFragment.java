package phdhtl.khoa63.doan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import phdhtl.khoa63.doan.model.DateTimeUtils;
import phdhtl.khoa63.doan.model.Phong;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatPhongGioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatPhongGioFragment extends Fragment implements OnPhongDatListener, OnTimeClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText edtTenKhachHangFN, edtSDTFN, edtCCCDFN, edtNgayNhanPhongFN, edtGioNhan;
    private TextView txtNgayNhanFN, txtGioNhanFN, txtNgayTraFN, txtGioTraFN;

    CustomAdapterTimPhongDat_PhongGio adapter;
    private Spinner spinnerFN, spinnerFN2, spinnerSoGio;
    private Button btnTimPhongFN;
    private ListView lvFN;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final  String loaiDatPhong  = "gio";

    DBHelperDatabse dbh;
    String maPhongFinal;
    Cursor cs;
    private RecyclerView recyclerView;
    private TimeAdapter timeAdapter;
    private List<String> timeList;

    public String gioNhanPhong = String.format("%02d:%02d", getGioHienTai(), getPhutHienTai());

    public DatPhongGioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatPhongNgayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatPhongGioFragment newInstance(String param1, String param2) {
        DatPhongGioFragment fragment = new DatPhongGioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dat_phong_gio, container, false);

        Log.d("Ngay gio hien tai", "Gio: " + getGioHienTai() + ", Phut: " + getPhutHienTai());

        // Tìm kiếm nút btnTimPhong trong rootView của Fragment
        btnTimPhongFN = rootView.findViewById(R.id.btnTimPhongFD);
        edtTenKhachHangFN = rootView.findViewById(R.id.edtTenKhachHangFD);
        edtSDTFN = rootView.findViewById(R.id.edtSDTFD);
        edtCCCDFN = rootView.findViewById(R.id.edtCCCDFD);
        edtNgayNhanPhongFN = rootView.findViewById(R.id.edtNgayNhanPhongFD);
        txtNgayNhanFN = rootView.findViewById(R.id.txtNgayNhanFD);
        txtGioNhanFN = rootView.findViewById(R.id.txtGioNhanFD);
        txtNgayTraFN = rootView.findViewById(R.id.txtNgayTraFD);
        txtGioTraFN = rootView.findViewById(R.id.txtGioTraFD);
        spinnerFN = rootView.findViewById(R.id.spinnerFD);
        spinnerFN2 = rootView.findViewById(R.id.spinnerFD2);
        spinnerSoGio = rootView.findViewById(R.id.spinnerSoGio);
        //edtGioNhan = rootView.findViewById(R.id.edtGioNhan);
        btnTimPhongFN = rootView.findViewById(R.id.btnTimPhongFD);
        lvFN = rootView.findViewById(R.id.lvFD);
        edtNgayNhanPhongFN.setFocusable(false);
        edtNgayNhanPhongFN.setClickable(false);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        setRecyclerView();


        SetNgayGioHienTai();

        maPhongFinal = getArguments().getString("maPhong");
        Log.d("maPhong", maPhongFinal);


        dbh = new DBHelperDatabse(requireContext());

        edtNgayNhanPhongFN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        // Bắt sự kiện click cho btnTimPhong
        btnTimPhongFN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhongTim();
                // Xử lý sự kiện click ở đây
                //Log.d("Time", getCheckInTime() +  " - " + getCheckOutTime());
                //Toast.makeText(getActivity(), "Tìm phòng " + loaiDatPhong, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "Spinner Index" + spinnerFN.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        setSpiner();

        if(DateTimeUtils.isLonHon("13:00",gioNhanPhong )){
            gioNhanPhong = "13:00";
        }

        txtGioNhanFN.setText(gioNhanPhong);
        txtGioTraFN.setText(DateTimeUtils.addHoursToTime(gioNhanPhong, spinnerSoGio.getSelectedItemPosition() + 1));

        spinnerSoGio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txtGioNhanFN.setText(gioNhanPhong);
                txtGioTraFN.setText(DateTimeUtils.addHoursToTime(gioNhanPhong, spinnerSoGio.getSelectedItemPosition() + 1));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        showPhongTim();

        return rootView;
    }
    public int getGioHienTai(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getPhutHienTai(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public void SetNgayGioHienTai(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        edtNgayNhanPhongFN.setText(simpleDateFormat1.format(calendar.getTime()));
        txtNgayNhanFN.setText(simpleDateFormat1.format(calendar.getTime()));
        txtNgayTraFN.setText(simpleDateFormat1.format(calendar.getTime()));
    }
    public void ChonNgay() {
        Calendar calendar = Calendar.getInstance();
        int d = calendar.get(Calendar.DATE);
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                if (selectedDate.before(Calendar.getInstance())) {
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                // Ngày nhận phòng
                edtNgayNhanPhongFN.setText(sdf.format(selectedDate.getTime()));
                txtNgayNhanFN.setText(sdf.format(selectedDate.getTime()));
                txtNgayTraFN.setText(sdf.format(selectedDate.getTime()));
                setRecyclerView();

            }
        }, y, m, d);

        // Thiết lập giới hạn chọn ngày từ hôm nay trở đi
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // -1000 để tránh ngày hôm nay
        datePickerDialog.show();
    }
    public String getCheckInTime(){
        return txtNgayNhanFN.getText().toString() + " " + txtGioNhanFN.getText().toString() + ":00";
    }
    public String getCheckOutTime(){
        return txtNgayTraFN.getText().toString() + " " + txtGioTraFN.getText().toString() + ":00";
    }
    public boolean isDaDienDayDu(){
        String tenKhachHang = edtTenKhachHangFN.getText().toString().trim();
        String soDienThoai = edtSDTFN.getText().toString().trim();
        String soCanCuoc = edtCCCDFN.getText().toString().trim();
        String ngayNhanPhong = edtNgayNhanPhongFN.getText().toString().trim();

        // Kiểm tra xem các EditText đã điền đầy đủ hay chưa
        if (tenKhachHang.isEmpty()) {
            edtTenKhachHangFN.setError("Vui lòng nhập tên khách hàng");
            return false;
        } else if (soDienThoai.isEmpty()) {
            edtSDTFN.setError("Vui lòng nhập số điện thoại");
            return false;
        } else if (soCanCuoc.isEmpty()) {
            edtCCCDFN.setError("Vui lòng nhập số căn cước");
            return false;
        } else if (ngayNhanPhong.isEmpty()) {
            edtNgayNhanPhongFN.setError("Vui lòng chọn ngày nhận phòng");
            return false;
        }

        // Nếu các EditText đã điền đầy đủ
        return true;
    }
    public void setSpiner() {
        SQLiteDatabase db = dbh.ketNoiDocData();
        Cursor cs = db.rawQuery("SELECT * FROM LOAIPHONG", null);

        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Tất cả loại phòng");

        while (cs.moveToNext()) {
            String loaiPhong = cs.getString(1);
            spinnerArray.add(loaiPhong);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFN2.setAdapter(adapter);


        cs = db.rawQuery("SELECT * FROM PHONG", null);

        List<String> spinnerArray2 = new ArrayList<>();
        spinnerArray2.add("Tất cả phòng");

        String maPhong = null;
        while (cs.moveToNext()) {
            maPhong = cs.getString(0);
            spinnerArray2.add(maPhong);
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFN.setAdapter(adapter2);

        // Tìm vị trí của maPhong trong danh sách spinnerArray2
        int position = adapter2.getPosition(maPhongFinal);

        // Nếu vị trí được tìm thấy (khác -1), thiết lập giá trị đã chọn của spinnerFN
        if (position != -1) {
            spinnerFN.setSelection(position);
        }else{
            spinnerFN.setSelection(0);
        }

        String[] arrSpiner3 = {"1 giờ", "2 giờ", "3 giờ", "4 giờ"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, arrSpiner3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSoGio.setAdapter(adapter3);


    }

    public void showPhongTim(){
        String selectedMaPhong = (String) spinnerFN.getSelectedItem();

        // Xây dựng câu truy vấn SQL
        String sql = "SELECT * FROM PHONG " +
                "WHERE maphong NOT IN (" +
                "    SELECT maphong FROM DATPHONG " +
                "    WHERE '"+ getCheckInTime() +"' <= NGAYTRAPHONG " +  // điều kiện ngày trả phòng sau ngày checkin
                "    AND '"+ getCheckOutTime() +"' >= NGAYDATPHONG" +  // điều kiện ngày nhận phòng trước ngày checkout
                ")";

        if( spinnerFN2.getSelectedItemPosition() != 0 && spinnerFN.getSelectedItemPosition() != 0){

            sql = "SELECT * FROM PHONG " +
                    "WHERE maloaiphong =  '" + spinnerFN2.getSelectedItemPosition() +
                    "' AND maphong =  '" + selectedMaPhong +
                    "' AND maphong NOT IN (" +
                    "    SELECT maphong FROM DATPHONG " +
                    "    WHERE '"+ getCheckInTime() +"' <= NGAYTRAPHONG " +  // điều kiện ngày trả phòng sau ngày checkin
                    "    AND '"+ getCheckOutTime() +"' >= NGAYDATPHONG" +  // điều kiện ngày nhận phòng trước ngày checkout
                    ")";
        }else if(spinnerFN2.getSelectedItemPosition() != 0 && spinnerFN.getSelectedItemPosition() == 0){
            sql = "SELECT * FROM PHONG " +
                    "WHERE maloaiphong =  '" + spinnerFN2.getSelectedItemPosition() +
                    "' AND maphong NOT IN (" +
                    "    SELECT maphong FROM DATPHONG " +
                    "    WHERE '"+ getCheckInTime() +"' <= NGAYTRAPHONG " +  // điều kiện ngày trả phòng sau ngày checkin
                    "    AND '"+ getCheckOutTime() +"' >= NGAYDATPHONG" +  // điều kiện ngày nhận phòng trước ngày checkout
                    ")";
        }else if(spinnerFN2.getSelectedItemPosition() == 0 && spinnerFN.getSelectedItemPosition() != 0){
            sql = "SELECT * FROM PHONG " +
                    " WHERE maphong =  '" + selectedMaPhong +
                    "' AND maphong NOT IN (" +
                    "    SELECT maphong FROM DATPHONG " +
                    "    WHERE '"+ getCheckInTime() +"' <= NGAYTRAPHONG " +  // điều kiện ngày trả phòng sau ngày checkin
                    "    AND '"+ getCheckOutTime() +"' >= NGAYDATPHONG" +  // điều kiện ngày nhận phòng trước ngày checkout
                    ")";
        }


        Log.d("sql ", sql);
        SQLiteDatabase db = dbh.ketNoiDocData();
        Cursor cs =  db.rawQuery(sql, null);

        ArrayList<Phong> phongList = new ArrayList<>();

        while (cs.moveToNext()) {
            phongList.add(new Phong(Integer.parseInt(cs.getString(0)),Integer.parseInt(cs.getString(1)),cs.getString(2), cs.getString(3), Integer.parseInt(cs.getString(0))));
            Log.d("Chay",cs.getString(0));

            Log.d("Ma phong", cs.getString(0));
        }

        CustomAdapterTimPhongDat_PhongGio adapter = new CustomAdapterTimPhongDat_PhongGio(requireActivity(), phongList,spinnerSoGio.getSelectedItemPosition() + 1, this);

        lvFN.setAdapter(adapter);

    }

    public void DatPhong(Phong phong,String giaPhong) {
        if(isDaDienDayDu()) {
            showDialogXacNhan(phong,phong.getMaPhong() + "", loaiDatPhong, edtTenKhachHangFN.getText().toString(), edtSDTFN.getText().toString(), edtCCCDFN.getText().toString(), getCheckInTime(), getCheckOutTime(), giaPhong, spinnerFN2.getSelectedItem().toString());
        }

        if(isDaDienDayDu()){

        }
    }

    public void showDialogXacNhan(Phong phong,String maPhong, String loaiDatPhong, String tenKhachHang, String sdt, String cccd, String checkIn, String checkOut, String gia, String loaiPhong) {
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_custom_datphong);
        dialog.show();

        // Ánh xạ các ID

        TextView txttenkhachhangDialog = dialog.findViewById(R.id.txttenkhachhangDialog);
        TextView txtsdtDialog = dialog.findViewById(R.id.txtsdtDialog);
        TextView txtcccdDialog = dialog.findViewById(R.id.txtcccdDialog);
        TextView txtloaidatphongDialog = dialog.findViewById(R.id.txtloaidatphongDialog);
        TextView txtcheckinDialog = dialog.findViewById(R.id.txtcheckinDialog);
        TextView txtcheckoutDialog = dialog.findViewById(R.id.txtcheckoutDialog);
        TextView txtmaphongDialog = dialog.findViewById(R.id.txtmaphongDialog);
        TextView txtloaiphongDialog = dialog.findViewById(R.id.txtloaiphongDialog);
        TextView txtgiatienDialog = dialog.findViewById(R.id.txtgiatienDialog);
        Button btnDatdialog = dialog.findViewById(R.id.btnDatdialog);
        Button btnhuyDialog = dialog.findViewById(R.id.btnhuyDialog);

        // Truyền các tham số vào các TextView
        txttenkhachhangDialog.setText("Họ tên: " + tenKhachHang);
        txtsdtDialog.setText("Số điện thoại: " + sdt);
        txtcccdDialog.setText("Số căn cước: " + cccd);
        txtloaidatphongDialog.setText("Loại đặt phòng: " + loaiDatPhong);
        txtcheckinDialog.setText("Nhận phòng: " + checkIn);
        txtcheckoutDialog.setText("Trả phòng: " + checkOut);
        txtmaphongDialog.setText("Phòng: " + maPhong);
        txtloaiphongDialog.setText("Loại phòng: " + loaiPhong);
        txtgiatienDialog.setText("Tổng tiền: " + gia + " vnd");

        // Thiết lập các hành động cho các button
        btnDatdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soGio = spinnerSoGio.getSelectedItemPosition() + 1;

                String insertData = "INSERT INTO DATPHONG (MAPHONG, LOAIDATPHONG, TENKHACHHANG, SODIENTHOAIKHACHHANG, SOCCCD, NGAYDATPHONG, NGAYTRAPHONG, SOGIODAT, GIA, TRANGTHAIDATPHONG) VALUES" +
                        "("+phong.getMaPhong()+", '"+loaiDatPhong+"', '"+edtTenKhachHangFN.getText().toString()+"', '"+edtSDTFN.getText().toString()+"', '"+edtCCCDFN.getText().toString()+"', '"+getCheckInTime()+"', '"+getCheckOutTime()+"',"+ soGio  +", "+gia+", 0)";

                Log.d("SQL insert", insertData);

                SQLiteDatabase db = dbh.ketNoiWriteData();

                try {
                    db.execSQL(insertData);
                    Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    showPhongTim();
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Thêm thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    showPhongTim();
                }

                dialog.dismiss();
            }
        });

        btnhuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện hành động khi nhấn nút Hủy
                dialog.dismiss();
            }
        });


        // Set độ rộng cho dialog
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setRecyclerView(){
        timeList = new ArrayList<>();

        if(DateTimeUtils.isToday(edtNgayNhanPhongFN.getText().toString())){
            // Thêm các mốc giờ vào danh sách

            for ( int hour = (getGioHienTai() < 13)?13:getGioHienTai() ; hour < 20; hour++) {
                timeList.add(String.format("%02d:00", hour));
                timeList.add(String.format("%02d:30", hour));
            }
        }else{
            // Thêm các mốc giờ vào danh sách
            for (int hour = 13; hour < 19; hour++) {
                timeList.add(String.format("%02d:00", hour));
                timeList.add(String.format("%02d:30", hour));
            }
        }

        timeAdapter = new TimeAdapter(getContext(),this, timeList, getGioHienTai(), getPhutHienTai());
        recyclerView.setAdapter(timeAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Cuộn đến vị trí gần nhất với thời gian truyền vào
        recyclerView.scrollToPosition(timeAdapter.getNearestPosition());
    }

    @Override
    public void onTimeClick(String time) {
        gioNhanPhong = time;
        txtGioNhanFN.setText(gioNhanPhong);
        txtGioTraFN.setText(DateTimeUtils.addHoursToTime(gioNhanPhong, spinnerSoGio.getSelectedItemPosition() + 1));
    }


}