package wiz;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Main application class for the Wiz task manager.
 */
public class Wiz {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /**
     * Initializes the Wiz application with the specified storage file path.
     *
     * @param path The path to the file where tasks are saved.
     */
    public Wiz(String path) {
        ui = new Ui();
        storage = new Storage(path);

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Oops! I couldn't load your saved tasks.");
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main application loop, reading and processing user commands.
     */
    public void run() {
        ui.showWelcome();

        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();

            try {
                String command = Parser.getCommandWord(input);
                String arguments = Parser.getArguments(input);

                if (command.equals("bye")) {
                    ui.showGoodbye();
                    isRunning = false;

                } else if (command.equals("list")) {
                    ui.showMessage("Here are the tasks in your list:");

                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showMessage((i + 1) + "." + tasks.get(i));
                    }

                } else if (command.equals("todo")) {
                    if (arguments.isBlank()) {
                        throw new WizException(
                                "Oops! A todo needs a description."
                        );
                    }

                    Task task = new ToDo(arguments);
                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showMessage("Got it. I've added this task:");
                    ui.showMessage("  " + task);
                    ui.showMessage(
                            "Now you have " + tasks.size()
                                    + " tasks in the list."
                    );

                } else if (command.equals("deadline")) {
                    int byIndex = arguments.indexOf(" /by ");

                    if (byIndex == -1) {
                        throw new WizException(
                                "Oops! Use /by for a deadline."
                        );
                    }

                    String description =
                            arguments.substring(0, byIndex);

                    String byString =
                            arguments.substring(byIndex + 5);

                    LocalDateTime by =
                            LocalDateTime.parse(
                                    byString,
                                    DATE_FORMAT
                            );

                    Task task =
                            new Deadline(description, by);

                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showMessage("Got it. I've added this task:");
                    ui.showMessage("  " + task);

                } else if (command.equals("event")) {
                    int fromIndex =
                            arguments.indexOf(" /from ");

                    int toIndex =
                            arguments.indexOf(" /to ");

                    if (fromIndex == -1 || toIndex == -1) {
                        throw new WizException(
                                "Oops! Use /from and /to for an event."
                        );
                    }

                    String description =
                            arguments.substring(0, fromIndex);

                    String fromString =
                            arguments.substring(
                                    fromIndex + 7,
                                    toIndex
                            );

                    String toString =
                            arguments.substring(toIndex + 5);

                    LocalDateTime from =
                            LocalDateTime.parse(
                                    fromString,
                                    DATE_FORMAT
                            );

                    LocalDateTime to =
                            LocalDateTime.parse(
                                    toString,
                                    DATE_FORMAT
                            );

                    Task task =
                            new Event(description, from, to);

                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showMessage("Got it. I've added this task:");
                    ui.showMessage("  " + task);

                } else if (command.equals("mark")) {
                    int index =
                            Integer.parseInt(arguments) - 1;

                    checkIndex(index);

                    tasks.get(index).markAsDone();
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Nice! I've marked this task as done:"
                    );
                    ui.showMessage("  " + tasks.get(index));

                } else if (command.equals("unmark")) {
                    int index =
                            Integer.parseInt(arguments) - 1;

                    checkIndex(index);

                    tasks.get(index).markAsNotDone();
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "OK, I've marked this task as not done yet:"
                    );
                    ui.showMessage("  " + tasks.get(index));

                } else if (command.equals("delete")) {
                    int index =
                            Integer.parseInt(arguments) - 1;

                    checkIndex(index);

                    Task removedTask = tasks.delete(index);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Noted. I've removed this task:"
                    );
                    ui.showMessage("  " + removedTask);
                    ui.showMessage(
                            "Now you have " + tasks.size()
                                    + " tasks in the list."
                    );

                } else if (command.equals("find")) {
                    if (arguments.isBlank()) {
                        throw new WizException(
                                "Oops! A find command needs a keyword."
                        );
                    }

                    ui.showMessage("Here are the matching tasks in your list:");
                    java.util.ArrayList<Task> foundTasks = tasks.find(arguments.trim());
                    for (int i = 0; i < foundTasks.size(); i++) {
                        ui.showMessage((i + 1) + "." + foundTasks.get(i));
                    }

                } else {
                    throw new WizException(
                            "Oops! I don't know what that command means."
                    );
                }

            } catch (WizException e) {
                ui.showError(e.getMessage());

            } catch (NumberFormatException e) {
                ui.showError(
                        "Oops! Please give me a valid task number."
                );

            } catch (DateTimeParseException e) {
                ui.showError(
                        "Oops! Please use yyyy-MM-dd HHmm."
                );

            } catch (IOException e) {
                ui.showError(
                        "Oops! I couldn't save your tasks."
                );
            }
        }

        ui.close();
    }

    private void checkIndex(int index) throws WizException {
        if (index < 0 || index >= tasks.size()) {
            throw new WizException(
                    "Oops! That task number does not exist."
            );
        }
    }

    public static void main(String[] args) {
        String path =
                "." + File.separator
                        + "data" + File.separator
                        + "wiz.txt";

        new Wiz(path).run();
    }
}