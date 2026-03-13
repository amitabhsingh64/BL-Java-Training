package FactoryPattern;

public class AccountBuilder {
    private final double balance;
    private final String username;
    private final double interestRate;

    AccountBuilder(Builder builder) {
        this.balance = builder.Balance;
        this.username = builder.username;
        this.interestRate = builder.interestRate;
    }
    public static class Builder{
        private double Balance;
        private String username;
        private double interestRate;
        public Builder(double balance,  String username) {
            this.Balance = balance;
            this.username = username;
        }
        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }
        public AccountBuilder build(){
            return new AccountBuilder(this);
        }  //
    }
}
