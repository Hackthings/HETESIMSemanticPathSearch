package main.java.ownClasses.domain.domainControllers.Drivers;


import main.java.ownClasses.domain.domainControllers.DomainMainController;

public class DriverDomainMainController {

    public static void main(String[] args) {
        DomainMainController dmc = new DomainMainController();
        System.out.println("Provant funcio newQuery()");
        dmc.newQuery();
        System.out.println("Provant funcio editGraph()");
        dmc.editGraph();
        System.out.println("Fi prova");
    }
}
