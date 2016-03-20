package main.java.domainControllers;

import main.java.domain.nodes.Author;
import main.java.domain.nodes.Conference;
import main.java.domain.nodes.Paper;
import main.java.domain.nodes.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DomainMainController {
    HashMap<Integer, Author> authors;
    HashMap<Integer, Paper> papers;
    HashMap<Integer, Conference> conferences;
    HashMap<Integer, Term> terms;


    public DomainMainController() {
    }

    public int[][] getAuthorPaperMatrix() {
        int[][] authorpaper = new int[500000][750000];  //rows authors id (till 500000),
        // columns paper id(till 750000)

        for (Author aut : authors.values()) {

            HashMap<Integer, Paper> autp = aut.getPapers();

            for (Paper pap : autp.values()) {

                authorpaper[aut.getId()][pap.getId()] = 1;
            }
        }

        return authorpaper;
    }

    public int[][] getPaperAuthorMatrix() {
        int[][] paperauthor = new int[750000][500000];  //rows authors id (till 500000),
        // columns paper id(till 750000)
        for (Paper pap : papers.values()) {

            HashMap<Integer, Author> pa = pap.getAuthors();

            for (Author aut : pa.values()) {

                paperauthor[pap.getId()][aut.getId()] = 1;
            }
        }

        return paperauthor;
    }

    public int[][] getTermPaperMatrix() {
        int[][] termpaper = new int[15000][750000];

        for (Term term : terms.values()) {

            HashMap<Integer, Paper> termpap = term.getPapersWhichTalkAboutThis();

            for (Paper pap : termpap.values()) {

                termpaper[term.getId()][pap.getId()] = 1;
            }
        }

        return termpaper;
    }

    public int[][] getPaperTermMatrix() {
        int[][] paperterm = new int[750000][15000];

        for (Paper pap : papers.values()) {

            HashMap<Integer, Term> papterm = pap.getTerms();

            for (Term term : papterm.values()) {

                paperterm[pap.getId()][term.getId()] = 1;
            }
        }

        return paperterm;
    }

    public int[][] geConferencePaperMatrix() {
        int[][] confpaper = new int[5000][750000];

        for (Conference conf : conferences.values()) {

            HashMap<Integer, Paper> confpap = conf.getExposedPapers();

            for (Paper pap : confpap.values()) {

                confpaper[conf.getId()][pap.getId()] = 1;
            }
        }

        return confpaper;
    }

    public int[][] getPaperConferenceMatrix() {
        int[][] paperconf = new int[750000][5000];

        for (Paper pap : papers.values()) {

            Conference conf = pap.getConference();

            paperconf[pap.getId()][conf.getId()] = 1;
        }

        return paperconf;
    }
}
