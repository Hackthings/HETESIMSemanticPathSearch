package main.java.ownClasses.domain.domainControllers;

import main.java.ownClasses.domain.queries.Query;
import main.java.sharedClasses.DomainControllers.DomainPersistanceController;
import main.java.sharedClasses.domain.nodes.Author;
import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;
import main.java.sharedClasses.domain.nodes.Term;
import main.java.sharedClasses.utils.Matrix;

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

        System.out.println("Selecciona el tipus que vols buscar:\n 1 Author\n 2 Paper\n 3 Conference\n 4 Term");
        String queryNode = scan.nextLine();

        int valid = 0;
        while(valid == 0){
            System.out.println("Introdueix el nom:");
            String queryName = scan.nextLine();
            switch(queryNode){
                case("1"):
                    if(authorsByName.containsKey(queryName)){
                        System.out.println("OK");
                        valid = 1;
                    }
                    break;
                case("2"):
                    if(papersByName.containsKey(queryName)){
                        System.out.println("OK");
                        valid = 1;
                    }
                    break;
                case("3"):
                    if(conferencesByName.containsKey(queryName)){
                        System.out.println("OK");
                        valid = 1;
                    }
                    break;
                case("4"):
                    if(termsByName.containsKey(queryName)){
                        System.out.println("OK");
                        valid = 1;
                    }
                    break;
            }
            if(valid==0){ System.err.println("nom no valid");}
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


        if(queryType.equals("2")) {
            System.out.println("Escogeix el tipus de filtre: 1 Intervals de rellevancia\n 2 Nombre maxim d'entrades\n 3 Ordre\n 4 Restriccio per element");


        }
        else{

        }



    }



    public void editGraph() {
        DomainPersistanceController domainPersistanceController = new DomainPersistanceController();
        domainPersistanceController.newEdit(authorsById, papersById, conferencesById, termsById, authorsByName, papersByName, conferencesByName,termsByName, authorMaxId, paperMaxId, termMaxId, conferenceMaxId);
    }



    public Matrix getAuthorPaperMatrix(){
        Matrix authorpaper = new Matrix();

        for(Author author : authorsById.values()){
            HashMap<Integer,Paper> papersOfAuthor = author.getPapersById();
            for(Paper paper : papersOfAuthor.values()){
                authorpaper.addValue(author.getId(),paper.getId(),1.0);
            }
        }
        return authorpaper;
    }

    public Matrix getPaperAuthorMatrix(){
        Matrix paperauthor = new Matrix();

        for(Paper paper : papersById.values()){
            HashMap<Integer,Author> authorsOfPaper = paper.getAuthorsById();
            for(Author author : authorsOfPaper.values()){
                paperauthor.addValue(paper.getId(),author.getId(),1.0);
            }
        }
        return paperauthor;
    }

    public Matrix getTermPaperMatrix(){
        Matrix termpaper = new Matrix();

        for(Term term : termsById.values()){
            HashMap<Integer,Paper> papersOfTerm = term.getPapersWhichTalkAboutThisById();
            for(Paper paper : papersOfTerm.values()){
                termpaper.addValue(term.getId(),paper.getId(),1.0);
            }
        }

        return termpaper;
    }

    public Matrix getPaperTermMatrix() {
        Matrix paperterm = new Matrix();

        for (Paper paper : papersById.values()) {
            HashMap<Integer, Term> termsOfPaper = paper.getTermsById();
            for (Term term : termsOfPaper.values()) {
                paperterm.addValue(paper.getId(), term.getId(), 1.0);
            }
        }
        return paperterm;
    }

    public Matrix getConferencePaperMatrix(){
        Matrix conferencepaper = new Matrix();

        for(Conference conf : conferencesById.values()){
            HashMap<Integer,Paper> papersOfConf = conf.getExposedPapersById();
            for(Paper paper : papersOfConf.values()){
                conferencepaper.addValue(conf.getId(),paper.getId(),1.0);
            }
        }

        return conferencepaper;
    }

    public Matrix getPaperConferenceMatrix(){
        Matrix paperconference = new Matrix();

        for(Paper paper : papersById.values()){
            paperconference.addValue(paper.getId(),paper.getConference().getId(),1.0);
        }

        return paperconference;
    }

}

