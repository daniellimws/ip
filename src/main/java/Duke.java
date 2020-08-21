import java.util.Scanner;

public class Duke {

    private static int requestsCount = 0;
    private static String[] requests = new String[100];

    private static void printRequests() {
        for (int i = 0; i < requestsCount; ++i) {
            System.out.printf("  %d. %s\n", i + 1, requests[i]);
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
        Boolean isRunning = true;

        while (isRunning) {
            String cmd = in.nextLine();

            switch (cmd.toLowerCase()) {
            case "list":
                printRequests();
                break;
            case "bye":
                System.out.println("Bye. Hope to see you again soon!");
                isRunning = false;
                break;
            default:
                System.out.printf(" added: %s\n", cmd);
                requests[requestsCount++] = cmd;
                break;
            }

            System.out.println("");
        }

        in.close();
    }
}
