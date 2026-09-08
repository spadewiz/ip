package wiz;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Main application class for the Wiz task manager.
 */
public class Wiz {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final String DEFAULT_FILE_PATH =
            "." + File.separator + "data" + File.separator + "wiz.txt";

    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /**
     * Initializes the Wiz application with the default storage file path.
     */
    public Wiz() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Initializes the Wiz application with the specified storage file path.
     *
     * @param path The path to the file where tasks are saved.
     */
    public Wiz(String path) {
        assert path != null && !path.isBlank() : "File path cannot be null or blank";
        ui = new Ui();
        storage = new Storage(path);
        assert ui != null : "Ui instance should be initialized";
        assert storage != null : "Storage instance should be initialized";

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Oops! I couldn't load your saved tasks.");
            tasks = new TaskList();
        }
        assert tasks != null : "TaskList should not be null after initialization";
    }

    /**
     * Runs the main application loop, reading and processing user commands.
     */
    public void run() {
        assert ui != null : "Ui must be initialized before running";
        ui.showWelcome();

        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();
            String response = getResponse(input);
            ui.showMessage(response);

            if (Parser.getCommandWord(input).equals("bye")) {
                isRunning = false;
            }
        }

        ui.close();
    }

    /**
     * Validates that the task index is within the valid range of the task list.
     *
     * @param index The zero-based task index.
     * @throws WizException If the index is out of bounds.
     */
    private void checkIndex(int index) throws WizException {
        if (index < 0 || index >= tasks.size()) {
            throw new WizException(
                    "Oops! That task number does not exist."
            );
        }
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input The user input command.
     * @return The response message as a String.
     */
    public String getResponse(String input) {
        if (input == null || input.isBlank()) {
            return "Please enter a valid command.";
        }
        try {
            return executeCommand(input);
        } catch (WizException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Oops! Please give me a valid task number.";
        } catch (DateTimeParseException e) {
            return "Oops! Please use yyyy-MM-dd HHmm.";
        } catch (IOException e) {
            return "Oops! I couldn't save your tasks.";
        }
    }

    /**
     * Dispatches and executes the parsed command.
     *
     * @param input The raw user input command.
     * @return The result message after execution.
     * @throws WizException If command format or validation fails.
     * @throws IOException If saving tasks fails.
     */
    private String executeCommand(String input) throws WizException, IOException {
        assert input != null : "Input command cannot be null";
        String command = Parser.getCommandWord(input);
        String arguments = Parser.getArguments(input);

        switch (command) {
        case "bye":
            return handleBye();
        case "list":
            return handleList();
        case "todo":
            return handleTodo(arguments);
        case "deadline":
            return handleDeadline(arguments);
        case "event":
            return handleEvent(arguments);
        case "mark":
            return handleMark(arguments);
        case "unmark":
            return handleUnmark(arguments);
        case "delete":
            return handleDelete(arguments);
        case "find":
            return handleFind(arguments);
        default:
            throw new WizException("Oops! I don't know what that command means.");
        }
    }

    private String handleBye() {
        return "Bye. Hope to see you again soon!";
    }

    private String handleList() {
        if (tasks.size() == 0) {
            return "Here are the tasks in your list:";
        }
        return "Here are the tasks in your list:\n"
                + IntStream.range(0, tasks.size())
                        .mapToObj(i -> (i + 1) + "." + tasks.get(i))
                        .collect(Collectors.joining("\n"));
    }

    private String handleTodo(String arguments) throws WizException, IOException {
        if (arguments.isBlank()) {
            throw new WizException("Oops! A todo needs a description.");
        }
        Task task = new ToDo(arguments);
        tasks.add(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleDeadline(String arguments) throws WizException, IOException {
        int byIndex = arguments.indexOf(" /by ");
        if (byIndex == -1) {
            throw new WizException("Oops! Use /by for a deadline.");
        }
        String description = arguments.substring(0, byIndex);
        String byString = arguments.substring(byIndex + 5);
        LocalDateTime by = LocalDateTime.parse(byString, DATE_FORMAT);
        Task task = new Deadline(description, by);
        tasks.add(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleEvent(String arguments) throws WizException, IOException {
        int fromIndex = arguments.indexOf(" /from ");
        int toIndex = arguments.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1) {
            throw new WizException("Oops! Use /from and /to for an event.");
        }
        String description = arguments.substring(0, fromIndex);
        String fromString = arguments.substring(fromIndex + 7, toIndex);
        String toString = arguments.substring(toIndex + 5);
        LocalDateTime from = LocalDateTime.parse(fromString, DATE_FORMAT);
        LocalDateTime to = LocalDateTime.parse(toString, DATE_FORMAT);
        Task task = new Event(description, from, to);
        tasks.add(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleMark(String arguments) throws WizException, IOException {
        int index = Integer.parseInt(arguments.trim()) - 1;
        checkIndex(index);
        tasks.get(index).markAsDone();
        storage.save(tasks.getTasks());
        return "Nice! I've marked this task as done:\n  " + tasks.get(index);
    }

    private String handleUnmark(String arguments) throws WizException, IOException {
        int index = Integer.parseInt(arguments.trim()) - 1;
        checkIndex(index);
        tasks.get(index).markAsNotDone();
        storage.save(tasks.getTasks());
        return "OK, I've marked this task as not done yet:\n  " + tasks.get(index);
    }

    private String handleDelete(String arguments) throws WizException, IOException {
        int index = Integer.parseInt(arguments.trim()) - 1;
        checkIndex(index);
        Task removedTask = tasks.delete(index);
        storage.save(tasks.getTasks());
        return "Noted. I've removed this task:\n  " + removedTask
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    private String handleFind(String arguments) throws WizException {
        if (arguments.isBlank()) {
            throw new WizException("Oops! A find command needs a keyword.");
        }
        ArrayList<Task> matchingTasks = tasks.find(arguments.trim());
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found in your list.";
        }
        return "Here are the matching tasks in your list:\n"
                + IntStream.range(0, matchingTasks.size())
                        .mapToObj(i -> (i + 1) + "." + matchingTasks.get(i))
                        .collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        String path =
                "." + File.separator
                        + "data" + File.separator
                        + "wiz.txt";

        new Wiz(path).run();
    }
}