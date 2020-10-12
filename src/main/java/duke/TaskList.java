package duke;

import duke.task.Task;

import java.util.ArrayList;

/**
 * Utility to store the list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private boolean isModified;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<Task>();
        isModified = false;
    }

    /**
     * Gets whether the list has been recently modified.
     * @return Whether the list has been recently modified.
     */
    public boolean isModified() {
        return isModified;
    }

    /**
     * Indicate that the list has been recently modified.
     */
    public void setAsModified() {
        isModified = true;
    }

    /**
     * Acknowledges that the list has been recently modified, and then mark it as
     * not modified.
     */
    public void ackModified() {
        isModified = false;
    }

    /**
     * Gets the ArrayList storing the tasks.
     * @return The ArrayList storing the tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets whether the list has been recently modified.
     * @return Whether the list has been recently modified.
     */
    public void addTask(Task task) {
        tasks.add(task);
        setAsModified();
    }

    /**
     * Removes a task from the list based on the given index.
     * @param index Index of the task that is to be removed.
     * @return Task that was removed from the list.
     */
    public Task removeTask(int index) {
        Task task = tasks.remove(index);
        setAsModified();
        return task;
    }

    /**
     * Gets the number of tasks in the list.
     * @return Number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Gets a task from the list at the given index.
     * @param index Index of the task to be returned.
     * @return Task at the given index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }
}
