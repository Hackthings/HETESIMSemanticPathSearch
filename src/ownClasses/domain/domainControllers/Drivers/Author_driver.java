package ownClasses.domain.domainControllers.Drivers;

import sharedClasses.domain.nodes.*;

import java.util.HashMap;
import java.util.TreeMap;

public class Author_driver {

    //AQUEST DRIVER PASSA TOTES LES PROVES CORRECTAMENT
    public static void main(String[] args) {

        //Crea un objecte de la classe Author.
        Author a = new Author("Muhamed Lee", 123);

        //Escriu el Id: 123.
        System.out.println(a.getId());


        //Escriu el nom: "Muhamed Lee".
        System.out.println(a.getName());

        //Afegeix 3 articles
        Paper p1 = new Paper("The NP Problem", 1);
        a.addPaper(p1);

        Paper p2 = new Paper("P equals NP", 2);
        a.addPaper(p2);

        Paper p3 = new Paper("NP contains P", 3);
        a.addPaper(p3);


        System.out.println("Articles de l'autor");
        System.out.println("Mostrem el llistat d'articles per id");
        HashMap<Integer, Paper> keyMap = new HashMap<>();
        keyMap = a.getPapersById(keyMap);
        for (Paper p : keyMap.values()) {
            System.out.println(p.getId() + " " + p.getName());
        }

        System.out.println("Mostrem el llistat d'articles per nom");
        HashMap<String,Paper> keyMap2 = new HashMap<>();
        keyMap2 = a.getPapersByName(keyMap);
        for (Paper p : keyMap2.values()) {
            System.out.println(p.getId()+" "+p.getName());
        }

        //Esborrem un dels articles i imprimim per pantalla els que queden
        a.removePaper(p1);
        System.out.println("Esborrem l'article 1 amb nom The NP Problem i comprovem que no hi es");
        HashMap<Integer, Paper> keyMap4 = new HashMap<>();
        keyMap4 = a.getPapersById(keyMap4);
        for (Paper p : keyMap4.values()) {
            System.out.println(p.getId() + " " + p.getName());
        }
    }
}
