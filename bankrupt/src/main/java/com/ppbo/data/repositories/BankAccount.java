package com.ppbo.data.repositories;

import java.util.Scanner;

public class BankAccount implements com.ppbo.domain.repositories.BankAccount {
    private final String accountNumber;
    // private attributes
    private String name;
    private String address;
    private String accountType;
    private String password;
    private int balance;
    private Scanner scan;

    // constructor
    public BankAccount(String name, String address, String accountNumber, String password, int balance) {
        this.name = name;
        this.address = address;
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public String getAccountType() {
        return this.accountType;
    }

    @Override
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void setBalance(int amount) {
        this.balance = amount;
    }
}
