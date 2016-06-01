package main.java.ownClasses.presentation;

import main.java.ownClasses.domain.domainControllers.DomainMainController;
import main.java.sharedClasses.domain.nodes.Author;
import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;
import main.java.sharedClasses.domain.nodes.Term;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


public class GraphViewController{
    private Graph graph;

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
        System.out.println(path);
        graph = new SingleGraph(path);
        
        graph.setAutoCreate(true);
        graph.setStrict(false);

        graph.addNode(name+path);

        genGraph(d, name, path);
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.display();
    }

    private void genGraph(DomainMainController d,String name, String path){
        System.out.println(name);
        System.out.println(path);
        Node n = graph.getNode(name+path);
        n.addAttribute("ui.class", Character.toString(path.charAt(0)));
        n.addAttribute("ui.label", name);
        if(path.length() > 1){
            switch (path.charAt(0)) {
                case 'A':
                    Author a = d.getAuthorsByName().get(name);
                    for(Integer i : a.getAuthorRelations()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName()+path, name+path, paper.getName()+path.substring(1));
                        genGraph(d, paper.getName(), path.substring(1));
                    }
                    break;
                case 'P':
                    Paper p = d.getPapersByName().get(name);
                    switch (path.charAt(1)){
                        case 'A':
                            for(Integer i : p.getRelatedAuthors()){
                                Author auth = d.getAuthorsById().get(i);
                                graph.addEdge(name+auth.getName()+path, name+path, auth.getName()+path.substring(1));
                                genGraph(d, auth.getName(), path.substring(1));
                            }
                            break;
                        case 'C':
                            Conference c = p.getConference();
                            graph.addEdge(name+c.getName()+path, name+path, c.getName()+path.substring(1));
                            genGraph(d, c.getName(), path.substring(1));
                            break;
                        case 'T':
                            for(Integer i : p.getRelatedTerms()){
                                Term t = d.getTermsById().get(i);
                                graph.addEdge(name+t.getName()+path, name+path, t.getName()+path.substring(1));
                                genGraph(d, t.getName(), path.substring(1));
                            }
                            break;
                    }
                    break;
                case 'C':
                    Conference c = d.getConferencesByName().get(name);
                    for(Integer i : c.getExposedPapers()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName()+path, name+path, paper.getName()+path.substring(1));
                        genGraph(d, paper.getName(), path.substring(1));
                    }
                    break;
                case 'T':
                    Term t = d.getTermsByName().get(name);
                    for(Integer i : t.getPapersWhichTalkAboutThis()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName()+path, name+path, paper.getName()+path.substring(1));
                        genGraph(d, paper.getName(), path.substring(1));
                    }
                    break;
            }
        }
    }

}
