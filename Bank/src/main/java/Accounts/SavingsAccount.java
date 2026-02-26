package Accounts;

import java.math.BigDecimal;

public class SavingsAccount implements AccountInterface{
    private static Long accountNumber;
    private static BigDecimal accountBalance;
    private static double interestRate = 1.5;


    public static SavingsAccount createNewAccount(Long accountNumber, BigDecimal accountBalance, double interestRate) {
        return new SavingsAccount(accountNumber, accountBalance, interestRate);
    }

    public SavingsAccount( Long accountNumber, BigDecimal accountBalance , Double interestRate )
    {
        SavingsAccount.accountNumber = accountNumber;
        SavingsAccount.accountBalance = accountBalance;
        SavingsAccount.interestRate = interestRate;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
    public boolean setAccountBalance( BigDecimal accountBalance ) {
        SavingsAccount.accountBalance = accountBalance;
        return true;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

}
