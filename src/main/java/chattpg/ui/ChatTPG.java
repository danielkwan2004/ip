package chattpg.ui;

import java.util.Scanner;
import chattpg.logic.TaskOrganiser;


public class ChatTPG {

    private static final String MAIN_MENU = """
    ********************************************
    *                                          *
    *                MAIN MENU                 *
    *                                          *
    ********************************************
    """;

    private static void printMainMenu() {
    System.out.println(MAIN_MENU);
    System.out.println("➣ open task organiser");
    System.out.println("➣ bye (to exit)");
    }

    public static void main(String[] args) {
        System.out.println("Hello! I'm ChatTPG. What can I do for you?");

        try (Scanner scanner = new Scanner(System.in)) {
            TaskOrganiser organiser = new TaskOrganiser(scanner);
            while (true) {
                printMainMenu();
                final String input = scanner.nextLine().trim().toLowerCase();
                switch (input) {
                case "open task organiser":
                    organiser.run();
                    break;
                case "bye":
                    System.out.println("\tYou shut me down... how sad. Maybe we will meet again...");
                    return;
                default:
                    System.out.println("Unknown command. Please try again.");
                    break;
                }
            }
        }
    }
}

