package main.java.domain.nodes;

import java.util.ArrayList;

public class Author extends Node {
    private ArrayList<Paper> papers;

    public Author(String name, int id) {
        super(name, id);
    }

    public ArrayList<Paper> getPapers() {
        return papers;
    }

    public void addPaper(Paper paper) {
        papers.add(paper);
    }

    public void removePaper(Paper paper) {
        papers.remove(paper);
    }
}
