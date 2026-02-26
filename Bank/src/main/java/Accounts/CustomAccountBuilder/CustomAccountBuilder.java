package Accounts.CustomAccountBuilder;

import java.math.BigDecimal;

public class CustomAccountBuilder {
    private final Long accountNumber;
    private final BigDecimal accountBalance;
    private final double interestRate;

    CustomAccountBuilder(Builder builder) {
        this.accountNumber = builder.accountNumber;
        this.accountBalance = builder.accountBalance;
        this.interestRate = builder.interestRate;
    }
    public static class Builder{
        private Long accountNumber;
        private BigDecimal accountBalance;
        private double interestRate;
        public Builder(Long accountNumber, BigDecimal accountBalance) {
            this.accountNumber = accountNumber;
            this.accountBalance = accountBalance;
        }
        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }
        public CustomAccountBuilder build(){
            return new CustomAccountBuilder(this);
        }
    }
}
