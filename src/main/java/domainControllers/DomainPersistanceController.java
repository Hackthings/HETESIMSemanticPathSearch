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

    public void newEdit(HashMap<Integer, Author> authors, HashMap<Integer, Paper> papers,
                        HashMap<Integer, Term> terms, HashMap<Integer, Conference> conferences,
                        int authorMaxId, int paperMaxId, int termMaxId, int conferenceMaxId){

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
                switch(objectType){
                case("A"):
                    System.out.print("Quin nom te ");
                    objName = aux.nextLine();
                    System.out.print("Te relacio amb algun Paper? (0 -> no en te, nomdelsPapers separats per ;)");
                    String paperNames = aux.nextLine();
                    String relationedPapers[] = paperNames.split(";");

                    Author autor = new Author(objName,authorMaxId+1);
                    Paper relatedPaper;
                    for(String p:relationedPapers){
                        //add dels papers en l'autor
                    }

                    break;
                case("P"):
                    break;
                case("T"):
                    break;
                case("C"):
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

    public void readAll(HashMap<Integer, Author> authors, HashMap<Integer, Paper> papers,
                        HashMap<Integer, Term> terms, HashMap<Integer, Conference> conferences,
                        int authorMaxId, int paperMaxId, int termMaxId, int conferenceMaxId){}

    private void readAuthorsFromFile(HashMap<Integer, Author> authors){
        File inputFile = new File("/../data/authors.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Author author = new Author(aux[1],id);
                readAuthorRelations(author);
                authors.put(id,author);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
        //ESCRIuRE LES RELACIONS
    }

    private void readPapersFromFile(HashMap<Integer, Paper> papers){
        File inputFile = new File("/../data/authors.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Paper paper = new Paper(aux[1],id);
                readPaperRelations(paper);
                papers.put(id,paper);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }

    private void readConferencesFromFile(HashMap<Integer, Conference> conferences){
        File inputFile = new File("/../data/conferences.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Conference conf = new Conference(aux[1],id);
                conf.setYear(Integer.parseInt(aux[2]));
                conf.setContinent(aux[3]);
                readConferenceRelations(conf);
                conferences.put(id,conf);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }


    private void readTermsFromFile(HashMap<Integer, Term> terms){
        File inputFile = new File("/../datda/terms.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split(";");
                int id = Integer.parseInt(aux[0]);
                Term term = new Term(aux[1],id);
                readTermRelations(term);
                terms.put(id,term);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);        }
    }


    private void writeAuthorToFile(Author author, HashMap<Integer,Author> authors){
        String wrauthor = Integer.toString(author.getId()) + ";" + author.getName();
        File inputFile = new File("/../datda/authors.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))){
            writer.write(wrauthor, 0, wrauthor.length());
            writeAuthorRelations(author);
            authors.put(author.getId(),author);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writePaperToFile(Paper paper, HashMap<Integer,Paper> papers){
        String wrpaper = Integer.toString(paper.getId()) + ";" + paper.getName();
        File inputFile = new File("/../data/papers.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))){
            writer.write(wrpaper, 0, wrpaper.length());
            writePaperRelations(paper);
            papers.put(paper.getId(), paper);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writeConferenceToFile(Conference conference,HashMap<Integer,Conference> conferences) {
        String wrconf = Integer.toString(conference.getId()) + ";" + conference.getName() +
                ";" + conference.getYear() + ";" + conference.getContinent();
        File inputFile = new File("/../data/conferences.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))){
            writer.write(wrconf, 0, wrconf.length());
            writeConferenceRelations(conference);
            conferences.put(conference.getId(), conference);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void writeTermToFile(Term term, HashMap<Integer,Term> terms){
        String wrterm = Integer.toString(term.getId()) + ";" + term.getName();
        File inputFile = new File("/../data/terms.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write(wrterm, 0, wrterm.length());
            writeTermRelations(term);
            terms.put(term.getId(), term);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }


    }


    private void deleteAuthorFromFile(Author author ,HashMap<Integer,Author> authors){
        File inputFile = new File("/../data/authors.txt");

       try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

           String lineToRemove = Integer.toString(author.getId()) + ";" + author.getName();
           String currentLine;

           while ((currentLine = reader.readLine()) != null) {
               if (currentLine.equals(lineToRemove))
                   writer.write("");
           }
           //BUSCAR L?AUTOR I ESBORRARLO DEL HASHMAP I LES SEVES RELACIONS
       } catch(IOException x){
           System.err.format("IOExeption: %s%n", x);
       }
    }

    private void deletePaperFromFile(Paper paper, HashMap<Integer,Paper> papers){

        File inputFile = new File("/../data/papers.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));) {
            String lineToRemove = Integer.toString(paper.getId()) + ";" + paper.getName();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write("");
            }
            //BUSCAR EL PAPER I ESBORRARLO DEL HASHMAP I LES SEVES RELACIONS
        }
        catch(IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }


    private void deleteConferenceFromFile(Conference conference, HashMap<Integer,Conference> Conferences){
        File inputFile = new File("/../data/conferences.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(conference.getId()) + ";" + conference.getName()
                    + ";" + conference.getYear() + ";" + conference.getContinent();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write("");
            }
            //BUSCAR LA CONFERENCIA I ESBORRARLA DEL HASHMAP I LES SEVES RELACIONS
        }
        catch (IOException x) {
            System.err.format("IOExeption: %s%n", x);
        }
    }

    private void deleteTermFromFile(Term term, HashMap<Integer,Term> terms){
        File inputFile = new File("/../data/terms.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(term.getId()) + ";" + term.getName();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write("");
            }
            //BuSCAR EL TERM I ESBORRARLO DEL HASHMAP I LES SEVES RELACIONS
        }
        catch (IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }

    private void editAuthorFromFile(Author author, HashMap<Integer,Author> authors, String key, String value){
        File inputFile = new File("/../data/authors.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(author.getId()) + ";" + author.getName();
            String currentLine;
            String wrauthor;

            if (key.equals("nom")) wrauthor = Integer.toString(author.getId()) + ";" + value;
            else wrauthor = value + ";" + author.getName();

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write(wrauthor);
            }
            //EDITAR DEL HASHMAP
        }
        catch(IOException x) {
            System.err.format("IOExeption: %s%n", x);
        }
    }


    private void editPaperFromFile(Paper paper, String key, String value) {
        File inputFile = new File("/../data/papers.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(paper.getId()) + ";" + paper.getName();
            String currentLine;
            String wrpaper;

            if (key.equals("nom")) wrpaper = Integer.toString(paper.getId()) + ";" + value;
            else wrpaper = value + ";" + paper.getName();

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write(wrpaper);
            }
            //EDITAR DEL HASHMAP
        }
        catch(IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }


    private void editConferenceFromFile(Conference conference, String key, String value){
        File inputFile = new File("/../data/conferences.txt");

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


    private void editTermFromFile(Term term, String key, String value){
        File inputFile = new File("/../data/terms.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {

            String lineToRemove = Integer.toString(term.getId()) + ";" + term.getName();
            String currentLine;
            String wrterm;

            if (key.equals("nom")) wrterm = Integer.toString(term.getId()) + ";" + value;
            else wrterm = value + ";" + term.getName();

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToRemove))
                    writer.write(wrterm);
            }
        }
        catch(IOException x){
            System.err.format("IOExeption: %s%n", x);
        }
    }

    private void readAuthorRelations(Author author){}
    private void readPaperRelations(Paper paper){}
    private void readConferenceRelations(Conference conference){}
    private void readTermRelations(Term term){}

    private void writeAuthorRelations(Author author){}
    private void writePaperRelations(Paper paper){}
    private void writeConferenceRelations(Conference conference){}
    private void writeTermRelations(Term term){}



}
