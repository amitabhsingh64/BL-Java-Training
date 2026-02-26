package Accounts.CreateAccountFactory;

import Accounts.*;

import java.math.BigDecimal;

public class CreateAccountFactory {

    public AccountInterface createAccount(@org.jetbrains.annotations.NotNull AccountType accountType, Long accountNumber, BigDecimal accountBalance, double interestRate) {
        switch (accountType) {
            case SavingsAccount:
                return SavingsAccount.createNewAccount(accountNumber, accountBalance, interestRate);
            case CurrentAccount:
                return CurrentAccount.createNewAccount(accountNumber, accountBalance);
            case FDAccount:
                return FDAccount.createNewAccount(accountNumber, accountBalance, interestRate);
        }
        return null;
    }
}
