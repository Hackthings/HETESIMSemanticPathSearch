package main.java.domain.nodes;

import java.util.ArrayList;

public class Term extends Node {
    public ArrayList<Paper> papersWhichTalkAboutThis;

    public Term(String name, int id) {
        super(name, id);
    }

    public ArrayList<Paper> getPapersWhichTalkAboutThis() {
        return papersWhichTalkAboutThis;
    }

    public void addPaperWhichTalkAboutIt(Paper paper) {
        papersWhichTalkAboutThis.add(paper);
    }

    public void removePaperWhichTalkAboutIt(Paper paper) {
        papersWhichTalkAboutThis.remove(paper);
    }
}
