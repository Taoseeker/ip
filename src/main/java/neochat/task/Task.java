package neochat.task;
import neochat.task.taskexception.EmptyTaskDescriptionException;

/**
 * The {@code Task} class represents a generic task.
 * It serves as a base class for specific types of tasks such as {@code Todo}, {@code Deadline}, and {@code Event}.
 * Each task has a description and a completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a {@code Task} with the specified description.
     *
     * @param description The description of the task.
     * @throws EmptyTaskDescriptionException If the description is null or empty.
     */
    public Task(String description) throws EmptyTaskDescriptionException {
        if (description == null || description.isEmpty()) {
            throw new EmptyTaskDescriptionException();
        }
        this.description = description;
        this.isDone = false;
    }

    /**
     * Checks if the task description contains the specified keyword (case-insensitive).
     *
     * @param keyword The keyword to search for.
     * @return {@code true} if the description contains the keyword; otherwise, {@code false}.
     */
    public boolean contains(String keyword) {
        return description.toLowerCase().contains(keyword);
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Retrieves the status icon representing the completion state of the task.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Converts the task to a string representation suitable for file storage.
     *
     * @return A formatted string representation of the task.
     */
    public abstract String toFileString();

    /**
     * Returns a string representation of the task.
     *
     * @return A string describing the task.
     */
    @Override
    public abstract String toString();
}
