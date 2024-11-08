package com.ppbo.data.repositories;

import com.ppbo.data.entities.DepartmentBankAccount;
import com.ppbo.data.entities.LoanBankAccount;
import com.ppbo.data.entities.PersonalBankAccount;
import com.ppbo.exception.PaidException;
import com.ppbo.exception.WithdrawException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static com.ppbo.data.repositories.AccountCenter.getActiveAccount;

public class BankFeatures implements com.ppbo.domain.repositories.BankFeatures {
    Scanner scan = new Scanner(System.in); // scanner

    @Override
    public void viewAccount() {
        switch (getActiveAccount().getAccountType()) {
            case "Personal Account":
                PersonalBankAccount personalAccount = (PersonalBankAccount) getActiveAccount();

                System.out.println("+======================+=============================================+");
                System.out.printf("| %-20s | %-43s |%n", "Identitas", "Keterangan");
                System.out.println("+======================+=============================================+");

                System.out.printf("| %-20s | %-43s |%n", "Name", personalAccount.getName());
                System.out.printf("| %-20s | %-43s |%n", "Account Number", personalAccount.getAccountNumber());
                System.out.printf("| %-20s | %-43s |%n", "Account Type", personalAccount.getAccountType());
                System.out.printf("| %-20s | Rp %-40s |%n", "Balance", personalAccount.getBalance());
                System.out.println("+======================+=============================================+");
                break;
            case "Department Account":
                DepartmentBankAccount departmentAccount = (DepartmentBankAccount) getActiveAccount();

                System.out.println("+======================+=============================================+");
                System.out.printf("| %-20s | %-43s |%n", "Identitas", "Keterangan");
                System.out.println("+======================+=============================================+");

                System.out.printf("| %-20s | %-43s |%n", "Name", departmentAccount.getName());
                System.out.printf("| %-20s | %-43s |%n", "Account Number", departmentAccount.getAccountNumber());
                System.out.printf("| %-20s | %-43s |%n", "Account Type", departmentAccount.getAccountType());
                System.out.printf("| %-20s | Rp %-40s |%n", "Balance", departmentAccount.getBalance());
                System.out.println("+======================+=============================================+");
                break;
            case "Loan Account":
                LoanBankAccount loanAccount = (LoanBankAccount) getActiveAccount();

                System.out.println("+======================+=============================================+");
                System.out.printf("| %-20s | %-43s |%n", "Identitas", "Keterangan");
                System.out.println("+======================+=============================================+");

                System.out.printf("| %-20s | %-43s |%n", "Name", loanAccount.getName());
                System.out.printf("| %-20s | %-43s |%n", "Account Number", loanAccount.getAccountNumber());
                System.out.printf("| %-20s | %-43s |%n", "Account Type", loanAccount.getAccountType());
                System.out.printf("| %-20s | Rp %-40s |%n", "Balance", loanAccount.getBalance());
                System.out.printf("| %-20s | %-43s |%n", "Tenor", loanAccount.getTenor());
                System.out.printf("| %-20s | %-43s |%n", "Monthly Interest", loanAccount.countMonthlyInterest());
                System.out.printf("| %-20s | %-43s |%n", "Monthly Instalment", loanAccount.getMonthlyInstalment());
                System.out.printf("| %-20s | %-43s |%n", "Paid Instalment", loanAccount.getPaidInstalment());
                System.out.println("+======================+=============================================+");
                break;
        }

        System.out.println("\nPress enter to continue...");
        scan.nextLine();
    }

    @Override
    public void updateAccount() {

    }

    @Override
    public void deleteAccount() {

    }

    @Override
    public void deposit() {
        try {
            if (getActiveAccount().getAccountType().equals("Loan Account")) {
                throw new Exception("You can't deposit to loan account.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.printf("%-15s: ", "Deposit Amount");
            int amount = scan.nextInt();
            scan.nextLine();

            getActiveAccount().setBalance(getActiveAccount().getBalance() + amount);
            System.out.println("Deposit was successfully.\n");
            System.out.println("Press enter to continue...");
            scan.nextLine();
        }

    }

    @Override
    public void withdraw() throws WithdrawException {
        try {
            if (getActiveAccount().getAccountType().equals("Loan Account")) {
                throw new Exception("You can't deposit to loan account.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.printf("%-15s: ", "Withdraw Amount");
            int amount = scan.nextInt();
            scan.nextLine();

            // different withdraw limitation due to account type
            if (getActiveAccount().getAccountType().equals("Personal Account")) {
                PersonalBankAccount personalAccount = (PersonalBankAccount) getActiveAccount();
                personalAccount.withdrawBalance(amount);
            } else if (getActiveAccount().getAccountType().equals("Department Account")) {
                DepartmentBankAccount departmentAccount = (DepartmentBankAccount) getActiveAccount();
                departmentAccount.withdrawBalance(amount);
            }
        }
    }

    @Override
    public void payInstalment() throws PaidException {
        try {
            if (getActiveAccount().getAccountType().equals("Personal Account")
                    || getActiveAccount().getAccountType().equals("Department Account")) {
                throw new PaidException("You can't pay installment with personal or department account.");
            }
        } catch (PaidException e) {
            System.out.println(e.getMessage());
            System.out.println("Press enter to continue...");
            scan.nextLine();
            return;
        }
        System.out.printf("%-15s: ", "Pay Amount");
        int amount = scan.nextInt();
        scan.nextLine();

        ((LoanBankAccount) getActiveAccount()).payInstalment(amount);
        System.out.println("Pay was successfully.\n");
        System.out.println("Press enter to continue...");
        scan.nextLine();
    }

    @Override
    public void transfer() {
        try {
            if (getActiveAccount().getAccountType().equals("Loan Account")) {
                throw new Exception("You can't use this feature due to loan account.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        String accountNum;
        String receiverName = new String();
        int amount;
        String password;
        boolean isReceiverAccountFound = false;

        System.out.printf("%-20s: ", "Rekening Tujuan");
        accountNum = scan.nextLine();

        System.out.printf("%-20s: ", "Nominal Transfer");
        amount = scan.nextInt();
        scan.nextLine();

        JSONArray accounts = new JSONArray();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("accounts.json")) {
            Object obj = jsonParser.parse(reader);
            accounts = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        for (Object acc : accounts) {
            JSONObject accObj = (JSONObject) acc;

            if (accObj.get("account number").equals(accountNum)) {
                isReceiverAccountFound = true;
                receiverName = (String) accObj.get("name");
                break;
            }
        }

        if (!isReceiverAccountFound) {
            System.out.println("No receiver account found.\n");
        }

        if (getActiveAccount().getBalance() >= amount) {
            System.out.printf("%-20s: ", "Password");
            password = scan.nextLine();

            System.out.printf("Transfer to %s is successfull\n", receiverName);
            System.out.printf("Your current balance: Rp %d\n\n", getActiveAccount().getBalance());
        } else {
            System.out.println("Transfer is failed due to insufficient balance.\n");
        }

        System.out.print("Press enter to continue...");
        scan.nextLine();
    }
}
