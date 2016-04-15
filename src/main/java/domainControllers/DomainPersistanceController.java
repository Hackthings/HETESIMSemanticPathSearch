package main.java.domainControllers;

import main.java.domain.nodes.Author;
import main.java.domain.nodes.Conference;
import main.java.domain.nodes.Paper;
import main.java.domain.nodes.Term;
import main.java.*;
import main.java.presentation.ConsolePrinter;

import java.io.*;
import java.util.*;

public class DomainPersistanceController {

    public DomainPersistanceController() {}

    public void readAll(HashMap<Integer, Author> authorsById,HashMap<Integer, Paper> papersById,
                        HashMap<Integer, Conference> conferencesById,HashMap<Integer, Term> termsById,
                        HashMap<String, Author> authorsByName,HashMap<String, Paper> papersByName,
                        HashMap<String, Conference> conferencesByName,
                        HashMap<String, Term> termsByName, int authorMaxId, int paperMaxId, int termMaxId, int conferenceMaxId){

        readAuthorsFromFile(authorsById, authorsByName,authorMaxId);
        readPapersFromFile(papersById, papersByName,paperMaxId);
        readConferencesFromFile(conferencesById, conferencesByName,termMaxId);
        readTermsFromFile(termsById, termsByName,conferenceMaxId);

    }


    public void newEdit(HashMap<Integer, Author> authorsById,HashMap<Integer, Paper> papersById,
                        HashMap<Integer, Conference> conferencesById,HashMap<Integer, Term> termsById,
                        HashMap<Integer, Author> authorsByName,HashMap<Integer, Paper> papersByName,
                        HashMap<Integer, Conference> conferencesByName,
                        HashMap<Integer, Term> termsByName, int authorMaxId, int paperMaxId, int termMaxId, int conferenceMaxId){


        System.out.println("Vols editar, insertar o eliminar? (Ed -> editar, In -> insertar, El -> eliminar");
        Scanner scan = new Scanner(System.in);
        String editType = scan.nextLine();
        System.out.println("Quin tipus d'objecte vols modificar? (A -> Author, P -> Paper, T -> Term, C -> Conference");
        String objectType = scan.nextLine();

        switch(editType){
            case("In"): //agefir

                switch(objectType) {
                    case ("A"): //Author
                        break;
                    case ("P"): //Paper
                        break;
                    case ("T"): //Term
                        break;
                    case ("C"): //Conference
                        break;
                }
                break;

            case("Ed"): //Editar

                switch(objectType){
                    case("A"):
                        break;
                    case("P"):
                        break;
                    case("T"):
                        break;
                    case("C"):
                        break;
                }
                break;

            case("El"): // Eliminar

                switch(objectType){
                    case("A"):
                        break;
                    case("P"):
                        break;
                    case("T"):
                        break;
                    case("C"):
                        break;
                }
                break;

        }

    }

