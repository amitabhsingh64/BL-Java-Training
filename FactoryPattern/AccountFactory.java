package FactoryPattern;

public class AccountFactory {
    public AccountInterface createAccount(AccountType accountType, double Balance, String username, double interestRate) {
        switch (accountType) {
            case SavingsAccount:
                return SavingsAccount.createNewAccount(Balance, username, interestRate);
            case CurrentAccount:
                return CurrentAccount.createNewAccount(Balance, username );
            case FDAccount:
                return FDAccount.createNewAccount(Balance, username, interestRate);
        }
        return null;
    }
}
