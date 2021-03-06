package duke;

import duke.exception.UnknownCommandException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Duke {

    private static final String DATA_DIR = "data";
    private static final String SAVE_PATH = "duke.txt";

    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;

    public Duke(String dataDir, String filePath) {
        ui = new Ui();
        taskList = new TaskList();
        storage = new Storage(dataDir, filePath, taskList);
        parser = new Parser(ui, taskList);
    }

    public void run() {
        ui.showWelcome();

        Scanner stdin = new Scanner(System.in);
        do {
            ui.printPrompt();

            try {
                parser.handleCommand(stdin.nextLine().trim());
                if (taskList.isModified()) {
                    storage.saveData();
                    taskList.ackModified();
                }
            } catch (UnknownCommandException e) {
                ui.showUnknownCommandError();
            } catch (NoSuchElementException e) {
                break;
            }
            ui.printResponse("");

        } while (!parser.isExit());

        stdin.close();
    }

    public static void main(String[] args) {
        Duke duke = new Duke(DATA_DIR, SAVE_PATH);
        duke.run();
    }
}
