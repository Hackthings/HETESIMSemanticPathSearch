package main.java.sharedClasses.domain;

import main.java.sharedClasses.domain.nodes.Author;
import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;
import main.java.sharedClasses.domain.nodes.Term;

import java.io.*;
import java.util.*;

public class DomainPersistanceController {

    public DomainPersistanceController() {}

    public void readAll(HashMap<Integer, Author> authorsById,
                        HashMap<Integer, Paper> papersById,
                        HashMap<Integer, Conference> conferencesById,
                        HashMap<Integer, Term> termsById,
                        HashMap<String, Author> authorsByName,
                        HashMap<String, Paper> papersByName,
                        HashMap<String, Conference> conferencesByName,
                        HashMap<String, Term> termsByName,
                        int authorMaxId,
                        int paperMaxId,
                        int termMaxId,
                        int conferenceMaxId){

        readAuthorsFromFile(authorsById, authorsByName,authorMaxId);
        readPapersFromFile(papersById, papersByName,paperMaxId);
        readConferencesFromFile(conferencesById, conferencesByName,termMaxId);
        readTermsFromFile(termsById, termsByName,conferenceMaxId);
        readPaperAuthorRelations(papersById,authorsById);
        readTermRelations(papersById,termsById);
        readConferenceRelations(papersById,conferencesById);
    }

    public void newEdit(HashMap<Integer, Author> authorsById,
                        HashMap<Integer, Paper> papersById,
                        HashMap<Integer, Conference> conferencesById,
                        HashMap<Integer, Term> termsById,
                        HashMap<String, Author> authorsByName,
                        HashMap<String, Paper> papersByName,
                        HashMap<String, Conference> conferencesByName,
                        HashMap<String, Term> termsByName,
                        int authorMaxId,
                        int paperMaxId,
                        int termMaxId,
                        int conferenceMaxId){


        System.out.println("Vols editar, insertar o eliminar? (Ed -> editar, In -> insertar, El -> eliminar");
        Scanner scan = new Scanner(System.in);
        String editType = scan.nextLine();
        System.out.println("Quin tipus d'objecte vols modificar? (A -> Author, P -> Paper, T -> Term, C -> Conference");
        String objectType = scan.nextLine();
        String name;
        String[] aux;
        String newName;
        String origenName;
        int id;
        switch(editType){
            case("In"): //agefir

                switch(objectType) {
                    case ("A"): //Author
                        System.out.println("Introduïu el nom de l'autor");
                        String nameAuthor = scan.nextLine();
                        Author a = new Author(nameAuthor,authorMaxId+1);
                        if (authorsByName.get(a.getName()) != null) {
                            System.err.println("Aquest autor ja existeix");
                            break;
                        }
                        authorsByName.put(a.getName(), a);
                        authorsById.put(a.getId(),a);
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
                                writeNewRelationPaperAuthor(p,a);
                            }catch(NullPointerException e) {
                                System.err.println("L'article no existeix");
                            }
                        }
                        break;
                    case ("P"): //Paper
                        System.out.println("Introduïu el nom de l'article");
                        String namePaper = scan.nextLine();
                        Paper p = new Paper(namePaper,paperMaxId+1);
                        if (papersByName.get(p.getName()) != null) {
                            System.err.println("Aquest article ja existeix");
                            break;
                        }
                        papersByName.put(p.getName(),p);
                        papersById.put(p.getId(),p);
                        writePaperToFile(p);
                        System.out.println("Quina és la seva conferència?");
                        String nameConf = scan.nextLine();
                        Conference c;
                        try {
                            c = conferencesByName.get(nameConf);
                        } catch (NullPointerException e) {
                            c = new Conference(nameConf,conferenceMaxId+1);
                            conferencesByName.put(c.getName(), c);
                            conferencesById.put(c.getId(),c);
                            writeConferenceToFile(c);
                        }
                        p.setConference(c);
                        System.out.println("Quants autors té aquest article?");
                        int numAut = scan.nextInt();
                        scan.nextLine();
                        for (int i = 0; i < numAut; ++i) {
                            System.out.println("Introduïu el nom de l'autor");
                            String nameAut = scan.nextLine();
                            try {
                                a = authorsByName.get(nameAut);
                            }catch(NullPointerException e) {
                                a = new Author(nameAut, authorMaxId + 1);
                                authorsByName.put(a.getName(), a);
                                authorsById.put(a.getId(),a);
                                writeAuthorToFile(a);
                            }
                            p.addAuthor(a);
                            writeNewRelationPaperAuthor(p,a);
                        }
                        //afegirTermes();
                        break;
                    case ("T"): //Term
                        break;
                    case ("C"): //Conference
                        System.out.println("Introduïu el nom de l'article");
                        String confName = scan.nextLine();
                        if (conferencesByName.get(confName) != null) {
                            System.err.println("la conferència ja existeix");
                            break;
                        }
                        Conference c1 = new Conference(confName,conferenceMaxId+1);
                        conferencesByName.put(c1.getName(), c1);
                        conferencesById.put(c1.getId(),c1);
                        writeConferenceToFile(c1);
                        System.out.println("Quants articles té aquesta conferència?");
                        int numConf = scan.nextInt();
                        scan.nextLine();
                        for (int i = 0; i < numConf; ++i) {
                            System.out.println("Introduïu el nom de l'article");
                            String nameC = scan.nextLine();
                            try {
                                Paper p1 = papersByName.get(nameC);
                                c1.addExposedPaper(p1);
                                writeNewRelationPaperConf(p1,c1);
                            }catch(NullPointerException e) {
                                System.err.println("L'article no existeix");
                            }
                        }
                        break;
                }
                break;

