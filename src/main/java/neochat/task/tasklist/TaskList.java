package neochat.task.tasklist;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import neochat.task.*;
import neochat.task.taskexception.EmptyTaskDescriptionException;

/**
 * Represents a list of tasks with operations to manage tasks persistently.
 * This class handles task storage, loading from/saving to files, and various task operations
 * including addition, deletion, status marking, and listing.
 * <p>
 * Tasks are stored in a text file and loaded automatically on initialization.
 * Supports three task types: Todo, Deadline, and Event with datetime validation.
 * </p>
 */
public class TaskList {
    private static int count = 0;
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final ArrayList<Task> tasks;
    private final File savedListFile;


    /**
     * Constructs a TaskList and initializes it by loading tasks from the saved file.
     * Creates the file and necessary directories if they don't exist.
     */
    public TaskList() {
        this.tasks = new ArrayList<>(100);
        this.savedListFile = new File("src/data/savedList.txt");
        loadTask();
    }

    /**
     * Saves current tasks to file and performs cleanup when exiting the application.
     */
    public void quit() {
        saveTasks();
    }

    /**
     * Adds a task to the list and updates the task count.
     *
     * @param task The task object to be added (Todo/Deadline/Event)
     */
    public String addTask(Task task) {
        tasks.add(task);
        count++;
        return printAddedTask(task);

    }


    private void loadTask() {
        try {
            if (!savedListFile.exists()) {
                File parentDir = savedListFile.getParentFile();
                if (parentDir != null) {
                    parentDir.mkdirs();
                }
                savedListFile.createNewFile();
                return;
            }

            Scanner scanner = new Scanner(savedListFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
                    assert task != null : "Task could not be parsed";
                    if (task != null) {
                        tasks.add(task);
                        count++;
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Fail to load save list:" + e.getMessage());
        }
    }

    /**
     * Parses a string from the save file into a Task object.
     *
     * @param line The saved task string in format "[Type] | [Status] | [Description] | [DateTime]"
     * @return Reconstructed Task object, or null if format is invalid
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        try {
            switch (type) {
            case "T":
                Todo todo = new Todo(description);
                if (isDone) {
                    todo.markAsDone();
                }
                return todo;
            case "D":
                if (parts.length < 4) {
                    return null;
                }
                LocalDateTime by = parseDateTime(parts[3].trim());
                Deadline deadline = new Deadline(description, by);
                if (isDone) {
                    deadline.markAsDone();
                }
                return deadline;
            case "E":
                if (parts.length < 5) {
                    return null;
                }
                LocalDateTime from = parseDateTime(parts[3].trim());
                LocalDateTime to = parseDateTime(parts[4].trim());
                Event event = new Event(description, from, to);
                if (isDone) {
                    event.markAsDone();
                }
                return event;
            default:
                return null;
            }
        } catch (EmptyTaskDescriptionException e) {
            // Should not reach this line: anything in the saved file should have a description as this exception is
            // already handled when the user set the task
            System.out.println("Wrong format of task description in the saved list, please check if the saved list is "
                    + "edited accidentally");
            return null;
        }

    }

    /**
     * Saves current task list to the storage file.
     * Uses each task's {@code toFileString()} method for serialization.
     */
    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savedListFile))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fail to save tasks" + e.getMessage());
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format, the correct format should be "
                    + ": yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Displays all tasks in the list with their indexes.
     * Shows "Empty task list!" if no tasks exist.
     */
    public String printList() {
        if (count == 0) {
            return "Empty task list!";
        } else {
            String s = "";
            for (int i = 0; i < count; i++) {
                Task task = tasks.get(i);
                s = s + (i + 1) + ": " + task.toString() + "\n";
            }
            return s;
        }
    }

    /**
     * Marks a task as done based on its position in the list.
     *
     * @param input String containing the task number (1-based index)
     * @throws NumberFormatException If input is not a valid integer
     * @throws IndexOutOfBoundsException If task number is out of valid range
     */
    public String markAsDone(String input) {
        String s = "";
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = tasks.get(taskIndex);
            task.markAsDone();
            s = s + "Nice! I've marked this task as done:" + "\n" + "  " + task;
        } catch (NumberFormatException e) {
            s = "Invalid input. Please provide a valid task number.";
        } catch (IndexOutOfBoundsException e) {
            s = "Invalid task number. Please provide a number between 1 and " + count + ".";
        }
        return s;
    }

    /**
     * Marks a task as not done based on its position in the list.
     *
     * @param input String containing the task number (1-based index)
     * @throws NumberFormatException If input is not a valid integer
     * @throws IndexOutOfBoundsException If task number is out of valid range
     */
    public String markAsNotDone(String input) {
        String s = "";
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = tasks.get(taskIndex);
            task.markAsNotDone();
            s = "OK, I've marked this task as not done yet:" + "\n" + "  " + task;
        } catch (NumberFormatException e) {
            s = "Invalid input. Please provide a valid task number.";
        } catch (IndexOutOfBoundsException e) {
            s = "Invalid task number. Please provide a number between 1 and " + count + ".";
        }
        return s;
    }

    /**
     * Deletes a task from the list based on its position.
     *
     * @param input String containing the task number (1-based index)
     * @throws NumberFormatException If input is not a valid integer
     * @throws IndexOutOfBoundsException If task number is out of valid range
     */
    public String delete(String input) {
        String s = "";
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = tasks.get(taskIndex);
            tasks.remove(taskIndex);
            count--;
            s = "Noted. I've removed this task:" + "\n" + "  " + task + "\n"
                + "Now you have " + count + " tasks in the list.";
        } catch (NumberFormatException e) {
            s = "Invalid input. Please provide a valid task number.";
        } catch (IndexOutOfBoundsException e) {
            s = "Invalid task number. Please provide a number between 1 and " + count + ".";
        }
        return s;
    }

    private String printAddedTask(Task task) {
        return "Got it. I've added this task:" + "\n  " + task + "\n"
                + "Now you have " + count + " tasks in the list.";
    }

    public String findTasks(String keyword) {
        String s = "";
        List<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            s = "No tasks found with keyword: \"" + keyword + "\"";
        } else {
            s = "Here are the matching tasks in your list:";
            for (int i = 0; i < matchingTasks.size(); i++) {
                s += (i + 1) + ": " + matchingTasks.get(i);
            }
        }
        return s;
    }
}
