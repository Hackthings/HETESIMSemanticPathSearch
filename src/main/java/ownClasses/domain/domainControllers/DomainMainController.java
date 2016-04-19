package main.java.ownClasses.domain.domainControllers;

import main.java.sharedClasses.domain.domainControllers.DomainPersistanceController;
import main.java.sharedClasses.domain.nodes.Author;
import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;
import main.java.sharedClasses.domain.nodes.Term;
import main.java.sharedClasses.utils.Matrix;

import java.util.*;

public class DomainMainController {
    private HashMap<Integer, Author> authorsById;
    private HashMap<Integer, Paper> papersById;
    private HashMap<Integer, Conference> conferencesById;
    private HashMap<Integer, Term> termsById;
    private HashMap<String, Author> authorsByName;
    private HashMap<String, Paper> papersByName;
    private HashMap<String, Conference> conferencesByName;
    private HashMap<String, Term> termsByName;
    private DomainPersistanceController persistanceController;
    private DomainHetesimController hetesimController;

    private int authorMaxId;
    private int paperMaxId;
    private int conferenceMaxId;
    private int termMaxId;


    public DomainMainController() {
        persistanceController = new DomainPersistanceController();

        authorMaxId = 0;
        paperMaxId = 0;
        conferenceMaxId = 0;
        termMaxId = 0;
        persistanceController.readAll(authorsById, papersById, conferencesById, termsById, authorsByName, papersByName, conferencesByName, termsByName, authorMaxId, paperMaxId, termMaxId, conferenceMaxId);
    }

    public void newQuery() {
        // TODO: Sergi needs to fix the Hetesim Controller
        //hetesimController.newQuery();
    }

    public void editGraph() {
        DomainPersistanceController domainPersistanceController = new DomainPersistanceController();
        domainPersistanceController.newEdit(authorsById, papersById, conferencesById, termsById, authorsByName, papersByName, conferencesByName,termsByName, authorMaxId, paperMaxId, termMaxId, conferenceMaxId);
    }

    public Matrix getAuthorPaperMatrix(){
        Matrix authorpaper = new Matrix();

        for(Author author : authorsById.values()){
            HashMap<Integer,Paper> papersOfAuthor = author.getPapersById();
            for(Paper paper : papersOfAuthor.values()){
                authorpaper.addValue(author.getId(),paper.getId(),1.0);
            }
        }
        return authorpaper;
    }

    public Matrix getPaperAuthorMatrix(){
        Matrix paperauthor = new Matrix();

        for(Paper paper : papersById.values()){
            HashMap<Integer,Author> authorsOfPaper = paper.getAuthorsById();
            for(Author author : authorsOfPaper.values()){
                paperauthor.addValue(paper.getId(),author.getId(),1.0);
            }
        }
        return paperauthor;
    }

    public Matrix getTermPaperMatrix(){
        Matrix termpaper = new Matrix();

        for(Term term : termsById.values()){
            HashMap<Integer,Paper> papersOfTerm = term.getPapersWhichTalkAboutThisById();
            for(Paper paper : papersOfTerm.values()){
                termpaper.addValue(term.getId(),paper.getId(),1.0);
            }
        }

        return termpaper;
    }

    public Matrix getPaperTermMatrix() {
        Matrix paperterm = new Matrix();

        for (Paper paper : papersById.values()) {
            HashMap<Integer, Term> termsOfPaper = paper.getTermsById();
            for (Term term : termsOfPaper.values()) {
                paperterm.addValue(paper.getId(), term.getId(), 1.0);
            }
        }
        return paperterm;
    }

    public Matrix getConferencePaperMatrix(){
        Matrix conferencepaper = new Matrix();

        for(Conference conf : conferencesById.values()){
            HashMap<Integer,Paper> papersOfConf = conf.getExposedPapersById();
            for(Paper paper : papersOfConf.values()){
                conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
            }
        }

        return conferencepaper;
    }

    public Matrix getPaperConferenceMatrix(){
        Matrix paperconference = new Matrix();

        for(Paper paper : papersById.values()){
            paperconference.addValue(paper.getId(),paper.getConference().getId(),1.0);
        }

        return paperconference;
    }

}

