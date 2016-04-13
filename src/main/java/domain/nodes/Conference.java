package main.java.domain.nodes;

import java.util.HashMap;

public class Conference extends Node {
    private int year;
    private String continent;
    private HashMap<Integer, Paper> exposedPapersById;
    private HashMap<String, Paper> exposedPapersByName;

    public Conference(String name, int id) {
        super(name, id);
    }
       
    public HashMap<Integer, Paper> getExposedPapersById() {
        return exposedPapersById;
    }

    public HashMap<String, Paper> getExposedPapersByName() {
        return exposedPapersByName;
    }


    public Paper getPaperByName (String name){

        Paper p = exposedPapersByName.get(name);
        if(p != null) return p;
        return null;
    }

    public Paper getPaperById (int id){

        Paper p = exposedPapersById.get(id);
        if(p != null) return p;
        return null;
    }


    public void addExposedPaper(Paper paper) {
        exposedPapersById.put(paper.getId(), paper);
        exposedPapersByName.put(paper.getName(),paper);
    }

    public void removeExposedPaperById(int id) {
        exposedPapersById.remove(id);
    }

    public void removeExposedPaperByName(String name) {
        exposedPapersByName.remove(name);
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
