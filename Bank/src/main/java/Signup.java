import Accounts.AccountInterface;
import Accounts.AccountType;
import Accounts.CreateAccountFactory.CreateAccountFactory;
import Accounts.CurrentAccount;

import java.math.BigDecimal;
import java.util.Scanner;

public class Signup {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String name;
        String email;
        String pin;
        String phoneNumber;
        AccountInterface account;

        System.out.println("lets Signup!");
        System.out.print("Enter your name: ");
        name = input.nextLine();
        System.out.print("Enter your email: ");
        email = input.nextLine();
        System.out.print("Enter your phone number: ");
        phoneNumber = input.nextLine();
        System.out.print("Enter your pin: ");
        pin = input.nextLine();


        System.out.println("1. Standard Account");
        System.out.println("2. Custom Account");
        while (true) {
            int accountChoice = input.nextInt();
            if(accountChoice == 1){
                System.out.println("Enter Account Type:");
                System.out.println("1. Current Account");
                System.out.println("2. Savings Account");
                System.out.println("3. FD Account");

                AccountType accountType;
                while (true) {
                    switch (input.nextInt()) {
                        case 1:
                            accountType = AccountType.CurrentAccount;
                            break;
                        case 2:
                            accountType = AccountType.SavingsAccount;
                            break;
                        case 3:
                            accountType = AccountType.FDAccount;
                            break;
                        default:
                            System.out.println("Invalid input!");

                    }
                    CreateAccountFactory accountFactory = new CreateAccountFactory();
                    Long accountNumber = input.nextLong();
                    BigDecimal balance = new BigDecimal(input.nextLine());
                    double interestRate = input.nextDouble();

                }
            }else if(accountChoice == 2){
                //custom account logic
            }else{
                System.out.println("Invalid input!");
            }
        }

    }
}
