package FactoryPattern;

public class CurrentAccount implements AccountInterface{
    private double balance;
    private String username;
    private final double interest = 0;

    public CurrentAccount(double balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    public static CurrentAccount createNewAccount( Double Balance, String username)
    {
        return new CurrentAccount(Balance, username);
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
