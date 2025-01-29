package neochat;

public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) throws EmptyTaskDescriptionException {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }
}
