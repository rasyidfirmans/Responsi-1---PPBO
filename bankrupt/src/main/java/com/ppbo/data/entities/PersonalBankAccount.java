package com.ppbo.data.entities;

import com.ppbo.exception.WithdrawException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Scanner;

public class PersonalBankAccount extends com.ppbo.data.repositories.BankAccount {
    private final String nik;
    private JSONParser jsonParser = new JSONParser();
    private JSONArray accountList = new JSONArray();
    private JSONObject account = new JSONObject();

    public PersonalBankAccount(String name, String address, String nik, String accountNumber, String password,
                               int balance) {
        super(name, address, accountNumber, password, balance);

        this.nik = nik;
        setAccountType("Personal Account");
    }

    // getter methods
    public String getNik() {
        return this.nik;
    }

    // class methods
    // Personal Account has limitter withdraw
    public void withdrawBalance(int amount) {
        Scanner scan = new Scanner(System.in);

        try {
            if (super.getBalance() < amount) {
                throw new WithdrawException("Your balance isn't enough\n");
            } else if (amount > 10000000) {
                throw new WithdrawException("Please withdraw under Rp 10.000.000\n");
            } else {
                super.setBalance(super.getBalance() - amount);
                System.out.println("Withdraw was successfully.\n");
            }
        } catch (WithdrawException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.print("Press enter to continue...");
            scan.nextLine();
        }
    }

    public void transfer(int amount, String receiverAccNumber) {
        super.setBalance(super.getBalance() - amount);


    }
}
