package wiz;

import java.util.Scanner;

/**
 * Handles text-based user interface interactions such as reading input and displaying messages.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a Ui instance with standard system input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message.
     */
    public void showWelcome() {
        showMessage("Hello! I'm Wiz.", "What can I do for you?");
    }

    /**
     * Reads a line of command input from the user.
     *
     * @return The user input string.
     */
    public String readCommand() {
        assert scanner != null : "Scanner must not be null";
        return scanner.nextLine();
    }

    /**
     * Prints one or more messages to the console, each on a new line.
     *
     * @param messages The messages to display.
     */
    public void showMessage(String... messages) {
        assert messages != null : "Messages array cannot be null";
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Prints one or more error messages to the console, each on a new line.
     *
     * @param messages The error messages to display.
     */
    public void showError(String... messages) {
        assert messages != null : "Error messages array cannot be null";
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Closes the scanner resource.
     */
    public void close() {
        scanner.close();
    }
}