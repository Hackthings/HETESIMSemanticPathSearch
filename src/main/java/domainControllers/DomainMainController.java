package main.java.domainControllers;

import main.java.domain.nodes.Author;
import main.java.domain.nodes.Conference;
import main.java.domain.nodes.Paper;
import main.java.domain.nodes.Term;

import java.util.ArrayList;
import java.util.HashMap;

public class DomainMainController {
    HashMap<Integer, Author> authors;
    HashMap<Integer, Paper> papers;
    HashMap<Integer, Conference> conferences;
    HashMap<Integer, Term> terms;


    public DomainMainController() {}

    public int[][] getAuthorPaperMatrix() {
        return null;
    }

    public int[][] getPaperAuthorMatrix() {
        return null;
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
