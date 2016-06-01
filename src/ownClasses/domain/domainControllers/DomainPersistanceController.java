package ownClasses.domain.domainControllers;
import sharedClasses.domain.nodes.Author;
import sharedClasses.domain.nodes.Conference;
import sharedClasses.domain.nodes.Paper;
import sharedClasses.domain.nodes.Term;
import java.io.*;
import java.util.*;


public class DomainPersistanceController {
    private static String filepath;

    public DomainPersistanceController() {

        filepath = "/src/data/";
    }


    public void readAllFromFile(String path,HashMap<Integer, Author> authorsById,
                                HashMap<Integer, Paper> papersById,
                                HashMap<Integer, Conference> conferencesById,
                                HashMap<Integer, Term> termsById,
                                HashMap<String, Author> authorsByName,
                                HashMap<String, Paper> papersByName,
                                HashMap<String, Conference> conferencesByName,
                                HashMap<String, Term> termsByName) {

        if (path != null && !path.equals("")) {
            filepath = path+"/";
        }
        readAuthorsFromFile(authorsById,authorsByName);
        readPapersFromFile(papersById,papersByName);
        readConferencesFromFile(conferencesById,conferencesByName );
        readTermsFromFile(termsById,termsByName);
        readPaperAuthorRelations(authorsById,papersById);
        readTermRelations(papersById,termsById);
        readConferenceRelations(papersById,conferencesById);

        //IMPRIMIR HASHMAPS
        //testDomain();
    }

