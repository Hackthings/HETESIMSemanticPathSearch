package main.java.sharedClasses.domain.domainControllers;


import main.java.sharedClasses.domain.nodes.Author;
import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;
import main.java.sharedClasses.domain.nodes.Term;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;


public class DomainPersistanceController {
    private static String filepath;

    HashMap<Integer, Author> authorsById;
    HashMap<Integer, Paper> papersById;
    HashMap<Integer, Conference> conferencesById;
    HashMap<Integer, Term> termsById;

    HashMap<String, Author> authorsByName;
    HashMap<String, Paper> papersByName;
    HashMap<String, Conference> conferencesByName;
    HashMap<String, Term> termsByName;

    public DomainPersistanceController(HashMap<Integer, Author> authorsById,
                                       HashMap<Integer, Paper> papersById,
                                       HashMap<Integer, Conference> conferencesById,
                                       HashMap<Integer, Term> termsById,
                                       HashMap<String, Author> authorsByName,
                                       HashMap<String, Paper> papersByName,
                                       HashMap<String, Conference> conferencesByName,
                                       HashMap<String, Term> termsByName) {

        this.authorsById = authorsById;
        this.papersById = papersById;
        this.conferencesById = conferencesById;
        this.termsById = termsById;
        this.authorsByName = authorsByName;
        this.papersByName = papersByName;
        this.conferencesByName = conferencesByName;
        this.termsByName = termsByName;
        filepath = "/src/main/java/data/";
    }

    public void testDomain() {

        System.out.println("TEST DOMAIN!");


        System.out.println("AUTORS : *_*_*_*_*_*_*_*_*_*_*_*_*_**_*_*_*_ ");

        for (Author a : authorsById.values()) {
            System.out.println("Autor: " + a.getId() + " " + a.getName());
            System.out.println("Papers Relacionats ");
            for (Paper p : a.getPapersById(papersById).values()) {
                System.out.println(p.getId() + " " + p.getName());
            }
        }

        System.out.println("PAPERS : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");
        for (Paper p : papersById.values()) {
            System.out.println("Paper: " + p.getId() + " " + p.getName());
            System.out.println("Autors relacionats: ");
            for (Author a : p.getAuthorsById(authorsById).values()) {
                System.out.println(a.getId() + " " + a.getName());
            }
            System.out.println("Termes relacionats: ");

            for (Term t : p.getTermsById(termsById).values()) {
                System.out.println(t.getId() + " " + t.getName());
            }
            System.out.println("Conferencia relacionada: ");
            System.out.println(p.getConference().getId() + " " + p.getConference().getName());
        }

        System.out.println("TERMES : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");

        for (Term t : termsById.values()) {
            System.out.println("Tema: " + t.getId() + " " + t.getName());
            System.out.println("Papers Relacionats: ");
            for (Paper p : t.getPapersWhichTalkAboutThisById(papersById).values()) {
                System.out.println(p.getId() + " " + p.getName());
            }
        }

        System.out.println("CONFERENCIES : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");

        for (Conference c : conferencesById.values()) {
            System.out.println("Conferencia: " + c.getId() + " " + c.getName() + " " + c.getYear() + " " + c.getContinent());
            System.out.println("Les seves relacions: ");
            for (Paper p : c.getExposedPapersById(papersById).values()) {
                System.out.println(p.getId() + " " + p.getName());
            }
        }
        System.out.println("FIIIII : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");

    }

    public void readAllFromFile(String path) {
        //filepath = path;
        readAuthorsFromFile();
        readPapersFromFile();
        readConferencesFromFile();
        readTermsFromFile();
        readPaperAuthorRelations();
        readTermRelations();
        readConferenceRelations();

        //IMPRIMIR HASHMAPS
        //testDomain();
    }