    private void readAuthorsFromFile(HashMap<Integer, Author> authorsById, HashMap<String,Author> authorsByName,int authorMaxId){
        File inputFile = new File("/../data/author.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = null;
            authorMaxId = 0;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                if(id > authorMaxId) authorMaxId = id;
                Author author = new Author(aux[1],id);
                authorsById.put(id,author);
                authorsByName.put(aux[1],author);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }

    private void readPapersFromFile(HashMap<Integer, Paper> papersById, HashMap<String,Paper> papersByName, int paperMaxId){
        File inputFile = new File("/../data/paper.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                if(id > paperMaxId) paperMaxId = id;
                Paper paper = new Paper(aux[1],id);
                papersById.put(id,paper);
                papersByName.put(aux[1], paper);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }

    private void readConferencesFromFile(HashMap<Integer, Conference> conferencesById, HashMap<String,Conference> conferencesByName, int conferenceMaxId){
        File inputFile = new File("/../data/conf.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                if(id > conferenceMaxId) conferenceMaxId = id;
                Conference conf = new Conference(aux[1],id);
                conf.setYear(Integer.parseInt(aux[2]));
                conf.setContinent(aux[3]);
                conferencesById.put(id,conf);
                conferencesByName.put(aux[1], conf);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }


    private void readTermsFromFile(HashMap<Integer, Term> termsById, HashMap<String, Term> termsByName, int termMaxId){
        File inputFile = new File("/../datda/term.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                if(id > termMaxId) termMaxId = id;

                Term term = new Term(aux[1],id);
                termsById.put(id,term);
                termsByName.put(aux[1], term);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }


    private void writeAuthorToFile(Author author){
        String wrauthor = Integer.toString(author.getId()) + ";" + author.getName();
        File inputFile = new File("/../datda/author.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            writer.write(wrauthor, 0, wrauthor.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writePaperToFile(Paper paper){
        String wrpaper = Integer.toString(paper.getId()) + ";" + paper.getName();
        File inputFile = new File("/../data/paper.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            writer.write(wrpaper, 0, wrpaper.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writeConferenceToFile(Conference conference) {
        String wrconf = Integer.toString(conference.getId()) + ";" + conference.getName() +
                ";" + conference.getYear() + ";" + conference.getContinent();
        File inputFile = new File("/../data/conf.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            writer.write(wrconf, 0, wrconf.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void writeTermToFile(Term term){
        String wrterm = Integer.toString(term.getId()) + ";" + term.getName();
        File inputFile = new File("/../data/term.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))) {
            writer.write(wrterm, 0, wrterm.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }


    }


    private void deleteAuthorFromFile(Author author){
        File inputFile = new File("/../data/author.txt");

    }

    private void deletePaperFromFile(Paper paper){

        File inputFile = new File("/../data/paper.txt");

    }


    private void deleteConferenceFromFile(Conference conference){
        File inputFile = new File("/../data/conf.txt");

    }

    private void deleteTermFromFile(Term term){
        File inputFile = new File("/../data/term.txt");

    }

    private void editAuthorFromFile(Author author, String name){
        File inputFile = new File("/../data/author.txt");

    }


    private void editPaperFromFile(Paper paper, String value) {
        File inputFile = new File("/../data/paper.txt");

    }


    private void editConferenceFromFile(Conference conference, String key, String value){
        File inputFile = new File("/../data/conference.txt");

    }


    private void editTermFromFile(Term term, String value){
        File inputFile = new File("/../data/term.txt");

    }

    private void readPaperAuthorRelations( HashMap<Integer, Paper> papersById,HashMap<Integer, Author> authorsById){
        File inputFile = new File("/../data/paper_author.txt");
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
        File inputFile = new File("/../data/paper_confr.txt");
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
        File inputFile = new File("/../data/paper_term.txt");
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
        File inputFile = new File("/../data/paper_author.txt");
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
        File inputFile = new File("/../data/paper_author.txt");
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
        inputFile = new File("/../data/paper_Term.txt");
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
        inputFile = new File("/../data/paper_conf.txt");
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
        File inputFile = new File("/../data/paper_conf.txt");
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
        File inputFile = new File("/../data/paper_term.txt");
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


    private void writeNewRelationPaperAuthor(Paper paper, Author author){
        File inputFile = new File("/../data/paper_author.txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            String paperId = Integer.toString(paper.getId());
            String relationToWrite = paperId + ";" + Integer.toString(author.getId());
            writer.write(relationToWrite,0,relationToWrite.length());
            writer.newLine();
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
    }

    private void writeNewRelationPaperTerm(Paper paper, Term term){
        File inputFile = new File("/../data/paper_term.txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            String relationToWrite = Integer.toString(paper.getId()) + ";" + Integer.toString(term.getId());
            writer.write(relationToWrite,0,relationToWrite.length());
            writer.newLine();
        }
        catch (IOException x){
            System.err.format("IOException : %s%n", x);
        }
    }

    private void writeNewRelationPaperConf(Paper paper, Conference conf) {
        File inputFile = new File("/../data/paper_conf.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, true))) {
            String relationToWrite = Integer.toString(paper.getId()) + ";" + Integer.toString(conf.getId());
            writer.write(relationToWrite, 0, relationToWrite.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException : %s%n", x);
        }
    }

    private void deleteAuthorRelationsOnFile(Author author){}
    private void deletePaperRelationsOnFile(Paper paper){}
    private void deleteConferenceRelationsOnFile(Conference conference){}
    private void deleteTermRelationsOnFile(Term term){}




}
