package main.java;

import main.java.ownClasses.domain.domainControllers.DomainMainController;
import main.java.ownClasses.presentation.ConsolePrinter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
