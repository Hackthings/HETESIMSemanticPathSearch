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

    public void addPaper(int id, Paper paper) {
        papers.put(id, paper);
    }

    public void removePaper(int id) {
        papers.remove(id);
    }
}
