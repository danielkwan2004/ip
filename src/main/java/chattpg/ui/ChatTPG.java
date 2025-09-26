package chattpg.ui;

import chattpg.logic.TaskOrganiser;
import chattpg.logic.exceptions.InvalidCommandException;
import java.util.Scanner;

/**
 * Entry point and main CLI shell for ChatTPG.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Render a simple main menu and read user input</li>
 *   <li>Delegate the "task organiser" flow to {@link TaskOrganiser}</li>
 *   <li>Handle unknown commands via {@link InvalidCommandException}</li>
 * </ul>
 * This class intentionally keeps I/O simple (System.in/System.out) and leaves
 * task management concerns to the organiser module.
 */
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

    /**
     * Constructs a new ChatTPG shell with a shared scanner passed to
     * the Task Organiser so both layers read from the same input stream.
     */
    public ChatTPG() {
        this.scanner = new Scanner(System.in);
        this.organiser = new TaskOrganiser(scanner);
    }

    /**
     * Prints the list of valid top-level commands followed by the prompt.
     */
    private void printOptions() {
        System.out.println("- open task organiser");
        System.out.println("- bye (to exit)");
        System.out.println(LINE);
        System.out.println("Please enter your command:");
    }

    /**
     * Prints the main menu banner and the available options.
     */
    private void printMainMenu() {
        System.out.println(MAIN_MENU);
        printOptions();
        System.out.println(LINE);
    }

    /**
     * Greets the user and prints a horizontal separator.
     */
    private void greetUser() {
        System.out.println("Hello! I'm ChatTPG.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Prints the farewell message before terminating the application.
     */
    private void printGoodbye() {
        System.out.println("You shut me down... how sad. Maybe we will meet again...");
        System.out.println(LINE);
    }

    /**
     * Throws an {@link InvalidCommandException} to signal an unknown command
     * to the caller, which is then caught to display a friendly message
     * and re-print the menu.
     *
     * @throws InvalidCommandException always thrown to indicate the command is not recognized
     */
    private void printUnknownCommand() throws InvalidCommandException {
        throw new InvalidCommandException("Unknown command. Please try again.");
    }

    /**
     * Runs the main interaction loop for ChatTPG. This loop handles the top-level
     * navigation and delegates into the Task Organiser when requested. The loop
     * exits when the user enters "bye".
     */
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

    /**
     * Application entry point.
     *
     * @param args CLI arguments (unused)
     */
    public static void main(String[] args) {
        new ChatTPG().run();
    }
}

