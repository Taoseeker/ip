package neochat.ui;

import java.util.*;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);
    public void greeting() {
        System.out.println("Hello! I am NeoChat!");
        System.out.println("What can I do for you?" + "\n");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("-------------------------------");
    }

}
