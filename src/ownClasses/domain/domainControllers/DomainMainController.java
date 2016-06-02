package ownClasses.domain.domainControllers;

import ownClasses.domain.domainControllers.Persistance.BinaryAuthors;
import ownClasses.domain.domainControllers.Persistance.BinaryConferences;
import ownClasses.domain.domainControllers.Persistance.BinaryPapers;
import ownClasses.domain.domainControllers.Persistance.BinaryTerms;
import ownClasses.domain.queries.IntervaledQuery;
import ownClasses.domain.queries.LimitedQuery;
import ownClasses.domain.queries.OrderedQuery;
import ownClasses.domain.queries.Query;
import ownClasses.domain.domainControllers.DomainPersistanceController;
import sharedClasses.domain.nodes.Author;
import sharedClasses.domain.nodes.Conference;
import sharedClasses.domain.nodes.Paper;
import sharedClasses.domain.nodes.Term;
import sharedClasses.utils.Matrix;
import ownClasses.domain.utils.Pair;
import sharedClasses.utils.Vertex;

import java.util.*;

public class DomainMainController {
    private HashMap<Integer, Author> authorsById;
    private HashMap<Integer, Paper> papersById;
    private HashMap<Integer, Conference> conferencesById;
    private HashMap<Integer, Term> termsById;
    private HashMap<String, Author> authorsByName;
    private HashMap<String, Paper> papersByName;
    private HashMap<String, Conference> conferencesByName;
    private HashMap<String, Term> termsByName;
    private DomainPersistanceController persistanceController;
    private DomainHetesimController hetesimController;
    private Scanner scanner;


    private Matrix result;
    private boolean edit;

    private Matrix authorpaper;
    private Matrix confpaper;
    private Matrix termpaper;


    public DomainMainController() {
        authorsById = new HashMap<>();
        papersById = new HashMap<>();
        conferencesById = new HashMap<>();
        termsById = new HashMap<>();
        authorsByName = new HashMap<>();
        papersByName = new HashMap<>();
        papersByName = new HashMap<>();
        conferencesByName = new HashMap<>();
        termsByName = new HashMap<>();
        persistanceController = new DomainPersistanceController();
        persistanceController.readAllFromFile("",authorsById, papersById, conferencesById, termsById, authorsByName, papersByName, conferencesByName, termsByName);
        BinaryAuthors binaryAuthors = new BinaryAuthors();
        BinaryPapers binaryPapers = new BinaryPapers();
        BinaryConferences binaryConferences = new BinaryConferences();
        BinaryTerms binaryTerms = new BinaryTerms();
        long timeini = System.currentTimeMillis();
        binaryAuthors.write(authorsById);
        binaryPapers.write(papersById);
        binaryTerms.write(termsById);
        binaryConferences.write(conferencesById);
        long timefinal = System.currentTimeMillis();
        System.out.println(timefinal-timeini);
        System.out.println("Importa");

        timeini = System.currentTimeMillis();
        authorsById = (HashMap<Integer,Author>) binaryAuthors.read();
        papersById = (HashMap<Integer,Paper>) binaryPapers.read();
        conferencesById = (HashMap<Integer,Conference>) binaryConferences.read();
        termsById = (HashMap<Integer,Term>) binaryTerms.read();
        timefinal = System.currentTimeMillis();
        System.out.println(timefinal-timeini);
//persistanceController.testDomain();
        authorpaper = getAuthorPaperMatrixD(null,null,null,null);
        confpaper =getConfPaperMatrixD(null,null,null,null);
        termpaper = getTermPaperMatrixD(null,null,null,null);
        hetesimController = new DomainHetesimController(authorpaper,authorpaper.transpose(),termpaper,termpaper.transpose(),confpaper,confpaper.transpose());
        scanner = new Scanner(System.in);
        edit=false;

    }


    /**
     * Returns the attribute persistanceController
     *
     * @return   returns the persistanceController
     * @see         DomainPersistanceController
     */

