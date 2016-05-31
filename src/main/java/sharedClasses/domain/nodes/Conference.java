package main.java.sharedClasses.domain.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Conference extends Node {
    private int year;
    private String continent;
    private ArrayList<Integer> exposedPapersById;
    private ArrayList<String> exposedPapersByName;

    private static int maxId;

    /**
     * Constructor function for the Conference node.
     * <p>
     * The Author node represents a node of the kind Conference in our graph.
     *
     * @param  name the name of the conference
     * @param  id the id of the conference
     * @see         Conference
     */
    public Conference(String name, int id) {
        super(name, id);
        exposedPapersById = new ArrayList<Integer>();
        exposedPapersByName = new ArrayList<String>();

        if (maxId < id) maxId = id;

    }

    /**
     * Returns the greater id of any Conference in the graph.
     *
     * @return      the greater id of any Conference in the graph.
     * @see         int
     */
    public static int getMaxId() {
        return maxId;
    }

    /**
     * Returns all the papers related with the Conference with their ids as key
     *
     * @param  papers all the papers in the graph with their id as key
     * @return      HashMap of the papers related with the Conference with its ids as key.
     * @see         HashMap
     */
    public HashMap<Integer, Paper> getExposedPapersById(HashMap<Integer, Paper> papers) {
        HashMap<Integer,Paper> papersById = new HashMap<Integer,Paper>();
        for(int i = 0; i < this.exposedPapersById.size(); i++){
            int keyId = this.exposedPapersById.get(i);
            papersById.put(keyId, papers.get(keyId));
        }
        return papersById;
    }

    /**
     * Returns all the papers related with the Conference with their name as key
     *
     * @param  papers all the papers in the graph with their id as key
     * @return      HashMap of the papers related with the Conference with its name as key.
     * @see         HashMap
     */
    public HashMap<String, Paper> getExposedPapersByName(HashMap<String, Paper> papers) {
        HashMap<String,Paper> papersByName = new HashMap<String,Paper>();
        for(int i = 0; i < this.exposedPapersByName.size(); i++){
            String keyName = this.exposedPapersByName.get(i);
            papersByName.put(keyName, papers.get(keyName));
        }
        return papersByName;
    }

    /**
     * Returns all the ids of the papers related with the Conference
     *
     * @return      ArrayList with all the ids of the papers related with the Conference
     * @see         ArrayList
     */
    public ArrayList<Integer> getExposedPapers(){
        return exposedPapersById;
    }

    /**
     * Adds a paper related to the Conference
     *
     * @param  paper the paper to be added
     * @see         Paper
     */
    public void addExposedPaper(Paper paper) {
        exposedPapersById.add(paper.getId());
        exposedPapersByName.add(paper.getName());
        sortExposedPapers();
    }

    /**
     * Removes a paper related to the Conference
     *
     * @param  paper the paper to be removed
     * @see         Paper
     */
    public void removeExposedPaperBy(Paper paper) {
        for(int i = 0; i < exposedPapersById.size(); i++){
            if(exposedPapersById.get(i) == paper.getId()){
                exposedPapersById.remove(i);
                exposedPapersByName.remove(i);
                break;
            }
        }
    }

    /**
     * Sorts all the papers related to the Author
     *
     * @see         Paper
     */private void sortExposedPapers(){
        Collections.sort(exposedPapersById);
        Collections.sort(exposedPapersByName);
    }

    /**
     * Returns the year of the conference
     *
     * @return      the year of the conference
     * @see         int
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the conference
     *
     * @param year the year of the conference
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets the continent of the conference
     *
     * @param continent the continent of the conference
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * Returns the continent of the conference
     *
     * @return      the continent of the conference
     * @see         String
     */
    public String getContinent() {
        return continent;
    }
}
