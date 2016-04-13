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

        ConsolePrinter print = new ConsolePrinter();
        print.printEditInsertOrDelete();
        Scanner scan = new Scanner(System.in);
        String editType = scan.nextLine();
        print.printTypeInputEditMessage();
        String objectType = scan.nextLine();

        switch(editType){
            case("In"): //agefir
                Scanner aux = new Scanner(System.in);
                String objName;
                switch(objectType) {
                    case ("A"):
                        break;
                    case ("P"):
                        break;
                    case ("T"):
                        break;
                    case ("C"):
                        break;
                }
                break;

            case("Ed"):
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

            case("El"):
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))){
            writer.write(wrauthor, 0, wrauthor.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writePaperToFile(Paper paper){
        String wrpaper = Integer.toString(paper.getId()) + ";" + paper.getName();
        File inputFile = new File("/../data/paper.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))){
            writer.write(wrpaper, 0, wrpaper.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writeConferenceToFile(Conference conference) {
        String wrconf = Integer.toString(conference.getId()) + ";" + conference.getName() +
                ";" + conference.getYear() + ";" + conference.getContinent();
        File inputFile = new File("/../data/conf.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))){
            writer.write(wrconf, 0, wrconf.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void writeTermToFile(Term term){
        String wrterm = Integer.toString(term.getId()) + ";" + term.getName();
        File inputFile = new File("/../data/term.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write(wrterm, 0, wrterm.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }


    }


    private void deleteAuthorFromFile(Author author){
        File inputFile = new File("/../data/author.txt");

       try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

           String lineToRemove = Integer.toString(author.getId()) + ";" + author.getName();
           String currentLine;

           while ((currentLine = reader.readLine()) != null) {
               if (currentLine.equals(lineToRemove))
                   writer.write("");
           }
       } catch(IOException x){
           System.err.format("IOExeption: %s%n", x);
       }
    }

    private void deletePaperFromFile(Paper paper){

        File inputFile = new File("/../data/paper.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));) {
            String lineToRemove = Integer.toString(paper.getId()) + ";" + paper.getName();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write("");
            }
        }
        catch(IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }


    private void deleteConferenceFromFile(Conference conference){
        File inputFile = new File("/../data/conf.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(conference.getId()) + ";" + conference.getName()
                    + ";" + conference.getYear() + ";" + conference.getContinent();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write("");
            }
        }
        catch (IOException x) {
            System.err.format("IOExeption: %s%n", x);
        }
    }

    private void deleteTermFromFile(Term term){
        File inputFile = new File("/../data/term.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(term.getId()) + ";" + term.getName();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write("");
            }
        }
        catch (IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }

    private void editAuthorFromFile(Author author, String name){
        File inputFile = new File("/../data/author.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(author.getId()) + ";" + author.getName();
            String currentLine;
            String wrauthor = Integer.toString(author.getId()) + ";" + name;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write(wrauthor);
            }
        }
        catch(IOException x) {
            System.err.format("IOExeption: %s%n", x);
        }
    }


    private void editPaperFromFile(Paper paper, String value) {
        File inputFile = new File("/../data/paper.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(paper.getId()) + ";" + paper.getName();
            String currentLine;
            String wrpaper = Integer.toString(paper.getId()) + ";" + value;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write(wrpaper);
            }

        }
        catch(IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }


    private void editConferenceFromFile(Conference conference, String key, String value){
        File inputFile = new File("/../data/conference.txt");

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(conference.getId()) + ";" + conference.getName()
                    + ";" + conference.getYear() + ";" + conference.getContinent();
            String currentLine;
            String wrconf = null;

            switch (key) {
                case "nom":
                    wrconf = Integer.toString(conference.getId()) + ";" + value
                            + ";" + conference.getYear() + ";" + conference.getContinent();
                    break;
                case "id":
                    wrconf = value + ";" + conference.getName()
                            + ";" + conference.getYear() + ";" + conference.getContinent();
                    break;
                case "any":
                    wrconf = Integer.toString(conference.getId()) + ";" + conference.getName()
                            + ";" + value + ";" + conference.getContinent();
                    break;
                case "continent":
                    wrconf = Integer.toString(conference.getId()) + ";" + conference.getName()
                            + ";" + conference.getYear() + ";" + value;
                    break;
            }

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write(wrconf);
            }
        }
        catch (IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }


    private void editTermFromFile(Term term, String value){
        File inputFile = new File("/../data/term.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(term.getId()) + ";" + term.getName();
            String currentLine;
            String wrterm = Integer.toString(term.getId()) + ";" + value;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write(wrterm);
            }
        }
        catch(IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
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

    private void writeAuthorRelations(Author author){}
    private void writePaperRelations(Paper paper){}
    private void writeConferenceRelations(Conference conference){}
    private void writeTermRelations(Term term){}

    private void deleteAuthorRelationsOnFile(Author author){}
    private void deletePaperRelationsOnFile(Paper paper){}
    private void deleteConferenceRelationsOnFile(Conference conference){}
    private void deleteTermRelationsOnFile(Term term){}


    private void writeNewRelationPaperAuthor(Paper paper, Author author){}
    private void writeNewRelationPaperTerm(Paper paper, Term term){}
    private void writeNewRelationPaperConf(Paper paper, Conference conf){}





}
