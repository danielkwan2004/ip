package chattpg.logic;

import chattpg.logic.exceptions.InvalidCommandException;
import chattpg.logic.exceptions.TaskIndexOutOfBoundsException;
import chattpg.model.Deadline;
import chattpg.model.Event;
import chattpg.model.Task;
import chattpg.model.Todo;
import chattpg.storage.Storage;

import java.util.ArrayList;

/**
 * Encapsulates all task-related operations and persistence hooks for the
 * Task Organiser feature. This class is the domain layer that manipulates the
 * in-memory list and delegates save/load to {@link chattpg.storage.Storage}.
 */
public class TaskActions {
    private final ArrayList<Task> tasks;
    private final Storage storage;
    private final String lineSep;
    private boolean loaded = false;

    /**
     * Constructs a new TaskActions facade.
     *
     * @param tasks   the backing in-memory task list managed by this instance
     * @param storage the storage component used to persist tasks
     * @param lineSep the UI separator used for consistent console output
     */
    public TaskActions(ArrayList<Task> tasks, Storage storage, String lineSep) {
        this.tasks = tasks;
        this.storage = storage;
        this.lineSep = lineSep;
    }

    /**
     * Loads tasks from storage into the in-memory list on the first call.
     * Subsequent calls are idempotent and will not reload (avoiding duplication).
     * Always prints the current number of tasks after ensuring the list is loaded.
     */
    public void loadFromFile() {
        if (!loaded) {
            tasks.clear();
            tasks.addAll(storage.load());
            System.out.printf("Loaded %d tasks from file.%n", tasks.size());
            loaded = true;
        }
        printNumberOfTasks();
    }

    /**
     * Persists the current in-memory task list to storage.
     */
    public void saveToFile() {
        storage.save(tasks);
    }

    /**
     * Prints the full list of tasks with 1-based indices. If the list is empty,
     * prints a friendly message instead.
     */
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks in your list.");
            System.out.println(lineSep);
            return;
        }
        System.out.println("Here are the tasks in your list, \"X\" means it is marked done: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i).toString());
        }
        System.out.println(lineSep);
    }

    /**
     * Parses the user input and appends a new task.
     *
     * Supported formats:
     * - todo <desc>
     * - deadline <desc> /by <when>
     * - event <desc> /from <start> /to <end>
     *
     * On success, prints a confirmation and triggers an autosave.
     *
     * @param description the raw user input for the add command
     * @throws InvalidCommandException if the input is malformed or unknown
     */
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
            if (desc.charAt(0) != ' ') {
                throw new InvalidCommandException("todo format: todo <desc>" + System.lineSeparator() + lineSep);
            } else if (desc.isEmpty()) {
                throw new InvalidCommandException("todo requires a description." + System.lineSeparator() + lineSep);
            } else if (desc.contains("/by") || desc.contains("/from") || desc.contains("/to")) {
                throw new InvalidCommandException("todo description must not contain /by, /from, or /to." + System.lineSeparator() + lineSep);
            }
            tasks.add(new Todo(desc));
        } else {
            throw new InvalidCommandException("Unknown command. Type help for available commands." + System.lineSeparator() + lineSep);
        }
        taskAdded();
        saveToFile();
    }

    /**
     * Deletes a task by 1-based index, prints confirmation, and autosaves.
     *
     * @param taskNumber the 1-based index of the task to delete
     * @throws TaskIndexOutOfBoundsException if the index is invalid
     */
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

    /**
     * Marks a task as done by 1-based index and autosaves.
     *
     * @param taskNumber the 1-based index of the task to mark done
     * @throws TaskIndexOutOfBoundsException if the index is invalid
     * @throws IllegalStateException         if the state transition is not allowed
     */
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

    /**
     * Marks a task as not done by 1-based index and autosaves.
     *
     * @param taskNumber the 1-based index of the task to mark undone
     * @throws TaskIndexOutOfBoundsException if the index is invalid
     * @throws IllegalStateException         if the state transition is not allowed
     */
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

    /**
     * Finds and prints tasks whose description contains the given single-word
     * keyword (case-insensitive). Multi-word inputs (containing whitespace)
     * are rejected with an {@link InvalidCommandException}.
     *
     * @param keyword a single token to match within task descriptions
     * @throws InvalidCommandException if the keyword is empty or contains whitespace
     */
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

    /**
     * Prints the confirmation for the most recently added task and the count.
     */
    private void taskAdded() {
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t  " + tasks.get(tasks.size() - 1).toString());
        System.out.println(lineSep);
        printNumberOfTasks();
    }

    /**
     * Prints the current number of tasks in the list with singular/plural form.
     */
    private void printNumberOfTasks() {
        if (tasks.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
        }
        System.out.println(lineSep);
    }
}