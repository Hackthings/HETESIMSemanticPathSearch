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

        Path file = ...;
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // insert en el hashmap
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public void readPapersFromFile(){
        Path file = ...;
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // insert en el hashmap
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public void readConferencesFromFile(){
        Path file = ...;
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // insert en el hashmap
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }


    public void readTermsFromFile(){
        //Path file = Paths.get(string del path);
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // insert en el hashmap
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }


    public void writeAuthorToFile(Author author){
        Charset charset = Charset.forName("US-ASCII");
        //String s = que escriure;
        //Path file = Paths.get(string del path);
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //fer insert al hashmap
    }

    public void writePaperToFile(Paper paper){        Charset charset = Charset.forName("US-ASCII");
        //String s = que escriure;
        //Path file = Paths.get(string del path);
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //fer insert al hashmap

    }

    public void writeConferenceToFile(Conference conference) {
        Charset charset = Charset.forName("US-ASCII");
        //String s = que escriure;
        //Path file = Paths.get(string del path);
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //fer insert al hashmap
    }

    public void writeTermToFile(Term term){
        Charset charset = Charset.forName("US-ASCII");
        //String s = que escriure;
        //Path file = Paths.get(string del path);
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        //fer insert al hashmap
    }


    public void deleteAuthorFromFile(Author author){

        //Path file = Paths.get(string del path);
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = ""; // "id nom" tret de l'objecte
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                 writer.write(currentLine);
        }
        writer.close();
        reader.close();
    }

    public void deletePaperFromFile(Paper paper){

        //Path file = Paths.get(string del path);
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = ""; // "id nom" tret de l'objecte
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write(currentLine);
        }
        writer.close();
        reader.close();
    }


    public void deleteConferenceFromFile(Conference conference){
        //Path file = Paths.get(string del path);
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = ""; // "id nom" tret de l'objecte
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write(currentLine);
        }
        writer.close();
        reader.close();
    }

    public void deleteTermFromFile(Term term){

        //Path file = Paths.get(string del path);
        File inputFile = new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileReader(inputFile));

        String lineToRemove = ""; // "id nom" tret de l'objecte
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            if(currentLine.equals(lineToRemove))
                writer.write(currentLine);
        }
        writer.close();
        reader.close();
    }




}
