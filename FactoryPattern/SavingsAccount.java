package FactoryPattern;

public class SavingsAccount implements AccountInterface{
    private double balance;
    private String username;
    private double interest;

    public SavingsAccount(double balance, String username, double interest) {
        this.balance = balance;
        this.username = username;
        this.interest = interest;
    }

    public static SavingsAccount createNewAccount( Double Balance, String username, double interest)
    {
        return new SavingsAccount(Balance, username, interest);
    }

    public boolean getBalance() {
        System.out.println("Current Balance is "+balance);
        return true;
    }

    public boolean getUserName() {
        System.out.println("Current User Name is "+username);
        return true;
    }
    public boolean deposit() {
        System.out.println("Deposit Current Balance is "+balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if(balance >= amount){
            balance = balance - amount;
        }
        return true;
    }

}
