package neochat.task.tasklist;

import java.util.*;
import java.io.*;
import neochat.*;

public class TaskList {
    private final ArrayList<Task> tasks;
    private final File savedListFile;
    private static int count = 0;

    public TaskList() {
        this.tasks = new ArrayList<>(100);
        this.savedListFile = new File("src/data/savedList.txt");
        loadTask();
    }

    public void quit() {
        saveTasks();
    }

    public void printTask() {
        try {
            Scanner s = new Scanner(this.savedListFile); // create a Scanner using the File as the source
            while (s.hasNext()) {
                System.out.println(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException e) {
            File parentDir = savedListFile.getParentFile();
            if (parentDir == null || !parentDir.exists()) {
                System.out.println("The file directory does not exist.");
            } else if (!savedListFile.exists()) {
                System.out.println("The saved list file does not exist.");
            }
        }
    }

    private void loadTask() {
        try {
            if (!savedListFile.exists()) {
                File parentDir = savedListFile.getParentFile();
                if (parentDir != null) parentDir.mkdirs();
                savedListFile.createNewFile();
                return;
            }

            Scanner scanner = new Scanner(savedListFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Task task = parseTask(line);
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

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        try {
            switch (type) {
                case "T":
                    Todo todo = new Todo(description);
                    if (isDone) todo.markDone();
                    return todo;
                case "D":
                    if (parts.length < 4) return null;
                    Deadline deadline = new Deadline(description, parts[3]);
                    if (isDone) deadline.markDone();
                    return deadline;
                case "E":
                    if (parts.length < 5) return null;
                    Event event = new Event(description, parts[3], parts[4]);
                    if (isDone) event.markDone();
                    return event;
                default:
                    return null;
            }
        } catch (EmptyTaskDescriptionException e) {
            // Should not reach this line: anything in the saved file should have a description as this exception is
            // already handled when the user set the task
            System.out.println("Wrong format of task description in the saved list, please check if the saved list is " +
                    "edited accidentally");
            return null;
        }

    }

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


    public void printList() {
        if(count == 0) {
            System.out.println("Empty task list!");
        } else {
            for (int i = 0; i < count; i++) {
                Task task = tasks.get(i);
                System.out.println((i + 1) + ": " + task.toString());
            }
        }
    }

    public void markAsDone(String input) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = tasks.get(taskIndex);
            task.markDone();
            System.out.println("____________________________________________________________");
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + task);
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please provide a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid task number. Please provide a number between 1 and " + count + ".");
        }
    }

    public void markAsNotDone(String input) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = tasks.get(taskIndex);
            task.markNotDone();
            System.out.println("____________________________________________________________");
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please provide a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid task number. Please provide a number between 1 and " + count + ".");
        }
    }

    public void delete(String input) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = tasks.get(taskIndex);
            tasks.remove(taskIndex);
            count--;
            System.out.println("____________________________________________________________");
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + count + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please provide a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid task number. Please provide a number between 1 and " + count + ".");
        }
    }

    public void addTodo(String description) {
        try {
            Todo todo = new Todo(description);
            count++;
            tasks.add(todo);
            printAddedTask(todo);
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Invalid task description. Please provide a valid description.");
        }

    }

    public void addDeadline(String userInput) {
        String[] tokens = userInput.split(" /by ", 2);
        if (tokens.length < 2) {
            System.out.println("Invalid command parameters. Please try again.");
        } else {
            try {
                Deadline deadline = new Deadline(tokens[0], tokens[1]);
                count++;
                tasks.add(deadline);
                printAddedTask(deadline);
            } catch (EmptyTaskDescriptionException e) {
                System.out.println("Invalid task description. Please provide a valid description.");
            }


        }
    }

    public void addEvent(String userInput) {
        String[] parts = userInput.split(" /from | /to ", 3);
        if (parts.length < 3) {
            System.out.println("Invalid command parameters. Please try again.");
        } else {
            try {
                Event event = new Event(parts[0], parts[1], parts[2]);
                count++;
                tasks.add(event);
                printAddedTask(event);
            } catch (EmptyTaskDescriptionException e) {
                System.out.println("Invalid task description. Please provide a valid description.");
            }

        }
    }

    public void printAddedTask(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}
