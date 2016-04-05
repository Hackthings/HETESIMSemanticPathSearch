package main.java;

import main.java.domainControllers.DomainGlobalController;
import main.java.presentation.ConsolePrinter;

import java.util.Scanner;

public class Main {

    public static void Main() {
        final int EXIT = -3;
        final int NEW_QUERY = -1;
        final int EDIT = -2;

        ConsolePrinter consolePrinter = new ConsolePrinter();

        Scanner sc = new Scanner(System.in);
        DomainGlobalController globalController = new DomainGlobalController();

        int option;
        do {
            consolePrinter.printMenu();
            option = sc.nextInt();
            if (option == NEW_QUERY) globalController.newQuery();
            else if (option == EDIT) globalController.editGraph();
        } while (option != EXIT);
    }
}
