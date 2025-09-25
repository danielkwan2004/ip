package chattpg.logic;

import chattpg.logic.exceptions.InvalidCommandException;
import chattpg.logic.exceptions.TaskIndexOutOfBoundsException;
import chattpg.model.Deadline;
import chattpg.model.Event;
import chattpg.model.Task;
import chattpg.model.Todo;
import chattpg.storage.Storage;

import java.util.ArrayList;

public class TaskActions {
    private final ArrayList<Task> tasks;
    private final Storage storage;
    private final String lineSep;
    private boolean loaded = false;

    public TaskActions(ArrayList<Task> tasks, Storage storage, String lineSep) {
        this.tasks = tasks;
        this.storage = storage;
        this.lineSep = lineSep;
    }

    public void loadFromFile() {
        if (!loaded) {
            tasks.clear();
            tasks.addAll(storage.load());
            System.out.printf("Loaded %d tasks from file.%n", tasks.size());
            loaded = true;
        }
        printNumberOfTasks();
    }

    public void saveToFile() {
        storage.save(tasks);
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks in your list.");
            System.out.println(lineSep);
            return;
        }
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i).toString());
        }
        System.out.println(lineSep);
    }

    public void addTask(String description) throws InvalidCommandException {
        if (description.startsWith("deadline")) {
            description = description.substring("deadline".length()).trim();
            String[] parts = description.split(" /by ", 2);
            if (parts.length < 2) {
                throw new InvalidCommandException("deadline format: deadline <desc> /by <when>" + System.lineSeparator() + lineSep);
            }
            tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
        } else if (description.startsWith("event")) {
            description = description.substring("event".length()).trim();
            int fromPos = description.indexOf(" /from ");
            int toPos = description.indexOf(" /to ");
            if (fromPos == -1 || toPos == -1 || fromPos > toPos) {
                throw new InvalidCommandException("event format: event <desc> /from <start> /to <end>" + System.lineSeparator() + lineSep);
            }
            String desc = description.substring(0, fromPos).trim();
            String start = description.substring(fromPos + 7, toPos).trim();
            String end = description.substring(toPos + 5).trim();
            if (desc.isEmpty() || start.isEmpty() || end.isEmpty()) {
                throw new InvalidCommandException("event parts must not be empty." + System.lineSeparator() + lineSep);
            }
            tasks.add(new Event(desc, start, end));
        } else if (description.startsWith("todo")) {
            String desc = description.substring("todo".length()).trim();
            if (desc.isEmpty()) {
                throw new InvalidCommandException("todo requires a description." + System.lineSeparator() + lineSep);
            }
            tasks.add(new Todo(desc));
        } else {
            throw new InvalidCommandException("Unknown command. Type help for available commands." + System.lineSeparator() + lineSep);
        }
        taskAdded();
        saveToFile();
    }

    public void deleteTask(int taskNumber) throws TaskIndexOutOfBoundsException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new TaskIndexOutOfBoundsException("Task " + taskNumber + " does not exist." + System.lineSeparator() + "You only have " + tasks.size() + " tasks in your list." + System.lineSeparator() + lineSep);
        }
        int taskIndex = taskNumber - 1;
        System.out.printf("Noted. I've removed task number %d. The following is the name of the task: \n", taskNumber);
        System.out.println(lineSep);
        System.out.println("  " + tasks.get(taskIndex));
        tasks.remove(taskIndex);
        System.out.println(lineSep);
        printNumberOfTasks();
        saveToFile();
    }

    public void markDone(int taskNumber) throws TaskIndexOutOfBoundsException, IllegalStateException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new TaskIndexOutOfBoundsException("Task " + taskNumber + " does not exist." + System.lineSeparator() + "You only have " + tasks.size() + " tasks in your list." + System.lineSeparator() + lineSep);
        }
        tasks.get(taskNumber - 1).markTaskAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(lineSep);
        System.out.println("\t" + tasks.get(taskNumber - 1));
        saveToFile();
    }

    public void markUndone(int taskNumber) throws TaskIndexOutOfBoundsException, IllegalStateException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new TaskIndexOutOfBoundsException("Task " + taskNumber + " does not exist." + System.lineSeparator() + "You only have " + tasks.size() + " tasks in your list." + System.lineSeparator() + lineSep);
        }
        tasks.get(taskNumber - 1).markTaskAsUndone();
        System.out.println("Nice! I've marked this task as undone:");
        System.out.println(lineSep);
        System.out.println("\t" + tasks.get(taskNumber - 1));
        saveToFile();
    }

    public void findTask(String keyword) throws InvalidCommandException{
            String trimmed = keyword == null ? "" : keyword.trim();
        // Must be exactly one non-whitespace token
        if (!trimmed.matches("\\S+")) {
            throw new InvalidCommandException("Keyword must be a single word." + System.lineSeparator() + lineSep);
        }
        System.out.println("Here are the matching tasks in your list: ");
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().toLowerCase().contains(trimmed.toLowerCase())) {
                System.out.println("\t" + (i + 1) + ". " + tasks.get(i).toString());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("\tNo matching tasks found.");
        }
        System.out.println(lineSep);
    }

    private void taskAdded() {
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t  " + tasks.get(tasks.size() - 1).toString());
        System.out.println(lineSep);
        printNumberOfTasks();
    }

    private void printNumberOfTasks() {
        if (tasks.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
        }
        System.out.println(lineSep);
    }
}