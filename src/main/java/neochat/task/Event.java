package neochat.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import neochat.task.taskexception.EmptyTaskDescriptionException;

public class Event extends Task {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter outputFormatter =
            DateTimeFormatter.ofPattern("MMM-dd-yyyy", Locale.ENGLISH);
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) throws EmptyTaskDescriptionException {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + from.format(outputFormatter) + " to: "
                + to.format(outputFormatter) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(DATE_TIME_FORMATTER) + " | "
                + to.format(DATE_TIME_FORMATTER);
    }
}
