package suboptimal;

public class CurrentAccount {
    private double balance;
    private String userName;
    public CurrentAccount(double balance, String userName) {
        this.balance = balance;
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }else  {
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
