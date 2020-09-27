package duke;

import duke.task.Task;

import java.util.ArrayList;

/**
 * Utility class for interacting with the user.
 */
public class Ui {

    private static final String PROMPT = "> ";
    private static final String PRE_RESPONSE_WHITESPACE = "  ";

    /**
     * Creates a Ui object.
     */
    public Ui() {
    }

    /**
     * Shows the welcome message at startup.
     */
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

    /**
     * Prints the prompt character.
     */
    public void printPrompt() {
        System.out.print(PROMPT);
    }

    /**
     * Formats a string then prints it out to the user.
     *
     * @param response The response to be printed to the user.
     */
    public void printResponse(String response) {
        System.out.printf("%s%s\n", PRE_RESPONSE_WHITESPACE, response);
    }

    /**
     * Prints out the tasks in a list.
     *
     * @param taskList The task list to be printed.
     */
    public void printTasks(ArrayList<Task> taskList) {
        for (int i = 0; i < taskList.size(); ++i) {
            printResponse(String.format("%d. %s", i + 1, taskList.get(i)));
        }
    }

    /**
     * Prints out the tasks in a list.
     *
     * @param taskList The task list to be printed.
     */
    public void printTasks(TaskList taskList) {
        printTasks(taskList.getTasks());
    }

    /**
     * Prints the instructions to use the "delete" command.
     */
    public void printDeleteCmdHelp() {
        printResponse("Usage: delete [task index]");
        printResponse("Example: delete 1");
    }

    /**
     * Prints the instructions to use the "done" command.
     */
    public void printDoneCmdHelp() {
        printResponse("Usage: done [task index]");
        printResponse("Example: done 1");
    }

    /**
     * Prints the instructions to use the "find" command.
     */
    public void printFindCmdHelp() {
        printResponse("Usage: find [filter]");
        printResponse("Example: find lecture");
    }

    /**
     * Prints the instructions to use the "todo" command.
     */
    public void printTodoCmdHelp() {
        printResponse("Usage: todo [description]");
        printResponse("Example: todo borrow book");
    }

    /**
     * Prints the instructions to use the "deadline" command.
     */
    public void printDeadlineCmdHelp() {
        printResponse("Usage: deadline [description] /by [when (format: dd/mm/yy hhmm)]");
        printResponse("Example: deadline review pr /by tomorrow");
    }

    /**
     * Prints the instructions to use the "event" command.
     */
    public void printEventCmdHelp() {
        printResponse("Usage: event [description] /at [when (format: dd/mm/yy hhmm)]");
        printResponse("Example: event presentation /at tomorrow");
    }

    /**
     * Tells the user that the given command is not recognized, and then prints out
     * the help message.
     */
    public void showUnknownCommandError() {
        printResponse("Idk what's that boss.");
        printHelp();
    }

    /**
     * Prints out the list of available commands and the instructions to use each command.
     */
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
