package neochat;

public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) throws EmptyTaskDescriptionException {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + from + " to: " + to + ")";
    }
}
