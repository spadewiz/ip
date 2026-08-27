public class Event extends Task {
    private final String from;
    private final String to;
    private final TaskType type = TaskType.EVENT;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]"
                + super.toString()
                + " (from: " + from + " to: " + to + ")";
    }
}