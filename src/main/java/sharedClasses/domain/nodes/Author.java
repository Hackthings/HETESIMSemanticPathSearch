package main.java.sharedClasses.domain.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Author extends Node{
    private ArrayList<Integer> papersById;
    private ArrayList<String> papersByName;

    private static int maxId;

    /**
     * Constructor function for the Author node.
     * <p>
     * The Author node represents a node of the kind Author in our graph.
     *
     * @param  name the name of the author
     * @param  id the id of the author
     * @see         Author
     */
    public Author(String name, int id) {
        super(name, id);
        papersById = new ArrayList<Integer>();
        papersByName = new ArrayList<String>();

        if (maxId < id) maxId = id;

    }

    /**
     * Returns the greater id of any Author in the graph.
     *
     * @return      the greater id of any Author in the graph.
     * @see         int
     */
    public static int getMaxId() {
        return maxId;
    }

    /**
     * Returns all the papers related with the Author with their ids as key
     *
     * @param  papers all the papers in the graph with their id as key
     * @return      HashMap of the papers related with the Author with its ids as key.
     * @see         HashMap
     */
    public HashMap<Integer, Paper> getPapersById(HashMap<Integer,Paper> papers) {
        HashMap<Integer,Paper> papersById = new HashMap<Integer,Paper>();
        for(int i = 0; i < this.papersById.size(); i++){
            int keyId = this.papersById.get(i);
            papersById.put(keyId, papers.get(keyId));
        }
        return papersById;
    }

    /**
     * Returns all the papers related with the Author with their name as key
     *
     * @param  papers all the papers in the graph with their id as key
     * @return      HashMap of the papers related with the Author with its name as key.
     * @see         HashMap
     */
    public HashMap<String, Paper> getPapersByName(HashMap<Integer,Paper> papers) {
        HashMap<String,Paper> papersByName = new HashMap<String,Paper>();
        for(int i = 0; i < this.papersByName.size(); i++){
            String keyName = this.papersByName.get(i);
            papersByName.put(keyName, papers.get(keyName));
        }
        return papersByName;
    }

    /**
     * Returns all the ids of the papers related with the Author
     *
     * @return      ArrayList with all the ids of the papers related with the Author
     * @see         ArrayList
     */
    public ArrayList<Integer> getAuthorRelations(){
        return papersById;
    }

    /**
     * Adds a paper related to the Author
     *
     * @param  paper the paper to be added
     * @see         Paper
     */
    public void addPaper(Paper paper) {
        papersById.add(paper.getId());
        papersByName.add(paper.getName());
        sortPapers();
    }

    /**
     * Removes a paper related to the Author
     *
     * @param  paper the paper to be removed
     * @see         Paper
     */
    public void removePaper(Paper paper) {
        for(int i = 0; i < papersById.size(); i++){
            if(papersById.get(i) == paper.getId()){
                papersById.remove(i);
                papersByName.remove(i);
                break;
            }
        }
    }

    /**
     * Sorts all the papers related to the Author
     *
     * @see         Paper
     */
    private void sortPapers(){
        Collections.sort(papersById);
        Collections.sort(papersByName);
    }

}
