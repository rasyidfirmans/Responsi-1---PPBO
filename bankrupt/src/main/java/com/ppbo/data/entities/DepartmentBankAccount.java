package com.ppbo.data.entities;

import com.ppbo.exception.WithdrawException;

import java.util.Scanner;

public class DepartmentBankAccount extends com.ppbo.data.repositories.BankAccount {
    private final String npwp;

    public DepartmentBankAccount(String name, String address, String npwp, String accountNumber, String password,
                                 int balance) {
        super(name, address, accountNumber, password, balance);

        this.npwp = npwp;
        setAccountType("Department Account");
    }

    // getter methods
    public String getNpwp() {
        return this.npwp;
    }

    // Department Account has bigger limitter withdraw
    public int withdrawBalance(int amount) throws WithdrawException {
        Scanner scan = new Scanner(System.in);
        boolean isWithdrawn = false;

        try {
            if (super.getBalance() < amount) {
                throw new WithdrawException("Your balance isn't enough\n");
            } else if (amount > 500000000) {
                throw new WithdrawException("Please withdraw under Rp 500.000.000\n");
            } else {
                isWithdrawn = true;
                super.setBalance(super.getBalance() - amount);
                System.out.println("Withdraw was successfully.\n");
            }
        } catch (WithdrawException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.print("Press enter to continue...");
            scan.nextLine();
        }

        return amount;
    }
}
