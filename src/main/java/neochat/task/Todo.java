package neochat.task;
import neochat.task.taskexception.EmptyTaskDescriptionException;

public class Todo extends Task {
    public Todo(String description) throws EmptyTaskDescriptionException {
        super(description);
    }

    @Override
    public String toString() {
        return "[T][" + getStatusIcon() + "] " + description;
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
