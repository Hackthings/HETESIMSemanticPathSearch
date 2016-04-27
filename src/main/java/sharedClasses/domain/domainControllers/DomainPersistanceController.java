package main.java.sharedClasses.domain.domainControllers;



import main.java.sharedClasses.domain.nodes.Author;
import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;
import main.java.sharedClasses.domain.nodes.Term;

import java.io.*;
import java.util.*;



public class DomainPersistanceController {
    private static String filepath;

    public DomainPersistanceController() {filepath = "/src/main/java/data/";}

    public void testDomain(HashMap<Integer, Author> authorsById,
                      HashMap<Integer, Paper> papersById,
                      HashMap<Integer, Conference> conferencesById,
                      HashMap<Integer, Term> termsById){

        System.out.println("TEST DOMAIN!");


        System.out.println("AUTORS : *_*_*_*_*_*_*_*_*_*_*_*_*_**_*_*_*_ ");

        for(Author a : authorsById.values()){
           System.out.println("Autor: " + a.getId() + " " + a.getName());
           System.out.println("Papers Relacionats ");
           for(Paper p : a.getPapersById().values()){
               System.out.println(p.getId() + " " + p.getName());
           }
       }

        System.out.println("PAPERS : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");
        for(Paper p : papersById.values()){
            System.out.println("Paper: " + p.getId() + " " + p.getName());
            System.out.println("Autors relacionats: ");
            for(Author a : p.getAuthorsById().values()){
                System.out.println(a.getId() + " " + a.getName());
            }
            System.out.println("Termes relacionats: ");

            for(Term t : p.getTermsById().values()){
                System.out.println(t.getId() + " " + t.getName());
            }
            System.out.println("Conferencia relacionada: ");
            System.out.println(p.getConference().getId() + " " + p.getConference().getName());
        }

        System.out.println("TERMES : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");

        for(Term t : termsById.values()){
            System.out.println("Tema: " + t.getId() + " " + t.getName());
            System.out.println("Papers Relacionats: ");
            for(Paper p : t.getPapersWhichTalkAboutThisById().values()){
                System.out.println(p.getId() + " " + p.getName());
            }
        }

        System.out.println("CONFERENCIES : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");

        for(Conference c : conferencesById.values()){
            System.out.println("Conferencia: " + c.getId() + " " + c.getName() + " " +c.getYear()+" "+c.getContinent());
            System.out.println("Les seves relacions: ");
            for(Paper p : c.getExposedPapersById().values()){
                System.out.println(p.getId() + " " + p.getName());
            }
        }
        System.out.println("FIIIII : *_*_*_*_*_*_*_*_**_*_*_*_*_*_*_**_*_*_*_*_*_*_*_");

    }

    public void readAll(HashMap<Integer, Author> authorsById,
                        HashMap<Integer, Paper> papersById,
                        HashMap<Integer, Conference> conferencesById,
                        HashMap<Integer, Term> termsById,
                        HashMap<String, Author> authorsByName,
                        HashMap<String, Paper> papersByName,
                        HashMap<String, Conference> conferencesByName,
                        HashMap<String, Term> termsByName){

        readAuthorsFromFile(authorsById, authorsByName);
        readPapersFromFile(papersById, papersByName);
        readConferencesFromFile(conferencesById, conferencesByName);
        readTermsFromFile(termsById, termsByName);
        readPaperAuthorRelations(papersById,authorsById);
        readTermRelations(papersById,termsById);
        readConferenceRelations(papersById,conferencesById);


        //IMPRIMIR HASHMAPS
        testDomain(authorsById,papersById,conferencesById,termsById);
    }

