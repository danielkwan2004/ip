package chattpg.logic;

import chattpg.model.Deadline;
import chattpg.model.Event;
import chattpg.model.Task;
import chattpg.model.Todo;
import java.util.Scanner;
import java.util.ArrayList;

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
        System.out.println(LINE);
    }

    public void printWelcomeMessage() {
        System.out.println(TASK_ORGANISER_BANNER);
        System.out.println("Welcome to Task Organiser!");
        System.out.println("You can manage your tasks here.");
        System.out.println(LINE);
        printAvailableCommands();
        System.out.println("Please enter your command:");
        System.out.println(LINE);
    }


    public void deleteTask(int taskNumber) throws TaskIndexOutOfBoundsException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new TaskIndexOutOfBoundsException("Task " + taskNumber + " does not exist." + System.lineSeparator() + "You only have " + tasks.size() + " tasks in your list." + System.lineSeparator() + LINE);
        }
        int taskIndex = taskNumber - 1;
        System.out.printf("Noted. I've removed task number %d. The following is the name of the task: \n", taskNumber);
        System.out.println(LINE);
        System.out.println("  " + tasks.get(taskIndex));
        tasks.remove(taskIndex);
        System.out.println(LINE);
        System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
        System.out.println(LINE);
    }

    public void addTask(String description) throws TaskListFullException, InvalidCommandException, TaskIndexOutOfBoundsException {
        if (description.startsWith("deadline")) {
            String[] parts = description.split(" /by ", 2);
            if (parts.length < 2) {
                throw new InvalidCommandException("deadline format: deadline <desc> /by <when>" + System.lineSeparator() + LINE);
            }
            tasks.add(new Deadline(parts[0].trim(), parts[1].trim()));
        } else if (description.startsWith("event")) {
            int fromPos = description.indexOf(" /from ");
            int toPos = description.indexOf(" /to ");
            if (fromPos == -1 || toPos == -1 || fromPos > toPos) {
                throw new InvalidCommandException("event format: event <desc> /from <start> /to <end>" + System.lineSeparator() + LINE);
            }
            String desc = description.substring(0, fromPos).trim();
            String start = description.substring(fromPos + 7, toPos).trim();
            String end = description.substring(toPos + 5).trim();
            if (desc.isEmpty() || start.isEmpty() || end.isEmpty()) {
                throw new InvalidCommandException("event parts must not be empty." + System.lineSeparator() + LINE);
            }
            tasks.add(new Event(desc, start, end));
        } else if (description.startsWith("todo")) {
            String desc = description.substring("todo".length()).trim();
            if (desc.isEmpty()) {
                throw new InvalidCommandException("todo requires a description." + System.lineSeparator() + LINE);
            }
            tasks.add(new Todo(desc));
        } else {
            throw new InvalidCommandException("Unknown command. Type help for available commands." + System.lineSeparator() + LINE);
        }
        taskAdded();
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks in your list.");
            System.out.println(LINE);
            return;
        }
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i).toString());
        }
        System.out.println(LINE);
    }

    public void taskAdded() {
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t  " + tasks.get(tasks.size() - 1).toString());
        System.out.printf("\tNow you have %d tasks in the list.%n", tasks.size());
        System.out.println(LINE);
    }

    public void run() {
        printWelcomeMessage();
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
                        if (doneTaskNumber < 1 || doneTaskNumber > tasks.size()) {
                            throw new TaskIndexOutOfBoundsException("Task " + doneTaskNumber + " does not exist.\nYou only have " + tasks.size() + " tasks in your list." + System.lineSeparator() + LINE);
                        }
                        tasks.get(doneTaskNumber - 1).markTaskAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(LINE);
                        System.out.println("\t" + tasks.get(doneTaskNumber - 1));
                        break;
                    case "mark undone":
                        System.out.println("Enter the task number you want to mark as undone: ");
                        int undoneTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                        System.out.println(LINE);
                        if (undoneTaskNumber < 1 || undoneTaskNumber > tasks.size()) {
                            throw new TaskIndexOutOfBoundsException("Task " + undoneTaskNumber + " does not exist.\nYou only have " + tasks.size() + " tasks in your list." + System.lineSeparator() + LINE);
                        }
                        tasks.get(undoneTaskNumber - 1).markTaskAsUndone();
                        System.out.println("Nice! I've marked this task as undone:");
                        System.out.println(LINE);
                        System.out.println("\t" + tasks.get(undoneTaskNumber - 1));
                        break;
                    case "delete task":
                        System.out.println("Enter the task number you want to delete: ");
                        int deleteTaskNumber = Integer.parseInt(scanner.nextLine().trim());
                        System.out.println(LINE);
                        if (deleteTaskNumber < 1 || deleteTaskNumber > tasks.size()) {
                            throw new TaskIndexOutOfBoundsException("Task " + deleteTaskNumber + " does not exist.\nYou only have " + tasks.size() + " tasks in your list." + System.lineSeparator() + LINE);
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
                        System.out.println(LINE);
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
