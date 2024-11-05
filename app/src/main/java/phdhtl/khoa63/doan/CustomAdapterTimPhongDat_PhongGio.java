package phdhtl.khoa63.doan;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import phdhtl.khoa63.doan.model.Phong;

public class CustomAdapterTimPhongDat_PhongGio extends ArrayAdapter<Phong>   {
    private ArrayList<Phong> arr;
    private String loaidatphong;
    private final Activity context;
    private OnPhongDatListener listener;
    private int soGio;

    DBHelperDatabse dbh;

    public CustomAdapterTimPhongDat_PhongGio(Activity context, ArrayList<Phong> arr,int soGio, OnPhongDatListener listener) {
        super(context, R.layout.item_timphong, arr);
        this.context = context;
        this.arr = arr;
        this.soGio = soGio;
        this.loaidatphong = loaidatphong;
        this.listener = listener;

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_timphong, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtMaPhongitem = convertView.findViewById(R.id.txtMaPhongitem);
            viewHolder.txtLoaiPhongitem = convertView.findViewById(R.id.txtLoaiPhongitem);
            viewHolder.txtMoTaitem = convertView.findViewById(R.id.txtMoTaitem);
            viewHolder.txtGiaitem = convertView.findViewById(R.id.txtGiaitem);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Phong phong = arr.get(position);

        viewHolder.txtMaPhongitem.setText("P" + phong.getMaPhong());
        viewHolder.txtMoTaitem.setText("Mô tả: " + phong.getMoTa());

        dbh = new DBHelperDatabse(context);
        SQLiteDatabase db =  dbh.ketNoiDocData();
        Cursor cs = db.rawQuery("SELECT * FROM LOAIPHONG WHERE maloaiphong = '" + phong.getMaLoaiPhong() + "'", null);

        cs.moveToFirst();

        viewHolder.txtLoaiPhongitem.setText("Loại phòng: " + cs.getString(1));

        int giaGiodautien = Integer.parseInt(cs.getString(5));
        int giatt = Integer.parseInt(cs.getString(6));
        int gia = giaGiodautien * 1 + giatt * (soGio - 1);


        viewHolder.txtGiaitem.setText("Giá: " + gia + ".000 vnd");

        //viewHolder.txtGiaitem.setText("Giá: " + cs.getString(3));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {

                    listener.DatPhong(phong,gia + "");
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView txtMaPhongitem ;
        TextView txtLoaiPhongitem;
        TextView txtMoTaitem ;
        TextView txtGiaitem;
    }

}
