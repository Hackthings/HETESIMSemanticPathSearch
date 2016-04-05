package main.java.domainControllers;

import main.java.presentation.ConsolePrinter;

import java.util.Scanner;

public class DomainGlobalController extends DomainMainController {
    private ConsolePrinter consolePrinter;

    public DomainGlobalController() {
        consolePrinter = new ConsolePrinter();
    }

    public void newQuery() {
        Scanner sc = new Scanner(System.in);

        DomainHetesimController hetesimController = new DomainHetesimController(getAuthorPaperMatrix(),
                                                                                getPaperAuthorMatrix(),
                                                                                getTermPaperMatrix(),
                                                                                getPaperTermMatrix(),
                                                                                getConferencePaperMatrix(),
                                                                                getPaperConferenceMatrix());

        consolePrinter.printQueryInputMessage();
        String query = sc.nextLine();
        hetesimController.heteSim(query);
    }

    public void editGraph() {
        Scanner sc = new Scanner(System.in);

        // TODO: Implement when Ian finishes the DomainEditController.
    }

}
