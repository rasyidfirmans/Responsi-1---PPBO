package com.ppbo.domain.repositories;

public interface BankAccount {
    // getter
    public String getName();

    public String getAddress();

    public String getAccountNumber();

    public String getAccountType();

    // setter
    public void setAccountType(String accountType);

    public String getPassword();

    public int getBalance();

    public void setBalance(int amount);
}
