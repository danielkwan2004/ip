package chattpg.ui;

import chattpg.logic.TaskOrganiser;
import java.util.Scanner;


public class ChatTPG {

    private static final String MAIN_MENU = """
    ********************************************
    *                                          *
    *                MAIN MENU                 *
    *                                          *
    ********************************************
    """;
    private static final String LINE = "---------------------------------------------";

    private static void printMainMenu() {
        System.out.println(MAIN_MENU);
        System.out.println("➣ open task organiser");
        System.out.println("➣ bye (to exit)");
    }

    private static void greetUser() {
        System.out.println("Hello! I'm ChatTPG.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public static void main(String[] args) {
        greetUser();
        try (Scanner scanner = new Scanner(System.in)) {
            TaskOrganiser organiser = new TaskOrganiser(scanner);
            while (true) {
                printMainMenu();
                final String input = scanner.nextLine().trim().toLowerCase();
                switch (input) {
                case "open task organiser":
                    try {
                        organiser.run(); // catch unexpected runtime errors from the organiser
                    } catch (RuntimeException ex) {
                        System.out.println("Something went wrong in Task Organiser: " + ex.getMessage());
                    }
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

