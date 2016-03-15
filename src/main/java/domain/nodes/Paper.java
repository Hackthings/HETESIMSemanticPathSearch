package main.java.domain.nodes;

import java.util.ArrayList;

public class Paper extends Node {
    private Conference conference;
    private ArrayList<Author> authors;
    private ArrayList<Term> terms;

    public Paper(String name, int id) {
        super(name, id);
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void addTerm(Term term) {
        terms.add(term);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    public void removeTerm(Term term) {
        terms.remove(term);
    }

    public Conference getConference() {
        return conference;
    }
    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public ArrayList<Term> getTerms() {
        return terms;
    }

}
