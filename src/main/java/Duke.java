import Task.Deadline;
import Task.Event;
import Task.Task;
import Task.Todo;

import java.util.Scanner;

public class Duke {

    private static int taskCount = 0;
    private static Task[] tasks = new Task[100];

    private static void printTasks() {
        if (taskCount == 0) {
            System.out.println("There are currently no tasks in your list.");
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; ++i) {
            System.out.printf("  %d. %s\n", i + 1, tasks[i]);
        }
    }

    private static void markTaskDone(String cmd) {
        String[] words = cmd.split(" ");
        if (words.length > 1 && words[1].matches("\\d+")) {
            int index = Integer.parseInt(words[1]);
            if (index <= taskCount && index > 0) {
                tasks[index - 1].markAsDone();

                System.out.println("Ok! I've marked this task as done:");
                System.out.printf("  %s\n", tasks[index - 1]);
            } else System.out.printf("Error: Task %d does not exist\n", index);
        } else {
            System.out.println("Usage: done [task index]");
            System.out.println("Example: done 1");
        }
    }

    private static Todo parseTodoCommand(String cmd) {
        int descriptionIndex = "todo ".length();
        String description = cmd.substring(descriptionIndex);
        return new Todo(description);
    }

    private static Deadline parseDeadlineCommand(String cmd) {
        int descriptionIndex = "deadline ".length();
        int dateIndex = cmd.indexOf("/by ");
        String description = cmd.substring(descriptionIndex, dateIndex - 1);
        String date = cmd.substring(dateIndex + "/by ".length());
        return new Deadline(description, date);
    }

    private static Event parseEventCommand(String cmd) {
        int descriptionIndex = "event ".length();
        int dateIndex = cmd.indexOf("/at ");
        String description = cmd.substring(descriptionIndex, dateIndex - 1);
        String date = cmd.substring(dateIndex + "/at ".length());
        return new Event(description, date);
    }

    private static Task parseTaskCommand(String cmd) {
        if (cmd.startsWith("todo ")) {
            return parseTodoCommand(cmd);
        } else if (cmd.startsWith("deadline ")) {
            return parseDeadlineCommand(cmd);
        } else if (cmd.startsWith("event ")) {
            return parseEventCommand(cmd);
        }
        return new Task(cmd);
    }

    private static void addTask(Task task) {
        System.out.printf(" Added: %s\n", task);
        tasks[taskCount++] = task;
        System.out.printf(
                " You now have %d task%s in your list.", taskCount, (taskCount == 1 ? "" : "s"));
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
            System.out.print("> ");
            String cmd = in.nextLine();

            if (cmd.equals("bye")) {
                break;
            } else if (cmd.equals("list")) {
                printTasks();
            } else if (cmd.startsWith("done")) {
                markTaskDone(cmd);
            } else {
                addTask(parseTaskCommand(cmd));
            }

            System.out.println("");
        }

        in.close();
    }
}
