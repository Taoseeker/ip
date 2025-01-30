package neochat;

import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime  from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime  from, LocalDateTime  to) throws EmptyTaskDescriptionException {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
}
