package bank;

import java.util.HashMap;

public class Payment {
    //database
    private HashMap<Long, Customer> database = new HashMap<>();

    // FEATURE 1: Create Account
    public void createAccount(long accNum, String name, String email, double bal) {
        if (database.containsKey(accNum)) {
            System.out.println("Error: Account " + accNum + " already exists!");
        } else {

            Customer newCust = new Customer(accNum, name, email, bal);
            database.put(accNum, newCust);
            System.out.println("Success: Account created for " + name);
        }
    }

    // FEATURE 2: Find a Customer
    public Customer getCustomer(int accNum) {
        return database.get(accNum);
    }

    // FEATURE 3: Process a Payment (Transfer)
    public void processPayment(int fromAcc, int toAcc, double amount) {
        Customer sender = database.get(fromAcc);
        Customer receiver = database.get(toAcc);

        if (sender == null || receiver == null) {
            System.out.println("Error: One or both account numbers are invalid.");
            return;
        }

        if (sender.withdraw(amount)) {
            receiver.deposit(amount);
            System.out.println("\n-------------------------------");
            System.out.print("Success: Transferred $" + amount + " to " + receiver.getName());
            System.out.println("\n-------------------------------");
            System.out.print("New Balance for " + sender.getName() + ":" + sender.getBalance());
        } else {
            System.out.println("\n-------------------------------");
            System.out.println("Error: Insufficient funds.");
            System.out.println("\n-------------------------------");
        }
    }
    public void showAllCustomers(){
        for(long i: database.keySet()){
            System.out.println( "acc:" + i + database.get(i) );
        }
    }
}