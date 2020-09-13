package duke.task;

import duke.exception.EmptyDescriptionException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws EmptyDescriptionException {
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); // return tick or X symbols
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    protected abstract String getSerializeType();

    public String serialized() {
        String isDoneString = isDone ? "Y" : "N";
        return String.format("%s,%s,%s", getSerializeType(), isDoneString, description);
    }

    public void setAsDone() {
        this.isDone = true;
    }
}
