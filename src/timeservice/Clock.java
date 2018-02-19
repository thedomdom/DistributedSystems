package timeservice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {

    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("kk:mm:ss");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    public static String date() {
        return dateFormatter.format(new Date());
    }

    public static String time() {
        return timeFormatter.format(new Date());

    }
}