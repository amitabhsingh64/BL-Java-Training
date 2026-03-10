package FactoryPattern;

public class FDAccount {
    private double balance;
    private String username;
    private double interest;

    public FDAccount(double balance, String username, double interest) {
        this.balance = balance;
        this.username = username;
        this.interest = interest;
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
}
