package phdhtl.khoa63.doan;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import phdhtl.khoa63.doan.model.DatPhong;
import phdhtl.khoa63.doan.model.Phong;

import java.io.Serializable;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;


public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {


    private List<Phong> phongList;
    private List<DatPhong> datPhongList;
    private Context context;



    DBHelperDatabse dbHelperDatabse;

    public RoomAdapter(List<Phong> phongList,List<DatPhong> datPhongList, Context context) {
        this.phongList = phongList;
        this.datPhongList = datPhongList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room2, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {



        Phong phong = phongList.get(position);

        holder.txtMaPhong.setText("P" + phong.getMaPhong());


        holder.txtTrangThai.setText("Phòng trống");

        if(phong.getTrangThai() == 1){
            int redColor = Color.parseColor("#94FF2020"); // Định nghĩa màu đỏ từ mã HEX
            Drawable redDrawable = new ColorDrawable(redColor); // Tạo một Drawable màu đỏ
            holder.layoutTrangThai.setBackground(redDrawable); // Thiết lập màu nền của holder.layout
            holder.txtTrangThai.setText("Phòng có người");
            for (DatPhong datPhong:datPhongList) {
                if(phong.getMaPhong() == datPhong.getMaPhong()){
                    String motathoigiandatphong = "Theo giờ";
                    if(datPhong.getLoaiDatPhong().equals("dem")){
                        motathoigiandatphong = "Qua đêm";
                    }else if(datPhong.getLoaiDatPhong().equals("ngay")){
                        motathoigiandatphong = "Theo ngày";
                    }

                    //motathoigiandatphong += "Từ " + datPhong.getNgayDatPhong() + " đến " + datPhong.getNgayTraPhong();

                    holder.txtLoaiDatPhong.setText(motathoigiandatphong);
                    holder.txtThongTinKhach.setText(datPhong.getTenKhachHang() + " - " + datPhong.getSoDienThoaiKhachHang());
                }
            }

        }

        //Chuyển đổi tên tài nguyên drawable thành ID tài nguyên
        // int drawableId = context.getResources().getIdentifier(phong.getAnhPhong(), "drawable", context.getPackageName());
        //holder.anhPhongImageView.setImageResource(drawableId);
        // Đặt tag là đối tượng Phong để sử dụng trong sự kiện click
        holder.itemView.setTag(phong);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phong clickedPhong = (Phong) v.getTag();
                Intent in = new Intent(context, PhongActivity.class);
                in.putExtra("Phong", clickedPhong);
                // Xử lý sự kiện click
                Toast.makeText(context, "Clicked Phòng: " + clickedPhong.getMaPhong(), Toast.LENGTH_SHORT).show();
                // Thực hiện các hành động khác

                ((Activity) context).startActivity(in);

            }
        });
    }


    @Override
    public int getItemCount() {
        return phongList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaPhong, txtTrangThai, txtLoaiDatPhong, txtThongTinKhach;
        //ImageView anhPhongImageView;
        ConstraintLayout layoutTrangThai;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPhong = itemView.findViewById(R.id.txtMaPhong);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtLoaiDatPhong = itemView.findViewById(R.id.txtLoaiDatPhong);
            txtThongTinKhach = itemView.findViewById(R.id.txtThongTinKhach);
            layoutTrangThai = itemView.findViewById(R.id.layoutTrangThai);
        }
    }
}
