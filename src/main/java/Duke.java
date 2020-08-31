import Task.Deadline;
import Task.Event;
import Task.Task;
import Task.Todo;

import java.util.Scanner;

public class Duke {

    private static final String REGEX_NUM = "\\d+";

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

    private static int taskCount = 0;
    private static Task[] tasks = new Task[TASKS_CAPACITY];

    private static void printPrompt() {
        System.out.print(PROMPT);
    }

    private static void printResponse(String response) {
        System.out.printf("  %s\n", response);
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

    private static void markTaskDone(String cmd) {
        String[] words = cmd.split(" ");
        if (words.length > 1 && words[1].matches(REGEX_NUM)) {
            int index = Integer.parseInt(words[1]);
            if (index <= taskCount && index > 0) {
                tasks[index - 1].markAsDone();

                printResponse("Ok! I've marked this task as done:");
                printResponse(String.format("  %s", tasks[index - 1]));
            } else System.out.printf("Error: Task %d does not exist", index);
        } else {
            printResponse("Usage: done [task index]");
            printResponse("Example: done 1");
        }
    }

    private static Todo parseTodoCommand(String cmd) {
        int descriptionIndex = TODO_COMMAND.length();
        String description = cmd.substring(descriptionIndex);
        return new Todo(description);
    }

    private static Deadline parseDeadlineCommand(String cmd) {
        int descriptionIndex = DEADLINE_COMMAND.length();
        int dateIndex = cmd.indexOf(BY_ARGUMENT);
        String description = cmd.substring(descriptionIndex, dateIndex - 1);
        String date = cmd.substring(dateIndex + BY_ARGUMENT.length());
        return new Deadline(description, date);
    }

    private static Event parseEventCommand(String cmd) {
        int descriptionIndex = EVENT_COMMAND.length();
        int dateIndex = cmd.indexOf(AT_ARGUMENT);
        String description = cmd.substring(descriptionIndex, dateIndex - 1);
        String date = cmd.substring(dateIndex + AT_ARGUMENT.length());
        return new Event(description, date);
    }

    private static Task parseTaskCommand(String cmd) {
        if (cmd.startsWith(TODO_COMMAND)) {
            return parseTodoCommand(cmd);
        } else if (cmd.startsWith(DEADLINE_COMMAND)) {
            return parseDeadlineCommand(cmd);
        } else if (cmd.startsWith(EVENT_COMMAND)) {
            return parseEventCommand(cmd);
        }
        return new Task(cmd);
    }

    private static void addTask(Task task) {
        printResponse(String.format("Added: %s", task));
        tasks[taskCount++] = task;
        printResponse(
                String.format(
                        "You now have %d task%s in your list.",
                        taskCount, (taskCount == 1 ? "" : "s")));
    }

    public static void main(String[] args) {
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

        Scanner in = new Scanner(System.in);

        while (true) {
            printPrompt();
            String cmd = in.nextLine();

            if (cmd.equals(BYE_COMMAND)) {
                printResponse("Bye. See you next time.");
                break;
            } else if (cmd.equals(LIST_COMMAND)) {
                printTasks();
            } else if (cmd.startsWith(DONE_COMMAND)) {
                markTaskDone(cmd);
            } else {
                addTask(parseTaskCommand(cmd));
            }

            System.out.println("");
        }

        in.close();
    }
}
