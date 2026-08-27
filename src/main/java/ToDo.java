public class ToDo extends Task {
    private final TaskType type = TaskType.TODO;

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + super.toString();
    }
}