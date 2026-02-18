package bank;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Payment bankSystem = new Payment(); // Create the system

        boolean running = true;

        while (running) {
            System.out.println("\n--- BANK MENU ---");
            System.out.println("1. Create New Account");
            System.out.println("2. Make a Payment (Transfer)");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.println("5. show all customers");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Acc Num: ");
                    long accNum = scanner.nextLong();
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Email: ");
                    String email = scanner.next();
                    while(!emailInputValidation(email)) {
                        System.out.println("invalid email");
                        email = scanner.next();
                    }
                    System.out.print("Enter Initial Balance: ");
                    double bal = scanner.nextDouble();
                    
                    bankSystem.createAccount(accNum, name, email, bal);
                    break;

                case 2:
                    System.out.print("From Account ID: ");
                    int from = scanner.nextInt();
                    System.out.print("To Account ID: ");
                    int to = scanner.nextInt();
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();

                    bankSystem.processPayment(from, to, amount);
                    break;

                case 3:
                    System.out.print("Enter Account ID: ");
                    int checkId = scanner.nextInt();
                    Customer c = bankSystem.getCustomer(checkId);
                    if (c != null) {
                        System.out.println("\n----------------------------");
                        System.out.print(c);
                        System.out.println("\n----------------------------");
                    } else {
                        System.out.println("\n-----------------------");
                        System.out.print("| Customer not found. |");
                        System.out.println("\n-----------------------");
                    }
                    break;

                case 4:
                    running = false;
                    System.out.println("\n--------------");
                    System.out.print("|  Goodbye!  |");
                    System.out.println("\n--------------");
                    break;

                case 5:
                    bankSystem.showAllCustomers();
                    break;
                default:
                    System.out.println("\n-------------------");
                    System.out.print("| Invalid option. |");
                    System.out.println("\n-------------------");
            }
        }
        scanner.close();
    }
    public static boolean emailInputValidation(String email){
        String regexPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern PATTERN = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        if (email == null) {
            return false;
        }
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
    public static long accGenerator(){
        long accNumber = (long)(Math.random()* 10000000000000L + 56758789);
        return accNumber;
    }
}