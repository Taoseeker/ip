package neochat.task;
import neochat.task.taskexception.EmptyTaskDescriptionException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws EmptyTaskDescriptionException {
        if(description == null || description.isEmpty()) {
            throw new EmptyTaskDescriptionException();
        }
        this.description = description;
        this.isDone = false;
    }

    public boolean contains(String keyword) {
        return description.toLowerCase().contains(keyword);
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public abstract String toFileString();

    @Override
    public abstract String toString();
}
