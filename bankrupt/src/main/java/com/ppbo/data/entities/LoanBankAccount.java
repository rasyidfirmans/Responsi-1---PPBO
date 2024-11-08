package com.ppbo.data.entities;

import com.ppbo.data.repositories.BankAccount;
import com.ppbo.exception.PaidException;

import java.util.Scanner;

public class LoanBankAccount extends BankAccount {
    private final String nik;
    private final int pokokPinjaman;
    private final int tenor; // peminjaman jangka berapa bulan
    private int cicilanTerbayar;

    public LoanBankAccount(String name, String address, String nik, String accountNumber, String password,
                           int pokokPinjaman, int tenor) {
        super(name, address, accountNumber, password, pokokPinjaman);

        this.nik = nik;
        this.pokokPinjaman = pokokPinjaman;
        this.tenor = tenor;
        setAccountType("Loan Account");
    }

    // getter methods
    public String getNik() {
        return this.nik;
    }

    public int getTenor() {
        return this.tenor;
    }

    public int getPaidInstalment() {
        return this.cicilanTerbayar;
    }

    public int getMonthlyInstalment() {
        return (this.pokokPinjaman / this.tenor) + countMonthlyInterest();
    }

    // class methods
    public int countMonthlyInterest() {
        int bungaTahunan = 12;
        return (bungaTahunan / 12) * this.pokokPinjaman / 100;
    }

    public void payInstalment(int amount) throws PaidException {
        Scanner scan = new Scanner(System.in);
        try {
            if (amount < getMonthlyInstalment()) {
                throw new PaidException("Your money isn't enough to pay the instalment.\n");
            } else {
                super.setBalance(super.getBalance() - (amount - countMonthlyInterest()));
                this.cicilanTerbayar += 1;

                System.out.printf("Remaining instalment: Rp %d\n", super.getBalance());
            }
        } catch (PaidException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Press enter to continue...");
            scan.nextLine();
        }
    }
}
