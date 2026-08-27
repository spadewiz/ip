package wiz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(
            String description,
            LocalDateTime from,
            LocalDateTime to
    ) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

        return "[E]"
                + super.toString()
                + " (from: "
                + from.format(formatter)
                + " to: "
                + to.format(formatter)
                + ")";
    }

    @Override
    public String toFileString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        return "E | "
                + (isDone ? "1" : "0")
                + " | "
                + description
                + " | "
                + from.format(formatter)
                + " | "
                + to.format(formatter);
    }
}