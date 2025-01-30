package neochat.task;


import neochat.task.tasklist.TaskList;
import neochat.task.taskexception.EmptyTaskDescriptionException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

public class Parser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final TaskList taskList;
    private boolean isExit;
    public Parser(TaskList taskList) {
        this.taskList = taskList;
        this.isExit = false;
    }

    public boolean isExit() {
        return isExit;
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
                case "bye" :
                    isExit = true;
                    break;
                case "list":
                    taskList.printList();
                    break;
                case "mark":
                    taskList.markAsDone(remainingInput);
                    break;
                case "unmark":
                    taskList.markAsNotDone(remainingInput);
                    break;
                case "delete":
                    taskList.delete(remainingInput);
                    break;
                case "help":
                    printCommandList();
                    break;
                case "find":
                    if (remainingInput.isEmpty()) {
                        System.out.println("Please provide a keyword to search.");
                    } else {
                        taskList.findTasks(remainingInput);
                    }
                    break;
                default:
                    System.out.println("Unknown command type" + commandType);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private static void printCommandList() {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the available commands:");
        System.out.println("1. list - Show all tasks");
        System.out.println("2. todo <description> - Add a Todo task");
        System.out.println("3. deadline <description> /by <yyyy-MM-dd HH:mm> - Add a Deadline task");
        System.out.println("4. event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm> - Add an Event task");
        System.out.println("5. mark <task number> - Mark a task as done");
        System.out.println("6. unmark <task number> - Mark a task as not done yet");
        System.out.println("7. help - Show the command list");
        System.out.println("8. bye - Exit the program");
        System.out.println("9. find <keyword> - Search tasks by keyword");
        System.out.println("____________________________________________________________");
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

    static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("time format error, the correct format should be: yyyy-MM-dd HH:mm");
        }
    }
}
