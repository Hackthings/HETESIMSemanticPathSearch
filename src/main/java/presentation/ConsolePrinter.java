package main.java.presentation;

public class ConsolePrinter {

    public ConsolePrinter() {}

    public void printMenu() {
        System.out.println("-1. Nova query.");
        System.out.println("-2. Editar graf.");
        System.out.println("-3. Sortir.");
    }

    public void printQueryInputMessage() {
        System.out.println("Write your query: (example: APA == Author - Paper - Author");
    }
}
