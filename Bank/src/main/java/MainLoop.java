import java.sql.SQLOutput;
import java.util.Scanner;

public class MainLoop {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Bank");

        System.out.println("1.Login");
        System.out.println("2.SignUp");

        while (true) {
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    //logic for login
                case 2:

            }
        }
    }
}
