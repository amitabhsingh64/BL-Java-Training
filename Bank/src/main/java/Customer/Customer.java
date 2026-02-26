package Customer;

import Accounts.AccountInterface;
import Accounts.AccountType;

import java.util.Scanner;

public class Customer {
    private String name;
    private String email;
    private String pin;
    private String phoneNumber;
    private AccountInterface account;

    public Customer( String name, String email, String pin, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
    }
    private Customer(AccountInterface account) {
        this.account = account;
    }

    public boolean addAccount(AccountInterface account) {
        Customer customer = new Customer(account);
        return true;
    }

    public void getCustomerDetails() {
        System.out.println("Customer details");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("phoneNumber: " + phoneNumber);
    }
    public void updateCustomerDetails() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Your Name: ");
        name = input.nextLine();
        System.out.println("Enter Your Email: ");
        email = input.nextLine();
        System.out.println("Enter Your Phone Number: ");
        phoneNumber = input.nextLine();
    }
}
