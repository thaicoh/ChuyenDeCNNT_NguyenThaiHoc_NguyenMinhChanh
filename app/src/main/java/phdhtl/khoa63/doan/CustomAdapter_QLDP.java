package phdhtl.khoa63.doan;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import java.util.ArrayList;

import phdhtl.khoa63.doan.model.DatPhong;
import phdhtl.khoa63.doan.model.DateTimeUtils;

public class CustomAdapter_QLDP extends ArrayAdapter<DatPhong>  {
    private ArrayList<DatPhong> arr;
    private final Activity context;

    DBHelperDatabse dbh;

    public CustomAdapter_QLDP(Activity context, ArrayList<DatPhong> arr){
        super(context, R.layout.itemdatphong_quanlydatphong, arr);
        this.context = context;
        this.arr = arr;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        dbh = new DBHelperDatabse(context);
        SQLiteDatabase db = dbh.ketNoiDocData();

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.itemdatphong_quanlydatphong, parent, false);
            viewHolder = new ViewHolder();


            viewHolder.txtThoiGian = convertView.findViewById(R.id.txtThoiGian);
            viewHolder.txtMaPhong_qldp = convertView.findViewById(R.id.txtMaPhong_qldatphong);
            viewHolder.txtthongtinloaiphong = convertView.findViewById(R.id.txtthongtinloaiphong);
            viewHolder.txtthongtinmadatphong = convertView.findViewById(R.id.txtthongtinmadatphong);
            viewHolder.txtthongtinkhachhang = convertView.findViewById(R.id.txtthongtinkhachhang);
            viewHolder.txtthongtingiatien = convertView.findViewById(R.id.txtthongtingiatien);
            viewHolder.txtthongtinloaidatphong = convertView.findViewById(R.id.txtthongtinloaidatphong);
            viewHolder.txtthongtintrangthaidatphong = convertView.findViewById(R.id.txtthongtintrangthaidatphong);
            viewHolder.layoutItemDatPhong = convertView.findViewById(R.id.layoutItemDatPhong);

            //viewHolder.imgAnh = convertView.findViewById(R.id.imganhsv);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DatPhong datPhong = arr.get(position);

        viewHolder.txtthongtinmadatphong.setText("Mã: "+datPhong.getMaDatPhong());
        viewHolder.txtMaPhong_qldp.setText("Phòng: "+datPhong.getMaPhong());
        viewHolder.txtThoiGian.setText(DateTimeUtils.convertDateTimeFormat(datPhong.getNgayDatPhong()) + " đến " + DateTimeUtils.convertDateTimeFormat(datPhong.getNgayTraPhong()));
        viewHolder.txtthongtinkhachhang.setText(datPhong.getTenKhachHang() + " - " + datPhong.getSoDienThoaiKhachHang());
        viewHolder.txtthongtingiatien.setText(datPhong.getGia() + "");
        viewHolder.txtthongtinloaidatphong.setText("Ngày");
        if(datPhong.getLoaiDatPhong().equals("dem")){
            viewHolder.txtthongtinloaidatphong.setText("Đêm");
        }else if(datPhong.getLoaiDatPhong().equals("gio")){
            viewHolder.txtthongtinloaidatphong.setText( datPhong.getSoGioDat() + " Giờ");
        }
        viewHolder.txtthongtintrangthaidatphong.setText(datPhong.getTrangThaiDatPhong() + "");

        String sql = String.format(" SELECT lp.TENLOAIPHONG FROM PHONG p INNER JOIN LOAIPHONG lp ON p.MALOAIPHONG = lp.MALOAIPHONG  WHERE p.MAPHONG = %d", datPhong.getMaPhong());

        Cursor cs = db.rawQuery(sql, null);
        if(cs.moveToFirst()){
            viewHolder.txtthongtinloaiphong.setText(cs.getString(0) + "");
        }else{
            Toast.makeText(context, "Khong tim thay loai phong", Toast.LENGTH_SHORT).show();
        }



        //(viewHolder.imgAnh).setImageBitmap(StringToBitMap(datPhong.getImg()));

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).onBookItemClick(book);
                }
            }
        });

        viewHolder.btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, DetailActivity.class);
                in.putExtra("ObjectBook", book);
                context.startActivityForResult(in,200);
            }
        });*/

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ChiTietDatPhongActivity.class);
                in.putExtra("DatPhong", datPhong);
                context.startActivityForResult(in, 200);
            }
        });


        return convertView;
    }

    static class ViewHolder {
        TextView txtthongtinmadatphong,txtThoiGian, txtthongtinkhachhang, txtthongtingiatien, txtthongtinloaidatphong, txtthongtintrangthaidatphong, txtMaPhong_qldp, txtthongtinloaiphong;
        LinearLayout layoutItemDatPhong;

    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