    public void newEdit(HashMap<Integer, Author> authorsById,
                        HashMap<Integer, Paper> papersById,
                        HashMap<Integer, Conference> conferencesById,
                        HashMap<Integer, Term> termsById,
                        HashMap<String, Author> authorsByName,
                        HashMap<String, Paper> papersByName,
                        HashMap<String, Conference> conferencesByName,
                        HashMap<String, Term> termsByName) {

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
                            Author a = new Author(nameAuthor,Author.getMaxId()+1);
                            if (authorsByName.get(a.getName()) != null) {
                                System.err.println("Aquest autor ja existeix");
                                break;
                            }
                            authorsByName.put(a.getName(), a);
                            authorsById.put(a.getId(), a);
                           writeAuthorToFile(a);
                            System.out.println("En quants articles ha participat?");
                            int numArt = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numArt; ++i) {
                                System.out.println("Introduïu el nom de l'article");
                                String nameArt = scan.nextLine();

                                try {
                                    Paper p = papersByName.get(nameArt);
                                    a.addPaper(p);
                                    writeNewRelationPaperAuthor(p, a);
                                } catch (NullPointerException e) {
                                    System.err.println("L'article no existeix");
                                }
                            }
                            break;
                        case ("P"): //Paper
                            System.out.println("Introduïu el nom de l'article");
                            String namePaper = scan.nextLine();
                            Paper p = new Paper(namePaper, Paper.getMaxId()+1);
                            if (papersByName.get(p.getName()) != null) {
                                System.err.println("Aquest article ja existeix");
                                break;
                            }
                            papersByName.put(p.getName(), p);
                            papersById.put(p.getId(), p);
                            writePaperToFile(p);
                            System.out.println("Quina és la seva conferència?");
                            String nameConf = scan.nextLine();
                            Conference c;
                            try {
                                c = conferencesByName.get(nameConf);
                            } catch (NullPointerException e) {
                                c = new Conference(nameConf, Conference.getMaxId()+1);
                               /* System.out.println("Introduïu l'any");
                                int confyear = scan.nextInt();
                                scan.nextLine();
                                System.out.println("Introduïu el continent");
                                String confcont = scan.nextLine();
                                c.setYear(confyear);
                                c.setContinent(confcont);*/
                                conferencesByName.put(c.getName(), c);
                                conferencesById.put(c.getId(), c);
                                writeConferenceToFile(c);
                            }
                            p.setConference(c);
                            writeNewRelationPaperConf(p,c);
                            System.out.println("Quants autors té aquest article?");
                            int numAut = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numAut; ++i) {
                                System.out.println("Introduïu el nom de l'autor");
                                String nameAut = scan.nextLine();
                                try {
                                    a = authorsByName.get(nameAut);
                                } catch (NullPointerException e) {
                                    a = new Author(nameAut, Author.getMaxId()+1);
                                    authorsByName.put(a.getName(), a);
                                    authorsById.put(a.getId(), a);
                                    writeAuthorToFile(a);
                                }
                                p.addAuthor(a);
                                a.addPaper(p);
                                writeNewRelationPaperAuthor(p, a);
                            }
                            //afegirTermes();
                            break;
                        case ("T"): //Term
                            System.out.println("Introduïu el nom del terme");
                            String nameTerm = scan.nextLine();
                            Term t = new Term(nameTerm, Term.getMaxId()+1);
                            if (termsByName.get(t.getName()) != null) {
                                System.err.println("\u001B[31m"+"Aquest autor ja existeix"+"\u001B[0m");
                                break;
                            }
                            termsByName.put(t.getName(), t);
                            termsById.put(t.getId(), t);
                            writeTermToFile(t);
                            System.out.println("En quants articles ha participat?");
                            numArt = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numArt; ++i) {
                                System.out.println("Introduïu el nom de l'article");
                                String nameArt = scan.nextLine();

                                try {
                                    p = papersByName.get(nameArt);
                                    t.addPaperWhichTalkAboutIt(p);
                                    writeNewRelationPaperTerm(p, t);
                                } catch (NullPointerException e) {
                                    System.err.println("\u001B[31m"+"L'article no existeix"+"\u001B[0m");
                                }
                            }
                            break;

                        case ("C"): //Conference
                            System.out.println("Introduïu el nom de la conferència");
                            String confname = scan.nextLine();
                            if (conferencesByName.get(confname) != null) {
                                System.err.println("\u001B[31m"+"la conferència ja existeix"+"\u001B[0m");
                                break;
                            }
                            Conference co = new Conference(confname, Conference.getMaxId() + 1);
                            System.out.println("Introduïu l'any");
                            int confyear = scan.nextInt();
                            scan.nextLine();
                            System.out.println("Introduïu el continent");
                            String confcont = scan.nextLine();
                            co.setYear(confyear);
                            co.setContinent(confcont);
                            conferencesByName.put(co.getName(), co);
                            conferencesById.put(co.getId(), co);
                            writeConferenceToFile(co);
                            System.out.println("Quants articles té aquesta conferència?");
                            int numart = scan.nextInt();
                            scan.nextLine();
                            for (int i = 0; i < numart; ++i) {
                                //S'esperen articles nous
                                System.out.println("Introduïu el nom del nou article");
                                String nameP = scan.nextLine();
                                Paper pa = new Paper(nameP, Paper.getMaxId()+1);
                                if (papersByName.get(pa.getName()) != null) {
                                    System.err.println("\u001B[31m"+"Aquest article ja existeix"+"\u001B[0m");
                                    break;
                                }
                                papersByName.put(pa.getName(), pa);
                                papersById.put(pa.getId(), pa);
                                writePaperToFile(pa);
                                pa.setConference(co);
                                co.addExposedPaper(pa);
                                System.out.println("Quants autors té aquest article?");
                                int numAut2 = scan.nextInt();
                                scan.nextLine();
                                for (int j = 0; j < numAut2; ++j) {
                                    System.out.println("Introduïu el nom de l'autor");
                                    String nameAut = scan.nextLine();
                                    Author at;
                                    if (authorsByName.get(nameAut) != null) {
                                        at = authorsByName.get(nameAut);
                                    } else {
                                        at = new Author(nameAut, Author.getMaxId() + 1);
                                        authorsByName.put(at.getName(), at);
                                        authorsById.put(at.getId(), at);
                                        writeAuthorToFile(at);
                                    }
                                    at.addPaper(papersById.get(pa.getId()));
                                    pa.addAuthor(at);
                                    writeNewRelationPaperAuthor(pa, at);
                                }
                                System.out.println("Quants Terms té aquest article?");
                                int numTerms = scan.nextInt();
                                scan.nextLine();
                                for (int k = 0; k < numTerms; ++k) {
                                    System.out.println("Introduïu el term");
                                    nameTerm = scan.nextLine();
                                    if (termsByName.get(nameTerm) != null) {
                                        t = termsByName.get(nameTerm);
                                    } else {
                                        t = new Term(nameTerm, Term.getMaxId() + 1);
                                        termsByName.put(t.getName(), t);
                                        termsById.put(t.getId(), t);
                                        writeTermToFile(t);
                                    }
                                    t = termsByName.get(nameTerm);
                                    t.addPaperWhichTalkAboutIt(papersById.get(pa.getId()));
                                    pa.addTerm(t);
                                    writeNewRelationPaperTerm(pa, t);
                                }
                                writeNewRelationPaperConf(pa, co);
                            }
                            break;
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
                            editAuthorFromFile(a, newName);
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
                            editPaperFromFile(p, newName);
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
                            editTermFromFile(t, newName);
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
                            editConferenceFromFile(c, newName, any, cont);
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
                                Collection<Paper> auxiliar = a.getPapersById().values();
                                for (Iterator it = auxiliar.iterator(); it.hasNext(); ) {
                                    Paper p = (Paper) it.next();
                                    p.removeAuthor(a);
                                    if (p.getAuthorsById().size() < 1) {

                                        //RECOMENÇAR UN PROCES DE ELIMINAR PAPER
                                        deletePaperRelationsOnConferences(conferencesById, conferencesByName, p);
                                        deletePaperRelationsOnTerms(termsById, termsByName, p);
                                        papersById.remove(p.getId());
                                        papersByName.remove(p.getName());
                                        deletePaperFromFile(p);
                                    }
                                }
                                //eliminem l'autor
                                deleteAuthorFromFile(a);
                                id = a.getId();
                                authorsByName.remove(name);
                                authorsById.remove(id);
                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m"+"Aquest autor no existeix."+"\u001B[0m");
                            }
                            break;
                        case ("P"):
                            try {
                                System.out.println("Introdueix el nom de l'article a esborrar)");
                                name = scan.nextLine();
                                Paper p = papersByName.get(name);
                                //eliminar relacions

                                deletePaperRelationsOnConferences(conferencesById, conferencesByName, p);

                                deletePaperRelationsOnAuthors(authorsById, authorsByName, p);
                                deletePaperRelationsOnTerms(termsById, termsByName, p);
                                //eliminar paper
                                deletePaperFromFile(p);
                                deletePaperRelationsOnFile(p);
                                id = p.getId();
                                papersByName.remove(name);
                                papersById.remove(id);
                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m"+"Aquest article no existeix."+"\u001B[0m");
                            }
                            break;
                        case ("T"):
                            try {
                                System.out.println("Introdueix el nom del terme a esborrar)");
                                name = scan.nextLine();
                                Term t = termsByName.get(name);
                                //eliminem relacions
                                Collection<Paper> auxiliar = t.getPapersWhichTalkAboutThisById().values();
                                for (Iterator it = auxiliar.iterator(); it.hasNext(); ) {
                                    Paper p = (Paper) it.next();
                                    p.removeTerm(t);
                                    if (p.getTermsById().size() < 1) { //Eliminar paper i relacions
                                        deletePaperRelationsOnAuthors(authorsById, authorsByName, p);

                                        deletePaperRelationsOnConferences(conferencesById, conferencesByName, p);

                                        deletePaperFromFile(p);
                                        deletePaperRelationsOnFile(p);
                                        papersByName.remove(p.getName());
                                        papersById.remove(p.getId());
                                    }
                                }
                                //eliminem el term
                                deleteTermFromFile(t);
                                id = t.getId();
                                termsByName.remove(name);
                                termsById.remove(id);
                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m"+"Aquest terme no existeix."+"\u001B[0m");
                            }
                            break;
                        case ("C"):
                            try {
                                System.out.println("Introdueix el nom de la conferencia a esborrar)");
                                name = scan.nextLine();
                                Conference c = conferencesByName.get(name);
                                //eliminem relacions
                                Collection<Paper> auxiliarp = c.getExposedPapersById().values();
                                for (Iterator it = auxiliarp.iterator(); it.hasNext(); ) {
                                    Paper p = (Paper) it.next();
                                    deletePaperRelationsOnAuthors(authorsById, authorsByName, p);
                                    deletePaperRelationsOnTerms(termsById, termsByName, p);
                                    deletePaperFromFile(p);
                                    papersById.remove(p.getId());
                                    papersByName.remove(p.getName());
                                }
                                //eliminem l'autor
                                deleteConferenceFromFile(c);
                                id = c.getId();
                                conferencesByName.remove(name);
                                conferencesById.remove(id);

                            } catch (NullPointerException ex) {
                                System.out.println("\u001B[31m"+"Aquesta conferencia no existeix."+"\u001B[0m");
                            }
                            break;
                    }
                    break;

            }

        }

        testDomain(authorsById,papersById,conferencesById,termsById);
    }





    private void readAuthorsFromFile(HashMap<Integer, Author> authorsById, HashMap<String,Author> authorsByName){
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "author.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Author author = new Author(aux[1],id);
                authorsById.put(id,author);
                authorsByName.put(aux[1],author);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }

    private void readPapersFromFile(HashMap<Integer, Paper> papersById, HashMap<String,Paper> papersByName){
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Paper paper = new Paper(aux[1],id);
                papersById.put(id,paper);
                papersByName.put(aux[1], paper);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }

    private void readConferencesFromFile(HashMap<Integer, Conference> conferencesById, HashMap<String,Conference> conferencesByName){
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "conf.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Conference conf = new Conference(aux[1],id);
                conf.setYear(Integer.parseInt(aux[2]));
                conf.setContinent(aux[3]);
                conferencesById.put(id,conf);
                conferencesByName.put(aux[1], conf);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }


    private void readTermsFromFile(HashMap<Integer, Term> termsById, HashMap<String, Term> termsByName){
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "term.txt"));
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Term term = new Term(aux[1],id);
                termsById.put(id,term);
                termsByName.put(aux[1], term);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }



    private void writeAuthorToFile(Author author){
        String wrauthor = Integer.toString(author.getId()) + ";" + author.getName();
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "author.txt"));
        writeToFile(wrauthor, inputFile);
    }

    public void writePaperToFile(Paper paper){
        String wrpaper = Integer.toString(paper.getId()) + ";" + paper.getName();
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper.txt"));
        writeToFile(wrpaper, inputFile);
    }


    public void writeConferenceToFile(Conference conference) {
        String wrconf = Integer.toString(conference.getId()) + ";" + conference.getName() +
                ";" + conference.getYear() + ";" + conference.getContinent();
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "conf.txt"));
        writeToFile(wrconf, inputFile);
    }

    private void writeTermToFile(Term term){
        String wrterm = Integer.toString(term.getId()) + ";" + term.getName();
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "term.txt"));
        writeToFile(wrterm, inputFile);
    }


    private void deleteAuthorFromFile(Author author){
        String authorId = Integer.toString(author.getId());
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "author.txt"));
        File tempFile = new File("myTempFile.txt");
        if (!deleteFromFile(authorId, inputFile, tempFile)) return;
        deleteAuthorRelationsOnFile(author);
    }



    private void deletePaperFromFile(Paper paper){
        String paperId = Integer.toString(paper.getId());
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper.txt"));
        File tempFile = new File("myTempFile.txt");
        if (!deleteFromFile(paperId, inputFile, tempFile)) return;
        deletePaperRelationsOnFile(paper);
    }


    private void deleteConferenceFromFile(Conference conference){
        String conferenceId = Integer.toString(conference.getId());
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "conf.txt"));
        File tempFile = new File("myTempFile.txt");
        if (!deleteFromFile(conferenceId, inputFile, tempFile)) return;
        deleteConferenceRelationsOnFile(conference);

    }

    private void deleteTermFromFile(Term term){
        String termId = Integer.toString(term.getId());
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "term.txt"));
        File tempFile = new File("myTempFile.txt");
        if (!deleteFromFile(termId, inputFile, tempFile)) return;
        deleteTermRelationsOnFile(term);

    }




    private void editAuthorFromFile(Author author, String name){
        String authorId = Integer.toString(author.getId());
        String p = new File("").getAbsolutePath();
        String replace = authorId + ";" + name;
        File inputFile = new File(p.concat(filepath + "author.txt"));
        File tempFile = new File("myTempFile.txt");
        editFromFile(authorId, replace, inputFile, tempFile);
    }




    private void editPaperFromFile(Paper paper, String value) {
            String paperId = Integer.toString(paper.getId());
            String p = new File("").getAbsolutePath();
            String replace = paperId + ";" + value;
            File inputFile = new File(p.concat(filepath + "paper.txt"));
            File tempFile = new File("myTempFile.txt");
            editFromFile(paperId, replace, inputFile, tempFile);

        }


    private void editConferenceFromFile(Conference conference, String key, String year, String continent){
            String conferenceId = Integer.toString(conference.getId());
            String p = new File("").getAbsolutePath();
            String replace = conferenceId + ";" + key + ";" + year + ";" + continent;
            File inputFile = new File(p.concat(filepath + "conf.txt"));
            File tempFile = new File("myTempFile.txt");
            editFromFile(conferenceId, replace, inputFile, tempFile);

        }


    private void editTermFromFile(Term term, String value){
            String termId = Integer.toString(term.getId());
            String p = new File("").getAbsolutePath();
            String replace = termId + ";" + value;
            File inputFile = new File(p.concat(filepath + "term.txt"));
            File tempFile = new File("myTempFile.txt");
            editFromFile(termId, replace, inputFile, tempFile);

        }

    private void readPaperAuthorRelations( HashMap<Integer, Paper> papersById,HashMap<Integer, Author> authorsById){
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_author.txt"));
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            Paper p;
            Author a;
            while((line = reader.readLine()) != null){
                String aux[] = line.split(";");
                p = papersById.get(Integer.parseInt(aux[0]));
                a = authorsById.get(Integer.parseInt(aux[1]));

                p.addAuthor(a);
                a.addPaper(p);
            }
        }
        catch (IOException x){
            System.err.format("IOException: %s%n", x);
        }
    }
    private void readConferenceRelations( HashMap<Integer, Paper> papersById,HashMap<Integer, Conference> conferencesById){
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_conf.txt"));
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            Paper p;
            Conference c;
            while((line = reader.readLine()) != null){
                String aux[] = line.split(";");
                p = papersById.get(Integer.parseInt(aux[0]));
                c = conferencesById.get(Integer.parseInt(aux[1]));

                p.setConference(c);
                c.addExposedPaper(p);
            }
        }
        catch (IOException x){
            System.err.format("IOException: %s%n", x);
        }
    }
    private void readTermRelations(HashMap<Integer, Paper> papersById, HashMap<Integer, Term> termsById){
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_term.txt"));
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line;
            Paper p;
            Term t;
            while((line = reader.readLine()) != null){
                String aux[] = line.split(";");
                p = papersById.get(Integer.parseInt(aux[0]));
                t = termsById.get(Integer.parseInt(aux[1]));

                p.addTerm(t);
                t.addPaperWhichTalkAboutIt(p);
            }
        }
        catch (IOException x){
            System.err.format("IOException: %s%n", x);
        }
    }

    private void writeAuthorRelations(Author author){
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_author.txt"));
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            String authorId = Integer.toString(author.getId());
            for(Paper p : author.getPapersById().values()){
                String relationToWrite = Integer.toString(p.getId()) + ";" + authorId;
                writer.write(relationToWrite,0,relationToWrite.length());
                writer.newLine();
            }
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
    }

    private void writePaperRelations(Paper paper){
        //Escriu la relació amb els autors
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper_author.txt"));
        String paperId = Integer.toString(paper.getId());
        try( BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            for(Author a : paper.getAuthorsById().values()){
                String relationToWrite = paperId + ";" + Integer.toString(a.getId());
                writer.write(relationToWrite,0,relationToWrite.length());
                writer.newLine();
            }
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
        //Escriu les relacions amb els termes
        p = new File("").getAbsolutePath();
        inputFile = new File(p.concat(filepath + "paper_term.txt"));
        try( BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            for(Term t : paper.getTermsById().values()){
                String relationToWrite = paperId + ";" + Integer.toString(t.getId());
                writer.write(relationToWrite,0,relationToWrite.length());
                writer.newLine();
            }
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
        //Escriu la relacio amb al conferencia
        p = new File("").getAbsolutePath();
        inputFile = new File(p.concat(filepath + "paper_conf.txt"));
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            String relationToWrite = paperId + ";" + Integer.toString(paper.getConference().getId());
            writer.write(relationToWrite,0,relationToWrite.length());
            writer.newLine();
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
    }

    private void writeConferenceRelations(Conference conference){
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_conf.txt"));
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            String confId = Integer.toString(conference.getId());
            for(Paper p : conference.getExposedPapersById().values()){
                String relationToWrite = Integer.toString(p.getId()) + ";" + confId;
                writer.write(relationToWrite,0,relationToWrite.length());
                writer.newLine();
            }
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
    }
    private void writeTermRelations(Term term){
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_term.txt"));
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            String termId = Integer.toString(term.getId());
            for(Paper p : term.getPapersWhichTalkAboutThisById().values()){
                String relationToWrite = Integer.toString(p.getId()) + ";" + termId;
                writer.write(relationToWrite,0,relationToWrite.length());
                writer.newLine();
            }
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
    }

    private void writeRelationToFile(int id1, int id2, File inputFile) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            String paperId = Integer.toString(id1);
            String relationToWrite = paperId + ";" + Integer.toString(id2);
            writer.write(relationToWrite,0,relationToWrite.length());
            writer.newLine();
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
    }

    private void writeNewRelationPaperAuthor(Paper paper, Author author){
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper_author.txt"));
        writeRelationToFile(paper.getId(), author.getId(), inputFile);
    }

    private void writeNewRelationPaperTerm(Paper paper, Term term){
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper_term.txt"));
        writeRelationToFile(paper.getId(), term.getId(), inputFile);
    }






    private void deleteAuthorRelationsOnFile(Author author){
            String authorId = Integer.toString(author.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat(filepath + "paper_author.txt"));
            File tempFile = new File("myTempFile.txt");
        deleteRelationsFromFile(authorId, inputFile, tempFile, false);

    }




    private void deletePaperRelationsOnFile(Paper paper){

           String paperId = Integer.toString(paper.getId());
           String p = new File("").getAbsolutePath();
           File inputFile1 = new File(p.concat(filepath + "paper_author.txt"));
           File inputFile2 = new File(p.concat(filepath + "paper_conf.txt"));
           File inputFile3 = new File(p.concat(filepath + "paper_term.txt"));
           File tempFile1 = new File("myTempFile1.txt");
           File tempFile2 = new File("myTempFile2.txt");
           File tempFile3 = new File("myTempFile3.txt");

           //PAPER_AUTHOR

           deleteRelationsFromFile(paperId, inputFile1, tempFile1, true);

           //PAPER_CONF

           deleteRelationsFromFile(paperId, inputFile2, tempFile2 , true);

           //PAPER_TERM

           deleteRelationsFromFile(paperId, inputFile3, tempFile3 , true);

       }
    private void deleteConferenceRelationsOnFile(Conference conference) {

            String confId = Integer.toString(conference.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat(filepath + "paper_conf.txt"));
            File tempFile = new File("myTempFile.txt");
        deleteRelationsFromFile(confId, inputFile, tempFile, false);

    }
    private void deleteTermRelationsOnFile(Term term) {

            String termId = Integer.toString(term.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat(filepath + "paper_term.txt"));
            File tempFile = new File("myTempFile.txt");
            deleteRelationsFromFile(termId, inputFile, tempFile, false);


    }

    private void deletePaperRelationsOnConferences(HashMap<Integer, Conference> conferencesById, HashMap<String, Conference> conferencesByName, Paper p) {
        Conference auxiliarc = p.getConference();
        auxiliarc.removeExposedPaperBy(p);
        if(auxiliarc.getExposedPapersById().size() < 1){
            conferencesById.remove(auxiliarc.getId());
            conferencesByName.remove(auxiliarc.getName());
            deleteConferenceFromFile(auxiliarc);
        }
    }

    private void deletePaperRelationsOnAuthors(HashMap<Integer, Author> authorsById, HashMap<String, Author> authorsByName, Paper p) {
        Collection<Author> auxiliara = p.getAuthorsById().values();
        for(Iterator ita = auxiliara.iterator(); ita.hasNext();){
            Author a = (Author) ita.next();
            a.removePaper(p);
            if(a.getPapersById().size() < 1){
                authorsById.remove(a.getId());
                authorsByName.remove(a.getName());
                deleteAuthorFromFile(a);
            }
        }
    }

    private void deletePaperRelationsOnTerms(HashMap<Integer, Term> termsById, HashMap<String, Term> termsByName, Paper p) {
        Collection<Term> auxiliart = p.getTermsById().values();
        for(Iterator itt = auxiliart.iterator(); itt.hasNext();){
            Term t = (Term) itt.next();
            t.removePaperWhichTalkAboutIt(p);
            if(t.getPapersWhichTalkAboutThisById().size() < 1) {
                termsById.remove(t.getId());
                termsByName.remove(t.getName());
                deleteTermFromFile(t);
            }
        }
    }

    private void writeNewRelationPaperConf(Paper paper, Conference conf) {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "paper_conf.txt"));
        writeRelationToFile(paper.getId(), conf.getId(), inputFile);
    }

    private void deleteRelationsFromFile(String nodeId, File inputFile, File tempFile, boolean is_paper) {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){


            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(!is_paper) {
                    if (!trimmedLine.endsWith(nodeId))
                        writer.write(currentLine + System.getProperty("line.separator"));
                }
                else {
                    if (!trimmedLine.startsWith(nodeId))
                        writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            writer.close();
            reader.close();

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("\u001B[31m"+"Could not delete file"+"\u001B[0m");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("\u001B[31m"+"Could not rename file"+"\u001B[0m");
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeToFile(String wrline, File inputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            writer.write(wrline, 0, wrline.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void editFromFile(String nodeId, String replace, File inputFile, File tempFile) {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(nodeId)){
                    writer.write(replace + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("\u001B[31m"+"Could not delete file"+"\u001B[0m");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("\u001B[31m"+"Could not rename file"+"\u001B[0m");

        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean deleteFromFile(String nodeId, File inputFile, File tempFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){ //


            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (!trimmedLine.startsWith(nodeId))
                    writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("\u001B[31m"+"Could not delete file"+"\u001B[0m");
                return false;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("\u001B[31m"+"Could not rename file"+"\u001B[0m");

        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }

}