    public ArrayList<String> readNames(String path) {   //NOMÉS PEL SUBSETQUERY
        String p = new File("").getAbsolutePath();
        System.out.println(p);
        System.out.println(p.concat(path));
        System.out.println(path);
        //File inputFile = new File(p.concat(path));
        File inputFile = new File(path);

        ArrayList<String> names = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                names.add(line);
            }
            return names;
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
            return null;
        }
    }

    public ArrayList<String> addNewAuthor(String authorName, ArrayList<String> papersToRelate,HashMap<Integer, Author> authorsById,
                                          HashMap<String, Author> authorsByName,HashMap<Integer, Paper> papersById,
                                          HashMap<String, Paper> papersByName ) {
        Author a = new Author(authorName, Author.getMaxId() + 1);
        Author b = authorsByName.get(a.getName());
        ArrayList<String> newPapers = new ArrayList<>();
        if (b != null) {
            System.err.println("Aquest autor ja existeix");
            newPapers.add("Ja Existeix");
            return newPapers;
        }

        Paper p = null;
        for (int i = 0; i < papersToRelate.size(); i++) {
            p = papersByName.get(papersToRelate.get(i));
            if (p == null){
                System.err.println("No troba el paper del add new autor");
                newPapers.add(papersToRelate.get(i));
            }
            else {
                System.err.println("Id del paper" + Integer.toString(p.getId()));
                a.addPaper(p);
                p.addAuthor(a);
            }
        }

        System.err.println("Id del autor" + Integer.toString(a.getId()));

        authorsById.put(a.getId(), a);

        authorsByName.put(a.getName(), a);

        Author c = authorsById.get(a.getId());

        if(c != null) System.err.print("c" + c.getName());
        else{
            for(int i = 0; i < c.getAuthorRelations().size(); i++){
                Paper k = papersById.get(c.getAuthorRelations().get(i));
                if( k != null) System.err.println("Relacio"+                 k.getName());
            }
        }


        Author.incrementMaxId();
        return newPapers;
    }

    public HashMap<String,ArrayList<String>> addNewPaper(String paperName, ArrayList<String> authorsToRelate, ArrayList<String> termsToRelate, String confToRelate,
                                                         HashMap<Integer, Paper> papersById,HashMap<String, Paper> papersByName,
                                                         HashMap<String, Author> authorsByName,HashMap<String, Conference> conferencesByName,
                                                         HashMap<String, Term> termsByName ) {
        Paper p = new Paper(paperName, Paper.getMaxId() + 1);
        ArrayList<String> newAuthors = new ArrayList<>();
        ArrayList<String> newTerms = new ArrayList<>();
        ArrayList<String> newConf = new ArrayList<>();
        HashMap<String,ArrayList<String>> ret = new HashMap<>();
        if (papersByName.get(p.getName()) != null) {
            System.err.println("Aquest article ja existeix");
            ret.put("Fail",new ArrayList<String>());
            return ret;
        }
        Author a = null;
        Term t = null;
        Conference c = null;
        for (int i = 0; i < authorsToRelate.size(); i++) {
            a = authorsByName.get(authorsToRelate.get(i));
            if(a == null) newAuthors.add(authorsToRelate.get(i));
            else {
                p.addAuthor(a);
                a.addPaper(p);
            }
        }
        for (int i = 0; i < termsToRelate.size(); i++) {
            t = termsByName.get(termsToRelate.get(i));
            if(t == null) newTerms.add(termsToRelate.get(i));
            else {
                p.addTerm(t);
                t.addPaperWhichTalkAboutIt(p);
            }
        }
        c = conferencesByName.get(confToRelate);
        if (c == null) newConf.add(confToRelate);
        else {
            p.setConference(c);
            c.addExposedPaper(p);
        }
        System.err.println(p.getId());
        papersById.put(p.getId(),p);
        papersByName.put(p.getName(),p);
        Paper.incrementMaxId();
        ret.put("A", newAuthors);
        ret.put("T", newTerms);
        ret.put("C",newConf);
        return ret;
    }

    public ArrayList<String> addNewTerm(String termName, ArrayList<String> papersToRelate,
                                        HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName,
                                        HashMap<String, Paper> papersByName) {
        Term a = new Term(termName, Term.getMaxId() + 1);
        Term b = termsByName.get(a.getName());
        ArrayList<String> newPapers = new ArrayList<>();
        if (b != null) {
            System.err.println("Aquest terme ja existeix");
            newPapers.add("Ja Existeix");
            return newPapers;
        }

        Paper p = null;
        for (int i = 0; i < papersToRelate.size(); i++) {
            p = papersByName.get(papersToRelate.get(i));
            if (p == null) newPapers.add(papersToRelate.get(i));
            else {
                a.addPaperWhichTalkAboutIt(p);
                p.addTerm(a);
            }
        }
        termsById.put(a.getId(), a);
        termsByName.put(a.getName(), a);
        Term.incrementMaxId();
        return newPapers;
    }

    public ArrayList<String> addNewConference(String confName, ArrayList<String> papersToRelate,
                                              HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName,
                                              HashMap<String, Paper> papersByName) {
        Conference a = new Conference(confName, Conference.getMaxId() + 1);
        Conference b = conferencesByName.get(a.getName());
        ArrayList<String> newPapers = new ArrayList<>();
        if (b != null) {
            System.err.println("Aquesta conferència ja existeix");
            newPapers.add("Ja Existeix");
            return newPapers;
        }

        Paper p = null;
        for (int i = 0; i < papersToRelate.size(); i++) {
            p = papersByName.get(papersToRelate.get(i));
            if (p == null) newPapers.add(papersToRelate.get(i));
            else {
                a.addExposedPaper(p);
                p.setConference(a);
            }
        }
        conferencesById.put(a.getId(), a);
        conferencesByName.put(a.getName(), a);
        Conference.incrementMaxId();
        return newPapers;
    }

    public boolean deleteAuthor(String authorName,HashMap<Integer, Author> authorsById, HashMap<String, Author> authorsByName ,
                                HashMap<Integer, Paper> papersById,HashMap<String, Paper> papersByName,
                                HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName ,
                                HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName) {
        try {
            Author a = authorsByName.get(authorName);
            HashMap<Integer, Paper> aux = a.getPapersById(papersById);
            for (Paper p : aux.values()) {
                p.removeAuthor(a);
                if (p.getAuthorsById(authorsById).size() < 1) {
                    deletePaperRelationsOnConferences(p,papersById,conferencesById,conferencesByName);
                    deletePaperRelationsOnTerms(p,papersById,termsById,termsByName);
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

    public boolean deletePaper(String paperName,HashMap<Integer, Author> authorsById, HashMap<String, Author> authorsByName ,
                               HashMap<Integer, Paper> papersById,HashMap<String, Paper> papersByName,
                               HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName ,
                               HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName) {
        try {
            Paper p = papersByName.get(paperName);
            deletePaperRelationsOnConferences(p,papersById,conferencesById,conferencesByName);
            deletePaperRelationsOnTerms(p,papersById,termsById,termsByName);
            deletePaperRelationsOnAuthors(p,authorsById,authorsByName,papersById);
            int id = p.getId();
            papersByName.remove(paperName);
            papersById.remove(id);
            return true;
        } catch (NullPointerException ex) {
            System.out.println("\u001B[31m" + "Aquest article no existeix." + "\u001B[0m");
            return false;
        }
    }

    public boolean deleteTerm(String termName,HashMap<Integer, Author> authorsById, HashMap<String, Author> authorsByName ,
                              HashMap<Integer, Paper> papersById,HashMap<String, Paper> papersByName,
                              HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName ,
                              HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName) {
        try {
            Term t = termsByName.get(termName);
            Collection<Paper> auxiliar = t.getPapersWhichTalkAboutThisById(papersById).values();
            for (Iterator it = auxiliar.iterator(); it.hasNext(); ) {
                Paper p = (Paper) it.next();
                p.removeTerm(t);
                if (p.getTermsById(termsById).size() < 1) { //Eliminar paper i relacions
                    deletePaperRelationsOnAuthors(p,authorsById,authorsByName,papersById);
                    deletePaperRelationsOnConferences(p,papersById,conferencesById,conferencesByName);
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

    public boolean deleteConference(String confName,HashMap<Integer, Author> authorsById, HashMap<String, Author> authorsByName ,
                                    HashMap<Integer, Paper> papersById,HashMap<String, Paper> papersByName,
                                    HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName ,
                                    HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName) {
        try {
            Conference c = conferencesByName.get(confName);
            Collection<Paper> auxiliarp = c.getExposedPapersById(papersById).values();
            for (Iterator it = auxiliarp.iterator(); it.hasNext(); ) {
                Paper p = (Paper) it.next();
                deletePaperRelationsOnAuthors(p,authorsById,authorsByName,papersById);
                deletePaperRelationsOnTerms(p,papersById,termsById,termsByName);
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

    public boolean editAuthor(String authorName, String newName,
                              HashMap<Integer, Author> authorsById, HashMap<String, Author> authorsByName) {
        try {
            Author a = authorsByName.get(authorName);
            a.setName(newName);
            authorsByName.remove(authorName);
            authorsByName.put(newName, a);
            authorsById.remove(a.getId());
            authorsById.put(a.getId(),a);
            return true;
        } catch (NullPointerException ex) {
            System.err.println("Aquest autor no existeix");
            return false;
        }
    }

    public boolean editPaper(String paperName, String newName,
                             HashMap<Integer, Paper> papersById,HashMap<String, Paper> papersByName) {
        try {
            Paper p = papersByName.get(paperName);
            p.setName(newName);
            papersByName.remove(paperName);
            papersByName.put(newName, p);
            papersById.remove(p.getId());
            papersById.put(p.getId(),p);
            return true;
        } catch (NullPointerException ex) {
            System.err.println("Aquest article no existeix");
            return false;
        }
    }

    public boolean editTerm(String termName, String newName,
                            HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName) {
        try {
            Term t = termsByName.get(termName);
            t.setName(newName);
            termsByName.remove(termName);
            termsByName.put(newName, t);
            termsById.remove(t.getId());
            termsById.put(t.getId(),t);
            return true;
        } catch (NullPointerException ex) {
            System.err.println("Aquest terme no existeix");
            return false;
        }
    }

    public boolean editConference(String confName, String newName,
                                  HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName) {
        try {
            Conference c = conferencesByName.get(confName);
            c.setName(newName);
            conferencesByName.remove(confName);
            conferencesByName.put(newName, c);
            conferencesById.remove(c.getId());
            conferencesById.put(c.getId(),c);
            return true;
        } catch (NullPointerException ex) {
            System.err.println("Aquesta conferencia no existeix");
            return false;
        }
    }



    private void readAuthorsFromFile(HashMap<Integer,Author> authorsById,HashMap<String,Author> authorsByName ) {
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

    private void readPapersFromFile(HashMap<Integer, Paper> papersById,HashMap<String, Paper> papersByName ) {
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

    private void readConferencesFromFile(HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName) {
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


    private void readTermsFromFile( HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName) {
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


    private void readPaperAuthorRelations(HashMap<Integer, Author> authorsById, HashMap<Integer, Paper> papersById) {
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

    private void readConferenceRelations(HashMap<Integer, Paper> papersById ,HashMap<Integer, Conference> conferencesById) {
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

    private void readTermRelations(HashMap<Integer, Paper> papersById,HashMap<Integer, Term> termsById) {
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



    private void deletePaperRelationsOnConferences(Paper p,HashMap<Integer, Paper> papersById,
                                                   HashMap<Integer, Conference> conferencesById,HashMap<String, Conference> conferencesByName) {
        Conference auxiliarc = p.getConference();
        auxiliarc.removeExposedPaperBy(p);
        if (auxiliarc.getExposedPapersById(papersById).size() < 1) {
            conferencesById.remove(auxiliarc.getId());
            conferencesByName.remove(auxiliarc.getName());
        }
    }

    private void deletePaperRelationsOnAuthors(Paper p,HashMap<Integer, Author> authorsById, HashMap<String, Author> authorsByName ,
                                               HashMap<Integer, Paper> papersById) {
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

    private void deletePaperRelationsOnTerms(Paper p,HashMap<Integer, Paper> papersById,
                                             HashMap<Integer, Term> termsById,HashMap<String, Term> termsByName) {
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


    private void writeAuthorsToFile(HashMap<Integer, Author> authorsById) {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "author.txt"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false));
            for (Author a : authorsById.values()) {
                String line = Integer.toString(a.getId()) + "\t" + a.getName();
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    private void writePaperToFile(HashMap<Integer, Paper> papersById) {
        String s = new File("").getAbsolutePath();
        File inputFile = new File(s.concat(filepath + "paper.txt"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false));
            for(Paper p : papersById.values()) {
                String line = Integer.toString(p.getId()) + "\t" + p.getName();
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    private void writeConferencesToFile(HashMap<Integer, Conference> conferencesById) {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "conf.txt"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false));
            for (Conference c : conferencesById.values()) {
                String line = Integer.toString(c.getId()) + "\t" + c.getName();
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }


    private void writeTermsToFile(HashMap<Integer, Term> termsById) {
        String p = new File("").getAbsolutePath();
        File inputFile = new File(p.concat(filepath + "term.txt"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, false));
            for (Term t : termsById.values()) {
                String line = Integer.toString(t.getId()) + "\t" + t.getName();
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }


    private void writePaperAuthorRelations(HashMap<Integer, Paper> papersById) {
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_author.txt"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,false))) {
            String line;
            for(Paper p:papersById.values()){
                ArrayList<Integer> authors = p .getRelatedAuthors();
                for(int i = 0; i < authors.size(); i++) {
                    line = Integer.toString(p.getId()) + "\t" + Integer.toString(authors.get(i));
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void writePaperConferenceRelations( HashMap<Integer, Paper> papersById) {
        String p1 = new File("").getAbsolutePath();
        File inputFile = new File(p1.concat(filepath + "paper_conf.txt"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,false))) {
            String line;
            for(Paper p:papersById.values()){
                line = Integer.toString(p.getId()) + "\t" + Integer.toString(p.getConference().getId());
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

   private void writePaperTermRelations( HashMap<Integer, Paper> papersById) {
       String p1 = new File("").getAbsolutePath();
       File inputFile = new File(p1.concat(filepath + "paper_term.txt"));
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile,false))) {
           String line;
           for(Paper p:papersById.values()){
               ArrayList<Integer> terms = p .getRelatedTerms();
               for(int i = 0; i < terms.size(); i++) {
                   line = Integer.toString(p.getId()) + "\t" + Integer.toString(terms.get(i));
                   writer.write(line);
                   writer.newLine();
               }
           }
           writer.flush();
           writer.close();
       } catch (IOException x) {
           System.err.format("IOException: %s%n", x);
       }
   }

    public void writeAllToFile(String path,HashMap<Integer, Author> authorsById,
                               HashMap<Integer, Paper> papersById,
                               HashMap<Integer, Conference> conferencesById,
                               HashMap<Integer, Term> termsById){
        if (path != null) {
            filepath = path;
        }
        writeAuthorsToFile(authorsById);
        writePaperToFile(papersById);
        writeConferencesToFile(conferencesById);
        writeTermsToFile(termsById);
        writePaperAuthorRelations(papersById);
        writePaperTermRelations(papersById);
        writePaperConferenceRelations(papersById);
    }

    public String newRelationPaperAuthor(String paperName, String authorName, HashMap<String,Paper> papersByName, HashMap<String,Author> authorsByName){
        Paper p = papersByName.get(paperName);
        if(p == null) return "NP";
        Author a = authorsByName.get(authorName);
        if(a == null) return "NA";
        p.addAuthor(a);
        a.addPaper(p);
        return null;
    }

    public String newRelationPaperTerm(String paperName, String termName, HashMap<String,Paper> papersByName, HashMap<String,Term> termsByName){
        Paper p = papersByName.get(paperName);
        if(p == null) return "NP";
        Term t = termsByName.get(termName);
        if(t == null) return "NT";
        p.addTerm(t);
        t.addPaperWhichTalkAboutIt(p);
        return null;
    }

    public String newRelationPaperConference(String paperName, String confName, HashMap<String,Paper> papersByName, HashMap<String,Conference> conferencesByName){
        Paper p = papersByName.get(paperName);
        if(p == null) return "NP";
        Conference c = conferencesByName.get(confName);
        if(c == null) return "NC";
        p.getConference().removeExposedPaperBy(p);
        p.setConference(c);
        c.addExposedPaper(p);
        return null;
    }

}