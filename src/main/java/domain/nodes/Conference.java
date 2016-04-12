package main.java.domain.nodes;

import java.util.HashMap;

public class Conference extends Node {
    private int year;
    private String continent;
    private HashMap<Integer, Paper> exposedPapers;

    public Conference(String name, int id) {
        super(name, id);
    }
       
    public HashMap<Integer, Paper> getExposedPapers() {
        return exposedPapers;
    }

    public Paper getPaperByName (String name){

        for(Paper p : exposedPapers.values()){
            if(p.getName().equals(name)) return p;
        }
        return null;
    }

    public void addExposedPaper(int id, Paper paper) {
        exposedPapers.put(id, paper);
    }

    public void removeExposedPaper(int id) {
        exposedPapers.remove(id);
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
