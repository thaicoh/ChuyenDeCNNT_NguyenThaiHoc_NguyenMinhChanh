package phdhtl.khoa63.doan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import phdhtl.khoa63.doan.model.DatPhong;
import phdhtl.khoa63.doan.model.DateTimeUtils;

public class CustomAdapterDatPhong extends ArrayAdapter<DatPhong>  {
    private ArrayList<DatPhong> arr;
    private final Activity context;

    public CustomAdapterDatPhong(Activity context, ArrayList<DatPhong> arr){
        super(context, R.layout.itemdatphong, arr);
        this.context = context;
        this.arr = arr;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.itemdatphong, parent, false);
            viewHolder = new ViewHolder();


            viewHolder.txtThoiGian = convertView.findViewById(R.id.txtThoiGian);
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

        viewHolder.txtthongtinmadatphong.setText("Mã: "+datPhong.getMaDatPhong() + "");
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



        //(viewHolder.imgAnh).setImageBitmap(StringToBitMap(datPhong.getImg()));

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
        TextView txtthongtinmadatphong,txtThoiGian, txtthongtinkhachhang, txtthongtingiatien, txtthongtinloaidatphong, txtthongtintrangthaidatphong;
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
