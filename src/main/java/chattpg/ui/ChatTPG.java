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
        System.out.println("- open task organiser");
        System.out.println("- bye (to exit)");
        System.out.println(LINE);
        System.out.println("Please enter your command:");
        System.out.println(LINE);
    }

    private static void greetUser() {
        System.out.println("Hello! I'm ChatTPG.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    private static void printGoodbye() {
        System.out.println("You shut me down... how sad. Maybe we will meet again...");
        System.out.println(LINE);
    }

    private static void printUnknownCommand() {
        System.out.println("Unknown command. Please try again.");
        System.out.println(LINE);
    }

    public static void main(String[] args) {
        greetUser();
        printMainMenu();
        try (Scanner scanner = new Scanner(System.in)) {
            TaskOrganiser organiser = new TaskOrganiser(scanner);
            while (true) {
                final String input = scanner.nextLine().trim().toLowerCase();
                System.out.println(LINE);
                switch (input) {
                case "open task organiser":
                    try {
                        System.out.println(LINE);
                        organiser.run(); // catch unexpected runtime errors from the organiser
                        printMainMenu();
                    } catch (RuntimeException ex) {
                        System.out.println("Something went wrong in Task Organiser: " + ex.getMessage());
                    }
                    break;
                case "bye":
                    printGoodbye();
                    return;
                default:
                    printUnknownCommand();
                    break;
                }
            }
        }
    }
}

