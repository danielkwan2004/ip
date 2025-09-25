package chattpg.logic;

import chattpg.logic.exceptions.InvalidCommandException;
import chattpg.logic.exceptions.TaskIndexOutOfBoundsException;
import chattpg.model.Task;
import chattpg.storage.Storage;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console controller for the Task Organiser. This class orchestrates the
 * interaction loop, delegates task operations to {@link TaskActions}, and
 * handles user prompts, error messages, and simple menu printing.
 */
public class TaskOrganiser {
    private static final String TASK_ORGANISER_BANNER = """
    ********************************************
    *                                          *
    *             TASK ORGANISER               *
    *                                          *
    ********************************************
    """;

    private static final String LINE = "---------------------------------------------";
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Scanner scanner;
    private final Storage storage = new Storage("tasks/tasks.txt");
    private final TaskActions actions = new TaskActions(tasks, storage, LINE);

    /**
     * Creates a TaskOrganiser bound to the provided input scanner.
     *
     * @param scanner shared scanner reading from standard input
     */
    public TaskOrganiser(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Loads persisted tasks on startup (idempotent via TaskActions). */
    public void loadTasksFromFile() {
        actions.loadFromFile();
    }
    
    /** Saves tasks to storage explicitly (normally autosaved on changes). */
    public void saveTasksToFile() {
        actions.saveToFile();
    }
    
    /** Prints the generic prompt to enter the next command. */
    public void printEnterCommand() {
        System.out.println("Please enter your command:");
        System.out.println(LINE);
    }

    /** Prints available commands for quick reference. */
    public void printAvailableCommands() {
        System.out.println("Available commands:");
        System.out.println("  - Add a task: todo <desc>, deadline <desc> /by <when>, event <desc> /from <start> /to <end>");
        System.out.println("  - Mark task as done: mark done");
        System.out.println("  - Mark task as undone: mark undone");
        System.out.println("  - Delete a task: delete task");
        System.out.println("  - List all tasks: list");
        System.out.println("  - Exit Task Organiser: exit task organiser or bye");
        System.out.println(LINE);
    }

    /** Prints the banner, welcome copy, and the command list. */
    public void printWelcomeMessage() {
        System.out.println(TASK_ORGANISER_BANNER);
        System.out.println("Welcome to Task Organiser!");
        System.out.println("You can manage your tasks here.");
        System.out.println(LINE);
        printAvailableCommands();
    }

    /**
     * Deletes a task by its 1-based index and re-prompts the user.
     *
     * @param taskNumber the 1-based index of the task to delete
     * @throws TaskIndexOutOfBoundsException if the index is invalid
     */
    public void deleteTask(int taskNumber) throws TaskIndexOutOfBoundsException {
        actions.deleteTask(taskNumber);
        printEnterCommand();
    }

    /**
     * Adds a task based on the raw input and re-prompts the user.
     *
     * @param description raw command text (e.g., "todo read book")
     * @throws InvalidCommandException if the command is unknown or malformed
     */
    public void addTask(String description) throws InvalidCommandException {
        actions.addTask(description);
        printEnterCommand();
    }

    /** Lists all tasks and re-prompts the user. */
    public void listTasks() {
        actions.listTasks();
        printEnterCommand();
    }

    /**
     * Finds tasks whose description contains the single-word keyword and
     * re-prompts the user.
     *
     * @param keyword a single, non-whitespace token to search for
     * @throws InvalidCommandException if the keyword is empty or contains whitespace
     */
    public void findTask(String keyword) throws InvalidCommandException {
        actions.findTask(keyword);
        printEnterCommand();
    }

    /** Hook retained for parity; actions already prints added-task feedback. */
    public void taskAdded() { /* delegated in TaskActions */ }

    /** Main interaction loop for the Task Organiser submenu. */
    public void run() {
        printWelcomeMessage();
        loadTasksFromFile();
        printEnterCommand();

        while (true) {
            final String userInput = scanner.nextLine().trim();
            System.out.println(LINE);
            try {
                switch (userInput) {
                case "help":
                    printAvailableCommands();
                    break;
                case "mark done":
                    System.out.println("Enter the task number you want to mark as done: ");
                    int doneTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println(LINE);
                    actions.markDone(doneTaskNumber);
                    printEnterCommand();
                    break;
                case "mark undone":
                    System.out.println("Enter the task number you want to mark as undone: ");
                    int undoneTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println(LINE);
                    actions.markUndone(undoneTaskNumber);
                    printEnterCommand();
                    break;
                case "delete task":
                    System.out.println("Enter the task number you want to delete: ");
                    System.out.println(LINE);
                    deleteTask(Integer.parseInt(scanner.nextLine().trim()));
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit task organiser":
                case "bye":
                    System.out.println("Exiting Task Organiser. Returning to main menu...");
                    System.out.println("Type bye again to exit the whole program.");
                    System.out.println(LINE);
                    return;
                case "find":
                    System.out.println("Enter a single keyword to show a list of tasks that has that keyword in the description");
                    String keyword = scanner.nextLine().trim();
                    System.out.println(LINE);
                    findTask(keyword);
                    break;
                default:
                    addTask(userInput);
                    break;
                }
            } catch (TaskIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (InvalidCommandException e) {
                System.out.println("Invalid command: " + e.getMessage());
                printEnterCommand();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IllegalStateException e) {
                System.out.println("Operation not allowed: " + e.getMessage());
            }
        }
    }
}
