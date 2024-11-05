package phdhtl.khoa63.doan;

import android.app.Activity;
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
import phdhtl.khoa63.doan.model.Phong;

public class CustomAdapterTimPhongDat extends ArrayAdapter<Phong>   {
    private ArrayList<Phong> arr;
    private String loaidatphong;
    private final Activity context;
    private OnPhongDatListener listener;

    DBHelperDatabse dbh;

    public CustomAdapterTimPhongDat(Activity context, ArrayList<Phong> arr,String loaidatphong, OnPhongDatListener listener) {
        super(context, R.layout.item_timphong, arr);
        this.context = context;
        this.arr = arr;
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

        if(loaidatphong.equals("dem")){
            viewHolder.txtGiaitem.setText("Giá: " + cs.getString(4) + ".000 vnd");
        }else if(loaidatphong.equals("ngay")){
            viewHolder.txtGiaitem.setText("Giá: " + cs.getString(3) + ".000 vnd");
        }else if(loaidatphong.equals("gio")){

        }

        //viewHolder.txtGiaitem.setText("Giá: " + cs.getString(3));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if(loaidatphong.equals("dem")){
                        listener.DatPhong(phong, cs.getString(4));
                    }else if(loaidatphong.equals("ngay")){
                        listener.DatPhong(phong, cs.getString(3));
                    }else if(loaidatphong.equals("gio")){

                    }
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
