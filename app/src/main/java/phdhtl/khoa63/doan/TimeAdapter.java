package phdhtl.khoa63.doan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    private List<String> timeList;
    private OnTimeClickListener listener;

    private Context context;
    private int nearestPosition;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnTimeClickListener2 {
        void onTimeClick(String time);
    }


    public TimeAdapter(Context context,OnTimeClickListener listener, List<String> timeList, int h, int m) {
        this.listener = listener;
        this.context = context;
        this.timeList = timeList;
        this.nearestPosition = findNearestPosition(h, m);
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.tvTime.setText(timeList.get(position));

        // Đổi nền của item đã được chọn
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#9966CC")); // Màu cam
            holder.tvTime.setBackgroundColor(Color.parseColor("#9966CC")); // Màu cam

        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#DDDDDD")); // Màu nền mặc định
            holder.tvTime.setBackgroundColor(Color.parseColor("#DDDDDD")); // Màu nền mặc định
        }
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public int getNearestPosition() {
        return nearestPosition;
    }

    private int findNearestPosition(int h, int m) {
        int targetTime = h * 60 + m;
        int nearestPosition = 0;
        int minDifference = Integer.MAX_VALUE;

        for (int i = 0; i < timeList.size(); i++) {
            String[] timeParts = timeList.get(i).split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            int listTime = hour * 60 + minute;

            int difference = Math.abs(listTime - targetTime);
            if (difference < minDifference) {
                minDifference = difference;
                nearestPosition = i;
            }
        }

        return nearestPosition;
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTime;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                notifyItemChanged(selectedPosition); // Cập nhật item trước đó
                selectedPosition = position;
                notifyItemChanged(selectedPosition); // Cập nhật item hiện tại

                String clickedTime = timeList.get(position);
                if (listener != null) {
                    listener.onTimeClick(timeList.get(position));
                }
                //Toast.makeText(context, "Clicked: " + clickedTime, Toast.LENGTH_SHORT).show();

            }
        }





    }
}