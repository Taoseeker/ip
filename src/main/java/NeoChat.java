import java.util.*;

public class NeoChat {
    private static ArrayList<Task> toDo = new ArrayList<>(100);
    private static final String finished = "X";
    private static final String notFinished = " ";
    private static final String MARK = "mark";
    private static final String UNMARK= "unmark";
    private static final String BYE = "bye";
    private static final String LIST = "list";
    private static int index = 0;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.println("Hello! I am NeoChat!");
        System.out.println("What can I do for you?" + "\n");

        while (true) {
            String userInput = sc.nextLine();
            if (BYE.equals(userInput)) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (LIST.equals(userInput)) {
                showList();
            } else {
                if (userInput.startsWith(MARK)) {
                    int i = Integer.parseInt(userInput.substring(5));
                    if (i > index) {
                        System.out.println("Not a valid index!");
                        continue;
                    }
                    toDo.get(i - 1).markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(toDo.get(i - 1));
                } else if (userInput.startsWith(UNMARK)) {
                    int i = Integer.parseInt(userInput.substring(7));
                    if (i > index) {
                        System.out.println("Not a valid index!");
                        continue;
                    }
                    toDo.get(i - 1).markNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(toDo.get(i - 1));
                } else {
                    Task userTask = new Task(userInput);
                    toDo.add(userTask);
                    index++;
                    System.out.println("added: " + userInput);
                }

            }
        }
    }

    private static void showList() {
            if(index == 0) {
                System.out.println("Empty todo list!");
            } else {
                for (int i = 0; i < index; i++) {
                    Task task = toDo.get(i);
                    System.out.println((i + 1) + ": " + task.toString());
                }
            }
    }
}
