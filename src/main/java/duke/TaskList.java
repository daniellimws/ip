package duke;

import duke.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    private boolean isModified;

    public boolean isModified() {
        return isModified;
    }

    public void setAsModified() {
        isModified = true;
    }

    public void ackModified() {
        isModified = false;
    }

    public TaskList() {
        tasks = new ArrayList<Task>();
        isModified = false;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        setAsModified();
    }

    public Task removeTask(Task task) {
        tasks.remove(task);
        setAsModified();
        return task;
    }

    public Task removeTask(int index) {
        Task task = tasks.remove(index);
        setAsModified();
        return task;
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }
}
