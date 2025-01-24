public class Todo extends Task {
    public Todo(String description) throws EmptyTaskDescriptionException {
        super(description);
    }

    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }
}
