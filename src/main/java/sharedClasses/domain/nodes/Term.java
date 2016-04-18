package main.java.sharedClasses.domain.nodes;

import java.util.HashMap;

public class Term extends Node {
    public HashMap<Integer, Paper> papersWhichTalkAboutThisById;
    public HashMap<String, Paper> papersWhichTalkAboutThisByName;


    public Term(String name, int id) {
        super(name, id);
    }

    public HashMap<Integer, Paper> getPapersWhichTalkAboutThisById() {
        return papersWhichTalkAboutThisById;
    }

    public HashMap<String, Paper> getPapersWhichTalkAboutThisByName() {
        return papersWhichTalkAboutThisByName;
    }

    public void addPaperWhichTalkAboutIt(Paper paper) {
        papersWhichTalkAboutThisById.put(paper.getId(), paper);
        papersWhichTalkAboutThisByName.put(paper.getName(), paper);
    }

    public Paper getPaperById (int id){
        Paper p = papersWhichTalkAboutThisById.get(id);
        if(p != null) return p;
        return null;
    }

    public Paper getPaperByName (String name){
        Paper p = papersWhichTalkAboutThisByName.get(name);
        if(p != null) return p;
        return null;
    }

    public void removePaperWhichTalkAboutItById(int id) {
        papersWhichTalkAboutThisById.remove(id);
    }

    public void removePaperWhichTalkAboutItByName(String name) {
        papersWhichTalkAboutThisByName.remove(name);
    }

}
