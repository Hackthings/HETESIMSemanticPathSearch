package main.java.domain.nodes;

import java.util.HashMap;

public class Paper extends Node {
    private Conference conference;
    private HashMap<Integer, Author> authorsById;
    private HashMap<String,Author> authorsByName;
    private HashMap<Integer, Term> termsById;
    private HashMap<String,Term> termsByName;

    public Paper(String name, int id) {
        super(name, id);
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public void addAuthor(Author author) {
        authorsById.put(author.getId(), author);
        authorsByName.put(author.getName(),author);
    }

    public void addTerm(Term term) {
        termsById.put(term.getId(), term);
        termsByName.put(term.getName(),term);
    }

    public void removeAuthor(Author author) {
        authorsById.remove(author.getId());
        authorsByName.remove(author.getName());
    }

    public void removeTerm(Term term) {
        termsByName.remove(term.getName());
        termsById.remove(term.getId());
    }

    public Conference getConference() {
        return conference;
    }
    
    public HashMap<Integer, Author> getAuthorsById() {
        return authorsById;
    }

    public HashMap<String,Author> getAuthorsByName() { return authorsByName;}

    public Author getAuthorByID (int id){
        Author a = authorsById.get(id);
        if(a != null) return a;
        return null;
    }

    public Author getAuthorByName (String name){
        Author a = authorsByName.get(name);
        if(a != null) return a;
        return null;
    }

    public HashMap<Integer, Term> getTermsById() {
        return termsById;
    }

    public HashMap<String,Term> getTermsByName() { return termsByName;}

    public Term getTermByID (int id){
       Term t = termsById.get(id);
        if(t != null) return t;
        return null;
    }

    public Term getTermByName (String name){
        Term t = termsByName.get(name);
        if(t != null) return t;
        return null;
    }

}
