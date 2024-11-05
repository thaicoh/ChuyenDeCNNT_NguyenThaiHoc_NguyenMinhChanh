package phdhtl.khoa63.doan.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    // Hàm chuyển đổi định dạng chuỗi ngày giờ
    public static String convertDateTimeFormat(String inputDateTime) {
        // Định dạng đầu vào
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Định dạng đầu ra
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM");

        String outputDateTime = "";
        try {
            // Chuyển chuỗi đầu vào thành đối tượng Date
            Date date = inputFormat.parse(inputDateTime);
            // Chuyển đối tượng Date thành chuỗi đầu ra với định dạng mong muốn
            outputDateTime = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateTime;
    }

    public static boolean isToday(String dateString) {
        // Định dạng ngày tháng mà bạn muốn kiểm tra
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Chuyển chuỗi ngày tháng thành đối tượng Date
            Date inputDate = dateFormat.parse(dateString);

            // Lấy ngày hôm nay
            Date today = new Date();

            // Định dạng ngày hôm nay về dạng yyyy-MM-dd
            String todayString = dateFormat.format(today);

            // So sánh ngày hôm nay với ngày đầu vào
            return dateString.equals(todayString);

        } catch (ParseException e) {
            // Nếu chuỗi không đúng định dạng yyyy-MM-dd
            e.printStackTrace();
        }

        return false;
    }

    public static String addHoursToTime(String time, int h) {
        // Định dạng thời gian "HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            // Chuyển đổi chuỗi thời gian thành đối tượng Date
            Date date = sdf.parse(time);

            // Tạo đối tượng Calendar từ Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Cộng thêm số giờ vào thời gian
            calendar.add(Calendar.HOUR_OF_DAY, h);

            // Chuyển đổi đối tượng Calendar mới thành chuỗi định dạng "HH:mm"
            return sdf.format(calendar.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isLonHon(String time1, String time2) {
        // Định dạng thời gian "HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            // Chuyển đổi chuỗi thời gian thành đối tượng Date
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);

            // So sánh hai đối tượng Date
            return date1.after(date2);

        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Trường hợp xảy ra lỗi khi phân tích chuỗi thời gian
        }
    }


}
