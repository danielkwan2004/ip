package chattpg.ui;

import chattpg.logic.TaskOrganiser;
import chattpg.logic.exceptions.InvalidCommandException;
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

    private final Scanner scanner;
    private final TaskOrganiser organiser;

    public ChatTPG() {
        this.scanner = new Scanner(System.in);
        this.organiser = new TaskOrganiser(scanner);
    }

    private void printOptions() {
        System.out.println("- open task organiser");
        System.out.println("- bye (to exit)");
        System.out.println(LINE);
        System.out.println("Please enter your command:");
    }

    private void printMainMenu() {
        System.out.println(MAIN_MENU);
        printOptions();
        System.out.println(LINE);
    }

    private void greetUser() {
        System.out.println("Hello! I'm ChatTPG.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    private void printGoodbye() {
        System.out.println("You shut me down... how sad. Maybe we will meet again...");
        System.out.println(LINE);
    }

    private void printUnknownCommand() throws InvalidCommandException {
        throw new InvalidCommandException("Unknown command. Please try again.");
    }

    public void run() {
        greetUser();
        printMainMenu();
        while (true) {
            final String input = scanner.nextLine().trim().toLowerCase();
            System.out.println(LINE);
            try {
                switch (input) {
                case "open task organiser":
                    try {
                        organiser.run();
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
            } catch (InvalidCommandException ice) {
                System.out.println(ice.getMessage());
                printOptions();
                System.out.println(LINE);
            }
        }
    }

    public static void main(String[] args) {
        new ChatTPG().run();
    }
}

