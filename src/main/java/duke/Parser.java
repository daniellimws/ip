package duke;

import duke.exception.EmptyDescriptionException;
import duke.exception.UnknownCommandException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Utility to parse the user input and handle it.
 */
public class Parser {
    private static final String AT_ARGUMENT = "/at ";
    private static final String BY_ARGUMENT = "/by ";

    private static final String BYE_COMMAND = "bye";
    private static final String DONE_COMMAND = "done";
    private static final String LIST_COMMAND = "list";
    private static final String DELETE_COMMAND = "delete";
    private static final String FIND_COMMAND = "find";

    private static final String EVENT_COMMAND = "event";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String TODO_COMMAND = "todo";

    private static final String HELP_COMMAND = "help";

    private boolean isExit;

    private Ui ui;
    private TaskList taskList;

    /**
     * Creates a Parser object with a reference to a Ui and TaskList object.
     */
    public Parser(Ui ui, TaskList taskList) {
        isExit = false;
        this.ui = ui;

        this.taskList = taskList;
    }

    /**
     * Gets whether the user wanted to exit the program.
     *
     * @return Whether the user wanted to exit the program.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Printing out the list of tasks.
     */
    private void handleList() {
        if (taskList.size() == 0) {
            ui.printResponse("There are currently no tasks in your list.");
            return;
        }
        ui.printResponse("Here are the tasks in your list:");
        ui.printTasks(taskList);
    }

    /**
     * Marks a task as done.
     *
     * @param args Index of the task (starting from 1) to be marked as done.
     */
    private void handleMarkDone(String args) {
        int index;

        try {
            index = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            ui.printDoneCmdHelp();
            return;
        }

        try {
            Task task = taskList.getTask(index - 1);
            task.setAsDone();
            taskList.setAsModified();
            ui.printResponse("Ok! I've marked this task as done:");
            ui.printResponse(String.format("  %s", task));
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("Error: Task %d does not exist", index);
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param args Index of the task (starting from 1) to be deleted.
     */
    private void handleDelete(String args) {
        int index;

        try {
            index = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            ui.printDeleteCmdHelp();
            return;
        }

        try {
            Task task = taskList.removeTask(index - 1);
            ui.printResponse("Ok! I've deleted this task:");
            ui.printResponse(String.format("  %s", task));
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("Error: Task %d does not exist", index);
        }
    }

    /**
     * Searches for tasks with the given string.
     *
     * @param args Keyword to search for.
     */
    private void handleFind(String args) {
        String filter = args;

        if (filter.isEmpty()) {
            ui.printFindCmdHelp();
            return;
        }

        ArrayList filteredList = (ArrayList) taskList.getTasks().stream().filter((task -> {
            return task.getDescription().contains(filter);
        })).collect(Collectors.toList());

        if (filteredList.size() == 0) {
            ui.printResponse("Could not find any tasks with this keyword.");
            return;
        }
        ui.printResponse(String.format("Here are the tasks that contains the text \"%s\"", filter));
        ui.printTasks(filteredList);
    }

    /**
     * Creates a new Todo object and adds it to the list.
     *
     * @param args Description of the task.
     */
    private void handleAddTodo(String args) {
        try {
            Todo todo = new Todo(args);
            addTask(todo);
        } catch (EmptyDescriptionException e) {
            ui.printTodoCmdHelp();
        }
    }

    /**
     * Creates a new Deadline object and adds it to the list.
     *
     * @param args Description and deadline of the task.
     */
    private void handleAddDeadline(String args) {
        int dateIndex = args.indexOf(BY_ARGUMENT);
        String description, date;

        try {
            description = args.substring(0, dateIndex - 1);
            date = args.substring(dateIndex + BY_ARGUMENT.length());
        } catch (StringIndexOutOfBoundsException e) {
            ui.printDeadlineCmdHelp();
            return;
        }

        try {
            Deadline deadline = new Deadline(description, date);
            addTask(deadline);
        } catch (EmptyDescriptionException | ParseException e) {
            ui.printDeadlineCmdHelp();
        }
    }

    /**
     * Creates a new Event object and adds it to the list.
     *
     * @param args Description and date of the event.
     */
    private void handleAddEvent(String args) {
        int dateIndex = args.indexOf(AT_ARGUMENT);
        String description, date;

        try {
            description = args.substring(0, dateIndex - 1);
            date = args.substring(dateIndex + AT_ARGUMENT.length());
        } catch (StringIndexOutOfBoundsException e) {
            ui.printEventCmdHelp();
            return;
        }

        try {
            Event event = new Event(description, date);
            addTask(event);
        } catch (EmptyDescriptionException | ParseException e) {
            ui.printEventCmdHelp();
        }
    }

    /**
     * Helper method to add a task to the list.
     *
     * @param task Task to be added.
     */
    private void addTask(Task task) {
        taskList.addTask(task);
        ui.printResponse(String.format("Added: %s", task));

        String taskWord = "task";
        if (taskList.size() != 1) {
            taskWord += "s";
        }
        ui.printResponse(String.format("You now have %d %s in your list.", taskList.size(), taskWord));
    }

    /**
     * Parses a command string and delegates to the respective methods.
     *
     * @param cmd Name and arguments of a command.
     */
    public void handleCommand(String cmd) throws UnknownCommandException {
        if (cmd.equals(BYE_COMMAND)) {
            ui.printResponse("Bye. See you next time.");
            isExit = true;
            return;
        }

        String cmdName = cmd;
        String cmdArgs = "";
        int spaceIndex = cmd.indexOf(" ");
        if (spaceIndex != -1) {
            cmdName = cmd.substring(0, spaceIndex).trim();
            cmdArgs = cmd.substring(spaceIndex + 1).trim();
        }

        cmdName = cmdName.toLowerCase();

        if (cmdName.equals(HELP_COMMAND)) {
            ui.printHelp();
        } else if (cmdName.equals(LIST_COMMAND)) {
            handleList();
        } else if (cmdName.equals(DONE_COMMAND)) {
            handleMarkDone(cmdArgs);
        } else if (cmdName.equals(TODO_COMMAND)) {
            handleAddTodo(cmdArgs);
        } else if (cmdName.equals(DEADLINE_COMMAND)) {
            handleAddDeadline(cmdArgs);
        } else if (cmdName.equals(EVENT_COMMAND)) {
            handleAddEvent(cmdArgs);
        } else if (cmdName.equals(DELETE_COMMAND)) {
            handleDelete(cmdArgs);
        } else if (cmdName.equals(FIND_COMMAND)) {
            handleFind(cmdArgs);
        } else {
            throw new UnknownCommandException();
        }
    }
}
