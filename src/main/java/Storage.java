import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);

        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        FileWriter writer = new FileWriter(file);

        for (Task task : tasks) {
            writer.write(task.toFileString());
            writer.write(System.lineSeparator());
        }

        writer.close();
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.isBlank()) {
                continue;
            }

            Task task = parseTask(line);
            tasks.add(task);
        }

        scanner.close();

        return tasks;
    }

    private Task parseTask(String line) throws IOException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new IOException("Invalid data file format.");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;

        if (type.equals("T")) {
            task = new ToDo(description);

        } else if (type.equals("D")) {
            if (parts.length < 4) {
                throw new IOException("Invalid deadline format.");
            }

            String by = parts[3];

            task = new Deadline(description, by);

        } else if (type.equals("E")) {
            if (parts.length < 5) {
                throw new IOException("Invalid event format.");
            }

            String from = parts[3];
            String to = parts[4];

            task = new Event(description, from, to);

        } else {
            throw new IOException("Unknown task type.");
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}