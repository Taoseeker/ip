package neochat.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import neochat.task.taskexception.EmptyTaskDescriptionException;
import neochat.task.tasklist.TaskList;

/**
 * The {@code Parser} class is responsible for parsing user commands and executing
 * the corresponding task operations.
 * It processes user input and delegates task operations to the {@link TaskList}.
 */
public class Parser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final TaskList taskList;

    /**
     * Constructs a Parser with a reference to the application's TaskList.
     *
     * @param taskList The TaskList instance to operate on
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Parses and executes the given command input.
     *
     * @param input The user command string.
     * @return A string representing the result of executing the command.
     * @throws IllegalArgumentException If the input is empty or invalid.
     */
    public String parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("The input is empty");
        }
        String s = ""; // a dummy variable to hold returned string
        String[] tokens = input.split("\\s+", 2);
        String commandType = tokens[0].toLowerCase();
        String remainingInput = (tokens.length > 1) ? tokens[1] : "";

        try {
            switch (commandType) {
            case "todo":
                s = parseTodo(remainingInput);
                break;
            case "deadline":
                s = parseDeadline(remainingInput);
                break;
            case "event":
                s = parseEvent(remainingInput);
                break;
            case "bye":
                taskList.saveTasks();
                s = "bye";
                break;
            case "list":
                s = taskList.printList();
                break;
            case "mark":
                s = taskList.markAsDone(remainingInput);
                break;
            case "unmark":
                s = taskList.markAsNotDone(remainingInput);
                break;
            case "delete":
                s = taskList.delete(remainingInput);
                break;
            case "archive":
                s = taskList.archive(remainingInput);
                break;
            case "loadarchive":
                taskList.loadArchivedTasks();
                s = "successfully loaded archived tasks!";
                break;
            case "help":
                s = printCommandList();
                break;
            case "find":
                s = taskList.findTasks(remainingInput);
                break;
            default:
                s = "Unknown command type" + commandType + "\n"
                        + "key in 'help' for more information.'";
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assert s != null : "parseCommand() should not return null";
        assert !s.isEmpty() : "parseCommand() should not return empty string";
        return s;
    }

    /**
     * Prints the available commands.
     *
     * @return A formatted string listing the supported commands.
     */
    private static String printCommandList() {
        return "Here are the available commands:" + "\n"
                + "1. list - Show all tasks" + "\n"
                + "2. todo <description> - Add a Todo task" + "\n"
                + "3. deadline <description> /by <yyyy-MM-dd HH:mm> - Add a Deadline task" + "\n"
                + "4. event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm> - Add an Event task" + "\n"
                + "5. mark <task number> - Mark a task as done" + "\n"
                + "6. unmark <task number> - Mark a task as not done" + "\n"
                + "7. delete <task number> - Delete a task" + "\n"
                + "8. archive <task number> - Archive a task" + "\n"
                + "9. loadarchive <task number> - Archive a task" + "\n"
                + "10. find <task number> - Find a task" + "\n"
                + "11. help - Show the command list" + "\n"
                + "12. bye - Exit the program";
    }


    /**
     * Parses a Todo command and adds the task.
     *
     * @param input Command arguments after "todo" keyword
     * @throws EmptyTaskDescriptionException If description is empty
     */
    private String parseTodo(String input) {
        String s = "";
        try {
            s = this.taskList.addTask(new Todo(input.trim()));
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Task description is empty");
        }
        assert !s.isEmpty() : "TodoTask description is empty";
        return s;
    }

    private String parseDeadline(String input) {
        String s = "";
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Deadline format error，the correct format should be"
                    + " deadline <description> /by yyyy-MM-dd HH:mm");
        }
        assert parts.length >= 2 : "Deadline format error";
        String description = parts[0].trim();
        LocalDateTime by = parseDateTime(parts[1].trim());
        try {
            s = this.taskList.addTask(new Deadline(description, by));
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Task description is empty");
        }
        assert !s.isEmpty() : "DeadlineTask description is empty";
        return s;
    }

    private String parseEvent(String input) {
        String s = "";
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Event format error，the correct format should be"
                    + " event <description> /from yyyy-MM-dd HH:mm /to yyyy-MM-dd HH:mm");
        }
        assert parts.length >= 3 : "Event format error";
        String description = parts[0].trim();
        LocalDateTime from = parseDateTime(parts[1].trim());
        LocalDateTime to = parseDateTime(parts[2].trim());
        try {
            s = this.taskList.addTask(new Event(description, from, to));
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Task description is empty");
        }
        assert !s.isEmpty() : "EventTask description is empty";
        return s;
    }

    /**
     * Validates and converts a datetime string to LocalDateTime.
     *
     * @param dateTimeStr Datetime string in "yyyy-MM-dd HH:mm" format
     * @return Parsed LocalDateTime object
     * @throws IllegalArgumentException If format is invalid
     */
    static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("time format error, the correct format should be: yyyy-MM-dd HH:mm");
        }
    }
}
