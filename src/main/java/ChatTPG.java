import java.util.*;

public class ChatTPG {
    public static void main(String[] args) {
        System.out.println("Hello! I'm ChatTPG");
        System.out.println("What can I do for you?");
        
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        
        Task task = new Task();

        while (!userInput.equals("bye")) {
            userInput = scanner.nextLine();
            if (userInput.equals("list")) {
                task.listTask();
                continue;
            }
            if (userInput.equals("bye")) {
                break;
            }
            task.addTask(userInput);
        }
        System.out.println("\tYou shut me down... how sad. Maybe we will meet again...");
    }
}

