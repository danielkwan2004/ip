package chattpg.logic;

import chattpg.logic.exceptions.InvalidCommandException;
import chattpg.logic.exceptions.TaskIndexOutOfBoundsException;
import chattpg.model.Task;
import chattpg.storage.Storage;
import java.util.ArrayList;
import java.util.Scanner;

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

    public TaskOrganiser(Scanner scanner) {
        this.scanner = scanner;
    }

    public void loadTasksFromFile() {
        actions.loadFromFile();
    }
    
    public void saveTasksToFile() {
        actions.saveToFile();
    }
    
    public void printEnterCommand() {
        System.out.println("Please enter your command:");
        System.out.println(LINE);
    }

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

    public void printWelcomeMessage() {
        System.out.println(TASK_ORGANISER_BANNER);
        System.out.println("Welcome to Task Organiser!");
        System.out.println("You can manage your tasks here.");
        System.out.println(LINE);
        printAvailableCommands();
    }


    public void deleteTask(int taskNumber) throws TaskIndexOutOfBoundsException {
        actions.deleteTask(taskNumber);
        printEnterCommand();
    }

    public void addTask(String description) throws InvalidCommandException {
        actions.addTask(description);
        printEnterCommand();
    }

    public void listTasks() {
        actions.listTasks();
        printEnterCommand();
    }

    public void taskAdded() { /* delegated in TaskActions */ }

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
                        int deleteTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                        System.out.println(LINE);
                        deleteTask(deleteTaskNumber);
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
                    default:
                        addTask(userInput);
                        break;
                }
            } catch (TaskIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (InvalidCommandException e) {
                System.out.println("Invalid command: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IllegalStateException e) {
                System.out.println("Operation not allowed: " + e.getMessage());
            }
        }
    }
}
