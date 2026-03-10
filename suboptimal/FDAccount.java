package suboptimal;

public class FDAccount {
    private double balance;
    private String userName;
    private double interestRate = 6d;
    public FDAccount(double balance, String userName, double interestRate) {
        this.balance = balance;
        this.userName = userName;
        this.interestRate = interestRate;
    }
    public double getBalance() {
        return balance;
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
