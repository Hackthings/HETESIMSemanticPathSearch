package main.java.domain.nodes;

import java.util.HashMap;

public class Author extends Node {
    private HashMap<Integer, Paper> papers;

    public Author(String name, int id) {
        super(name, id);
    }

    public HashMap<Integer, Paper> getPapers() {
        return papers;
    }

    public Paper getPaperByName (String name){

        for(Paper p : papers.values()){
            if(p.getName().equals(name)) return p;
        }
        return null;
    }

    public void addPaper(int id, Paper paper) {
        papers.put(id, paper);
    }

    public void removePaper(int id) {
        papers.remove(id);
    }
}
