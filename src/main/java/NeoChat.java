import java.util.*;

public class NeoChat {
    private static ArrayList<String> toDo = new ArrayList<>(100);
    private static int index = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String BYE = "bye";
        String LIST = "list";
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
                toDo.add(userInput);
                index++;
                System.out.println("added: " + userInput);
            }
        }
    }
    private static void showList() {
            if(index == 0) {
                System.out.println("Empty todo list!");
            } else {
                for (int i = 0; i < index; i++) {
                    System.out.println("" + (i + 1)  + ". " + toDo.get(i));
                }
            }
    }
}
