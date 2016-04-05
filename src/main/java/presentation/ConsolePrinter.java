package main.java.presentation;

public class ConsolePrinter {

    public ConsolePrinter() {}

    public void printMenu() {
        System.out.println("-1. Nova query.");
        System.out.println("-2. Editar graf.");
        System.out.println("-3. Sortir.");
    }

    public void printQueryInputMessage() {
        System.out.println("Escriu la teva query: (exemple: APA == Author - Paper - Author");
    }

    public void printEditInsertOrDelete() {
        System.out.println("Vols editar, insertar o eliminar? (Ed -> editar, In -> insertar, El -> eliminar");
    }

    public void printTypeInputEditMessage() {
        System.out.println("Quin tipus d'objecte vols modificar? (A -> Author, P -> Paper, T -> Term, C -> Conference");
    }
}
