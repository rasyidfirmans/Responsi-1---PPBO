package com.ppbo.domain.repositories;

import com.ppbo.exception.PaidException;
import com.ppbo.exception.WithdrawException;

public interface BankFeatures {
//    public void createAccount(int accountType);

    public void viewAccount();

    public void updateAccount();

    public void deleteAccount();

    public void deposit();

    public void withdraw() throws WithdrawException;

    public void payInstalment() throws PaidException;

    public void transfer();
}
