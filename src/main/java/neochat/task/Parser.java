package neochat.task;
import neochat.*;
import neochat.task.tasklist.TaskList;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

public class Parser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    TaskList taskList;
    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    public void parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("The input is empty");
        }

        String[] tokens = input.split("\\s+", 2);
        String commandType = tokens[0].toLowerCase();
        String remainingInput = (tokens.length > 1) ? tokens[1] : "";

        try {
            switch (commandType) {
                case "todo":
                    parseTodo(remainingInput);
                    break;
                case "deadline":
                    parseDeadline(remainingInput);
                    break;
                case "event":
                    parseEvent(remainingInput);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command type" + commandType);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void parseTodo(String input) {
        try {
            this.taskList.addTask(new Todo(input.trim()));
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Task description is empty");
        }
    }

    private void parseDeadline(String input) {
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Deadline format error，the correct format should be" +
                    " deadline <description> /by yyyy-MM-dd HH:mm");
        }
        String description = parts[0].trim();
        LocalDateTime by = parseDateTime(parts[1].trim());
        try {
            this.taskList.addTask(new Deadline(description, by));
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Task description is empty");
        }
    }

    private void parseEvent(String input) {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Event format error，the correct format should be" +
                    " event <description> /from yyyy-MM-dd HH:mm /to yyyy-MM-dd HH:mm");
        }
        String description = parts[0].trim();
        LocalDateTime from = parseDateTime(parts[1].trim());
        LocalDateTime to = parseDateTime(parts[2].trim());
        try {
            this.taskList.addTask(new Event(description, from, to));
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Task description is empty");
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("time format error, the correct format should be: yyyy-MM-dd HH:mm");
        }
    }
}
