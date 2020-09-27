package duke.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Deadline extends Task {
    private Date date;
    private DateFormat df;

    public Deadline(String description, String date) throws ParseException {
        super(description);
        df = new SimpleDateFormat("dd/MM/yy HHmm");
        this.date = df.parse(date);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), date);
    }

    @Override
    protected String getSerializeType() {
        return "D";
    }

    @Override
    public String serialized() {
        return String.format("%s,%s", super.serialized(), df.format(date));
    }
}
