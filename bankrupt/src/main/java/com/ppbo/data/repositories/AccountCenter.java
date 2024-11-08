package com.ppbo.data.repositories;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountCenter {
    private static BankAccount activeAccount;
    private JSONParser jsonParser = new JSONParser();
    private JSONArray accountList = new JSONArray();
    private JSONObject account = new JSONObject();

    public AccountCenter() {
        this.loadBankAccount();
    }

    public static BankAccount getActiveAccount() {
        return activeAccount;
    }

    public static void setActiveAccount(BankAccount activeAccount) {
        AccountCenter.activeAccount = activeAccount;
    }

    public JSONArray getAccountList() {
        return accountList;
    }

    private void loadBankAccount() {
        File file = new File("accounts.json");

        try (FileReader reader = new FileReader(file)) {
            // Parsing file JSON jika tidak kosong
            Object obj = this.jsonParser.parse(reader);
            this.accountList = (JSONArray) obj;
        } catch (IOException e) {
            System.out.println("System: accounts.json not found or cannot be read.");
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file.");
        }
    }

    private AtomicInteger getLastAccNumber() {
        if (accountList.isEmpty()) {
            return null;
        }

        JSONObject lastAccount = (JSONObject) this.accountList.get(accountList.size() - 1);
        String lastAccNumberStr = (String) lastAccount.get("account number"); // Ambil sebagai String

        try {
            int lastAccNumber = Integer.parseInt(lastAccNumberStr); // Konversi ke int
            return new AtomicInteger(lastAccNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null; // Mengembalikan null jika parsing gagal
        }
    }

    @SuppressWarnings("unchecked")
    public void createAccount(int accType) throws IOException {
        final String name;
        final String address;
        final String accountNumber;
        final String password;
        final int balance;

        AtomicInteger accountNumberGenerator = getLastAccNumber();
        Scanner scan = new Scanner(System.in);

        if (accountNumberGenerator == null) {
            accountNumberGenerator = new AtomicInteger(1000000);
        }

        // Create personal bank account
        if (accType == 1) {
            final String nik;

            System.out.printf("%-10s: ", "Name");
            name = scan.nextLine();

            System.out.printf("%-10s: ", "NIK");
            nik = scan.nextLine();

            System.out.printf("%-10s: ", "Address");
            address = scan.nextLine();

            accountNumber = String.valueOf(accountNumberGenerator.incrementAndGet());

            System.out.printf("%-10s: ", "Password");
            password = scan.nextLine();

            System.out.printf("%-10s: ", "Balance");
            balance = scan.nextInt();
            scan.nextLine();

            account.put("name", name);
            account.put("address", address);
            account.put("nik", nik);
            account.put("account number", accountNumber);
            account.put("account type", "Personal Account");
            account.put("password", password);
            account.put("balance", balance);
            this.accountList.add(account);

            try {
                FileWriter file = new FileWriter("accounts.json");
                file.write(accountList.toJSONString());
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        // Create department bank account
        else if (accType == 2) {
            final String npwp;

            System.out.printf("%-10s: ", "Name");
            name = scan.nextLine();

            System.out.printf("%-10s: ", "NPWP");
            npwp = scan.nextLine();

            System.out.printf("%-10s: ", "Address");
            address = scan.nextLine();

            accountNumber = String.valueOf(accountNumberGenerator.incrementAndGet());

            System.out.printf("%-10s: ", "Password");
            password = scan.nextLine();

            System.out.printf("%-10s: ", "Balance");
            balance = scan.nextInt();
            scan.nextLine();

            account.put("name", name);
            account.put("address", address);
            account.put("npwp", npwp);
            account.put("account number", accountNumber);
            account.put("account type", "Department Account");
            account.put("password", password);
            account.put("balance", balance);
            accountList.add(account);

            try {
                FileWriter file = new FileWriter("accounts.json");
                file.write(accountList.toJSONString());
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        // Create loan bank account
        else if (accType == 3) {
            final String nik;
            final int tenor;

            System.out.printf("%-10s: ", "Name");
            name = scan.nextLine();

            System.out.printf("%-10s: ", "NIK");
            nik = scan.nextLine();

            System.out.printf("%-10s: ", "Address");
            address = scan.nextLine();

            accountNumber = String.valueOf(accountNumberGenerator.incrementAndGet());

            System.out.printf("%-10s: ", "Password");
            password = scan.nextLine();

            System.out.printf("%-10s: ", "Loan Bal.");
            balance = scan.nextInt();

            System.out.printf("%-10s: ", "Tenor");
            tenor = scan.nextInt();

            scan.nextLine();

            account.put("name", name);
            account.put("address", address);
            account.put("nik", nik);
            account.put("account number", accountNumber);
            account.put("account type", "Loan Account");
            account.put("password", password);
            account.put("balance", balance);
            account.put("tenor", tenor);
            accountList.add(account);

            try {
                FileWriter file = new FileWriter("accounts.json");
                file.write(accountList.toJSONString());
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        } else if (accType == 0) {
            return;
        }

        this.loadBankAccount();

        System.out.println("Account was successfully created.\n");
        System.out.print("Press enter to continue...");
        scan.nextLine();
    }
}
