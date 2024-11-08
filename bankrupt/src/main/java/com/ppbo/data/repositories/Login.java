package com.ppbo.data.repositories;

import com.ppbo.data.entities.DepartmentBankAccount;
import com.ppbo.data.entities.LoanBankAccount;
import com.ppbo.data.entities.PersonalBankAccount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

import static com.ppbo.data.repositories.AccountCenter.setActiveAccount;

public class Login {
    Scanner scan = new Scanner(System.in);

    public boolean authenticate() {
        JSONArray accountList = new AccountCenter().getAccountList();

        String accountNumber;
        String password;

        System.out.printf("\n%-15s: ", "Account Number");
        accountNumber = scan.nextLine();

        System.out.printf("%-15s: ", "Password");
        password = scan.nextLine();


        // Iterate over employee array
        for (Object acc : accountList) {
            JSONObject account = (JSONObject) acc;
            String number = (String) account.get("account number");
            String pass = (String) account.get("password");

            if (number.equals(accountNumber) && pass.equals(password)) {
                String accountType = (String) account.get("account type");

                switch (accountType) {
                    case "Personal Account": {
                        String name = (String) account.get("name");
                        String nik = (String) account.get("nik");
                        String address = (String) account.get("address");
                        int balance = ((Number) account.get("balance")).intValue();

                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("[BankRupt]");
                        System.out.println("Login was successfully.");
                        System.out.println("Welcome to BankRupt " + name + "!\n");
                        System.out.print("Press enter to continue...");
                        scan.nextLine();

                        setActiveAccount(new PersonalBankAccount(name, address, nik, accountNumber, password, balance));
                        break;
                    }
                    case "Department Account": {
                        String name = (String) account.get("name");
                        String npwp = (String) account.get("npwp");
                        String address = (String) account.get("address");
                        int balance = ((Number) account.get("balance")).intValue();

                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("[BankRupt]");
                        System.out.println("Login was successfully.");
                        System.out.println("Welcome to BankRupt " + name + "!\n");
                        System.out.print("Press enter to continue...");
                        scan.nextLine();

                        setActiveAccount(new DepartmentBankAccount(name, address, npwp, accountNumber, password, balance));
                        break;
                    }
                    case "Loan Account": {
                        String name = (String) account.get("name");
                        String nik = (String) account.get("nik");
                        String address = (String) account.get("address");
                        int balance = ((Number) account.get("balance")).intValue();
                        int tenor = ((Number) account.get("tenor")).intValue();

                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("[BankRupt]");
                        System.out.println("Login was successfully.");
                        System.out.println("Welcome to BankRupt " + name + "!\n");
                        System.out.print("Press enter to continue...");
                        scan.nextLine();

                        setActiveAccount(new LoanBankAccount(name, address, nik, accountNumber, password, balance, tenor));
                        break;
                    }
                }

                return true;
            }
        }

        System.out.println("\nAccount was not found. Please, create account first.");
        System.out.print("Press enter to continue...");
        scan.nextLine();

        return false;
    }

    {
        scan.close();
    }
}
