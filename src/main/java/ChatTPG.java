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
        TaskOrganiser organiser = new TaskOrganiser(scanner);

        while (true) {
            System.out.println(MAIN_MENU);
            System.out.println("➣ open task organiser\n➣ bye (to exit)");
            String input = scanner.nextLine();
            switch (input) {
            case "open task organiser":
                organiser.run();
                break;
            case "bye":
                break;
            default:
                System.out.println("Unknown command. Please try again.");
                break;
            }
            if (input.equals("bye")) {
                break;
            }
        }
        System.out.println("\tYou shut me down... how sad. Maybe we will meet again...");
    }
}

