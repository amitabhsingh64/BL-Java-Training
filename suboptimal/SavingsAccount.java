package suboptimal;

public class SavingsAccount {
    private double balance;
    private String userName;
    private double interestRate;

    public SavingsAccount(double balance, String userName, double interestRate){
        this.balance = balance;
        this.userName = userName;
        this.interestRate = interestRate;
    }

    public double getBalance() {
        return balance;
    }
    public boolean withdraw(double amount){
        if(balance >= amount){
            balance -= amount;
            return true;
        }else {
            return false;
        }
    }

    public boolean deposit(double amount) {
        if(amount<0){
            return false;
        }else{
            balance += amount;
            return true;
        }
    }

    public String getUserName() {
        return userName;
    }
}