    public boolean AddNewAuthor(String authorName, ArrayList<Paper> papersToRelate) {
        Author a = new Author(authorName, Author.getMaxId() + 1);
        if (authorsByName.get(a.getName()) != null) {
            System.err.println("Aquest autor ja existeix");
            return false;
        }
        authorsById.put(a.getId(), a);
        authorsByName.put(a.getName(), a);
        for (int i = 0; i < papersToRelate.size(); i++) {
            a.addPaper(papersToRelate.get(i));
            papersToRelate.get(i).addAuthor(a);
        }
        return true;
    }

    public boolean addNewPaper(String paperName, ArrayList<Author> authorsToRelate, ArrayList<Term> termsToRelate, Conference confToRelate) {
        Paper p = new Paper(paperName, Paper.getMaxId() + 1);
        if (papersByName.get(p.getName()) != null) {
            System.err.println("Aquest article ja existeix");
            return false;
        }
        for (int i = 0; i < authorsToRelate.size(); i++) {
            p.addAuthor(authorsToRelate.get(i));
            authorsToRelate.get(i).addPaper(p);
        }
        for (int i = 0; i < termsToRelate.size(); i++) {
            p.addTerm(termsToRelate.get(i));
            termsToRelate.get(i).addPaperWhichTalkAboutIt(p);
        }
        p.setConference(confToRelate);
        confToRelate.addExposedPaper(p);
        return true;
    }

    public boolean addNewTerm(String termName, ArrayList<Paper> papersToRelate) {
        Term t = new Term(termName, Term.getMaxId() + 1);
        if (termsByName.get(t.getName()) != null) {
            System.err.println("Aquest article ja existeix");
            return false;
        }
        for (int i = 0; i < papersToRelate.size(); i++) {
            t.addPaperWhichTalkAboutIt(papersToRelate.get(i));
            papersToRelate.get(i).addTerm(t);
        }
        return true;
    }

    public boolean addNewConference(String confName, ArrayList<Paper> papersToRelate) {
        Conference c = new Conference(confName, Conference.getMaxId() + 1);
        if (conferencesByName.get(c.getName()) != null) {
            System.err.println("Aquesta conferencia ja existeix");
            return false;
        }
        for (int i = 0; i < papersToRelate.size(); i++) {
            c.addExposedPaper(papersToRelate.get(i));
            papersToRelate.get(i).setConference(c);
        }
        return true;
    }

    public boolean deleteAuthor(String authorName) {
        try {
            Author a = authorsByName.get(authorName);
            HashMap<Integer, Paper> aux = a.getPapersById(papersById);
            for (Paper p : aux.values()) {
                p.removeAuthor(a);
                if (p.getAuthorsById(authorsById).size() < 1) {
                    deletePaperRelationsOnConferences(p);
                    deletePaperRelationsOnTerms(p);
                    papersById.remove(p.getId());
                    papersByName.remove(p.getName());
                }
            }
            //eliminem l'autor
            int id = a.getId();
            authorsByName.remove(authorName);
            authorsById.remove(id);
            return true;
        } catch (NullPointerException ex) {
            System.out.println("\u001B[31m" + "Aquest autor no existeix." + "\u001B[0m");
            return false;
        }
    }

    public boolean deletePaper(String paperName){
        try {
            Paper p = papersByName.get(paperName);
            deletePaperRelationsOnConferences(p);
            deletePaperRelationsOnAuthors(p);
            deletePaperRelationsOnTerms(p);
            int id = p.getId();
            papersByName.remove(paperName);
            papersById.remove(id);
            return true;
        } catch (NullPointerException ex) {
            System.out.println("\u001B[31m" + "Aquest article no existeix." + "\u001B[0m");
            return false;
        }
    }

    public boolean deleteTerm(String termName){
        try {
            Term t = termsByName.get(termName);
            Collection<Paper> auxiliar = t.getPapersWhichTalkAboutThisById(papersById).values();
            for (Iterator it = auxiliar.iterator(); it.hasNext(); ) {
                Paper p = (Paper) it.next();
                p.removeTerm(t);
                if (p.getTermsById(termsById).size() < 1) { //Eliminar paper i relacions
                    deletePaperRelationsOnAuthors(p);
                    deletePaperRelationsOnConferences(p);
                    papersByName.remove(p.getName());
                    papersById.remove(p.getId());
                }
            }
            int id = t.getId();
            termsByName.remove(termName);
            termsById.remove(id);
            return true;
        } catch (NullPointerException ex) {
            System.out.println("\u001B[31m" + "Aquest terme no existeix." + "\u001B[0m");
            return false;
        }
    }

