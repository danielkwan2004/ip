package chattpg.storage;

import chattpg.model.Deadline;
import chattpg.model.Event;
import chattpg.model.Task;
import chattpg.model.Todo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles persistence of tasks to and from the hardcoded file path tasks/tasks.txt.
 */
public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!file.exists()) {
            System.out.println("No existing task file found. Starting with an empty task list.");
            return tasks;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                Task task;
                switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    String by = parts[3];
                    task = new Deadline(description, by);
                    break;
                case "E":
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
                default:
                    // Unknown type; skip
                    continue;
                }
                if (isDone) {
                    task.markTaskAsDone();
                }
                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error marking task as done: " + e.getMessage());
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file, false)) {
            for (Task t : tasks) {
                String doneFlag = t.isDone() ? "1" : "0";
                if (t instanceof Todo) {
                    writer.write("T | " + doneFlag + " | " + t.getDescription() + System.lineSeparator());
                } else if (t instanceof Deadline d) {
                    writer.write("D | " + doneFlag + " | " + d.getDescription() + " | " + d.getBy() + System.lineSeparator());
                } else if (t instanceof Event e) {
                    writer.write("E | " + doneFlag + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}