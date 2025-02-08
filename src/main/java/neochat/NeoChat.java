package neochat;

import neochat.task.Parser;
import neochat.task.tasklist.TaskList;
import neochat.ui.Ui;

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
    private static final Ui ui = new Ui();

    /**
     * The entry point of the NeoChat application.
     * Calls the {@code run()} method to start the program.
     *
     * @param args Command-line arguments (not used in this application)
     */


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
//    private static void run() {
//        ui.greeting();
//        boolean isExit = false;
//        while (!isExit) {
//            try {
//                String fullCommand = ui.readCommand();
//                parser.parseCommand(fullCommand);
//                if (parser.isExit()) {
//                    isExit = true;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                ui.showLine();
//            }
//        }
//        taskList.quit();
//    }

    public String getResponse(String userInput) {
        String s = "";
        try {
            s = parser.parseCommand(userInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}