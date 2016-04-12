package main.java.domainControllers;

import main.java.utils.Pair;
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


    public HashMap<Integer,ArrayList<Pair<Integer,Double>>> getAuthorPaperMatrix() {

        HashMap<Integer, ArrayList<Pair<Integer, Double>>> authorPaper = new HashMap<>();

        for (Author aut : authors.values()) {
            HashMap<Integer, Paper> papersOfAuthor = aut.getPapers();
            ArrayList<Pair<Integer,Double>> papersOfAuthorIDs = new ArrayList<>();
            for (Paper paper : papersOfAuthor.values()) {
                Pair<Integer,Double> parID= new Pair(paper.getId(),1.0);
                papersOfAuthorIDs.add(parID);
            }
            authorPaper.put(aut.getId(),papersOfAuthorIDs);
        }
        return authorPaper;
    }


    public HashMap<Integer, ArrayList<Pair<Integer, Double>>> getPaperAuthorMatrix() {
        HashMap<Integer, ArrayList<Pair<Integer, Double>>> paperAuthor = new HashMap<>();

        for (Paper pap : papers.values()) {
            HashMap<Integer, Author> authorsOfPaper = pap.getAuthors();
            ArrayList<Pair<Integer,Double>> authorsOfPaperIDs = new ArrayList<>();
            for (Author author : authorsOfPaper.values()) {
                Pair<Integer,Double> parID= new Pair(author.getId(),1.0);
                authorsOfPaperIDs.add(parID);
            }
            paperAuthor.put(pap.getId(),authorsOfPaperIDs);
        }
        return paperAuthor;
    }

    public HashMap<Integer, ArrayList<Pair<Integer, Double>>> getTermPaperMatrix() {
        HashMap<Integer, ArrayList<Pair<Integer, Double>>> termPaper = new HashMap<>();
        for (Term term : terms.values()) {
            HashMap<Integer, Paper> papersOfTerm = term.getPapersWhichTalkAboutThis();
            ArrayList<Pair<Integer,Double>> papersOfTermIDs = new ArrayList<>();
            for (Paper pap : papersOfTerm.values()) {
                Pair<Integer,Double> parID= new Pair(pap.getId(),1.0);
                papersOfTermIDs.add(parID);
            }
            termPaper.put(term.getId(),papersOfTermIDs);
        }
        return termPaper;
    }

    public HashMap<Integer, ArrayList<Pair<Integer, Double>>> getPaperTermMatrix() {
        HashMap<Integer, ArrayList<Pair<Integer, Double>>> paperTerm = new HashMap<>();
        for (Paper pap : papers.values()) {
            HashMap<Integer, Term> termsOfPapers = pap.getTerms();
            ArrayList<Pair<Integer,Double>> termsOfPaperIDs = new ArrayList<>();
            for (Term term : termsOfPapers.values()) {
                Pair<Integer,Double> parID= new Pair(term.getId(),1.0);
                termsOfPaperIDs.add(parID);
            }
            paperTerm.put(pap.getId(),termsOfPaperIDs);
        }
        return paperTerm;
    }

    public HashMap<Integer, ArrayList<Pair<Integer, Double>>> getConferencePaperMatrix() {
        HashMap<Integer, ArrayList<Pair<Integer, Double>>> confPaper = new HashMap<>();
        for (Conference conf : conferences.values()) {
            HashMap<Integer, Paper> papersOfConf = conf.getExposedPapers();
            ArrayList<Pair<Integer,Double>> papersOfConfIDs = new ArrayList<>();
            for (Paper pap : papersOfConf.values()) {
                Pair<Integer,Double> parID= new Pair(pap.getId(),1.0);
                papersOfConfIDs.add(parID);
            }
            confPaper.put(conf.getId(),papersOfConfIDs);
        }
        return confPaper;
    }

    public HashMap<Integer, ArrayList<Pair<Integer, Double>>> getPaperConferenceMatrix() {
        HashMap<Integer, ArrayList<Pair<Integer, Double>>> paperConf = new HashMap<>();
        for (Paper pap : papers.values()) {
            Conference conf = pap.getConference();
            ArrayList<Pair<Integer,Double>> confOfPaperID = new ArrayList<>();

            Pair<Integer,Double> parID= new Pair(conf.getId(),1.0);
            confOfPaperID.add(parID);

            paperConf.put(pap.getId(),confOfPaperID);
        }
        return paperConf;
    }
}
