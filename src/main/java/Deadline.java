public class Deadline extends Task {
    private final String by;
    private final TaskType type = TaskType.DEADLINE;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]"
                + super.toString()
                + " (by: " + by + ")";
    }
}