package com.ppbo.data.repositories;

import com.ppbo.exception.MenuException;

import java.util.Scanner;

public class ProgramMenu{
    public int login(Scanner scan) {
        int loginChoice = -1;

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("[BankRupt]");
        System.out.println("Welcome to BankRupt!\n");

        System.out.println("Please, login to your account first. ");
        System.out.println("[1] Already have an account?");
        System.out.println("[2] Don't have an account?");
        System.out.print("Your choice: ");

        try {
            System.out.println(scan.hasNextInt());
            loginChoice = Integer.parseInt(scan.next());
            scan.nextLine();

            if (loginChoice < 1 || loginChoice > 2) {
                throw new MenuException("Invalid input. Please enter number 1 or 2.\n");
            }
        } catch (MenuException e) {
            System.out.println(e.getMessage());
            return createAccountMenu(scan);
        }

        scan.close();

        return loginChoice;
    }

    public int mainMenu(Scanner scan) {
        int getChoice = -1;

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("[BankRupt]");
        System.out.println("Welcome to BankRupt!\n");

        System.out.println("[1] View Bank Account");
        System.out.println("[2] Deposit");
        System.out.println("[3] Withdraw");
        System.out.println("[4] Bayar Cicilan");
        System.out.println("[5] Transfer");
        System.out.println("[0] Exit");
        System.out.print("Your choice: ");

        try {
            getChoice = scan.nextInt();
            scan.nextLine();

            if (getChoice < 0 || getChoice > 6) {
                throw new MenuException("Invalid input. Please enter a number between 0 and 6.\n");
            }
        } catch (MenuException e) {
            System.out.println(e.getMessage());
            return mainMenu(scan);
        }

        return getChoice;
    }

    public int createAccountMenu(Scanner scan) {
        int getChoice = -1;

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("[BankRupt]");
        System.out.println("Welcome to BankRupt!\n");

        System.out.println("[1] Personal Account");
        System.out.println("[2] Department Account");
        System.out.println("[3] Loan Account");
        System.out.println("[0] Back");
        System.out.print("Your choice: ");

        try {
            getChoice = scan.nextInt();
            scan.nextLine();

            if (getChoice < 0 || getChoice > 3) {
                throw new MenuException("Invalid input. Please enter a number between 0 and 3.\n");
            }
        } catch (MenuException e) {
            System.out.println(e.getMessage());
            return createAccountMenu(scan);
        }

        return getChoice;
    }

    public int updateAccountMenu(Scanner scan) {
        int getChoice = -1;

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("[BankRupt]");
        System.out.println("Welcome to BankRupt!\n");

        System.out.println("[1] Update Name");
        System.out.println("[2] Update NIK");
        System.out.println("[3] Update Address");
        System.out.println("[4] Update Password");
        System.out.println("[0] Back to Main Menu");
        System.out.print("Your choice: ");

        try {
            getChoice = scan.nextInt();
            scan.nextLine();

            if (getChoice < 0 || getChoice > 4) {
                throw new MenuException("Invalid input. Please enter a number between 0 and 4.\n");
            }
        } catch (MenuException e) {
            System.out.println(e.getMessage());
            return updateAccountMenu(scan);
        }

        return getChoice;
    }
}
