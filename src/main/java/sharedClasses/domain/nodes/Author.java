package main.java.sharedClasses.domain.nodes;

import java.util.HashMap;

public class Author extends Node {
    private HashMap<Integer, Paper> papersById;
    private HashMap<String, Paper> papersByName;

    public Author(String name, int id) {
        super(name, id);
        papersById = new HashMap<Integer, Paper>();
        papersByName = new HashMap<String, Paper>();
    }

    public HashMap<Integer, Paper> getPapersById() {
        return papersById;
    }

    public HashMap<String, Paper> getPapersByName() {
        return papersByName;
    }


    public Paper getPaperById (int id){
        Paper p = papersById.get(id);
        if(p != null) return p;
        return null;
    }

    public Paper getPaperByName (String name){
        Paper p = papersByName.get(name);
        if(p != null) return p;
        return null;
    }

    public void addPaper(Paper paper) {
        papersById.put(paper.getId(), paper);
        papersByName.put(paper.getName(),paper);
    }

    public void removePaper(Paper paper) {
        papersById.remove(paper);
        papersByName.remove(paper);
    }

}
