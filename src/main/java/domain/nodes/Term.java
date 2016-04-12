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

    public Paper getPaper (int id){
        Paper p = papersWhichTalkAboutThis.get(id);
        if(p != null) return p;
        return null;
    }

    public void removePaperWhichTalkAboutIt(int id) {
        papersWhichTalkAboutThis.remove(id);
    }
}
