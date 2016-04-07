package main.java;

import main.java.domainControllers.DomainMainController;
import main.java.presentation.ConsolePrinter;

import java.util.Scanner;

public class Main {

    public static void Main() {
        final int EXIT = -3;
        final int NEW_QUERY = -1;
        final int EDIT = -2;

        ConsolePrinter consolePrinter = new ConsolePrinter();

        Scanner sc = new Scanner(System.in);
        DomainMainController mainController = new DomainMainController();

        int option;
        do {
            consolePrinter.printMenu();
            option = sc.nextInt();
            if (option == NEW_QUERY) mainController.newQuery();
            else if (option == EDIT) mainController.editGraph();
        } while (option != EXIT);
    }
}
