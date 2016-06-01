package ownClasses.domain.domainControllers.Drivers;

import sharedClasses.domain.nodes.*;

import java.util.HashMap;
import java.util.TreeMap;

public class Paper_driver {

    //AQUEST DRIVER PASSA TOTES LES PROVES CORRECTAMENT
    public static void main(String[] args) {
        //Crea un objecte de la classe Paper.
        Paper p = new Paper("The NP problem",321);

        //Escriu el Id: 321.
        System.out.println(p.getId());


        //Escriu el nom: "The NP problem".
        System.out.println(p.getName());

        //Assigna una conferencia al Paper i la mostra per pantalla el seu nom
        Conference c = new Conference("CSS",3);
        p.setConference(c);
        System.out.println(p.getConference().getId() + " " + p.getConference().getName());

        //Afegeix un parell d'autors al Paper i els mostra per pantalla
        Author a1 = new Author("Muhamed Lee",123);
        p.addAuthor(a1);

        Author a2 = new Author("Jose Garcia",456);
        p.addAuthor(a2);

        HashMap<Integer,Author> keyMap = new HashMap<>();
        keyMap = p.getAuthorsById(keyMap);
        for (Author a : keyMap.values()) {
            System.out.println(a.getId()+" "+a.getName());
        }

        System.out.println("Mostrem el llistat d'articles per nom");
        HashMap<String,Author> keyMap2 = new HashMap<>();
        keyMap2 = p.getAuthorsByName(keyMap2);
        for (Author a : keyMap2.values()) {
            System.out.println(a.getId()+" "+a.getName());
        }



        //Afegeix uns cuants termes i els mostra per id i per nom
        Term t1 = new Term("The",1);
        p.addTerm(t1);
        Term t2 = new Term("NP",2);
        p.addTerm(t2);
        Term t3 = new Term("problem",3);
        p.addTerm(t3);

        //Tot seguit mostra tots els termes associats al Paper,tant per nom com per id
        System.out.println("Termes de l'article per id");
        HashMap<Integer,Term> keyMap3 = new HashMap<>();
        keyMap3 = p.getTermsById(keyMap3);
        for (Term t : keyMap3.values()) {
            System.out.println(t.getId()+" "+t.getName());
        }

        System.out.println("Termes de l'article per nom");
        HashMap<Integer,Term> keyMap4 = new HashMap<>();
        keyMap4 = p.getTermsById(keyMap3);
        for (Term t : keyMap4.values()) {
            System.out.println(t.getId()+" "+t.getName());
        }

        //Borra un dels autors
        System.out.println("Autors que queden després d'eliminar a Muhamed Lee ");
        p.removeAuthor(a1);
        HashMap<Integer,Author> keyMap5 = new HashMap<>();
        keyMap5 = p.getAuthorsById(keyMap5);
        for (Author a : keyMap5.values()) {
            System.out.println(a.getId()+" "+a.getName());
        }

        //Borra un dels termes
        System.out.println("Termes que queden després d'eliminar el terme NP ");
        p.removeTerm(t2);
        HashMap<Integer,Term> keyMap6 = new HashMap<>();
        keyMap6 = p.getTermsById(keyMap6);
        for (Term t : keyMap6.values()) {
            System.out.println(t.getId()+" "+t.getName());
        }
    }
}