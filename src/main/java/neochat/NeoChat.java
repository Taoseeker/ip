package neochat;

import neochat.task.Parser;
import neochat.task.tasklist.TaskList;

/**
 * Represents the main class for the NeoChat application.
 * This class initializes the necessary components (UI, TaskList, Parser)
 * and handles the main command processing loop.
 * <p>
 * The application continuously reads user input, parses commands,
 * and executes corresponding actions until an exit command is received.
 * </p>
 *
 */
public class NeoChat {
    private static final TaskList taskList = new TaskList();
    private static final Parser parser = new Parser(taskList);


    /**
     * Manages the main execution loop of the application.
     * <p>
     * Displays a greeting message and processes user commands interactively.
     * Handles exceptions internally and ensures proper cleanup when exiting.
     * </p>
     * <p>
     * The loop continues until an exit command is parsed by the {@link Parser}.
     * </p>
     */
    public String getResponse(String userInput) {
        String s = "";
        try {
            s = parser.parseCommand(userInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void save() {
        this.taskList.saveTasks();
    }
}