    public boolean deleteConference(String confName){
        try {
            Conference c = conferencesByName.get(confName);
            Collection<Paper> auxiliarp = c.getExposedPapersById(papersById).values();
            for (Iterator it = auxiliarp.iterator(); it.hasNext(); ) {
                Paper p = (Paper) it.next();
                deletePaperRelationsOnAuthors(p);
                deletePaperRelationsOnTerms(p);
                papersById.remove(p.getId());
                papersByName.remove(p.getName());
            }
            int id = c.getId();
            conferencesByName.remove(confName);
            conferencesById.remove(id);
            return true;
        } catch (NullPointerException ex) {
            System.out.println("\u001B[31m" + "Aquesta conferencia no existeix." + "\u001B[0m");
            return false;
        }
    }
    public void newEdit() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Quants edits vols fer?");
        int editsToDo = scan.nextInt();
        scan.nextLine();
        for (int n = 0; n < editsToDo; ++n) {
            System.out.println("Vols editar, insertar o eliminar? (Ed -> editar, In -> insertar, El -> eliminar");
            String editType = scan.nextLine();
            System.out.println("Quin tipus d'objecte vols modificar? (A -> Author, P -> Paper, T -> Term, C -> Conference");
            String objectType = scan.nextLine();
            String name;
            String newName;
            String origenName;
            int id;
            switch (editType) {
                case ("In"): //agefir

                    switch (objectType) {
                        case ("A"): //Author
                            System.out.println("Introduïu el nom de l'autor");
                            String nameAuthor = scan.nextLine();
                            Author a = new Author(nameAuthor, Author.getMaxId() + 1);
                            ArrayList<Paper> addedPapers = new ArrayList<>();
                            ArrayList<Conference> addedConferences = new ArrayList<>();
                            if (authorsByName.get(a.getName()) != null) {
                                System.err.println("Aquest autor ja existeix");
                                return;
                            }
                            System.out.println("En quants articles ha participat?");
                            int numArt = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numArt; ++i) {
                                System.out.println("Introduïu el nom de l'article");
                                String nameArt = scan.nextLine();
                                Paper p;
                                p = papersByName.get(nameArt);
                                if (p == null) {
                                    System.out.println("L'article no existeix, vols crear-lo? [s/n] ");
                                    if (scan.nextLine().equals("s")) {
                                        p = new Paper(nameArt, Paper.getMaxId() + 1);
                                        System.out.println("Quina és la seva conferència?");
                                        String nameConf = scan.nextLine();
                                        Conference c;
                                        c = conferencesByName.get(nameConf);
                                        if (c == null) {
                                            System.out.println("La conferencia no existeix, vols crear-la? [s/n]");
                                            if (scan.nextLine().equals("s")) {
                                                c = new Conference(nameConf, Conference.getMaxId() + 1);
                                                System.out.println("Introduïu l'any");
                                                int confyear = scan.nextInt();
                                                scan.nextLine();
                                                System.out.println("Introduïu el continent");
                                                String confcont = scan.nextLine();
                                                c.setYear(confyear);
                                                c.setContinent(confcont);
                                                conferencesByName.put(c.getName(), c);
                                                conferencesById.put(c.getId(), c);
                                                p.setConference(c);
                                                c.addExposedPaper(p);
                                                addedConferences.add(c);
                                            } else {
                                                System.out.println("L'autor no s'ha creat");
                                                for (int j = 0; j < addedPapers.size(); j++) {
                                                    a.removePaper(addedPapers.get(j));
                                                    papersById.remove(addedPapers.get(j).getId());
                                                    papersByName.remove(addedPapers.get(j).getName());
                                                }
                                                for (int j = 0; j < addedConferences.size(); j++) {
                                                    conferencesById.remove(addedConferences.get(j).getId());
                                                    conferencesByName.remove(addedConferences.get(j).getName());
                                                }
                                                return;
                                            }
                                        } else {
                                            p.setConference(c);
                                            c.addExposedPaper(p);
                                        }
                                        //afegirTermes();


                                        a.addPaper(p);
                                        p.addAuthor(a);
                                        papersByName.put(p.getName(), p);
                                        papersById.put(p.getId(), p);
                                        addedPapers.add(p);
                                    } else {
                                        System.out.println("L'autor no s'ha creat");
                                        for (int j = 0; j < addedPapers.size(); j++) {
                                            a.removePaper(addedPapers.get(j));
                                            papersById.remove(addedPapers.get(j).getId());
                                            papersByName.remove(addedPapers.get(j).getName());
                                        }
                                        for (int j = 0; j < addedConferences.size(); j++) {
                                            conferencesById.remove(addedConferences.get(j).getId());
                                            conferencesByName.remove(addedConferences.get(j).getName());
                                        }
                                        return;
                                    }
                                } else {
                                    a.addPaper(p);
                                    p.addAuthor(a);

                                }


                            }
                            authorsByName.put(a.getName(), a);
                            authorsById.put(a.getId(), a);
                            break;

                        case ("P"): //Paper
                            System.out.println("Introduïu el nom de l'article");
                            String namePaper = scan.nextLine();
                            Paper p = new Paper(namePaper, Paper.getMaxId() + 1);
                            if (papersByName.get(p.getName()) != null) {
                                System.err.println("Aquest article ja existeix");
                                return;
                            }
                            ArrayList<Author> addedAuthors = new ArrayList<>();

                            System.out.println("Quina és la seva conferència?");
                            String nameConf = scan.nextLine();
                            Conference c;
                            c = conferencesByName.get(nameConf);
                            if (c == null) {
                                System.out.println("La conferencia no existeix, vols crear-la? [s/n]:");
                                if (scan.nextLine().equals("s")) {
                                    c = new Conference(nameConf, Conference.getMaxId() + 1);
                                    System.out.println("Introduïu l'any");
                                    int confyear = scan.nextInt();
                                    scan.nextLine();
                                    System.out.println("Introduïu el continent");
                                    String confcont = scan.nextLine();
                                    c.setYear(confyear);
                                    c.setContinent(confcont);
                                    p.setConference(c);
                                    conferencesByName.put(c.getName(), c);
                                    conferencesById.put(c.getId(), c);
                                } else {
                                    System.out.println("El paper no s'ha creat");
                                    return;
                                }
                            } else {
                                p.setConference(c);
                                c.addExposedPaper(p);
                            }

                            System.out.println("Quants autors té aquest article?");
                            int numAut = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numAut; ++i) {
                                System.out.println("Introduïu el nom de l'autor");
                                String nameAut = scan.nextLine();
                                a = authorsByName.get(nameAut);
                                if (a == null) {
                                    System.out.println("Aquest autor no existeix, vols crear-lo? [s/n]");
                                    if (scan.nextLine().equals("s")) {
                                        a = new Author(nameAut, Author.getMaxId() + 1);
                                        authorsByName.put(a.getName(), a);
                                        authorsById.put(a.getId(), a);
                                        addedAuthors.add(a);
                                        p.addAuthor(a);
                                        a.addPaper(p);
                                    } else {
                                        System.out.println("El paper no s'ha creat");
                                        for (int j = 0; j < addedAuthors.size(); i++) {
                                            authorsById.remove(addedAuthors.get(j).getId());
                                            authorsByName.remove(addedAuthors.get(j).getName());
                                            p.removeAuthor(addedAuthors.get(j));
                                        }
                                        return;
                                    }
                                } else {
                                    p.addAuthor(a);
                                    a.addPaper(p);
                                }
                            }
                            //afegirTermes();
                            papersByName.put(p.getName(), p);
                            papersById.put(p.getId(), p);
                            break;

                        case ("T"): //Term
                            System.out.println("Introduïu el nom del terme");
                            String nameTerm = scan.nextLine();
                            Term t = new Term(nameTerm, Term.getMaxId() + 1);
                            if (termsByName.get(t.getName()) != null) {
                                System.err.println("\u001B[31m" + "Aquest terme ja existeix" + "\u001B[0m");
                                return;
                            }
                           /* ArrayList<Paper> addedPapers = new ArrayList<>();
                            ArrayList<Conference> addedConferences = new ArrayList<>();*/
                            System.out.println("En quants articles ha participat?");
                            numArt = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numArt; ++i) {
                                System.out.println("Introduïu el nom de l'article");
                                String nameArt = scan.nextLine();

                                p = papersByName.get(nameArt);
                                if (p == null) {
                                    System.out.println("L'article no existeix, voleu crear-lo? [s/n]");
                                    if (scan.nextLine().equals("s")) {
                                        p = new Paper(nameArt, Paper.getMaxId() + 1);
                                        System.out.println("Quina és la seva conferència?");
                                        nameConf = scan.nextLine();
                                        c = conferencesByName.get(nameConf);
                                        if (c == null) {
                                            System.out.println("La conferencia no existeix, vols crear-la? [s/n]:");
                                            if (scan.nextLine().equals("s")) {
                                                c = new Conference(nameConf, Conference.getMaxId() + 1);
                                                System.out.println("Introduïu l'any");
                                                int confyear = scan.nextInt();
                                                scan.nextLine();
                                                System.out.println("Introduïu el continent");
                                                String confcont = scan.nextLine();
                                                c.setYear(confyear);
                                                c.setContinent(confcont);
                                                p.setConference(c);
                                                c.addExposedPaper(p);
                                                conferencesByName.put(c.getName(), c);
                                                conferencesById.put(c.getId(), c);

                                            } else {
                                                System.out.println("El paper no s'ha creat");
                                                return;
                                            }
                                        } else {
                                            p.setConference(c);
                                            c.addExposedPaper(p);
                                        }

                                        System.out.println("Quants autors té aquest article?");
                                        numAut = scan.nextInt();
                                        scan.nextLine();
                                        for (i = 0; i < numAut; ++i) {
                                            System.out.println("Introduïu el nom de l'autor");
                                            String nameAut = scan.nextLine();
                                            a = authorsByName.get(nameAut);
                                            if (a != null) {
                                                System.out.println("Aquest autor no existeix, vols crear-lo? [s/n]");
                                                if (scan.nextLine().equals("s")) {
                                                    a = new Author(nameAut, Author.getMaxId() + 1);
                                                    p.addAuthor(a);
                                                    a.addPaper(p);
                                                    authorsById.put(a.getId(), a);
                                                    authorsByName.put(a.getName(), a);
                                                } else {
                                                    System.out.println("El paper no s'ha creat");
                                                    //eliminar autors i conferencies creats i relacions
                                                    return;
                                                }
                                            } else {
                                                p.addAuthor(a);
                                                a.addPaper(p);
                                            }
                                        }
                                    } else {
                                        System.out.println("El terme no s'ha creat");
                                        //eliminar autors i conferencies creats i relacions

                                        return;
                                    }

                                    papersById.put(p.getId(), p);
                                    papersByName.put(p.getName(), p);
                                } else {
                                    t.addPaperWhichTalkAboutIt(p);
                                    p.addTerm(t);
                                }

                            }
                            termsByName.put(t.getName(), t);
                            termsById.put(t.getId(), t);
                            return;

