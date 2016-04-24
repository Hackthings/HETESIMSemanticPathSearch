package main.java.ownClasses.domain.domainControllers;

import main.java.ownClasses.domain.queries.IntervaledQuery;
import main.java.ownClasses.domain.queries.LimitedQuery;
import main.java.ownClasses.domain.queries.OrderedQuery;
import main.java.ownClasses.domain.queries.Query;
import main.java.sharedClasses.domain.domainControllers.DomainPersistanceController;
import main.java.sharedClasses.domain.nodes.Author;
import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;
import main.java.sharedClasses.domain.nodes.Term;
import main.java.sharedClasses.utils.Matrix;
import main.java.sharedClasses.utils.Pair;

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

    private int authorMaxId;
    private int paperMaxId;
    private int conferenceMaxId;
    private int termMaxId;


    public DomainMainController() {
        persistanceController = new DomainPersistanceController();
        authorsById = new HashMap<>();
        papersById = new HashMap<>();
        conferencesById = new HashMap<>();
        termsById = new HashMap<>();
        authorsByName = new HashMap<>();
        papersByName = new HashMap<>();
        papersByName = new HashMap<>();
        conferencesByName = new HashMap<>();
        termsByName = new HashMap<>();
        authorMaxId = 0;
        paperMaxId = 0;
        conferenceMaxId = 0;
        termMaxId = 0;
        persistanceController.readAll(authorsById, papersById, conferencesById, termsById, authorsByName, papersByName, conferencesByName, termsByName, authorMaxId, paperMaxId, termMaxId, conferenceMaxId);
    }

    public void newQuery() {

        hetesimController = new DomainHetesimController(getAuthorPaperMatrix(),getPaperAuthorMatrix(), getTermPaperMatrix(), getPaperTermMatrix(), getConferencePaperMatrix(), getPaperConferenceMatrix());
        Scanner scan = new Scanner(System.in);

        System.out.println("Quin tipus de cerca vols: 1 Simple\n 2 Amb filtres");
        String queryType = scan.nextLine();
        while(!queryType.equals("1") || !queryType.equals("2")){
            System.err.println("tipus no valid, escriu 1 si Simple o 2 si Amb Filtres");
            queryType = scan.nextLine();
        }

        int pathvalid=0;
        System.out.println("Introdueix el path (exemple: APA):");
        String queryPath = scan.nextLine();
        while(pathvalid==0) {
            int surt = 0;
            for(int i=0; i<queryPath.length() && surt==0; ++i){
                char node= queryPath.charAt(i);
                if(node != 'A' && node != 'P' && node!='C' && node!='T') surt=1;
            }
            if(surt==0){ pathvalid=1;}
            else{
                System.err.println("path no valid, torna'l a escriure (exemple APA):");
                queryPath = scan.nextLine();
            }
        }

        Query query = new Query(queryPath);
        Matrix result = hetesimController.heteSim((queryPath));

        int exit = 0;
        char type = queryPath.charAt(0);

        while(exit == 0) {

            int valid = 0;
            Integer queryId = 0;
            while (valid == 0) {
                System.out.println("Introdueix el nom:");
                String queryName = scan.nextLine();
                switch (type) {
                    case ('A'):
                        if (authorsByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = 1;
                            queryId = authorsByName.get(queryName).getId();
                        }
                        break;
                    case ('P'):
                        if (papersByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = 1;
                            queryId = papersByName.get(queryName).getId();
                        }
                        break;
                    case ('C'):
                        if (conferencesByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = 1;
                            queryId = conferencesByName.get(queryName).getId();
                        }
                        break;
                    case ('T'):
                        if (termsByName.containsKey(queryName)) {
                            System.out.println("OK");
                            valid = 1;
                            queryId = termsByName.get(queryName).getId();
                        }
                        break;
                }
                if (valid == 0) {
                    System.err.println("nom no valid");
                }
            }

            HashMap<Integer, Double> resultquery = result.columns(queryId);


            if (queryType.equals("1")) {
                resultWithoutFilters(resultquery, query);
            } else {
                int exitfiltres = 0;
                while (exitfiltres == 0) {
                    System.out.println("Escogeix el tipus de filtre: 1 Intervals de rellevancia\n 2 Nombre maxim d'entrades\n 3 Ordre\n 4 Restriccio per element");
                    String queryfilter = scan.nextLine();
                    switch (queryfilter) {
                        case ("1"):

                            System.out.println("Introdueix el valor mes petit (entre 0 i 1)");
                            double firstrelevance = Double.parseDouble(scan.nextLine());
                            System.out.println("Introdueix el valor mes gran (entre 0 i 1)");
                            double secondrelevance = Double.parseDouble(scan.nextLine());
                            if (firstrelevance > secondrelevance) {
                                System.err.println("firstrelevance > secondrelevance)");
                            } else {
                                IntervaledQuery iq = new IntervaledQuery(query.getPath(), firstrelevance, secondrelevance);
                                resultWithIntervals(resultquery, iq);
                            }

                            break;
                        case ("2"):

                            System.out.println("Introdueix el nombre maxim d'entrades");
                            int nomMax = Integer.parseInt(scan.nextLine());
                            LimitedQuery lq = new LimitedQuery(query.getPath(), nomMax);
                            resultWithMax(resultquery, lq);

                            break;
                        case ("3"):

                            System.out.println("Selecciona l'ordre 1 Ascendent 2 Descendent");
                            int i = Integer.parseInt(scan.nextLine());
                            OrderedQuery oq = new OrderedQuery(query.getPath(), false);
                            if (i > 2 || i <= 0) {
                                System.out.println("Ordre no disponible");
                                break;
                            } else if (i == 1) {
                                oq.setAscendent(true);
                            }

                            resultWithOrder(resultquery,oq);

                            break;
                        case ("4"):
                            System.out.println("no disponible");
                            break;
                    }
                    System.out.println("Vols escollir un altre tipus de filtre? YES or NO");
                    String answer = scan.nextLine();
                    if (answer.equals("NO")) exitfiltres = 1;
                }

            }
            System.out.println("Vols seleccionar un altre nom amb el mateix path? YES or NO");
            String answer = scan.nextLine();
            if (answer.equals("NO")) exit = 1;
        }

    }

    private void printresult(char tipus, Integer id, Double relevance){
        switch (tipus) {
            case ('A'):
                System.out.print(authorsById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('P'):
                System.out.print(papersById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('C'):
                System.out.print(conferencesById.get(id).getName() + "  ->  " + relevance);
                break;
            case ('T'):
                System.out.print(termsById.get(id).getName() + "  ->  " + relevance);
                break;
        }
    }

    private void resultWithOrder(HashMap<Integer, Double> resultquery, OrderedQuery query){
        char tipus = query.getPath().charAt(query.getPath().length()-1);
        System.out.println(" NOM  ->  rellevancia");

        Iterator<Map.Entry<Integer, Double>> it= resultquery.entrySet().iterator();
        ArrayList<Pair<Integer,Double>> resultOrdered = new ArrayList<>();
        while(it.hasNext()) {
            Map.Entry<Integer, Double> resultat = it.next();
            int id = Integer.parseInt(resultat.getKey().toString());
            double relevance = Double.parseDouble(resultat.getValue().toString());
            if(resultOrdered.isEmpty()){
                resultOrdered.add(new Pair<Integer, Double>(id,relevance));
            }
            else{
                boolean end=false;
                if(query.isAscendent()) {
                    for (int i = 0; i < resultOrdered.size() && !end; ++i) {
                        if(resultOrdered.get(i).getSecond()>= relevance){
                            resultOrdered.add(i,new Pair<Integer, Double>(id,relevance));
                            end = true;
                        }
                    }
                }
                else{
                    for(int i = 0; i < resultOrdered.size() && !end; ++i) {
                        if(resultOrdered.get(i).getSecond()<= relevance){
                            resultOrdered.add(i,new Pair<Integer, Double>(id,relevance));
                            end = true;
                        }
                    }
                }
                if(!end) resultOrdered.add(new Pair<Integer, Double>(id,relevance));
            }
        }

        for (Pair<Integer, Double> aResultOrdered : resultOrdered) {
            printresult(tipus, aResultOrdered.getFirst(), aResultOrdered.getSecond());
        }
    }

    private void resultWithMax(HashMap<Integer, Double> resultquery, LimitedQuery query) {
        char tipus = query.getPath().charAt(query.getPath().length()-1);
        System.out.println(" NOM  ->  rellevancia");

        Iterator<Map.Entry<Integer, Double>> it= resultquery.entrySet().iterator();
        while(it.hasNext() && query.getLimit()>0) {
            Map.Entry<Integer, Double> resultat = it.next();

            printresult(tipus,Integer.parseInt(resultat.getKey().toString()),Double.parseDouble(resultat.getValue().toString()));

            query.setLimit(query.getLimit()-1);
        }



    }
    private void resultWithIntervals(HashMap<Integer, Double> resultquery, IntervaledQuery query){
        char tipus = query.getPath().charAt(query.getPath().length()-1);
        System.out.println(" NOM  ->  rellevancia");

        for (Object o : resultquery.entrySet()) {
            Map.Entry resultat = (Map.Entry) o;
            double res = Double.parseDouble(resultat.getValue().toString());
            if (res >= query.getFirstRelevance() && res <= query.getSecondRelevance()) {

                printresult(tipus, Integer.parseInt(resultat.getKey().toString()), Double.parseDouble(resultat.getValue().toString()));

            }
        }

    }

    private void resultWithoutFilters(HashMap<Integer, Double> resultquery, Query query){
        char tipus = query.getPath().charAt(query.getPath().length()-1);
        System.out.println(" NOM  ->  rellevancia");

        for (Object o : resultquery.entrySet()) {
            Map.Entry resultat = (Map.Entry) o;
            printresult(tipus, Integer.parseInt(resultat.getKey().toString()), Double.parseDouble(resultat.getValue().toString()));

        }

    }

    public void editGraph() {
        DomainPersistanceController domainPersistanceController = new DomainPersistanceController();
        domainPersistanceController.newEdit(authorsById, papersById, conferencesById, termsById, authorsByName, papersByName, conferencesByName,termsByName, authorMaxId, paperMaxId, termMaxId, conferenceMaxId);
    }



    private Matrix getAuthorPaperMatrix(){
        Matrix authorpaper = new Matrix();

        for(Author author : authorsById.values()){
            HashMap<Integer,Paper> papersOfAuthor = author.getPapersById();
            for(Paper paper : papersOfAuthor.values()){
                authorpaper.addValue(author.getId(),paper.getId(),1.0);
            }
        }
        return authorpaper;
    }

    private Matrix getPaperAuthorMatrix(){
        Matrix paperauthor = new Matrix();

        for(Paper paper : papersById.values()){
            HashMap<Integer,Author> authorsOfPaper = paper.getAuthorsById();
            for(Author author : authorsOfPaper.values()){
                paperauthor.addValue(paper.getId(),author.getId(),1.0);
            }
        }
        return paperauthor;
    }

    private Matrix getTermPaperMatrix(){
        Matrix termpaper = new Matrix();

        for(Term term : termsById.values()){
            HashMap<Integer,Paper> papersOfTerm = term.getPapersWhichTalkAboutThisById();
            for(Paper paper : papersOfTerm.values()){
                termpaper.addValue(term.getId(),paper.getId(),1.0);
            }
        }

        return termpaper;
    }

    private Matrix getPaperTermMatrix() {
        Matrix paperterm = new Matrix();

        for (Paper paper : papersById.values()) {
            HashMap<Integer, Term> termsOfPaper = paper.getTermsById();
            for (Term term : termsOfPaper.values()) {
                paperterm.addValue(paper.getId(), term.getId(), 1.0);
            }
        }
        return paperterm;
    }

    private Matrix getConferencePaperMatrix(){
        Matrix conferencepaper = new Matrix();

        for(Conference conf : conferencesById.values()){
            HashMap<Integer,Paper> papersOfConf = conf.getExposedPapersById();
            for(Paper paper : papersOfConf.values()){
                conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
            }
        }

        return conferencepaper;
    }

    private Matrix getPaperConferenceMatrix(){
        Matrix paperconference = new Matrix();

        for(Paper paper : papersById.values()){
            paperconference.addValue(paper.getId(),paper.getConference().getId(),1.0);
        }

        return paperconference;
    }

}

