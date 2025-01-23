import java.util.*;

public class NeoChat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String BYE = "bye";
        System.out.println("Hello! I am NeoChat!");

        System.out.println("What can I do for you?" + "\n");
        while (true) {
            String userInput = sc.nextLine();
            if (BYE.equals(userInput)) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            System.out.println(userInput);
        }


    }
}
