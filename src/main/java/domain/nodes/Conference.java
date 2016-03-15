package main.java.domain.nodes;

import java.util.ArrayList;

public class Conference extends Node {
    private int year;
    private String continent;
    private ArrayList<Paper> exposedPapers;

    public Conference(String name, int id) {
        super(name, id);
    }

    public ArrayList<Paper> getExposedPapers() {
        return exposedPapers;
    }

    public void addExposedPaper(Paper paper) {
        exposedPapers.add(paper);
    }

    public void removeExposedPaper(Paper paper) {
        exposedPapers.remove(paper);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getContinent() {
        return continent;
    }
}
