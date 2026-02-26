package Accounts;

import java.math.BigDecimal;

public class FDAccount implements AccountInterface{
    private static Long accountNumber;
    private static BigDecimal accountBalance;
    private static double interestRate = 6.0;

    public static FDAccount createNewAccount(Long accountNumber, BigDecimal accountBalance, double interestRate){
        return new FDAccount(accountNumber, accountBalance, interestRate);
    }

    private FDAccount(Long accountNumber, BigDecimal accountBalance, double interestRate) {
        FDAccount.accountNumber = accountNumber;
        FDAccount.accountBalance = accountBalance;
        FDAccount.interestRate = interestRate;
    }

    public Long  getAccountNumber()
    {
        return accountNumber;
    }
    public boolean setAccountNumber( Long accountNumber )
    {
        FDAccount.accountNumber = accountNumber;
        return true;
    }
    public BigDecimal getAccountBalance()
    {
        return accountBalance;
    }
    public double getInterestRate()
    {
        return interestRate;
    }

    public void setInterestRate(double interestRate)
    {
        FDAccount.interestRate = interestRate;
    }
}