    public DomainPersistanceController getPersistanceController() {
        return persistanceController;
    }

    /**
     * Returns the HashMap that contains all the authors ordered by ID
     * @return the hashmap authors by Id
     */

    public HashMap<Integer,Author> getAuthorsById(){
        return authorsById;
    }

    /**
     * Returns the HashMap that contains all the papers ordered by ID
     * @return the hashmap papers by Id
     */
    public HashMap<Integer,Paper> getPapersById(){
        return papersById;
    }

    /**
     * Returns the HashMap that contains all the terms ordered by ID
     * @return the hashmap terms by Id
     */

    public HashMap<Integer,Term> getTermsById(){ return termsById; }

    /**
     * Returns the HashMap that contains all the conferences ordered by ID
     * @return the hashmap conferences by Id
     */

    public HashMap<Integer,Conference> getConferencesById(){ return conferencesById; }

    /**
     * Returns the HashMap that contains all the authors ordered by name
     * @return the hashmap authors by name
     */

    public HashMap<String,Author> getAuthorsByName(){ return authorsByName; }

    /**
     * Returns the HashMap that contains all the papers ordered by name
     * @return the hashmap papers by name
     */

    public HashMap<String,Paper> getPapersByName(){ return papersByName;}

    /**
     * Returns the HashMap that contains all the conferences ordered by name
     * @return the hashmap conferences by name
     */

    public HashMap<String,Conference> getConferencesByName(){return conferencesByName;}

    /**
     * Returns the HashMap that contains all the terms ordered by name
     * @return the hashmap terms by name
     */

    public HashMap<String,Term> getTermsByName(){return termsByName;}


    /**
     * If it has been edited, update Matrix and then calculate
     *  the new result Matrix calling
     * the hetesimController
     *
     * @param  path the path which Hetesim will calculate the new Matrix result.
     */

    public void NQ(String path){
        if(edit) {
            updateMatrix(null,null,null,null);

        }
        result = hetesimController.heteSim(path);

    }

    /**
     * Returns true if the name is at the corresponding HashMap of the Node type node,
     * Otherwise, false.
     *
     * @param  name the name of the Node
     * @param node indicates the type of Node
     *
     */

    public boolean checkName(String name, char node){
        boolean check = false;
        switch (node) {
            case ('A'):
                check = authorsByName.containsKey(name);
                break;
            case ('P'):
                check = papersByName.containsKey(name);
                break;
            case ('C'):
                check = conferencesByName.containsKey(name);
                break;
            case ('T'):
                check = termsByName.containsKey(name);
                break;
        }
        return check;
    }

    /**
     * Returns an ArrayList with all the nodes related with the node identified by name by the path
     * with a filter applied in function of the parameter querytype.
     *
     * @param  path the corresponding path of the result
     * @param querytype the type of query that is requested
     * @param ascendent if true, the result will be ordered ascending, otherwise descending
     * @param name indicates the name of the principal node that determines the result.
     * @param n indicates the number of Strings that will be added to the ArrayList that will be returned
     * @param max indicates the maximum relevance of the elements that will be added to the ArrayList
     * @param min indicates the minimum relevance of the elements that will be added to the ArrayList
     * @return  ArrayList with all the
     */

    public ArrayList<String> resultat(String path, int querytype,boolean ascendent,String name,int n,double max,double min){
        OrderedQuery query = new OrderedQuery(path,ascendent);

        ArrayList<String> total = new ArrayList<>();

        Integer queryId = 0;
        char type = path.charAt(0);

        switch (type) {
            case ('A'):
                    queryId = authorsByName.get(name).getId();
                break;
            case ('P'):
                    queryId = papersByName.get(name).getId();
                break;
            case ('C'):
                    queryId = conferencesByName.get(name).getId();
                break;
            case ('T'):
                    queryId = termsByName.get(name).getId();
                break;
        }


        LinkedList<Vertex> resultquery = new LinkedList<>();
        if(result.getRow(queryId)!= null) resultquery = result.getRow(queryId);


        ArrayList<Pair<Integer,Double>> resultOrdered = resultWithOrder(resultquery, query);

        switch (querytype){
            case (1):
                total = resultWithoutFilters(resultOrdered,query);
                break;
            case (2):
                LimitedQuery lq = new LimitedQuery(query.getPath(), n);
                total = resultWithMax(resultOrdered, lq);
                break;
            case(3):
                IntervaledQuery iq = new IntervaledQuery(query.getPath(), min, max);
                total = resultWithIntervals(resultOrdered, iq);
                break;
        }


        return total;
    }

