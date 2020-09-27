package duke;

import duke.task.Task;

import java.util.ArrayList;

public class Ui {

    private static final String PROMPT = "> ";
    private static final String PRE_RESPONSE_WHITESPACE = "  ";

    public Ui() {

    }

    public void showWelcome() {
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

    public void printPrompt() {
        System.out.print(PROMPT);
    }

    public void printResponse(String response) {
        System.out.printf("%s%s\n", PRE_RESPONSE_WHITESPACE, response);
    }

    public void printTasks(ArrayList<Task> taskList) {
        for (int i = 0; i < taskList.size(); ++i) {
            printResponse(String.format("%d. %s", i + 1, taskList.get(i)));
        }
    }

    public void printTasks(TaskList taskList) {
        printTasks(taskList.getTasks());
    }

    public void printDeleteCmdHelp() {
        printResponse("Usage: delete [task index]");
        printResponse("Example: delete 1");
    }

    public void printDoneCmdHelp() {
        printResponse("Usage: done [task index]");
        printResponse("Example: done 1");
    }

    public void printFindCmdHelp() {
        printResponse("Usage: find [filter]");
        printResponse("Example: find lecture");
    }

    public void printTodoCmdHelp() {
        printResponse("Usage: todo [description]");
        printResponse("Example: todo borrow book");
    }

    public void printDeadlineCmdHelp() {
        printResponse("Usage: deadline [description] /by [when (format: dd/mm/yy hhmm)]");
        printResponse("Example: deadline review pr /by tomorrow");
    }

    public void printEventCmdHelp() {
        printResponse("Usage: event [description] /at [when (format: dd/mm/yy hhmm)]");
        printResponse("Example: event presentation /at tomorrow");
    }

    public void showUnknownCommandError() {
        printResponse("Idk what's that boss.");
        printHelp();
    }

    public void printHelp() {
        printResponse("Available commands are: help, list, todo, deadline, event, done, delete, find, bye");
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

        printResponse("Delete:");
        printDeleteCmdHelp();

        printResponse("Find:");
        printFindCmdHelp();
    }
}
