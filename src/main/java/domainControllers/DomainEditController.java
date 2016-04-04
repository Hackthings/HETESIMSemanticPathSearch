package main.java.domainControllers;

import main.java.domain.nodes.Author;
import main.java.domain.nodes.Conference;
import main.java.domain.nodes.Paper;
import main.java.domain.nodes.Term;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DomainEditController {
    HashMap<Integer, Author> authors;
    HashMap<Integer, Paper> papers;
    HashMap<Integer, Conference> conferences;
    HashMap<Integer, Term> terms;

    public DomainEditController() {}

    public void readAuthorsFromFile(){

        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("\t");
                int id = Integer.parseInt(aux[0]);
                Author author = new Author(aux[1],id);
                authors.put(id,author);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public void readPapersFromFile(){
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("\t");
                int id = Integer.parseInt(aux[0]);
                Paper paper = new Paper(aux[1],id);
                papers.put(id,paper);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public void readConferencesFromFile(){
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("\t");
                int id = Integer.parseInt(aux[0]);
                Conference conf = new Conference(aux[1],id);
                conf.setYear(Integer.parseInt(aux[2]));
                conf.setContinent(aux[3]);
                conferences.put(id,conf);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }


    public void readTermsFromFile(){
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] aux = line.split("\t");
                int id = Integer.parseInt(aux[0]);
                Term term = new Term(aux[1],id);
                authors.put(id,term);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }


    public void writeAuthorToFile(Author author){
        Charset charset = Charset.forName("US-ASCII");
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        String wrauthor = Integer.toString(author.getId()) + "\t" + author.getName();
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(wrauthor, 0, wrauthor.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        authors.put(author.getId(),author);
    }

    public void writePaperToFile(Paper paper){
        Charset charset = Charset.forName("US-ASCII");
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        String wrpaper = Integer.toString(paper.getId()) + "\t" + paper.getName();
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(wrpaper, 0, wrpaper.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //fer insert al hashmap

    }

    public void writeConferenceToFile(Conference conference) {
        Charset charset = Charset.forName("US-ASCII");
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        String wrconf = Integer.toString(conference.getId()) + "\t" + conference.getName() +
                "\t" + conference.getYear() + "\t" + conference.getContinent();
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(wrconf, 0, wrconf.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //fer insert al hashmap
    }

    public void writeTermToFile(Term term){
        Charset charset = Charset.forName("US-ASCII");
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        String wrterm = Integer.toString(term.getId()) + "\t" + term.getName();
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(wrterm, 0, wrterm.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //fer put al hashmap
    }


    public void deleteAuthorFromFile(Author author){
    //FER TRY CATCH
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(author.getId()) + "\t" + author.getName();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                 writer.write("");
        }
        writer.close();
        reader.close();
    }

    public void deletePaperFromFile(Paper paper){

        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(paper.getId()) + "\t" + paper.getName();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write("");
        }
        writer.close();
        reader.close();
    }


    public void deleteConferenceFromFile(Conference conference){
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(conference.getId()) + "\t" + conference.getName()
                + "\t" + conference.getYear() + "\t" + conference.getContinent();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write("");
        }
        writer.close();
        reader.close();
    }

    public void deleteTermFromFile(Term term){

        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(term.getId()) + "\t" + term.getName();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write("");
        }
        writer.close();
        reader.close();
    }

    public void editAuthorFromFile(Author author, String key, String value){

        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(author.getId()) + "\t" + author.getName();
        String currentLine;
        String wrauthor;

        if(key.equals("nom")) wrauthor = Integer.toString(author.getId()) + "\t" + value;
        else wrauthor = value + "\t" + author.getName();

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write(wrauthor);
        }
        writer.close();
        reader.close();
    }


    public void editPaperFromFile(Paper paper, String key, String value){
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(paper.getId()) + "\t" + paper.getName();
        String currentLine;
        String wrpaper;

        if(key.equals("nom")) wrpaper = Integer.toString(paper.getId()) + "\t" + value;
        else wrpaper = value + "\t" + paper.getName();

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write(wrpaper);
        }
        writer.close();
        reader.close();
    }


    public void editConferenceFromFile(Conference conference, String key, String value){ Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(conference.getId()) + "\t" + conference.getName()
                + "\t" + conference.getYear() + "\t" + conference.getContinent();
        String currentLine;
        String wrconf;

        switch(key){
            case "nom":
                wrconf = Integer.toString(conference.getId()) + "\t" + value
                        + "\t" + conference.getYear() + "\t" + conference.getContinent();
                break;
            case "id":
                wrconf = value + "\t" + conference.getName()
                        + "\t" + conference.getYear() + "\t" + conference.getContinent();
                break;
            case "any":
                wrconf = Integer.toString(conference.getId()) + "\t" + conference.getName()
                        + "\t" + value + "\t" + conference.getContinent();
                break;
            case "continent":
                wrconf = Integer.toString(conference.getId()) + "\t" + conference.getName()
                        + "\t" + conference.getYear() + "\t" + value;
                break;
        }

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write(wrconf);
        }
        writer.close();
        reader.close();

    }


    public void editTermFromFile(Term term, String key, String value){
        Path file = "HETESIMSemanticPathSearch/src/data/NOMARXIU";
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = Integer.toString(term.getId()) + "\t" + term.getName();
        String currentLine;
        String wrterm;

        if(key.equals("nom")) wrterm = Integer.toString(term.getId()) + "\t" + value;
        else wrterm = value + "\t" + term.getName();

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write(wrterm);
        }
        writer.close();
        reader.close();
    }


}
