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


    public DomainMainController() {}

    public int[][] getAuthorPaperMatrix() {
        int[][] authorpaper = new int[500000][750000];  //rows authors id (till 500000),
                                                        // columns paper id(till 750000)

        for (Author aut : authors.values()) {

            HashMap<Integer, Paper> autp = aut.getPapers();

            for(Paper pap : autp.values()) {

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

            for(Author aut : pa.values()) {

                paperauthor[aut.getId()][pap.getId()] = 1;
            }
        }

        return paperauthor;
    }

    public int[][] getTermPaperMatrix() {
        return null;
    }

    public int[][] getPaperTermMatrix() {
        return null;
    }

    public int[][] geConferencePaperMatrix() {
        return null;
    }

    public int[][] getPaperConferenceMatrix() {
        return null;
    }
}
