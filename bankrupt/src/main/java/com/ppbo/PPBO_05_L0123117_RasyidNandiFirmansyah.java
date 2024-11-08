package com.ppbo;

import com.ppbo.data.repositories.AccountCenter;
import com.ppbo.data.repositories.BankFeatures;
import com.ppbo.data.repositories.Login;
import com.ppbo.data.repositories.ProgramMenu;
import com.ppbo.exception.MenuException;
import com.ppbo.exception.PaidException;
import com.ppbo.exception.WithdrawException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class PPBO_05_L0123117_RasyidNandiFirmansyah {
    public static void main(String[] args)
            throws MenuException, WithdrawException, PaidException, IOException, ParseException {
        Scanner scan = new Scanner(System.in);

        AccountCenter acc = new AccountCenter();
        Login login = new Login();
        ProgramMenu menu = new ProgramMenu();
        BankFeatures appFeatures = new BankFeatures();

        boolean isAuthenticated = false;

        while (!isAuthenticated) {
            int isAccountExist = menu.login(scan);
            int accountType;

            if (isAccountExist == 1) {
                if (!login.authenticate()) {
                    accountType = menu.createAccountMenu(scan);
                    acc.createAccount(accountType);
                } else {
                    isAuthenticated = true;
                }

            } else if (isAccountExist == 2) {
                accountType = menu.createAccountMenu(scan);
                acc.createAccount(accountType);
            }
        }

        int getChoice = menu.mainMenu(scan);

        while (getChoice != 0) {
            switch (getChoice) {
                case 1:
                    // membersihkan terminal
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    appFeatures.viewAccount();

                    getChoice = menu.mainMenu(scan);
                    break;

                case 2:
                    // membersihkan terminal
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    appFeatures.deposit();

                    getChoice = menu.mainMenu(scan);
                    break;
                case 3:
                    // membersihkan terminal
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    appFeatures.withdraw();

                    getChoice = menu.mainMenu(scan);
                    break;

                case 4:
                    // membersihkan terminal
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    appFeatures.payInstalment();

                    getChoice = menu.mainMenu(scan);
                    break;

                case 5:
                    // membersihkan terminal
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    appFeatures.transfer();

                    getChoice = menu.mainMenu(scan);
                    break;

                // case 6:
                // // membersihkan terminal
                // System.out.print("\033[H\033[2J");
                // System.out.flush();
                //
                // appFeatures.transfer();
                //
                // getChoice = menu.mainMenu();
                // break;
            }
        }

        System.out.println("Thank you for using BankRupt. Goodbye!\n");
    }
}
