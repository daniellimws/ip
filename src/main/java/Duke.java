import java.util.Scanner;

public class Duke {

    private static int taskCount = 0;
    private static Task[] tasks = new Task[100];

    private static void printRequests() {
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
                printRequests();
            } else if (cmd.startsWith("done")) {
                markTaskDone(cmd);
            } else {
                System.out.printf(" added: %s\n", cmd);
                tasks[taskCount++] = new Task(cmd);
            }

            System.out.println("");
        }

        in.close();
    }
}
