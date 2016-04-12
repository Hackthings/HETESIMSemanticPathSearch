package main.java.domain.nodes;

import java.util.HashMap;

public class Paper extends Node {
    private Conference conference;
    private HashMap<Integer, Author> authors;
    private HashMap<Integer, Term> terms;

    public Paper(String name, int id) {
        super(name, id);
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public void addAuthor(int id, Author author) {
        authors.put(id, author);
    }

    public void addTerm(int id, Term term) {
        terms.put(id, term);
    }

    public void removeAuthor(int id) {
        authors.remove(id);
    }

    public void removeTerm(int id) {
        terms.remove(id);
    }

    public Conference getConference() {
        return conference;
    }
    
    public HashMap<Integer, Author> getAuthors() {
        return authors;
    }

    public Author getAuthorByName (String name){

        for(Author a : authors.values()){
            if(a.getName().equals(name)) return a;
        }
        return null;
    }

    public HashMap<Integer, Term> getTerms() {
        return terms;
    }

    public Term getTermByName (String name){
        for(Term t : terms.values()){
            if(t.getName().equals(name)) return t;
        }
        return null;
    }

}
