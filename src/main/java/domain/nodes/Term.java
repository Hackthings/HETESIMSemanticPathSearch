package main.java.domain.nodes;

import java.util.HashMap;

public class Term extends Node {
    public HashMap<Integer, Paper> papersWhichTalkAboutThis;

    public Term(String name, int id) {
        super(name, id);
    }

    public HashMap<Integer, Paper> getPapersWhichTalkAboutThis() {
        return papersWhichTalkAboutThis;
    }

    public void addPaperWhichTalkAboutIt(int id, Paper paper) {
        papersWhichTalkAboutThis.put(id, paper);
    }

    public Paper getPaperByName (String name){

        for(Paper p : papersWhichTalkAboutThis.values()){
            if(p.getName().equals(name)) return p;
        }
        return null;
    }

    public void removePaperWhichTalkAboutIt(int id) {
        papersWhichTalkAboutThis.remove(id);
    }
}
