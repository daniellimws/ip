package duke.task;

public class Event extends Task {
    private String date;

    public Event(String description, String date) {
        super(description);
        this.date = date;
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
        return String.format("%s,%s", super.serialized(), date);
    }
}
