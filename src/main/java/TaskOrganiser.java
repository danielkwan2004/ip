import java.util.Scanner;

public class TaskOrganiser {
    private Task task;
    private Scanner scanner;

    public TaskOrganiser(Scanner scanner, Task task) {
        this.scanner = scanner;
        this.task = task;
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
                    task.markTaskAsDone(doneTaskNumber);
                    break;
                case "mark undone":
                    System.out.println("Enter the task number you want to mark as undone: ");
                    int undoneTaskNumber = scanner.nextInt();
                    scanner.nextLine();
                    task.markTaskAsUndone(undoneTaskNumber);
                    break;
                case "delete task":
                    System.out.println("Enter the task number you want to delete: ");
                    int deleteTaskNumber = scanner.nextInt();
                    scanner.nextLine();
                    task.deleteTask(deleteTaskNumber);
                    break;
                case "list":
                    task.listTask();
                    break;
                case "exit task organiser":
                case "bye":
                    System.out.println("Exiting Task Organiser. Returning to main menu...");
                    System.out.println("Type bye again to exit the whole program.");
                    return;
                default:
                    task.addTask(userInput);
                    break;
            }
        }
    }
}
