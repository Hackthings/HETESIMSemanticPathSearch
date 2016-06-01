package sharedClasses.domain.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Paper extends Node {
    private Conference conference;
    private ArrayList<Integer> authorsById;
    private ArrayList<String> authorsByName;
    private ArrayList<Integer> termsById;
    private ArrayList<String> termsByName;

    private static int maxId;

    /**
     * Constructor function for the Paper node.
     * <p>
     * The Paper node represents a node of the kind Paper in our graph.
     *
     * @param  name the name of the paper
     * @param  id the id of the paper
     * @see         Paper
     */
    public Paper(String name, int id) {
        super(name, id);
        authorsById = new ArrayList<Integer>();
        authorsByName = new ArrayList<String>();
        termsById = new ArrayList<Integer>();
        termsByName = new ArrayList<String>();

        if (maxId < id) maxId = id;

    }

    /**
     * Returns the greater id of any Paper in the graph.
     *
     * @return      the greater id of any Paper in the graph.
     * @see         int
     */
    public static int getMaxId() {
        return maxId;
    }

    public static void incrementMaxId() {maxId++;}


    /**
     * Set the conference where the Paper was published
     *
     * @param  conference the the conference where the Paper was published
     * @see         Conference
     */
    public void setConference(Conference conference) {
        this.conference = conference;
    }

    /**
     * Adds an Author related to the Paper
     *
     * @param  author the author to be added
     * @see         Author
     */
    public void addAuthor(Author author) {
        authorsById.add(author.getId());
        authorsByName.add(author.getName());
        sortAuthors();
    }

    /**
     * Adds a Term related to the Paper
     *
     * @param  term the term to be added
     * @see         Term
     */
    public void addTerm(Term term) {
        termsById.add(term.getId());
        termsByName.add(term.getName());
        sortTerms();
    }

    /**
     * Removes an Author related to the Paper
     *
     * @param  author the author to be removed
     * @see         Author
     */
    public void removeAuthor(Author author) {
        for(int i = 0; i < authorsById.size(); i++){
            if(authorsById.get(i) == author.getId()){
                authorsById.remove(i);
                authorsByName.remove(i);
                break;
            }
        }
    }

    /**
     * Removes a term related to the Paper
     *
     * @param  term the author to be removed
     * @see         Term
     */
    public void removeTerm(Term term) {
        for(int i = 0; i < termsById.size(); i++){
            if(termsById.get(i) == term.getId()){
                termsById.remove(i);
                termsByName.remove(i);
                break;
            }
        }
    }

    /**
     * Returns the Conference of the Paper
     *
     * @return      the Conference of the Paper
     * @see         Conference
     */
    public Conference getConference() {
        return conference;
    }

    /**
     * Returns all the authors related with the Paper with their ids as key
     *
     * @param  authors all the authors in the graph with their id as key
     * @return      HashMap of the authors related with the Paper with its ids as key.
     * @see         HashMap
     */
    public HashMap<Integer, Author> getAuthorsById(HashMap<Integer,Author> authors) {
        HashMap<Integer,Author> authorsById = new HashMap<Integer,Author>();
        for(int i = 0; i < this.authorsById.size(); i++){
            int keyId = this.authorsById.get(i);
            authorsById.put(keyId, authors.get(keyId));
        }
        return authorsById;
    }

    /**
     * Returns all the authors related with the Paper with their name as key
     *
     * @param  authors all the authors in the graph with their id as key
     * @return      HashMap of the authors related with the Paper with its name as key.
     * @see         HashMap
     */
    public HashMap<String,Author> getAuthorsByName(HashMap<String,Author> authors) {
        HashMap<String,Author> authorsByName = new HashMap<String,Author>();
        for(int i = 0; i < this.authorsByName.size(); i++){
            String keyName = this.authorsByName.get(i);
            authorsByName.put(keyName, authors.get(keyName));
        }
        return authorsByName;
    }

    /**
     * Returns all the terms related with the Paper with their ids as key
     *
     * @param  terms all the terms in the graph with their id as key
     * @return      HashMap of the authors related with the Paper with its ids as key.
     * @see         HashMap
     */
    public HashMap<Integer, Term> getTermsById(HashMap<Integer,Term> terms) {
        HashMap<Integer,Term> termsById = new HashMap<Integer,Term>();
        for(int i = 0; i < this.termsById.size(); i++){
            int keyId = this.termsById.get(i);
            termsById.put(keyId, terms.get(keyId));
        }
        return termsById;
    }

    /**
     * Returns all the terms related with the Paper with their name as key
     *
     * @param  terms all the terms in the graph with their id as key
     * @return      HashMap of the terms related with the Paper with its name as key.
     * @see         HashMap
     */
    public HashMap<String,Term> getTermsByName(HashMap<String,Term> terms) {
        HashMap<String,Term> termsByName = new HashMap<String,Term>();
        for(int i = 0; i < this.termsByName.size(); i++){
            String keyName = this.termsByName.get(i);
            termsByName.put(keyName, terms.get(keyName));
        }
        return termsByName;
    }

    /**
     * Returns all the ids of the authors related with the Paper
     *
     * @return      ArrayList with all the ids of the authors related with the Paper
     * @see         ArrayList
     */
    public ArrayList<Integer> getRelatedAuthors(){
        return authorsById;
    }

    /**
     * Returns all the ids of the terms related with the Paper
     *
     * @return      ArrayList with all the ids of the terms related with the Paper
     * @see         ArrayList
     */
    public ArrayList<Integer> getRelatedTerms(){
        return termsById;
    }

    /**
     * Sorts all the authors related to the Paper
     *
     * @see         Author
     */
    private void sortAuthors(){
        Collections.sort(authorsById);
        Collections.sort(authorsByName);
    }

    /**
     * Sorts all the terms related to the Paper
     *
     * @see         Term
     */
    private void sortTerms(){
        Collections.sort(termsById);
        Collections.sort(termsByName);
    }

}
