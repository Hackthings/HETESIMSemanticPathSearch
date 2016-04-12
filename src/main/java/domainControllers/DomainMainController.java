package main.java.domainControllers;

import main.java.domain.nodes.Author;
import main.java.domain.nodes.Conference;
import main.java.domain.nodes.Paper;
import main.java.domain.nodes.Term;

import java.util.*;

public class DomainMainController {
    private HashMap<Integer, Author> authors;
    private HashMap<Integer, Paper> papers;
    private HashMap<Integer, Conference> conferences;
    private HashMap<Integer, Term> terms;
    private DomainPersistanceController persistanceController;

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
        persistanceController.readAll(authors, papers, terms, conferences, authorMaxId, paperMaxId, termMaxId, conferenceMaxId);
    }

    public void newQuery() {
        //hetesimController.newQuery();
    }

    public void editGraph() {
        DomainPersistanceController domainPersistanceController = new DomainPersistanceController();
        domainPersistanceController.newEdit(authors,papers,terms,conferences,authorMaxId,paperMaxId,termMaxId,conferenceMaxId);

    }


    public HashMap<Integer, HashMap<Integer, Double>> getAuthorPaperMatrix() {

        HashMap<Integer, HashMap<Integer, Double>> authorPaper = new HashMap<Integer, HashMap<Integer, Double>>();

        for (Author aut : authors.values()) {
            HashMap<Integer, Paper> papersOfAuthor = aut.getPapers();
            HashMap<Integer, Double> papersOfAuthorIDs = new HashMap<Integer, Double>();
            for (Paper paper : papersOfAuthor.values()) {
                papersOfAuthorIDs.put(paper.getId(),1.0);
            }
            authorPaper.put(aut.getId(),papersOfAuthorIDs);
        }
        return authorPaper;
    }


    public HashMap<Integer, HashMap<Integer, Double>> getPaperAuthorMatrix() {
        HashMap<Integer, HashMap<Integer, Double>> paperAuthor = new HashMap<Integer, HashMap<Integer, Double>>();

        for (Paper pap : papers.values()) {
            HashMap<Integer, Author> authorsOfPaper = pap.getAuthors();
            HashMap<Integer, Double> authorsOfPaperIDs = new HashMap<Integer, Double>();
            for (Author author : authorsOfPaper.values()) {
                authorsOfPaperIDs.put(author.getId(),1.0);
            }
            paperAuthor.put(pap.getId(),authorsOfPaperIDs);
        }
        return paperAuthor;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getTermPaperMatrix() {
        HashMap<Integer, HashMap<Integer, Double>> termPaper = new HashMap<Integer, HashMap<Integer, Double>>();
        for (Term term : terms.values()) {
            HashMap<Integer, Paper> papersOfTerm = term.getPapersWhichTalkAboutThis();
            HashMap<Integer, Double> papersOfTermIDs = new HashMap<Integer, Double>();
            for (Paper pap : papersOfTerm.values()) {
                papersOfTermIDs.put(term.getId(),1.0);
            }
            termPaper.put(term.getId(),papersOfTermIDs);
        }
        return termPaper;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getPaperTermMatrix() {
        HashMap<Integer, HashMap<Integer, Double>> paperTerm = new HashMap<Integer, HashMap<Integer, Double>>();
        for (Paper pap : papers.values()) {
            HashMap<Integer, Term> termsOfPapers = pap.getTerms();
            HashMap<Integer, Double> termsOfPaperIDs = new HashMap<Integer, Double>();
            for (Term term : termsOfPapers.values()) {
                termsOfPaperIDs.put(term.getId(),1.0);
            }
            paperTerm.put(pap.getId(),termsOfPaperIDs);
        }
        return paperTerm;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getConferencePaperMatrix() {
        HashMap<Integer, HashMap<Integer, Double>> confPaper = new HashMap<Integer, HashMap<Integer, Double>>();
        for (Conference conf : conferences.values()) {
            HashMap<Integer, Paper> papersOfConf = conf.getExposedPapers();
            HashMap<Integer, Double> papersOfConfIDs = new HashMap<Integer, Double>();
            for (Paper pap : papersOfConf.values()) {
                papersOfConfIDs.put(pap.getId(),1.0);
            }
            confPaper.put(conf.getId(),papersOfConfIDs);
        }
        return confPaper;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getPaperConferenceMatrix() {
        HashMap<Integer, HashMap<Integer, Double>> paperConf = new HashMap<Integer, HashMap<Integer, Double>>();
        for (Paper pap : papers.values()) {
            Conference conf = pap.getConference();
            HashMap<Integer, Double> confOfPaperID = new HashMap<Integer, Double>();

            confOfPaperID.put(conf.getId(),1.0);

            paperConf.put(pap.getId(),confOfPaperID);
        }
        return paperConf;
    }
}
