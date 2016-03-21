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

    int Authormaxid = 0;
    int Papermaxid = 0;
    int Conferencemaxid = 0;
    int Termmaxid = 0;


    public DomainMainController() {
    }


    public void setAuthormaxid (int id){
        Authormaxid = id;
    }

    public void setPapermaxid (int id){
        Papermaxid = id;
    }

    public void setConferencermaxid (int id){
        Conferencemaxid = id;
    }

    public void setTermmaxid (int id){
        Termmaxid = id;
    }

    public int[][] getAuthorPaperMatrix() {

        int[][] authorpaper = new int[Authormaxid][Papermaxid];  //rows authors id (till 500000),
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
        int[][] paperauthor = new int[Papermaxid][Authormaxid];  //rows authors id (till 500000),
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
        int[][] termpaper = new int[Termmaxid][Papermaxid];

        for (Term term : terms.values()) {

            HashMap<Integer, Paper> termpap = term.getPapersWhichTalkAboutThis();

            for (Paper pap : termpap.values()) {

                termpaper[term.getId()][pap.getId()] = 1;
            }
        }

        return termpaper;
    }

    public int[][] getPaperTermMatrix() {
        int[][] paperterm = new int[Papermaxid][Termmaxid];

        for (Paper pap : papers.values()) {

            HashMap<Integer, Term> papterm = pap.getTerms();

            for (Term term : papterm.values()) {

                paperterm[pap.getId()][term.getId()] = 1;
            }
        }

        return paperterm;
    }

    public int[][] geConferencePaperMatrix() {
        int[][] confpaper = new int[Conferencemaxid][Papermaxid];

        for (Conference conf : conferences.values()) {

            HashMap<Integer, Paper> confpap = conf.getExposedPapers();

            for (Paper pap : confpap.values()) {

                confpaper[conf.getId()][pap.getId()] = 1;
            }
        }

        return confpaper;
    }

    public int[][] getPaperConferenceMatrix() {
        int[][] paperconf = new int[Papermaxid][Conferencemaxid];

        for (Paper pap : papers.values()) {

            Conference conf = pap.getConference();

            paperconf[pap.getId()][conf.getId()] = 1;
        }

        return paperconf;
    }
}
