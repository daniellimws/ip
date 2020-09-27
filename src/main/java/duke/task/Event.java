package duke.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {
    private Date date;
    private DateFormat df;

    public Event(String description, String date) throws ParseException {
        super(description);
        df = new SimpleDateFormat("dd/MM/yy HHmm");
        this.date = df.parse(date);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (at: %s)", super.toString(), date);
    }

    @Override
    protected String getSerializeType() {
        return "E";
    }

    @Override
    public String serialized() {
        return String.format("%s,%s", super.serialized(), df.format(date));
    }
}
