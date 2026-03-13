package FactoryPattern;

public class Main {
    static void main() {

        // Factory
        AccountType t;
        t=AccountType.SavingsAccount;
        double balance=100;
        String username="Amitabh";
        double interest=6;
        AccountFactory factory = new AccountFactory();
        AccountInterface account = factory.createAccount(t, balance, username, interest);
        account.getBalance();
        account.getUserName();

        //Builder
        AccountBuilder b = new AccountBuilder(new AccountBuilder.Builder(balance,username));

    }
}
