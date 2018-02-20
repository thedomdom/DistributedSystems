package timeservicermi;

import java.util.Date;

public class Event {

    private Date date;
    private String description;

    public Event(Date date, String description) {
        this.date = date;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
