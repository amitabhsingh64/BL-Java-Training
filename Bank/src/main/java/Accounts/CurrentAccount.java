package Accounts;

import java.math.BigDecimal;

public class CurrentAccount implements AccountInterface{
    private static Long accountNumber;
    private static BigDecimal accountBalance;
    private final double interestRate = 0.0;

    public static CurrentAccount createNewAccount( Long accountNumber, BigDecimal accountBalance)
    {
        return new CurrentAccount(accountNumber, accountBalance);
    }


    private CurrentAccount( Long accountNumber, BigDecimal accountBalance) {
        CurrentAccount.accountNumber = accountNumber;
        CurrentAccount.accountBalance = accountBalance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
    public boolean setAccountNumber( Long accountNumber ) {
        CurrentAccount.accountNumber = accountNumber;
        return true;
    }
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }
    public boolean setAccountBalance( BigDecimal accountBalance ) {
        CurrentAccount.accountBalance = accountBalance;
        return true;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
