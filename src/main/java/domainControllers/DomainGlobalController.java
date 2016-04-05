package main.java.domainControllers;

public class DomainGlobalController extends DomainMainController {
    public DomainGlobalController() {

    }

    public void newQuery() {
        DomainHetesimController hetesimController = new DomainHetesimController(getAuthorPaperMatrix(),
                                                                                getPaperAuthorMatrix(),
                                                                                getTermPaperMatrix(),
                                                                                getPaperTermMatrix(),
                                                                                getConferencePaperMatrix(),
                                                                                getPaperConferenceMatrix());
        //hetesimController.newQuery();
    }

    public void editGraph() {
        DomainEditController domainEditController = new DomainEditController();
        domainEditController.newEdit(authors,papers,terms,conferences,authorMaxId,paperMaxId,termMaxId,conferenceMaxId);
        //Si veus aixo es que esta be
    }

}
