import java.util.Scanner;

public class TaskOrganiser {
    static final int MAX_TASKS = 100;
    Task[] tasks = new Task[MAX_TASKS];
    private Scanner scanner;
    private int taskCount = 0;

    public TaskOrganiser(Scanner scanner) {
        this.scanner = scanner;
    }

    public void deleteTask(int taskNumber) {
        if (taskNumber < 0 || taskNumber >= taskCount) {
            System.out.println("You have gone out of bounds. This task number does not exist.");
            return;
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

    public void addTask(String description) {
        if (description.startsWith("todo")) {
            tasks[taskCount] = new Todo(description);
        } else if (description.startsWith("deadline")) {
            String[] parts = description.split(" /by ");
            tasks[taskCount] = new Deadline(parts[0], parts[1]);
        } else if (description.startsWith("event")) {
            String[] parts = description.split(" /from | /to ");
            tasks[taskCount] = new Events(parts[0], parts[1], parts[2]);
        } else {
            System.out.println("Unknown task type. Please use 'todo', 'deadline', or 'event'.");
            return;
        }
        taskCount++;
        taskAdded();
    }

    public void listTask() {
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < taskCount; i++) {
            // add some if statement to check if task is done or not
            System.out.println("\t" + (i + 1) + ". " + tasks[i].toString());
        }
    }

    public void taskAdded() {
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t  " + tasks[taskCount - 1].toString());
        System.out.printf("\tNow you have %d tasks in the list.\n", taskCount);
    }

    public void run() {
        System.out.println("Welcome to Task Organiser!");
        System.out.println("You can add, delete, or modify tasks.");
        

        String userInput = "";
        while (true) {
            userInput = scanner.nextLine();
            switch (userInput) {
                case "mark done":
                    System.out.println("Enter the task number you want to mark as done: ");
                    int doneTaskNumber = scanner.nextInt();
                    scanner.nextLine();
                    if (doneTaskNumber < 1 || doneTaskNumber > taskCount) {
                        System.out.println("You have gone out of bounds. This task number does not exist.");
                        break;
                    }
                    tasks[doneTaskNumber - 1].markTaskAsDone();
                    break;
                case "mark undone":
                    System.out.println("Enter the task number you want to mark as undone: ");
                    int undoneTaskNumber = scanner.nextInt();
                    scanner.nextLine();
                    if (undoneTaskNumber < 1 || undoneTaskNumber > taskCount) {
                        System.out.println("You have gone out of bounds. This task number does not exist.");
                        break;
                    }
                    tasks[undoneTaskNumber - 1].markTaskAsUndone();
                    break;
                case "delete task":
                    System.out.println("Enter the task number you want to delete: ");
                    int deleteTaskNumber = scanner.nextInt();
                    scanner.nextLine();
                    if (deleteTaskNumber < 1 || deleteTaskNumber > taskCount) {
                        System.out.println("You have gone out of bounds. This task number does not exist.");
                        break;
                    }
                    deleteTask(deleteTaskNumber);
                    break;
                case "list":
                    listTask();
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
        }
    }
}
