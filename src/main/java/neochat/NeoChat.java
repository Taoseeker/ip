package neochat;

import neochat.task.Parser;
import neochat.task.tasklist.TaskList;
import neochat.ui.Ui;


public class NeoChat {
    private static final TaskList taskList = new TaskList();
    private static final Parser parser = new Parser(taskList);
    private static final Ui ui = new Ui();



    public static void main(String[] args) {
        run();
    }

    private static void run() {
        ui.greeting();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                parser.parseCommand(fullCommand);
                if (parser.isExit()) {
                    isExit = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ui.showLine();
            }
        }
        taskList.quit();
    }

}
