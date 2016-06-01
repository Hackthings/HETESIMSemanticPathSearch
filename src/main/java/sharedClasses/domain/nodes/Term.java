package main.java.sharedClasses.domain.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Term extends Node {
    public ArrayList<Integer> papersWhichTalkAboutThisById;
    public ArrayList<String> papersWhichTalkAboutThisByName;

    private static int maxId;


    /**
     * Constructor function for the Term node.
     * <p>
     * The Author node represents a node of the kind Term in our graph.
     *
     * @param  name the name of the term
     * @param  id the id of the term
     * @see         Term
     */
    public Term(String name, int id) {
        super(name, id);
        papersWhichTalkAboutThisById = new ArrayList<Integer>();
        papersWhichTalkAboutThisByName = new ArrayList<String>();

        if (maxId < id) maxId = id;
    }

    /**
     * Returns the greater id of any Term in the graph.
     *
     * @return      the greater id of any Term in the graph.
     * @see         int
     */
    public static int getMaxId() {
        return maxId;
    }

    public static void incrementMaxId() {maxId++;}


    /**
     * Returns all the papers related with the Term with their ids as key
     *
     * @param  papers all the papers in the graph with their id as key
     * @return      HashMap of the papers related with the Term with its ids as key.
     * @see         HashMap
     */
    public HashMap<Integer, Paper> getPapersWhichTalkAboutThisById(HashMap<Integer,Paper> papers) {

        HashMap<Integer,Paper> papersWhichTalkAboutThisById = new HashMap<Integer,Paper>();
        for(int i = 0; i < this.papersWhichTalkAboutThisById.size(); i++){
            int keyId = this.papersWhichTalkAboutThisById.get(i);
            papersWhichTalkAboutThisById.put(keyId, papers.get(keyId));
        }
        return papersWhichTalkAboutThisById;
    }

    /**
     * Returns all the papers related with the Term with their ids as key
     *
     * @param  papers all the papers in the graph with their id as key
     * @return      HashMap of the papers related with the Term with its ids as key.
     * @see         HashMap
     */
    public HashMap<String, Paper> getPapersWhichTalkAboutThisByName(HashMap<Integer,Paper> papers) {
        HashMap<String,Paper> papersWhichTalkAboutThisByNam = new HashMap<String,Paper>();
        for(int i = 0; i < this.papersWhichTalkAboutThisByName.size(); i++){
            String keyName = this.papersWhichTalkAboutThisByName.get(i);
            papersWhichTalkAboutThisByNam.put(keyName, papers.get(keyName));
        }
        return papersWhichTalkAboutThisByNam;
    }

    /**
     * Adds a paper related to the Term
     *
     * @param  paper the paper to be added
     * @see         Paper
     */
    public void addPaperWhichTalkAboutIt(Paper paper) {
        papersWhichTalkAboutThisById.add(paper.getId());
        papersWhichTalkAboutThisByName.add(paper.getName());
        sortPapersWhichTalkAboutThis();
    }

    /**
     * Removes a paper related to the Term
     *
     * @param  paper the paper to be removed
     * @see         Paper
     */
    public void removePaperWhichTalkAboutIt(Paper paper) {
        for(int i = 0; i < papersWhichTalkAboutThisById.size(); i++){
            if(papersWhichTalkAboutThisById.get(i) == paper.getId()){
                papersWhichTalkAboutThisById.remove(i);
                papersWhichTalkAboutThisByName.remove(i);
                break;
            }
        }
    }

    /**
     * Returns all the ids of the papers related with the Term
     *
     * @return      ArrayList with all the ids of the papers related with the Term
     * @see         ArrayList
     */
    public ArrayList<Integer> getPapersWhichTalkAboutThis(){
        return papersWhichTalkAboutThisById;
    }

    /**
     * Sorts all the papers related to the Term
     *
     * @see         Paper
     */
    private void sortPapersWhichTalkAboutThis(){
        Collections.sort(papersWhichTalkAboutThisById);
        Collections.sort(papersWhichTalkAboutThisByName);
    }
}
