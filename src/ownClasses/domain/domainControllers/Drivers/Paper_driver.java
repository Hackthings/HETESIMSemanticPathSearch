package ownClasses.domain.domainControllers.Drivers;

import sharedClasses.domain.nodes.Author;
import sharedClasses.domain.nodes.Conference;
import sharedClasses.domain.nodes.Paper;
import sharedClasses.domain.nodes.Term;

import java.util.Set;

public class Paper_driver {
/*
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
        System.out.println(p.getConference().getName());

        //Cas 1: 1 unic autor
        //Afegeix un autor al Paper i el mostra per pantalla el seu nom i id
        Author a1 = new Author("Muhamed Lee",123);
        p.addAuthor(a1);
        System.out.println(p.getAuthorByName("Muhamed Lee").getName());
        System.out.println(p.getAuthorByID(123).getId());

        //Cas 2: mes d'un autor
        //Afegeix un altre autor i mostra ambos autors tant per nom com per id
        Author a2 = new Author("Jose Garcia",456);
        p.addAuthor(a2);
        Set <Integer> keyMap = p.getAuthorsById().keySet();
        for (Integer i : keyMap){
            System.out.println(p.getAuthorsById().get(i).getId());
        }
        Set<String> keyMap2 = p.getAuthorsByName().keySet();
        for (String s : keyMap2){
            System.out.println(p.getAuthorByName(s).getName());
        }



        //Afegeix un terme i el mostra per pantalla, tant per nom com per id
        Term t1 = new Term("The",1);
        p.addTerm(t1);
        Term t2 = new Term("NP",2);
        p.addTerm(t2);
        Term t3 = new Term("problem",3);
        p.addTerm(t3);
        System.out.println(p.getTermByName("NP").getName());
        System.out.println(p.getTermByID(2).getId());

        //Tot seguit mostra tots els termes associats al Paper,tant per nom com per id
        System.out.println("Termes de l'article");
        Set<Integer> keyMap3 = p.getTermsById().keySet();
        for (Integer i : keyMap3){
            System.out.println(p.getTermsById().get(i).getId());
        }
        Set<String> keyMap4 = p.getTermsByName().keySet();
        for (String s : keyMap4){
            System.out.println(p.getTermByName(s).getName());
        }

        //Borra un dels autors
        p.removeAuthor(a1);
        if(p.getAuthorByName("Muhamed Lee") == null)System.out.println("Aquest autor no es d'aquest article");
        if(p.getAuthorByID(123) == null)System.out.println("Aquest autor no es d'aquest article");

        //Borra un dels termes
        p.removeTerm(t2);
        if(p.getTermByName("NP") == null)System.out.println("Aquest article no conte aquest terme");
        if(p.getTermByID(2) == null)System.out.println("Aquest article no conte aquest terme");
    }*/
}