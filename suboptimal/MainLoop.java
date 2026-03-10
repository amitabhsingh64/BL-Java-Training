package suboptimal;

public class MainLoop {
    public static void main(String[] args) {
        CurrentAccount currAcc = new CurrentAccount(1200,"Amitabh");
        SavingsAccount savAcc = new SavingsAccount(1200,"abc",3);
        FDAccount fdAcc = new FDAccount(1200,"def",8);

        System.out.println("---Current Account---");
        System.out.println("Current Balance: " + currAcc.getBalance());
        System.out.println("User Name: " + currAcc.getUserName());

        System.out.println("---Savings Account---");
        System.out.println("Savings Balance: " + savAcc.getBalance());
        System.out.println("User Name: " + savAcc.getUserName());

        System.out.println("---FD Account---");
        System.out.println("FD Balance: " + fdAcc.getBalance());
    }
}
