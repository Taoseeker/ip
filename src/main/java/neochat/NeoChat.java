package neochat;

import java.util.*;

public class NeoChat {
    private static final ArrayList<Task> taskList = new ArrayList<>(100);
    private static final String finished = "X";
    private static final String notFinished = " ";
    private static final String MARK = "mark ";
    private static final String UNMARK= "unmark ";
    private static final String BYE = "bye";
    private static final String LIST = "list";
    private static final String HELP = "help";
    private static final String DELETE = "delete";
    private static int count = 0;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("Hello! I am neochat.NeoChat!");
        System.out.println("What can I do for you?" + "\n");

        while (true) {
            String input = sc.nextLine();

            if (BYE.equals(input)) {
                System.out.println("Bye! See you next time!");
                break;
            } else if (LIST.equals(input)) {
                printList();
            } else if (input.startsWith("todo ")) {
                addTodo(input.substring(5));
            } else if (input.startsWith("deadline ")) {
                addDeadline(input.substring(9));
            } else if (input.startsWith("event ")) {
                addEvent(input.substring(6));
            } else if (input.startsWith(MARK)) {
                markAsDone(input.substring(5));
            } else if (input.startsWith(UNMARK)) {
                markAsNotDone(input.substring(7));
            } else if (input.startsWith(DELETE)) {
                delete(input.substring(7));
            } else if (HELP.equals(input)) {
                printCommandList();
            } else {
                System.out.println("Invalid command. Please try again, or type 'help' for help.");
            }
        }
        sc.close();
    }

    private static void printCommandList() {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the available commands:");
        System.out.println("1. list - Show all tasks");
        System.out.println("2. todo <description> - Add a neochat.Todo task");
        System.out.println("3. deadline <description> /by <time> - Add a neochat.Deadline task");
        System.out.println("4. event <description> /from <start> /to <end> - Add an neochat.Event task");
        System.out.println("5. mark <task number> - Mark a task as done");
        System.out.println("6. unmark <task number> - Mark a task as not done yet");
        System.out.println("7. help - Show the command list");
        System.out.println("8. bye - Exit the program");
        System.out.println("____________________________________________________________");
    }

    private static void printList() {
            if(count == 0) {
                System.out.println("Empty task list!");
            } else {
                for (int i = 0; i < count; i++) {
                    Task task = taskList.get(i);
                    System.out.println((i + 1) + ": " + task.toString());
                }
            }
    }

    private static void markAsDone(String input) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = taskList.get(taskIndex);
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

    private static void markAsNotDone(String input) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = taskList.get(taskIndex);
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

    private static void delete(String input) {
        try {
            int taskIndex = Integer.parseInt(input) - 1;
            if (taskIndex < 0 || taskIndex >= count) {
                throw new IndexOutOfBoundsException();
            }
            Task task = taskList.get(taskIndex);
            taskList.remove(taskIndex);
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

    private static void addTodo(String description) {
        try {
            Todo todo = new Todo(description);
            count++;
            taskList.add(todo);
            printAddedTask(todo);
        } catch (EmptyTaskDescriptionException e) {
            System.out.println("Invalid task description. Please provide a valid description.");
        }

    }

    private static void addDeadline(String userInput) {
        String[] tokens = userInput.split(" /by ", 2);
        if (tokens.length < 2) {
            System.out.println("Invalid command parameters. Please try again.");
            return;
        } else {
            try {
                Deadline deadline = new Deadline(tokens[0], tokens[1]);
                count++;
                taskList.add(deadline);
                printAddedTask(deadline);
            } catch (EmptyTaskDescriptionException e) {
                System.out.println("Invalid task description. Please provide a valid description.");
            }


        }
    }

    private static void addEvent(String userInput) {
        String[] parts = userInput.split(" /from | /to ", 3);
        if (parts.length < 3) {
            System.out.println("Invalid command parameters. Please try again.");
            return;
        } else {
            try {
                Event event = new Event(parts[0], parts[1], parts[2]);
                count++;
                taskList.add(event);
                printAddedTask(event);
            } catch (EmptyTaskDescriptionException e) {
                System.out.println("Invalid task description. Please provide a valid description.");
            }

        }
    }

    private static void printAddedTask(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}
