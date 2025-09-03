import java.util.Scanner;

public class ChatTPG {

    static String MAIN_MENU = "********************************************\r\n" + //
                "*                                          *\r\n" + //
                "               MAIN MENU              \r\n" + //
                "*                                          *\r\n" + //
                "********************************************\r\n" + //
                "";
    public static void main(String[] args) {
        System.out.println("Hello! I'm ChatTPG. What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        Task task = new Task();
        TaskOrganiser organiser = new TaskOrganiser(scanner, task);

        while (true) {
            System.out.println(MAIN_MENU);
            System.out.println("open task organiser\nbye (to exit)");
            String input = scanner.nextLine();
            switch (input) {
            case "open task organiser":
                organiser.run();
                break;
            case "bye":
                break;
            default:
                System.out.println("Unknown command. Please try again.");
            }
            if (input.equals("bye")) {
                break;
            }
        }
        System.out.println("\tYou shut me down... how sad. Maybe we will meet again...");
    }
}

