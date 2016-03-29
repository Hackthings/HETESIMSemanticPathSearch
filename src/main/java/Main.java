package main.java;

import java.util.Scanner;

public class Main {

    public static void Main() {
        final int EXIT = -3;
        final int NEW_QUERY = -1;
        final int EDIT = -2;

        Scanner sc = new Scanner(System.in);
        // TODO: INIT GLOBAL MAIN CONTROLLER
        int option;
        do {
            System.out.println("-1. Nova query.");
            System.out.println("-2. Editar graf.");
            System.out.println("-3. Sortir.");
            option = sc.nextInt();
        } while (option != EXIT);
    }
}
