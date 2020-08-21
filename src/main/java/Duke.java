import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = "" +
            "                 _______\n"+
            "               _/       \\_\n"+
            "              / |       | \\\n"+
            "             /  |__   __|  \\\n"+
            "            |__/((o| |o))\\__|\n"+
            "            |      | |      |\n"+
            "            |\\     |_|     /|\n"+
            "            | \\           / |\n"+
            "             \\| /  ___  \\ |/\n"+
            "              \\ | / _ \\ | /\n"+
            "               \\_________/\n"+
            "                _|_____|_\n"+
            "           ____|_________|____\n"+
            "          /                   \\\n";
        System.out.println(logo);
        System.out.println("Hello. What can I do for you?");

        Scanner in = new Scanner(System.in);
        Boolean isRunning = true;

        while (isRunning) {
            String cmd = in.nextLine();

            System.out.print("  ");
            switch (cmd.toLowerCase()) {
            case "bye":
                System.out.println("Bye. Hope to see you again soon!");
                isRunning = false;
                break;
            default:
                System.out.println(cmd);
                break;
            }
        }

        in.close();
    }
}
