package chattpg.logic;

import chattpg.model.Deadline;
import chattpg.model.Event;
import chattpg.model.Task;
import chattpg.model.Todo;
import java.util.Scanner;

public class TaskOrganiser {
    private static final int MAX_TASKS = 100;
    private final Task[] tasks = new Task[MAX_TASKS];
    private final Scanner scanner;
    private int taskCount = 0;

    public TaskOrganiser(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printAvailableCommands() {
        System.out.println("Available commands:");
        System.out.println("  - Add a task: todo <desc>, deadline <desc> /by <when>, event <desc> /from <start> /to <end>");
        System.out.println("  - Mark task as done: mark done");
        System.out.println("  - Mark task as undone: mark undone");
        System.out.println("  - Delete a task: delete task");
        System.out.println("  - List all tasks: list");
        System.out.println("  - Exit Task Organiser: exit task organiser or bye");
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to Task Organiser!");
        System.out.println("You can manage your tasks here.");
        printAvailableCommands();
        System.out.println("Please enter your command:");
    }


    public void deleteTask(int taskNumber) throws TaskIndexOutOfBoundsException {
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new TaskIndexOutOfBoundsException("Task " + taskNumber + " does not exist.");
        }
        int taskIndex = taskNumber - 1;
        System.out.printf("Noted. I've removed task number %d. The following is the name of the task: \n", taskNumber);
        System.out.println("  " + tasks[taskIndex]);
        for (int i = taskIndex; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[taskCount - 1] = null; // Clear the last element
        taskCount--;
    }

    public void addTask(String description) throws TaskListFullException, InvalidCommandException, TaskIndexOutOfBoundsException {
        if (taskCount >= MAX_TASKS) {
            throw new TaskListFullException("Task list is full. Cannot add more tasks.");
        } else if (description.startsWith("deadline")) {
            String[] parts = description.split(" /by ", 2);
            if (parts.length < 2) {
                throw new InvalidCommandException("deadline format: deadline <desc> /by <when>");
            }
            tasks[taskCount] = new Deadline(parts[0].trim(), parts[1].trim());
        } else if (description.startsWith("event")) {
            int fromPos = description.indexOf(" /from ");
            int toPos = description.indexOf(" /to ");
            if (fromPos == -1 || toPos == -1 || fromPos > toPos) {
                throw new InvalidCommandException("event format: event <desc> /from <start> /to <end>");
            }
            String desc = description.substring(0, fromPos).trim();
            String start = description.substring(fromPos + 7, toPos).trim();
            String end = description.substring(toPos + 5).trim();
            if (desc.isEmpty() || start.isEmpty() || end.isEmpty()) {
                throw new InvalidCommandException("event parts must not be empty.");
            }
            tasks[taskCount] = new Event(desc, start, end);
        } else if (description.startsWith("todo")) {
            String desc = description.substring("todo".length()).trim();
            if (desc.isEmpty()) {
                throw new InvalidCommandException("todo requires a description.");
            }
            tasks[taskCount] = new Todo(desc);
        } else {
            throw new InvalidCommandException("Unknown command. Type help for available commands.");
        }
        taskCount++;
        taskAdded();
    }

    public void listTasks() {
        if (taskCount == 0) {
            System.out.println("You have no tasks in your list.");
            return;
        }
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks[i].toString());
        }
    }

    public void taskAdded() {
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t  " + tasks[taskCount - 1].toString());
        System.out.printf("\tNow you have %d tasks in the list.%n", taskCount);
    }

    public void run() {
        printWelcomeMessage();
        
        while (true) {
            final String userInput = scanner.nextLine().trim();
            try {
                switch (userInput) {
                    case "help":
                        printAvailableCommands();
                        break;
                    case "mark done":
                        System.out.println("Enter the task number you want to mark as done: ");
                        int doneTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                        if (doneTaskNumber < 1 || doneTaskNumber > taskCount) {
                            throw new TaskIndexOutOfBoundsException("Task " + doneTaskNumber + " does not exist.\nYou only have " + taskCount + " tasks in your list.");
                        }
                        tasks[doneTaskNumber - 1].markTaskAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("\t" + tasks[doneTaskNumber - 1]);                        
                        break;
                    case "mark undone":
                        System.out.println("Enter the task number you want to mark as undone: ");
                        int undoneTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                        if (undoneTaskNumber < 1 || undoneTaskNumber > taskCount) {
                            throw new TaskIndexOutOfBoundsException("Task " + undoneTaskNumber + " does not exist.\nYou only have " + taskCount + " tasks in your list.");
                        }
                        tasks[undoneTaskNumber - 1].markTaskAsUndone();
                        System.out.println("Nice! I've marked this task as undone:");
                        System.out.println("\t" + tasks[undoneTaskNumber - 1]);
                        break;
                    case "delete task":
                        System.out.println("Enter the task number you want to delete: ");
                        int deleteTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                        if (deleteTaskNumber < 1 || deleteTaskNumber > taskCount) {
                            throw new TaskIndexOutOfBoundsException("Task " + deleteTaskNumber + " does not exist.\nYou only have " + taskCount + " tasks in your list.");
                        }
                        deleteTask(deleteTaskNumber);
                        break;
                    case "list":
                        listTasks();
                        break;
                    case "exit task organiser":
                    case "bye":
                        System.out.println("Exiting Task Organiser. Returning to main menu...");
                        System.out.println("Type bye again to exit the whole program.");
                        return;
                    default:
                        addTask(userInput);
                        break;
                }
            } catch (TaskListFullException e) {
                System.out.println("Cannot add task: " + e.getMessage());
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
