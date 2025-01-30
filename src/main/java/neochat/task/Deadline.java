package neochat.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import neochat.task.taskexception.EmptyTaskDescriptionException;

public class Deadline extends Task {
    private LocalDateTime by;
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter outputFormatter =
            DateTimeFormatter.ofPattern("MMM-dd-yyyy", Locale.ENGLISH);

    public Deadline(String description, LocalDateTime by) throws EmptyTaskDescriptionException {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + by.format(outputFormatter) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(DATE_TIME_FORMATTER);
    }
}
