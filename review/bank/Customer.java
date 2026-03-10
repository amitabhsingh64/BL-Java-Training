package review.bank;

public class Customer {
    //Private variables (Encapsulation)
    private long accountNumber;
    private String name;
    private double balance;
    private String email;

    //Constructor
    public Customer(long accountNumber, String name, String email, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    //Methods to change balance SAFELY
    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > this.balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    // 4. Getters (to read data)
    public long getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Acc: " + accountNumber + " | Name: " + name + " | Bal: $" + balance;
    }
}