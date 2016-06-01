package ownClasses.domain.domainControllers.Drivers;

import sharedClasses.domain.nodes.*;

import java.util.HashMap;
import java.util.TreeMap;

public class Term_driver {

    //AQUEST DRIVER PASSA TOTES LES PROVES CORRECTAMENT
    public static void main(String[] args) {

        //Crea un objecte de la classe Term.
        Term t = new Term("NP", 123);

        //Escriu el Id: 123.
        System.out.println(t.getId());


        //Escriu el nom: "NP".
        System.out.println(t.getName());

        //Afegeix un parell d'articles en el quals surt el terme
        Paper p1 = new Paper("The NP Problem", 1);
        t.addPaperWhichTalkAboutIt(p1);
        Paper p2 = new Paper("P equals NP", 2);
        t.addPaperWhichTalkAboutIt(p2);

        System.out.println("Articles on apareix el terme per id");
        HashMap<Integer,Paper> keyMap = new HashMap<>();
        keyMap = t.getPapersWhichTalkAboutThisById(keyMap);
        for (Paper p : keyMap.values()) {
            System.out.println(p.getId()+" "+p.getName());

        }
        System.out.println("Articles on apareix el terme per nom");
        HashMap <Integer,Paper> keyMap2 = new HashMap<>();
        HashMap <String,Paper> keyMap3 = t.getPapersWhichTalkAboutThisByName(keyMap2);
        for (Paper p : keyMap3.values()) {
            System.out.println(p.getId()+" "+p.getName());
        }

        //Borra un dels articles per Id
        t.removePaperWhichTalkAboutIt(p1);
        System.out.println("Esborrem l'article 1 amb nom The NP Problem i comprovem que no hi es");

        System.out.println("Articles restants on apareix el terme per id");
        HashMap <Integer,Paper> keyMap4 = new HashMap<>();
        keyMap4 = t.getPapersWhichTalkAboutThisById(keyMap4);
        for (Paper p : keyMap4.values()) {
            System.out.println(p.getId()+" "+p.getName());

        }
    }
}
