import java.util.ArrayList;
import java.util.Scanner;

public class Wiz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hello! I'm Wiz.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");

                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }

                } else if (input.equals("todo")) {
                    throw new WizException(
                            "Oops! A todo needs a description."
                    );

                } else if (input.startsWith("todo ")) {
                    String description = input.substring(5);

                    if (description.isBlank()) {
                        throw new WizException(
                                "Oops! A todo needs a description."
                        );
                    }

                    Task task = new ToDo(description);
                    tasks.add(task);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println(
                            "Now you have " + tasks.size() + " tasks in the list."
                    );

                } else if (input.equals("deadline")) {
                    throw new WizException(
                            "Oops! A deadline needs a description and /by time."
                    );

                } else if (input.startsWith("deadline ")) {
                    String details = input.substring(9);

                    int byIndex = details.indexOf(" /by ");

                    if (byIndex == -1) {
                        throw new WizException(
                                "Oops! Please specify the deadline using /by."
                        );
                    }

                    String description = details.substring(0, byIndex);
                    String by = details.substring(byIndex + 5);

                    if (description.isBlank()) {
                        throw new WizException(
                                "Oops! A deadline needs a description."
                        );
                    }

                    if (by.isBlank()) {
                        throw new WizException(
                                "Oops! A deadline needs a /by value."
                        );
                    }

                    Task task = new Deadline(description, by);
                    tasks.add(task);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println(
                            "Now you have " + tasks.size() + " tasks in the list."
                    );

                } else if (input.equals("event")) {
                    throw new WizException(
                            "Oops! An event needs a description, /from, and /to."
                    );

                } else if (input.startsWith("event ")) {
                    String details = input.substring(6);

                    int fromIndex = details.indexOf(" /from ");
                    int toIndex = details.indexOf(" /to ");

                    if (fromIndex == -1 || toIndex == -1) {
                        throw new WizException(
                                "Oops! Use /from and /to for an event."
                        );
                    }

                    String description = details.substring(0, fromIndex);
                    String from = details.substring(fromIndex + 7, toIndex);
                    String to = details.substring(toIndex + 5);

                    if (description.isBlank()) {
                        throw new WizException(
                                "Oops! An event needs a description."
                        );
                    }

                    if (from.isBlank() || to.isBlank()) {
                        throw new WizException(
                                "Oops! An event needs both /from and /to values."
                        );
                    }

                    Task task = new Event(description, from, to);
                    tasks.add(task);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + task);
                    System.out.println(
                            "Now you have " + tasks.size() + " tasks in the list."
                    );

                } else if (input.startsWith("mark ")) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(input.substring(5));
                    } catch (NumberFormatException e) {
                        throw new WizException(
                                "Oops! Please give me a valid task number."
                        );
                    }

                    int index = taskNumber - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new WizException(
                                "Oops! That task number does not exist."
                        );
                    }

                    tasks.get(index).markAsDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(index));

                } else if (input.startsWith("unmark ")) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(input.substring(7));
                    } catch (NumberFormatException e) {
                        throw new WizException(
                                "Oops! Please give me a valid task number."
                        );
                    }

                    int index = taskNumber - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new WizException(
                                "Oops! That task number does not exist."
                        );
                    }

                    tasks.get(index).markAsNotDone();

                    System.out.println(
                            "OK, I've marked this task as not done yet:"
                    );
                    System.out.println("  " + tasks.get(index));

                } else if (input.startsWith("delete ")) {
                    int taskNumber;

                    try {
                        taskNumber = Integer.parseInt(input.substring(7));
                    } catch (NumberFormatException e) {
                        throw new WizException(
                                "Oops! Please give me a valid task number."
                        );
                    }

                    int index = taskNumber - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new WizException(
                                "Oops! That task number does not exist."
                        );
                    }

                    Task removedTask = tasks.remove(index);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println(
                            "Now you have " + tasks.size() + " tasks in the list."
                    );

                } else {
                    throw new WizException(
                            "Oops! I don't know what that command means."
                    );
                }

            } catch (WizException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}