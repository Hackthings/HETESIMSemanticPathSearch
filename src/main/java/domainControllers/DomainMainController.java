package main.java.domainControllers;

import main.java.domain.nodes.Author;
import main.java.domain.nodes.Conference;
import main.java.domain.nodes.Paper;
import main.java.domain.nodes.Term;

import java.util.*;

public class DomainMainController {
    HashMap<Integer, Author> authors;
    HashMap<Integer, Paper> papers;
    HashMap<Integer, Conference> conferences;
    HashMap<Integer, Term> terms;

    int authorMaxId = 0;
    int paperMaxId = 0;
    int conferenceMaxId = 0;
    int termMaxId = 0;


    public DomainMainController() {}

    public void setAuthorMaxId (int maxId){ authorMaxId = maxId; }

    public void setPaperMaxId (int maxId){ paperMaxId = maxId; }

    public void setConferenceMaxId (int maxId){ conferenceMaxId = maxId; }

    public void setTermMaxId (int maxId){ termMaxId = maxId; }

    public int[][] getAuthorPaperMatrix() {
        int[][] authorpaper = new int[authorMaxId][paperMaxId];  /* Rows: authors id (till 500000),
        Columns: paper id(till 750000) */
        for (Author aut : authors.values()) {
            HashMap<Integer, Paper> autp = aut.getPapers();
            for (Paper pap : autp.values()) {
                authorpaper[aut.getId()][pap.getId()] = 1;
            }
        }
        return authorpaper;
    }

    public int[][] getPaperAuthorMatrix() {
        int[][] paperauthor = new int[paperMaxId][authorMaxId];  /* Rows: authors id (till 500000),
        Columns: paper id(till 750000) */
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
    }
}