            case("Ed"): //Editar

                switch(objectType){
                    case("A"):
                        System.out.println("Introdueix el nom del autor a modificar:");
                         origenName = scan.nextLine();
                        Author a = authorsByName.get(origenName);
                        System.out.println("Introdueix el nou nom del autor:");
                         newName = scan.nextLine();
                        editAuthorFromFile(a,newName);
                        a.setName(newName);
                        authorsByName.remove(origenName);
                        authorsByName.put(newName,a);
                        break;
                    case("P"):
                        System.out.println("Introdueix el nom de l'article a modificar:");
                        origenName = scan.nextLine();
                        Paper p = papersByName.get(origenName);
                        System.out.println("Introdueix el nou nom de l'article:");
                        newName = scan.nextLine();
                        editPaperFromFile(p,newName);
                        p.setName(newName);
                        papersByName.remove(origenName);
                        papersByName.put(newName,p);
                        break;
                    case("T"):
                        System.out.println("Introdueix el nom del terme a modificar:");
                        origenName = scan.nextLine();
                        Term t = termsByName.get(origenName);
                        System.out.println("Introdueix el nou nom del terme:");
                        newName = scan.nextLine();
                        editTermFromFile(t,newName);
                        t.setName(newName);
                        termsByName.remove(origenName);
                        termsByName.put(newName,t);
                        break;
                    case("C"):
                        System.out.println("Introdueix el nom de la conferència a modificar:");
                        origenName = scan.nextLine();
                        Conference c = conferencesByName.get(origenName);
                        System.out.println("Introdueix el nou nom de la conferència:");
                        newName = scan.nextLine();
                        System.out.println("Introdueix l'any de la conferència:");
                        String any = scan.nextLine();
                        System.out.println("Introdueix el continent de la conferència: ");
                        String cont = scan.nextLine();
                        editConferenceFromFile(c,newName,any,cont);
                        c.setName(newName);
                        conferencesByName.remove(origenName);
                        conferencesByName.put(newName,c);
                        break;
                }
                break;

            case("El"): // Eliminar

