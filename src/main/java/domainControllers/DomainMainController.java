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
    private DomainEditController editController;

    private int authorMaxId;
    private int paperMaxId;
    private int conferenceMaxId;
    private int termMaxId;


    public DomainMainController() {
        editController = new DomainEditController();
        authorMaxId = 0;
        paperMaxId = 0;
        conferenceMaxId = 0;
        termMaxId = 0;
        editController.readAll(authors, papers, terms, conferences, authorMaxId, paperMaxId, termMaxId, conferenceMaxId);
    }

    public void newQuery() {
        //hetesimController.newQuery();
    }

    public void editGraph() {
        DomainEditController domainEditController = new DomainEditController();
        domainEditController.newEdit(authors,papers,terms,conferences,authorMaxId,paperMaxId,termMaxId,conferenceMaxId);

    }

    /*
    public int[][] getAuthorPaperMatrix() {
        int[][] authorpaper = new int[authorMaxId][paperMaxId];  // Rows: authors id (till 500000),
        // Columns: paper id(till 750000)
        for (Author aut : authors.values()) {
            HashMap<Integer, Paper> autp = aut.getPapers();
            for (Paper pap : autp.values()) {
                authorpaper[aut.getId()][pap.getId()] = 1;
            }
        }
        return authorpaper;
    }

    public int[][] getPaperAuthorMatrix() {
        int[][] paperauthor = new int[paperMaxId][authorMaxId];  // Rows: authors id (till 500000),
        // Columns: paper id(till 750000)
        for (Paper pap : papers.values()) {
            HashMap<Integer, Author> pa = pap.getAuthors();
            for (Author aut : pa.values()) {
                paperauthor[pap.getId()][aut.getId()] = 1;
            }
        }
        return paperauthor;
    }

    public int[][] getTermPaperMatrix() {
        int[][] termpaper = new int[termMaxId][paperMaxId];
        for (Term term : terms.values()) {
            HashMap<Integer, Paper> termpap = term.getPapersWhichTalkAboutThis();
            for (Paper pap : termpap.values()) {
                termpaper[term.getId()][pap.getId()] = 1;
            }
        }
        return termpaper;
    }

    public int[][] getPaperTermMatrix() {
        int[][] paperterm = new int[paperMaxId][termMaxId];
        for (Paper pap : papers.values()) {
            HashMap<Integer, Term> papterm = pap.getTerms();
            for (Term term : papterm.values()) {
                paperterm[pap.getId()][term.getId()] = 1;
            }
        }
        return paperterm;
    }

    public int[][] getConferencePaperMatrix() {
        int[][] confpaper = new int[conferenceMaxId][paperMaxId];
        for (Conference conf : conferences.values()) {
            HashMap<Integer, Paper> confpap = conf.getExposedPapers();
            for (Paper pap : confpap.values()) {
                confpaper[conf.getId()][pap.getId()] = 1;
            }
        }
        return confpaper;
    }

    public int[][] getPaperConferenceMatrix() {
        int[][] paperconf = new int[paperMaxId][conferenceMaxId];
        for (Paper pap : papers.values()) {
            Conference conf = pap.getConference();
            paperconf[pap.getId()][conf.getId()] = 1;
        }
        return paperconf;
    } */
}
