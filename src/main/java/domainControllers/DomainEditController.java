package main.java.domainControllers;

import main.java.domain.nodes.Author;
import main.java.domain.nodes.Conference;
import main.java.domain.nodes.Paper;
import main.java.domain.nodes.Term;

import java.util.HashMap;

public class DomainEditController {
    HashMap<Integer, Author> authors;
    HashMap<Integer, Paper> papers;
    HashMap<Integer, Conference> conferences;
    HashMap<Integer, Term> terms;

    public DomainEditController() {}

    public void readAuthorsFromFile(){}

    public void readPapersFromFile(){}

    public void readConferencesFromFile(){}

    public void readTermsFromFile(){}


    public void writeAuthorToFile(Author author){}

    public void writePaperToFile(Paper paper){}

    public void writeConferenceToFile(Conference conference){}

    public void writeTermToFile(Term term){}


    public void deleteAuthorFromFile(Author author){}

    public void deletePaperFromFile(Paper paper){}

    public void deleteConferenceFromFile(Conference conference){}

    public void deleteTermFromFile(Term term){}



}