    /**
     * function that will show the corresponding result in function of the options
     * selected by the user and asked before.
     */

    public void newQuery() {

        hetesimController = new DomainHetesimController(authorpaper,authorpaper.transpose(),termpaper,termpaper.transpose(),confpaper,confpaper.transpose());
        System.out.println("Quin tipus de cerca vols: -1 Simple o -2 Amb filtres");
        int queryType = scanner.nextInt();
        while(queryType != -1 && queryType != -2){
            System.err.println("Tipus no valid, escriu -1 Simple o -2 Amb filtres");
            queryType = scanner.nextInt();
        }

        boolean pathValid = false;
        System.out.println("Introdueix el teu path (exemple: APA):");
        String queryPath = "";
        while ("".equals(queryPath)) queryPath = scanner.nextLine();
        while(!pathValid) {
            boolean surt = false;
            for(int i=0; i<queryPath.length() && !surt; ++i){
                char node= queryPath.charAt(i);
                if(node != 'A' && node != 'P' && node!='C' && node!='T') surt = true;
            }
            if(!surt){ pathValid = true;}
            else{
                System.err.println("Path no valid, torna'l a escriure (exemple APA):");
                //while ("".equals(queryPath)) queryPath = scanner.nextLine();
                queryPath = scanner.nextLine();
            }
        }

        OrderedQuery query = new OrderedQuery(queryPath,false);
        Matrix result = hetesimController.heteSim(queryPath);

        boolean exit = false;
        char type = queryPath.charAt(0);

        while(!exit) {
            System.out.println("Selecciona l'ordre 1 Ascendent 2 Descendent");
            String r = scanner.nextLine();
            int i = Integer.parseInt(r);
            if (i > 2 || i <= 0) {
                System.out.println("Ordre no disponible");
                break;
            }
            else {
                if (i == 1)
                    query.setAscendent(true);
            }

            System.out.println("OK");
            boolean valid = false;
            Integer queryId = 0;

            while (!valid) {
                System.out.println("Introdueix el nom:");
                String queryName = scanner.nextLine();
                switch (type) {
                    case ('A'):
                        if (authorsByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = true;
                            queryId = authorsByName.get(queryName).getId();
                        }
                        break;
                    case ('P'):
                        if (papersByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = true;
                            queryId = papersByName.get(queryName).getId();
                        }
                        break;
                    case ('C'):
                        if (conferencesByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = true;
                            queryId = conferencesByName.get(queryName).getId();
                        }
                        break;
                    case ('T'):
                        if (termsByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = true;
                            queryId = termsByName.get(queryName).getId();
                        }
                        break;
                }
                if (!valid) {
                    System.err.println("Nom no vàlid. Siusplau introdueix un nom correcte.");
                }
            }

            /*TreeMap<Integer, Double> resultquery = new TreeMap<>();
            if (result.column(queryId) != null) resultquery = result.column(queryId);*/
            LinkedList<Vertex> resultquery = new LinkedList<>();
            if(result.getRow(queryId)!= null) resultquery = result.getRow(queryId);


            ArrayList<Pair<Integer,Double>> resultOrdered = resultWithOrder(resultquery, query);


            if (queryType == -1) {
                resultWithoutFilters(resultOrdered, query);
            } else {
                int exitfiltres = 0;
                while (exitfiltres == 0) {
                    System.out.println("Escull el tipus de filtre: -1 Intervals de rellevancia, -2 Nombre maxim d'entrades, -3 Restriccio per element:");
                    int queryfilter = scanner.nextInt();
                    switch (queryfilter) {
                        case (-1):
                            System.out.println("Introdueix el valor mes petit (entre 0 i 1)");
                            double firstrelevance = scanner.nextDouble();
                            System.out.println("Introdueix el valor mes gran (entre 0 i 1)");
                            double secondrelevance = scanner.nextDouble();
                            if (firstrelevance > secondrelevance) {
                                System.err.println("firstrelevance > secondrelevance)");
                            } else {
                                IntervaledQuery iq = new IntervaledQuery(query.getPath(), firstrelevance, secondrelevance);
                                resultWithIntervals(resultOrdered, iq);
                            }

                            break;
                        case (-2):

                            System.out.println("Introdueix el nombre maxim d'entrades");
                            int nomMax = scanner.nextInt();
                            LimitedQuery lq = new LimitedQuery(query.getPath(), nomMax);
                            resultWithMax(resultOrdered, lq);

                            break;
                        case (-3):
                            System.out.println("no disponible");
                            break;
                    }
                    System.out.println("Vols escollir un altre tipus de filtre? YES or NO");
                    String answer = "";
                    boolean validesa = false;
                    while(!validesa){
                        answer = scanner.nextLine();
                        if (answer.equals("NO") || answer.equals("no")){
                            exitfiltres = 1;
                            validesa = true;
                        }
                        else{
                            if(answer.equals("YES") || answer.equals("yes")) validesa = true;
                            else System.out.println("torna a introduir la resposta");
                        }
                    }
                }

            }
            System.out.println("Vols seleccionar un altre nom amb el mateix path? YES or NO");
            String answer = scanner.nextLine();
            if (answer.equals("NO") || answer.equals("no")) exit = true;
        }

    }