                        case ("C"): //Conference
                            System.out.println("Introduïu el nom de la conferència");
                            String confname = scan.nextLine();
                            if (conferencesByName.get(confname) != null) {
                                System.err.println("\u001B[31m" + "la conferència ja existeix" + "\u001B[0m");
                                return;
                            }
                            Conference co = new Conference(confname, Conference.getMaxId() + 1);
                            System.out.println("Introduïu l'any");
                            int confyear = scan.nextInt();
                            scan.nextLine();
                            System.out.println("Introduïu el continent");
                            String confcont = scan.nextLine();
                            co.setYear(confyear);
                            co.setContinent(confcont);
                            System.out.println("Quants articles té aquesta conferència?");
                            int numart = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numart; ++i) {
                                //S'esperen articles nous
                                System.out.println("Introduïu el nom del nou article");
                                String nameP = scan.nextLine();
                                Paper pa = new Paper(nameP, Paper.getMaxId() + 1);
                                if (papersByName.get(pa.getName()) != null) {
                                    System.err.println("\u001B[31m" + "Aquest article ja existeix" + "\u001B[0m");
                                    return;
                                }
                                System.out.println("Quants autors té aquest article?");
                                int numAut2 = scan.nextInt();
                                scan.nextLine();
                                for (int j = 0; j < numAut2; ++j) {
                                    System.out.println("Introduïu el nom de l'autor");
                                    String nameAut = scan.nextLine();
                                    Author at = authorsByName.get(nameAut);
                                    if (at == null) {
                                        System.out.println("L'autor no existeix, vols crear-lo? [s/n]");
                                        if (scan.nextLine().equals("s")) {
                                            at = new Author(nameAut, Author.getMaxId() + 1);
                                            authorsByName.put(at.getName(), at);
                                            authorsById.put(at.getId(), at);
                                        } else {
                                            System.out.println("La conferencia no s'ha creat");
                                            //eliminar conferencia, papers i relacions
                                            return;
                                        }
                                    } else {
                                        at.addPaper(pa);
                                        pa.addAuthor(at);
                                    }
                                }
                                System.out.println("Quants Terms té aquest article?");
                                int numTerms = scan.nextInt();
                                scan.nextLine();
                                for (int k = 0; k < numTerms; ++k) {
                                    System.out.println("Introduïu el term");
                                    nameTerm = scan.nextLine();
                                    t = termsByName.get(nameTerm);
                                    if (t == null) {
                                        System.out.println("El terme no existeix, vols crear-lo? [s/n]");
                                        if (scan.nextLine().equals("s")) {
                                            t = new Term(nameTerm, Term.getMaxId() + 1);
                                            termsByName.put(t.getName(), t);
                                            termsById.put(t.getId(), t);
                                        } else {
                                            System.out.println("La conferencia no s'ha creat");
                                            //Eliminar conferencia, papers, termes i relacions
                                            return;
                                        }
                                    }
                                    t.addPaperWhichTalkAboutIt(papersById.get(pa.getId()));
                                    pa.addTerm(t);

                                }
                                papersByName.put(pa.getName(), pa);
                                papersById.put(pa.getId(), pa);
                                pa.setConference(co);
                                co.addExposedPaper(pa);
                            }
                            conferencesByName.put(co.getName(), co);
                            conferencesById.put(co.getId(), co);
                            return;
                    }
                    break;

                case ("Ed"): //Editar

                    switch (objectType) {
                        case ("A"):
                            System.out.println("Introdueix el nom del autor a modificar:");
                            origenName = scan.nextLine();
                            Author a = authorsByName.get(origenName);
                            System.out.println("Introdueix el nou nom del autor:");
                            newName = scan.nextLine();
                            a.setName(newName);
                            authorsByName.remove(origenName);
                            authorsByName.put(newName, a);
                            break;
                        case ("P"):
                            System.out.println("Introdueix el nom de l'article a modificar:");
                            origenName = scan.nextLine();
                            Paper p = papersByName.get(origenName);
                            System.out.println("Introdueix el nou nom de l'article:");
                            newName = scan.nextLine();
                            p.setName(newName);
                            papersByName.remove(origenName);
                            papersByName.put(newName, p);
                            break;
                        case ("T"):
                            System.out.println("Introdueix el nom del terme a modificar:");
                            origenName = scan.nextLine();
                            Term t = termsByName.get(origenName);
                            System.out.println("Introdueix el nou nom del terme:");
                            newName = scan.nextLine();
                            t.setName(newName);
                            termsByName.remove(origenName);
                            termsByName.put(newName, t);
                            break;
                        case ("C"):
                            System.out.println("Introdueix el nom de la conferència a modificar:");
                            origenName = scan.nextLine();
                            Conference c = conferencesByName.get(origenName);
                            System.out.println("Introdueix el nou nom de la conferència:");
                            newName = scan.nextLine();
                            System.out.println("Introdueix l'any de la conferència:");
                            String any = scan.nextLine();
                            System.out.println("Introdueix el continent de la conferència: ");
                            String cont = scan.nextLine();
                            c.setName(newName);
                            conferencesByName.remove(origenName);
                            conferencesByName.put(newName, c);
                            break;
                    }
                    break;

                case ("El"): // Eliminar

                    switch (objectType) {
                        case ("A"):
                            try {
                                System.out.println("Introdueix el nom de l'autor a esborrar)");
                                name = scan.nextLine();
                                Author a = authorsByName.get(name);
                                //eliminem relacions
                                Collection<Paper> auxiliar = a.getPapersById(papersById).values();
                                for (Iterator it = auxiliar.iterator(); it.hasNext(); ) {
                                    Paper p = (Paper) it.next();
                                    p.removeAuthor(a);
                                    if (p.getAuthorsById(authorsById).size() < 1) {

                                        //RECOMENÇAR UN PROCES DE ELIMINAR PAPER
                                        deletePaperRelationsOnConferences(p);
                                        deletePaperRelationsOnTerms(p);
                                        papersById.remove(p.getId());
                                        papersByName.remove(p.getName());
                                    }
                                }
                                //eliminem l'autor
                                id = a.getId();
                                authorsByName.remove(name);
                                authorsById.remove(id);
                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m" + "Aquest autor no existeix." + "\u001B[0m");
                            }
                            break;
                        case ("P"):
                            try {
                                System.out.println("Introdueix el nom de l'article a esborrar)");
                                name = scan.nextLine();
                                Paper p = papersByName.get(name);
                                //eliminar relacions

                                deletePaperRelationsOnConferences(p);

                                deletePaperRelationsOnAuthors(p);
                                deletePaperRelationsOnTerms(p);
                                //eliminar paper
                                id = p.getId();
                                papersByName.remove(name);
                                papersById.remove(id);
                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m" + "Aquest article no existeix." + "\u001B[0m");
                            }
                            break;
                        case ("T"):
                            try {
                                System.out.println("Introdueix el nom del terme a esborrar)");
                                name = scan.nextLine();
                                Term t = termsByName.get(name);
                                //eliminem relacions
                                Collection<Paper> auxiliar = t.getPapersWhichTalkAboutThisById(papersById).values();
                                for (Iterator it = auxiliar.iterator(); it.hasNext(); ) {
                                    Paper p = (Paper) it.next();
                                    p.removeTerm(t);
                                    if (p.getTermsById(termsById).size() < 1) { //Eliminar paper i relacions
                                        deletePaperRelationsOnAuthors(p);
                                        deletePaperRelationsOnConferences(p);
                                        papersByName.remove(p.getName());
                                        papersById.remove(p.getId());
                                    }
                                }
                                //eliminem el term
                                id = t.getId();
                                termsByName.remove(name);
                                termsById.remove(id);
                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m" + "Aquest terme no existeix." + "\u001B[0m");
                            }
                            break;
                        case ("C"):
                            try {
                                System.out.println("Introdueix el nom de la conferencia a esborrar)");
                                name = scan.nextLine();
                                Conference c = conferencesByName.get(name);
                                //eliminem relacions
                                Collection<Paper> auxiliarp = c.getExposedPapersById(papersById).values();
                                for (Iterator it = auxiliarp.iterator(); it.hasNext(); ) {
                                    Paper p = (Paper) it.next();
                                    deletePaperRelationsOnAuthors(p);
                                    deletePaperRelationsOnTerms(p);
                                    papersById.remove(p.getId());
                                    papersByName.remove(p.getName());
                                }
                                //eliminem l'autor
                                id = c.getId();
                                conferencesByName.remove(name);
                                conferencesById.remove(id);

                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m" + "Aquesta conferencia no existeix." + "\u001B[0m");
                            }
                            break;
                    }
                    break;

            }

        }

        //testDomain(authorsById,papersById,conferencesById,termsById);
    }


    private void readAuthorsFromFile() {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "author.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("	");
                int id = Integer.parseInt(aux[0]);
                Author author = new Author(aux[1], id);
                authorsById.put(id, author);
                authorsByName.put(aux[1], author);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void readPapersFromFile() {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("	");
                int id = Integer.parseInt(aux[0]);
                Paper paper = new Paper(aux[1], id);
                papersById.put(id, paper);
                papersByName.put(aux[1], paper);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void readConferencesFromFile() {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "conf.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("	");
                int id = Integer.parseInt(aux[0]);
                Conference conf = new Conference(aux[1], id);
//                conf.setYear(Integer.parseInt(aux[2]));
                //              conf.setContinent(aux[3]);
                conferencesById.put(id, conf);
                conferencesByName.put(aux[1], conf);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


    private void readTermsFromFile() {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "term.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("	");
                int id = Integer.parseInt(aux[0]);
                Term term = new Term(aux[1], id);
                termsById.put(id, term);
                termsByName.put(aux[1], term);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


    private void readPaperAuthorRelations() {
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_author.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            Paper p;
            Author a;
            while ((line = reader.readLine()) != null) {
                String aux[] = line.split("	");
                p = papersById.get(Integer.parseInt(aux[0]));
                a = authorsById.get(Integer.parseInt(aux[1]));

                p.addAuthor(a);
                a.addPaper(p);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void readConferenceRelations() {
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_conf.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            Paper p;
            Conference c;
            while ((line = reader.readLine()) != null) {
                String aux[] = line.split("	");
                p = papersById.get(Integer.parseInt(aux[0]));
                c = conferencesById.get(Integer.parseInt(aux[1]));

                p.setConference(c);
                c.addExposedPaper(p);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void readTermRelations() {
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_term.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            Paper p;
            Term t;
            while ((line = reader.readLine()) != null) {
                String aux[] = line.split("	");
                p = papersById.get(Integer.parseInt(aux[0]));
                t = termsById.get(Integer.parseInt(aux[1]));

                p.addTerm(t);
                t.addPaperWhichTalkAboutIt(p);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


    private void deletePaperRelationsOnConferences(Paper p) {
        Conference auxiliarc = p.getConference();
        auxiliarc.removeExposedPaperBy(p);
        if (auxiliarc.getExposedPapersById(papersById).size() < 1) {
            conferencesById.remove(auxiliarc.getId());
            conferencesByName.remove(auxiliarc.getName());
        }
    }

    private void deletePaperRelationsOnAuthors(Paper p) {
        Collection<Author> auxiliara = p.getAuthorsById(authorsById).values();
        for (Iterator ita = auxiliara.iterator(); ita.hasNext(); ) {
            Author a = (Author) ita.next();
            a.removePaper(p);
            if (a.getPapersById(papersById).size() < 1) {
                authorsById.remove(a.getId());
                authorsByName.remove(a.getName());
            }
        }
    }

    private void deletePaperRelationsOnTerms(Paper p) {
        Collection<Term> auxiliart = p.getTermsById(termsById).values();
        for (Iterator itt = auxiliart.iterator(); itt.hasNext(); ) {
            Term t = (Term) itt.next();
            t.removePaperWhichTalkAboutIt(p);
            if (t.getPapersWhichTalkAboutThisById(papersById).size() < 1) {
                termsById.remove(t.getId());
                termsByName.remove(t.getName());
            }
        }
    }

}