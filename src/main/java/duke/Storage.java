package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Utility to interact with the filesystem to load and store data for persistent storage.
 */
public class Storage {
    private String dataDir;
    private String saveFilePath;
    private TaskList taskList;

    /**
     * Creates a Storage object with the path to save the data. It also contains a reference to the TaskList
     * that stores all the tasks.
     *
     * @param dataDir Directory containing the save file.
     * @param saveFilePath Path to store the save file.
     * @param taskList List of tasks.
     */
    public Storage(String dataDir, String saveFilePath, TaskList taskList) {
        this.taskList = taskList;

        this.dataDir = dataDir;
        ensureDataDir();
        this.saveFilePath = Paths.get(dataDir, saveFilePath).toString();
        loadData();
    }

    /**
     * Creates a directory at dataDir if it does not exist in the filesystem.
     */
    private void ensureDataDir() {
        File directory = new File(dataDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Loads the tasks from the indicated file path into the task list.
     */
    public void loadData() {
        File saveFile = new File(saveFilePath);
        Scanner scanner = null;
        int line = 1;
        try {
            scanner = new Scanner(saveFile);
            while (scanner.hasNext()) {
                taskList.addTask(parseTaskLine(scanner.nextLine()));
                line++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Either this is your first time using, or your previously saved data is losted");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(String.format("Save data is corrupted at line %d.", line));
            System.exit(1);
        }
    }

    /**
     * Parses a line that contains information about a Task object.
     *
     * @param taskLine Serialized task.
     * @return Task represented by the line.
     */
    private Task parseTaskLine(String taskLine) throws ParseException {
        String[] tokens = taskLine.split(",");
        String type = tokens[0];
        String description = tokens[2];

        if (type.equals("T")) {
            return new Todo(description);
        }

        String additionalInfo = tokens[3];
        if (type.equals("D")) {
            return new Deadline(description, additionalInfo);
        } else {
            return new Event(description, additionalInfo);
        }
    }

    /**
     * Saves the task list into the save file.
     */
    public void saveData() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(saveFilePath);
            for (Task task : taskList.getTasks()) {
                fileWriter.write(task.serialized() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There is something wrong with saving the tasks data.");
        }
    }
}