    /**
     * Returns a string composed by the name of the node identified by the char tipus
     * and the Integer id, the string " -> " and the relevance
     *
     * @param tipus indicates the type of the node
     * @param id the id of the node
     * @param relevance a double that indicates the relevance of the node with another node.
     *
     * @return The string composed by the name of the node identified by the char tipus
     * and the Integer id, the string " -> " and the relevance
     */


    private String GetString(char tipus, Integer id, Double relevance){
        if (relevance > 1.0) relevance = 1.0;
        else if (relevance < 0.0) relevance = 0.0;
        String row = new String();
        switch (tipus) {
            case ('A'):
                row = (authorsById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('P'):
                row = (papersById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('C'):
                row = (conferencesById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('T'):
                row = (termsById.get(id).getName() + "  ->  " + relevance);
                break;
        }
        return row;

    }

    /**
     * Shows at the screen the string composed by the name of the node identified by the char tipus
     * and the Integer id, the string " -> " and the relevance
     *
     * @param tipus indicates the type of the node
     * @param id the id of the node
     * @param relevance a double that indicates the relevance of the node with another node.
     */

    private void printresult(char tipus, Integer id, Double relevance){
        if (relevance > 1.0) relevance = 1.0;
        else if (relevance < 0.0) relevance = 0.0;
        switch (tipus) {
            case ('A'):
                System.out.println(authorsById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('P'):
                System.out.println(papersById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('C'):
                System.out.println(conferencesById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('T'):
                System.out.println(termsById.get(id).getName() + "  ->  " + relevance);
                break;
        }
    }

    /**
     * Returns an ArrayList of Pair<Integer,Double> ordered by the second paramenter in function of the paramenter of
     * the ordered query
     *
     * @param resultquery the linkedlist with the result values of the requested node
     * @param query the Ordered query that contains the path and the boolean that indicates the type of order of the ArrayList
     * @return ArrayList of Pair<Integer,Double> ordered by the second paramenter in function of the parameter of the ordered query
     */

    private ArrayList<Pair<Integer,Double>> resultWithOrder(LinkedList<Vertex> resultquery, OrderedQuery query){
        ListIterator<Vertex> it = resultquery.listIterator();
        ArrayList<Pair<Integer,Double>> resultOrdered = new ArrayList<>();
        while(it.hasNext()) {
            Vertex v = it.next();
            if(resultOrdered.isEmpty()){
                resultOrdered.add(new Pair<Integer, Double>(v.getSecond(),v.getValue()));
            }
            else{
                boolean end=false;
                if(query.isAscendent()) {
                    for (int i = 0; i < resultOrdered.size() && !end; ++i) {
                        if(resultOrdered.get(i).getSecond()>= v.getValue()){
                            resultOrdered.add(i,new Pair<Integer, Double>(v.getSecond(),v.getValue()));
                            end = true;
                        }
                    }
                }
                else{
                    for(int i = 0; i < resultOrdered.size() && !end; ++i) {
                        if(resultOrdered.get(i).getSecond()<= v.getValue()){
                            resultOrdered.add(i,new Pair<Integer, Double>(v.getSecond(),v.getValue()));
                            end = true;
                        }
                    }
                }
                if(!end) resultOrdered.add(new Pair<Integer, Double>(v.getSecond(),v.getValue()));
            }
        }


        return resultOrdered;
    }

    /**
     *
     * Returns an ArrayList of Pair<Integer,Double> ordered with the size of the attribute Limit of the parameter query
     *
     * @param resultquery the linkedlist with the result values of the requested node
     * @param query the Limited query that contains the path and the integer which indicates the maximum number or results
     * @return ArrayList of Pair<Integer,Double> ordered with the size of the attribute Limit of the parameter query
     */

    private ArrayList<String> resultWithMax(ArrayList<Pair<Integer,Double>> resultquery, LimitedQuery query) {
        char tipus = query.getPath().charAt(query.getPath().length()-1);
        //System.out.println(" NOM  ->  rellevancia");
        ArrayList<String> total = new ArrayList<>();
        for(Pair<Integer,Double> p : resultquery){
            if(query.getLimit() >0){
                total.add(GetString(tipus,p.getFirst(),p.getSecond()));
                printresult(tipus,p.getFirst(),p.getSecond());
            }
            else{
                break;
            }
            query.setLimit(query.getLimit()-1);
        }

        return total;
    }

    /**
     *Returns an ArrayList of Pair<Integer,Double> ordered with the size of the attribute Limit of the parameter query
     * @param resultquery the linkedlist with the result values of the requested node
     * @param query
     * @return
     */

    private ArrayList<String> resultWithIntervals(ArrayList<Pair<Integer,Double>> resultquery, IntervaledQuery query){
        char tipus = query.getPath().charAt(query.getPath().length()-1);
        //System.out.println(" NOM  ->  rellevancia");

        ArrayList<String> total = new ArrayList<>();
        for(Pair<Integer,Double> p : resultquery){
            if(p.getSecond() >= query.getFirstRelevance() && p.getSecond() <= query.getSecondRelevance()) {
                total.add(GetString(tipus,p.getFirst(),p.getSecond()));
                printresult(tipus,p.getFirst(),p.getSecond());
            }
        }

        return total;
        /*for (Object o : resultquery.entrySet()) {
            Map.Entry resultat = (Map.Entry) o;
            double res = Double.parseDouble(resultat.getValue().toString());
            if (res >= query.getFirstRelevance() && res <= query.getSecondRelevance()) {

                printresult(tipus, Integer.parseInt(resultat.getKey().toString()), Double.parseDouble(resultat.getValue().toString()));

            }
        }*/

    }

    private ArrayList<String> resultWithoutFilters(ArrayList<Pair<Integer,Double>> resultquery, Query query){
        char tipus = query.getPath().charAt(query.getPath().length()-1);
        //System.out.println(" NOM  ->  rellevancia");

        ArrayList<String> total = new ArrayList<>();
        for(Pair<Integer,Double> p : resultquery){
            String row = GetString(tipus,p.getFirst(),p.getSecond());
            total.add(row);
            printresult(tipus,p.getFirst(),p.getSecond());
        }

        return total;
        /*for (Object o : resultquery.entrySet()) {
            Map.Entry resultat = (Map.Entry) o;
            printresult(tipus, Integer.parseInt(resultat.getKey().toString()), Double.parseDouble(resultat.getValue().toString()));

        }*/

    }

    public void editGraph() {
        edit = true;
    }

    private Matrix getAuthorPaperMatrixD(ArrayList<String> authors, ArrayList<String> papers, ArrayList<String> terms, ArrayList<String> conferences){
        Matrix authorpaper = new Matrix();

        if(authors == null && papers == null && terms==null && conferences==null){
            for (Author author : authorsById.values()) {
                HashMap<Integer, Paper> papersOfAuthor = author.getPapersById(papersById);
                for (Paper paper : papersOfAuthor.values()) {
                    authorpaper.addValue(author.getId(), paper.getId(), 1.0);
                }
            }
        }
        if(authors!=null){
            for(String authorname : authors){
                Author author = authorsByName.get(authorname);
                HashMap<Integer,Paper> papersOfAuthor = author.getPapersById(papersById);
                for(Paper paper : papersOfAuthor.values()){
                    HashMap<Integer,Author> authorsOfPaper = paper.getAuthorsById(authorsById);
                    for(Author authorOfPaper : authorsOfPaper.values()){
                        authorpaper.addValue(authorOfPaper.getId(),paper.getId(),1.0);
                    }
                }
            }
        }
        if(papers!=null){
            for(String papername:papers){
                Paper paper = papersByName.get(papername);
                HashMap<Integer,Author> authorsOfPaper = paper.getAuthorsById(authorsById);
                for(Author authorOfPaper : authorsOfPaper.values()){
                    authorpaper.addValue(authorOfPaper.getId(),paper.getId(),1.0);
                }
            }
        }
        if(terms!=null){
            for(String termname : terms){
                Term term = termsByName.get(termname);
                HashMap<Integer, Paper> papersOfTerm = term.getPapersWhichTalkAboutThisById(papersById);
                for(Paper paper:papersOfTerm.values()){
                    HashMap<Integer,Author> authorsOfPaper = paper.getAuthorsById(authorsById);
                    for(Author authorOfPaper : authorsOfPaper.values()){
                        authorpaper.addValue(authorOfPaper.getId(),paper.getId(),1.0);
                    }
                }
            }
        }
        if(conferences!=null){
            for(String confname:conferences){
                Conference conf = conferencesByName.get(confname);
                HashMap<Integer,Paper> papersOfConf = conf.getExposedPapersById(papersById);
                for(Paper paper:papersOfConf.values()){
                    HashMap<Integer,Author> authorsOfPaper = paper.getAuthorsById(authorsById);
                    for(Author authorOfPaper : authorsOfPaper.values()){
                        authorpaper.addValue(authorOfPaper.getId(),paper.getId(),1.0);
                    }
                }
            }
        }


        return authorpaper;
    }

    private Matrix getTermPaperMatrixD(ArrayList<String> authors, ArrayList<String> papers, ArrayList<String> terms, ArrayList<String> conferences){
        Matrix termpaper = new Matrix();

        if(authors == null && papers == null && terms==null && conferences==null){
            for(Term term : termsById.values()){
                HashMap<Integer,Paper> papersOfTerm = term.getPapersWhichTalkAboutThisById(papersById);
                for(Paper paper : papersOfTerm.values()){
                    termpaper.addValue(term.getId(),paper.getId(),1.0);
                }
            }
        }
        if(authors!=null){
            for(String authorname : authors){
                Author author = authorsByName.get(authorname);
                HashMap<Integer,Paper> papersOfAuthor = author.getPapersById(papersById);
                for(Paper paper : papersOfAuthor.values()){
                    HashMap<Integer,Term> termsOfPaper = paper.getTermsById(termsById);
                    for(Term term : termsOfPaper.values()){
                        termpaper.addValue(term.getId(),paper.getId(),1.0);
                    }
                }
            }
        }
        if(papers!=null){
            for(String papername:papers){
                Paper paper = papersByName.get(papername);
                HashMap<Integer,Term> termsOfPaper = paper.getTermsById(termsById);
                for(Term term : termsOfPaper.values()){
                    termpaper.addValue(term.getId(),paper.getId(),1.0);
                }
            }
        }
        if(terms!=null){
            for(String termname : terms){
                Term term = termsByName.get(termname);
                HashMap<Integer, Paper> papersOfTerm = term.getPapersWhichTalkAboutThisById(papersById);
                for(Paper paper:papersOfTerm.values()){
                    HashMap<Integer,Term> termsOfPaper = paper.getTermsById(termsById);
                    for(Term termofpaper : termsOfPaper.values()){
                        termpaper.addValue(termofpaper.getId(),paper.getId(),1.0);
                    }
                }
            }
        }
        if(conferences!=null){
            for(String confname:conferences){
                Conference conf = conferencesByName.get(confname);
                HashMap<Integer,Paper> papersOfConf = conf.getExposedPapersById(papersById);
                for(Paper paper:papersOfConf.values()){
                    HashMap<Integer,Term> termsOfPaper = paper.getTermsById(termsById);
                    for(Term term : termsOfPaper.values()){
                        termpaper.addValue(term.getId(),paper.getId(),1.0);
                    }
                }
            }
        }
        return termpaper;
    }

    private Matrix getConfPaperMatrixD(ArrayList<String> authors, ArrayList<String> papers, ArrayList<String> terms, ArrayList<String> conferences){
        Matrix conferencepaper = new Matrix();

        if(authors == null && papers == null && terms==null && conferences==null){
            for(Conference conf : conferencesById.values()){
                HashMap<Integer,Paper> papersOfConf = conf.getExposedPapersById(papersById);
                for(Paper paper : papersOfConf.values()){
                    conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
                }
            }
        }
        if(authors!=null){
            for(String authorname : authors){
                Author author = authorsByName.get(authorname);
                HashMap<Integer,Paper> papersOfAuthor = author.getPapersById(papersById);
                for(Paper paper : papersOfAuthor.values()){
                    Conference conf = paper.getConference();
                    conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
                }
            }
        }
        if(papers!=null){
            for(String papername:papers){
                Paper paper = papersByName.get(papername);
                Conference conf = paper.getConference();
                conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
            }
        }
        if(terms!=null){
            for(String termname : terms){
                Term term = termsByName.get(termname);
                HashMap<Integer, Paper> papersOfTerm = term.getPapersWhichTalkAboutThisById(papersById);
                for(Paper paper:papersOfTerm.values()){
                    Conference conf = paper.getConference();
                    conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
                }
            }
        }
        if(conferences!=null){
            for(String confname:conferences){
                Conference conf = conferencesByName.get(confname);
                HashMap<Integer,Paper> papersOfConf = conf.getExposedPapersById(papersById);
                for(Paper paper:papersOfConf.values()){
                    conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
                }
            }
        }

        return conferencepaper;
    }


    public void updateMatrix(ArrayList<String> authors, ArrayList<String> papers, ArrayList<String> confs, ArrayList<String> terms){
        authorpaper = getAuthorPaperMatrixD(authors,papers,terms,confs);
        confpaper = getConfPaperMatrixD(authors,papers,terms,confs);
        termpaper = getTermPaperMatrixD(authors,papers,terms,confs);
        hetesimController = new DomainHetesimController(authorpaper,authorpaper.transpose(),termpaper,termpaper.transpose(),confpaper,confpaper.transpose());
        edit = false;
    }

}