                switch(objectType){
                    case("A"):
                        try {
                            System.out.println("Introdueix el nom de l'autor a esborrar)");
                            name = scan.nextLine();
                            Author a = authorsByName.get(name);
                            //eliminem relacions
                            Collection<Paper> auxiliar = a.getPapersById().values();
                            for(Iterator it = auxiliar.iterator();it.hasNext();) {
                                Paper p = (Paper) it.next();
                                p.removeAuthor(a);
                                if(p.getAuthorsById().size() < 1) {
                                    //RECOMENÇAR UN PROCES DE ELIMINAR PAPER
                                    Collection<Term> auxiliart = p.getTermsById().values();
                                    Conference auxiliarc = p.getConference();
                                    auxiliarc.removeExposedPaperBy(p);
                                    if(auxiliarc.getExposedPapersById().size() < 1) deleteConferenceFromFile(auxiliarc);
                                    for(Iterator itt = auxiliart.iterator(); itt.hasNext();){
                                        Term t = (Term) itt.next();
                                        t.removePaperWhichTalkAboutIt(p);
                                        if(t.getPapersWhichTalkAboutThisById().size() < 1) deleteTermFromFile(t);
                                    }
                                }
                            }
                            //eliminem l'autor
                            deleteAuthorFromFile(a);
                            id = a.getId();
                            authorsByName.remove(name);
                            authorsById.remove(id);
                        }
                        catch (NullPointerException ex){
                            System.out.println("Aquest autor no existeix.");
                        }
                        break;
                    case("P"):
                        try {
                            System.out.println("Introdueix el nom de l'article a esborrar)");
                            name = scan.nextLine();
                            Paper p = papersByName.get(name);
                            //eliminar relacions
                            Collection<Author> auxiliara = p.getAuthorsById().values();
                            Collection<Term> auxiliart = p.getTermsById().values();
                            Conference auxiliarc = p.getConference();
                            auxiliarc.removeExposedPaperBy(p);
                            if(auxiliarc.getExposedPapersById().size() < 1) deleteConferenceFromFile(auxiliarc);
                            for(Iterator ita = auxiliara.iterator(); ita.hasNext();){
                                Author a = (Author) ita.next();
                                a.removePaper(p);
                                if(a.getPapersById().size() < 1) deleteAuthorFromFile(a);
                            }
                            for(Iterator itt = auxiliart.iterator(); itt.hasNext();){
                                Term t = (Term) itt.next();
                                t.removePaperWhichTalkAboutIt(p);
                                if(t.getPapersWhichTalkAboutThisById().size() < 1) deleteTermFromFile(t);
                            }
                            //eliminar paper
                            deletePaperFromFile(p);
                            deletePaperRelationsOnFile(p);
                            id = p.getId();
                            papersByName.remove(name);
                            papersById.remove(id);
                        }catch (NullPointerException ex){
                            System.out.println("Aquest article no existeix.");
                        }
                        break;
                    case("T"):
                        System.out.println("això no té sentit");
                        /*try {
                            System.out.println("Introdueix el nom del terme a esborrar)");
                            name = scan.nextLine();
                            Term t = termsByName.get(name);
                            //eliminem relacions
                            Collection<Paper> auxiliar = t.getPapersWhichTalkAboutThisById().values();
                            for(Iterator it = auxiliar.iterator();it.hasNext();) {
                                Paper p = (Paper) it.next();
                                p.removeTerm(t);
                                if(p.getAuthorsById().size() < 1) ;//RECOMENÇAR UN PROCES DE ELIMINAR PAPER
                            }
                            //eliminem el term
                            deleteTermFromFile(t);
                            id = t.getId();
                            termsByName.remove(name);
                            termsById.remove(id);
                        }catch (NullPointerException ex){
                            System.out.println("Aquest terme no existeix.");
                        }*/
                        break;
                    case("C"):
                        try {
                            System.out.println("Introdueix el nom de l'autor a esborrar)");
                            name = scan.nextLine();
                            Conference c =conferencesByName.get(name);
                            //eliminem relacions
                            Collection<Paper> auxiliar = c.getExposedPapersById().values();
                            for(Iterator it = auxiliar.iterator();it.hasNext();) {
                                //COMENÇAR UN PROCES DE ELIMINAR PAPERS
                            }
                            //eliminem l'autor
                            deleteConferenceFromFile(c);
                            id = c.getId();
                            conferencesByName.remove(name);
                            conferencesById.remove(id);
                        }catch (NullPointerException ex){
                            System.out.println("Aquesta conferencia no existeix.");
                        }
                        break;
                }
                break;

        }

    }

    private void readAuthorsFromFile(HashMap<Integer, Author> authorsById, HashMap<String,Author> authorsByName,int authorMaxId){
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\author.txt"));
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper.txt"));
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\conf.txt"));
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\term.txt"));
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\author.txt"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            writer.write(wrauthor, 0, wrauthor.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void writePaperToFile(Paper paper){
        String wrpaper = Integer.toString(paper.getId()) + ";" + paper.getName();
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper.txt"));
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\conf.txt"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))){
            writer.write(wrconf, 0, wrconf.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void writeTermToFile(Term term){
        String wrterm = Integer.toString(term.getId()) + ";" + term.getName();
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\term.txt"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,true))) {
            writer.write(wrterm, 0, wrterm.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }


    }


    private void deleteAuthorFromFile(Author author){
        try { //
            String authorId = Integer.toString(author.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\author.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = authorId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");
            deleteAuthorRelationsOnFile(author);
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    private void deletePaperFromFile(Paper paper){

        try { //
            String paperId = Integer.toString(paper.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = paperId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");

            deletePaperRelationsOnFile(paper);
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void deleteConferenceFromFile(Conference conference){
        try { //
            String conferenceId = Integer.toString(conference.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\conf.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = conferenceId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void deleteTermFromFile(Term term){
        try { //
            String termId = Integer.toString(term.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\term.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = termId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void editAuthorFromFile(Author author, String name){
        try{
                String authorId = Integer.toString(author.getId());
                String p = new File("").getAbsolutePath();
                String replace = authorId + ";" + name;
                File inputFile = new File(p.concat("\\src\\com\\company\\data\\author.txt"));
                File tempFile = new File("myTempFile.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String lineToEdit = authorId;
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    if (trimmedLine.startsWith(lineToEdit)){
                        writer.write(replace + System.getProperty("line.separator"));
                        continue;
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                boolean successful2 = tempFile.renameTo(inputFile);

                //Delete the original file
                if (!inputFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }

                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inputFile))
                    System.out.println("Could not rename file");

            }

            catch (IOException ex) {
                ex.printStackTrace();
            }
        }




    private void editPaperFromFile(Paper paper, String value) {
        try{
            String paperId = Integer.toString(paper.getId());
            String p = new File("").getAbsolutePath();
            String replace = paperId + ";" + value;
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToEdit = paperId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToEdit)){
                    writer.write(replace + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");

        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    private void editConferenceFromFile(Conference conference, String key, String year, String continent){
        try{
            String conferenceId = Integer.toString(conference.getId());
            String p = new File("").getAbsolutePath();
            String replace = conferenceId + ";" + key + ";" + year + ";" + continent;
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\conf.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToEdit = conferenceId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToEdit)){
                    writer.write(replace + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");

        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    private void editTermFromFile(Term term, String value){
        try{
            String paperId = Integer.toString(term.getId());
            String p = new File("").getAbsolutePath();
            String replace = paperId + ";" + value;
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\term.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToEdit = paperId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.startsWith(lineToEdit)){
                    writer.write(replace + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");

        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void readPaperAuthorRelations( HashMap<Integer, Paper> papersById,HashMap<Integer, Author> authorsById){
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat("\\src\\com\\company\\data\\paper_author.txt"));
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
        File inputFile = new File(p1.concat("\\src\\com\\company\\data\\paper_conf.txt"));
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
        File inputFile = new File(p1.concat("\\src\\com\\company\\data\\paper_term.txt"));
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
        File inputFile = new File(p1.concat("\\src\\com\\company\\data\\paper_author.txt"));
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
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_author.txt"));
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
        inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_term.txt"));
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
        inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_conf.txt"));
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
        File inputFile = new File(p1.concat("\\src\\com\\company\\data\\paper_conf.txt"));
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
        File inputFile = new File(p1.concat("\\src\\com\\company\\data\\paper_term.txt"));
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_author.txt"));;
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_term.txt"));
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
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_conf.txt"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, true))) {
            String relationToWrite = Integer.toString(paper.getId()) + ";" + Integer.toString(conf.getId());
            writer.write(relationToWrite, 0, relationToWrite.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException : %s%n", x);
        }
    }

    private void deleteAuthorRelationsOnFile(Author author){
        try { //PAPER_author
            String authorId = Integer.toString(author.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_author.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = authorId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.endsWith(lineToRemove)) {}
                else writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    private void deletePaperRelationsOnFile(Paper paper){

       try {
           String paperId = Integer.toString(paper.getId());
           String p = new File("").getAbsolutePath();
           File inputFile1 = new File(p.concat("\\src\\com\\company\\data\\paper_author.txt"));
           System.out.println(inputFile1.getAbsolutePath());
           File inputFile2 = new File(p.concat("\\src\\com\\company\\data/" + "paper_conf.txt"));
           File inputFile3 = new File(p.concat("\\src\\com\\company\\data/" + "paper_term.txt"));
           File tempFile1 = new File("myTempFile1.txt");
           File tempFile2 = new File("myTempFile2.txt");
           File tempFile3 = new File("myTempFile3.txt");

           //PAPER_AUTHOR

           BufferedReader reader = new BufferedReader(new FileReader(inputFile1));
           BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile1));

           String lineToRemove = paperId;
           String currentLine;

           while ((currentLine = reader.readLine()) != null) {
               // trim newline when comparing with lineToRemove
               String trimmedLine = currentLine.trim();
               if (trimmedLine.startsWith(lineToRemove)) continue;
               writer.write(currentLine + System.getProperty("line.separator"));
           }
           writer.close();
           reader.close();
           boolean successful = tempFile1.renameTo(inputFile1);

           //Delete the original file
           if (!inputFile1.delete()) {
               System.out.println("Could not delete file");
               return;
           }

           //Rename the new file to the filename the original file had.
           if (!tempFile1.renameTo(inputFile1))
               System.out.println("Could not rename file");



           //PAPER_CONF

           reader = new BufferedReader(new FileReader(inputFile2));
           writer = new BufferedWriter(new FileWriter(tempFile2));

           while ((currentLine = reader.readLine()) != null) {
               // trim newline when comparing with lineToRemove
               String trimmedLine = currentLine.trim();
               if (trimmedLine.startsWith(lineToRemove)) continue;
               writer.write(currentLine + System.getProperty("line.separator"));
           }
           writer.close();
           reader.close();
           boolean successful2 = tempFile2.renameTo(inputFile1);

           //Delete the original file
           if (!inputFile2.delete()) {
               System.out.println("Could not delete file");
               return;
           }

           //Rename the new file to the filename the original file had.
           if (!tempFile2.renameTo(inputFile2))
               System.out.println("Could not rename file");

           //PAPER_TERM

           reader = new BufferedReader(new FileReader(inputFile3));
           writer = new BufferedWriter(new FileWriter(tempFile3));


           while ((currentLine = reader.readLine()) != null) {
               // trim newline when comparing with lineToRemove
               String trimmedLine = currentLine.trim();
               if (trimmedLine.startsWith(lineToRemove)) continue;
               writer.write(currentLine + System.getProperty("line.separator"));
           }
           writer.close();
           reader.close();
           boolean successful3 = tempFile3.renameTo(inputFile3);

           //Delete the original file
           if (!inputFile3.delete()) {
               System.out.println("Could not delete file");
               return;
           }

           //Rename the new file to the filename the original file had.
           if (!tempFile3.renameTo(inputFile3))
               System.out.println("Could not rename file");
       }
        catch (IOException ex) {
           ex.printStackTrace();
       }
    }
    private void deleteConferenceRelationsOnFile(Conference conference) {
        try { //PAPER_CONF
            String confId = Integer.toString(conference.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_conf.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = confId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.endsWith(lineToRemove)){}
                else writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void deleteTermRelationsOnFile(Term term) {
        try { //PAPER_CONF
            String termId = Integer.toString(term.getId());
            String p = new File("").getAbsolutePath();
            File inputFile = new File(p.concat("\\src\\com\\company\\data\\paper_term.txt"));
            File tempFile = new File("myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = termId;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.endsWith(lineToRemove)){}
                else writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful2 = tempFile.renameTo(inputFile);

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
