import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoredData {
    private Date startTime;
    private Date endTime;
    private String activity;

    public MonitoredData(String startTime, String endTime, String activity) throws ParseException {
        this.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);;
        this.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);;
        this.activity = activity;
    }

    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = "Start time: " + df.format(startTime) + ", End time: " + df.format(endTime) + ", Activity: " + activity;
        return s;
    }

    public String getStartDay() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(startTime);
        return s;
    }

    public String getEndDay() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(endTime);
        return s;
    }

    public long getDuration() {
        return endTime.getTime() - startTime.getTime();
    }

    public String getActivity() {
        return activity;
    }
}

