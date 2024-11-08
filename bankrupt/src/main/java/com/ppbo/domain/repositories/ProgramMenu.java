package com.ppbo.domain.repositories;

import java.util.Scanner;

public interface ProgramMenu {
    public int login(Scanner scan);

    public int mainMenu();

    public int createAccountMenu();

    public int updateAccountMenu();

    int mainMenu(Scanner scan);

    int createAccountMenu(Scanner scan);

    int updateAccountMenu(Scanner scan);
}
