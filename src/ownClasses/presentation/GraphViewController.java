package ownClasses.presentation;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import ownClasses.domain.domainControllers.DomainMainController;
import ownClasses.domain.domainControllers.Drivers.Author_driver;
import sharedClasses.domain.nodes.Author;
import sharedClasses.domain.nodes.Conference;
import sharedClasses.domain.nodes.Paper;
import sharedClasses.domain.nodes.Term;
import org.graphstream.graph.Graph;

import java.util.ArrayList;


public class GraphViewController{
    private Graph graph;

    //variables for subset graphs
    private ArrayList<String> sAu;
    private ArrayList<String> sPa;
    private ArrayList<String> sCo;
    private ArrayList<String> sTe;

    private final String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "}" +
                    "node.A {" +
                    "	fill-color: red;" +
                    "}" +
                    "node.P {" +
                    "	fill-color: yellow;" +
                    "}" +
                    "node.C {" +
                    "	fill-color: green;" +
                    "}" +
                    "node.T {" +
                    "	fill-color: blue;" +
                    "}";

    public GraphViewController(DomainMainController d, String name, String path){
        System.out.println("Generating graph for: " + path);
        graph = new SingleGraph(path);

        graph.setAutoCreate(true);
        graph.setStrict(false);

        graph.addNode(name+path);

        genGraph(d, name, path, false);
        graph.addAttribute("ui.stylesheet", styleSheet);

        Viewer v = graph.display();
        v.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }

    public GraphViewController(DomainMainController d, String name, String path, ArrayList<String> sAu, ArrayList<String> sPa, ArrayList<String> sCo, ArrayList<String> sTe){
        if(sAu == null) this.sAu = new ArrayList<String>();
        else this.sAu = sAu;
        if(sPa == null) this.sPa = new ArrayList<String>();
        else this.sPa = sPa;
        if(sCo == null) this.sCo = new ArrayList<String>();
        else this.sCo = sCo;
        if(sTe == null) this.sTe = new ArrayList<String>();
        else this.sTe = sTe;
        System.out.println("Authors: " + this.sAu.toString());
        System.out.println("Papers: " + this.sPa.toString());
        System.out.println("Conferences: " + this.sCo.toString());
        System.out.println("Terms: " + this.sTe.toString());


        System.out.println("Generating graph for: " + path + " (with subset)");

        graph = new SingleGraph(path+"subset");

        graph.setAutoCreate(true);
        graph.setStrict(false);

        graph.addNode(name+path);

        genGraph(d, name, path, true);
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.display();
    }

    private void genGraph(DomainMainController d, String name, String path, boolean subset){
        Node n = graph.getNode(name+path);
        n.addAttribute("ui.class", Character.toString(path.charAt(0)));
        n.addAttribute("ui.label", name);

        if(subset && path.charAt(0) == 'P'){
            boolean elim = true;

            if (sPa.size() > 0) {
                elim = true;
                for (String nm : sPa) {
                    if (nm.equals(name)) {
                        elim = false;
                        break;
                    }
                }
            }
            if (sAu.size() > 0 && elim) {
                out_loop:
                for (String nm : sAu) {
                    for(Integer aId : d.getPapersByName().get(name).getRelatedAuthors()){
                        if(nm.equals(d.getAuthorsById().get(aId).getName())){
                            elim = false;
                            break out_loop;
                        }
                    }
                }
            }
            if (sCo.size() > 0 && elim) {
                for (String nm : sCo) {
                    Conference c = d.getPapersByName().get(name).getConference();
                    if(nm.equals(c.getName())){
                        elim = false;
                        break;
                    }
                }
            }
            if (sTe.size() > 0 && elim) {
                out_loop:
                for (String nm : sTe) {
                    for(Integer tId : d.getPapersByName().get(name).getRelatedTerms()){
                        if(nm.equals(d.getTermsById().get(tId).getName())){
                            elim = false;
                            break out_loop;
                        }
                    }
                }
            }


            if(elim){
                graph.removeNode(n);
                //System.out.println(n.toString());
                return;
            }
        }

        if(path.length() > 1){
            switch (path.charAt(0)) {
                case 'A':
                    Author a = d.getAuthorsByName().get(name);
                    for(Integer i : a.getAuthorRelations()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName()+path, name+path, paper.getName()+path.substring(1));
                        genGraph(d, paper.getName(), path.substring(1), subset);
                    }
                    break;
                case 'P':
                    Paper p = d.getPapersByName().get(name);
                    switch (path.charAt(1)){
                        case 'A':
                            for(Integer i : p.getRelatedAuthors()){
                                Author auth = d.getAuthorsById().get(i);
                                graph.addEdge(name+auth.getName()+path, name+path, auth.getName()+path.substring(1));
                                genGraph(d, auth.getName(), path.substring(1), subset);
                            }
                            break;
                        case 'C':
                            Conference c = p.getConference();
                            graph.addEdge(name+c.getName()+path, name+path, c.getName()+path.substring(1));
                            genGraph(d, c.getName(), path.substring(1), subset);
                            break;
                        case 'T':
                            for(Integer i : p.getRelatedTerms()){
                                Term t = d.getTermsById().get(i);
                                graph.addEdge(name+t.getName()+path, name+path, t.getName()+path.substring(1));
                                genGraph(d, t.getName(), path.substring(1), subset);
                            }
                            break;
                    }
                    break;
                case 'C':
                    Conference c = d.getConferencesByName().get(name);
                    for(Integer i : c.getExposedPapers()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName()+path, name+path, paper.getName()+path.substring(1));
                        genGraph(d, paper.getName(), path.substring(1), subset);
                    }
                    break;
                case 'T':
                    Term t = d.getTermsByName().get(name);
                    for(Integer i : t.getPapersWhichTalkAboutThis()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName()+path, name+path, paper.getName()+path.substring(1));
                        genGraph(d, paper.getName(), path.substring(1), subset);
                    }
                    break;
            }
        }
    }

}
