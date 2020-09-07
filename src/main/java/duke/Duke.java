package duke;

import duke.exception.EmptyDescriptionException;
import duke.exception.UnknownCommandException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.Scanner;

public class Duke {

    private static final int TASKS_CAPACITY = 100;

    private static final String AT_ARGUMENT = "/at ";
    private static final String BY_ARGUMENT = "/by ";

    private static final String BYE_COMMAND = "bye";
    private static final String DONE_COMMAND = "done";
    private static final String LIST_COMMAND = "list";

    private static final String EVENT_COMMAND = "event ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String TODO_COMMAND = "todo ";

    private static final String PROMPT = "> ";
    private static final String PRE_RESPONSE_WHITESPACE = "  ";

    private static int taskCount = 0;
    private static Task[] tasks = new Task[TASKS_CAPACITY];

    private static void printPrompt() {
        System.out.print(PROMPT);
    }

    private static void printResponse(String response) {
        System.out.printf("%s%s\n", PRE_RESPONSE_WHITESPACE, response);
    }

    private static void welcome() {
        String logo =
                ""
                        + "                 _______\n"
                        + "               _/       \\_\n"
                        + "              / |       | \\\n"
                        + "             /  |__   __|  \\\n"
                        + "            |__/((o| |o))\\__|\n"
                        + "            |      | |      |\n"
                        + "            |\\     |_|     /|\n"
                        + "            | \\           / |\n"
                        + "             \\| /  ___  \\ |/\n"
                        + "              \\ | / _ \\ | /\n"
                        + "               \\_________/\n"
                        + "                _|_____|_\n"
                        + "           ____|_________|____\n"
                        + "          /                   \\\n";
        System.out.println(logo);
        System.out.println("Hello. What can I do for you?");
    }

    private static void printTasks() {
        if (taskCount == 0) {
            printResponse("There are currently no tasks in your list.");
            return;
        }
        printResponse("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; ++i) {
            printResponse(String.format("%d. %s", i + 1, tasks[i]));
        }
    }

    private static void handleMarkDone(String cmd) {
        String[] words = cmd.split(" ");
        int index;

        try {
            index = Integer.parseInt(words[1]);
        } catch (NumberFormatException e) {
            printDoneCmdHelp();
            return;
        } catch (IndexOutOfBoundsException e) {
            printDoneCmdHelp();
            return;
        }

        try {
            Task task = tasks[index - 1];
            task.setAsDone();
            printResponse("Ok! I've marked this task as done:");
            printResponse(String.format("  %s", task));
        } catch (NullPointerException e) {
            System.out.printf("Error: Task %d does not exist", index);
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("Error: Task %d does not exist", index);
        }
    }

    private static void printDoneCmdHelp() {
        printResponse("Usage: done [task index]");
        printResponse("Example: done 1");
    }

    private static void handleAddTodo(String cmd) {
        int descriptionIndex = TODO_COMMAND.length();
        String description = cmd.substring(descriptionIndex);
        try {
            Todo todo = new Todo(description);
            addTask(todo);
        } catch (EmptyDescriptionException e) {
            printTodoCmdHelp();
        }
    }

    private static void printTodoCmdHelp() {
        printResponse("Usage: todo [description]");
        printResponse("Example: todo borrow book");
    }

    private static void handleAddDeadline(String cmd) {
        int descriptionIndex = DEADLINE_COMMAND.length();
        int dateIndex = cmd.indexOf(BY_ARGUMENT);
        String description, date;

        try {
            description = cmd.substring(descriptionIndex, dateIndex - 1);
            date = cmd.substring(dateIndex + BY_ARGUMENT.length());
        } catch (StringIndexOutOfBoundsException e) {
            printDeadlineCmdHelp();
            return;
        }

        try {
            Deadline deadline = new Deadline(description, date);
            addTask(deadline);
        } catch (EmptyDescriptionException e) {
            printTodoCmdHelp();
        }
    }

    private static void printDeadlineCmdHelp() {
        printResponse("Usage: deadline [description] /by [when]");
        printResponse("Example: deadline review pr /by tomorrow");
    }

    private static void handleAddEvent(String cmd) {
        int descriptionIndex = EVENT_COMMAND.length();
        int dateIndex = cmd.indexOf(AT_ARGUMENT);
        String description, date;

        try {
            description = cmd.substring(descriptionIndex, dateIndex - 1);
            date = cmd.substring(dateIndex + AT_ARGUMENT.length());
        } catch (StringIndexOutOfBoundsException e) {
            printEventCmdHelp();
            return;
        }

        try {
            Event event = new Event(description, date);
            addTask(event);
        } catch (EmptyDescriptionException e) {
            printTodoCmdHelp();
        }
    }

    private static void printEventCmdHelp() {
        printResponse("Usage: event [description] /at [when]");
        printResponse("Example: event presentation /at tomorrow");
    }

    private static void addTask(Task task) {
        if (taskCount == TASKS_CAPACITY) {
            printResponse("You got too many tasks boss. I won't add this task. Time to take a rest.");
            return;
        }

        tasks[taskCount++] = task;
        printResponse(String.format("Added: %s", task));

        String taskWord = "task";
        if (taskCount != 1) {
            taskWord += "s";
        }
        printResponse(String.format("You now have %d %s in your list.", taskCount, taskWord));
    }

    private static boolean handleCommand(String cmd) throws UnknownCommandException {
        if (cmd.equals(BYE_COMMAND)) {
            printResponse("Bye. See you next time.");
            return false;
        }

        if (cmd.startsWith(LIST_COMMAND)) {
            printTasks();
        } else if (cmd.startsWith(DONE_COMMAND)) {
            handleMarkDone(cmd);
        } else if (cmd.startsWith(TODO_COMMAND)) {
            handleAddTodo(cmd);
        } else if (cmd.startsWith(DEADLINE_COMMAND)) {
            handleAddDeadline(cmd);
        } else if (cmd.startsWith(EVENT_COMMAND)) {
            handleAddEvent(cmd);
        } else {
            throw new UnknownCommandException();
        }

        return true;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        welcome();

        boolean stillRunning = true;
        do {
            printPrompt();

            try {
                stillRunning = handleCommand(in.nextLine().trim());
            } catch (UnknownCommandException e) {
                printHelp();
            }
            printResponse("");

        } while (stillRunning);

        in.close();
    }

    private static void printHelp() {
        printResponse("Idk what's that boss.");
        printResponse("Available commands are: help, list, todo, deadline, event, done, bye");
        printResponse("");

        printResponse("Todo:");
        printTodoCmdHelp();
        printResponse("");

        printResponse("Deadline:");
        printDeadlineCmdHelp();
        printResponse("");

        printResponse("Event:");
        printEventCmdHelp();
        printResponse("");

        printResponse("Done:");
        printDoneCmdHelp();
    }
}
