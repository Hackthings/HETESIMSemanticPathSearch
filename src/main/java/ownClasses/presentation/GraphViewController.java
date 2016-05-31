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
        graph = new SingleGraph(path);

        graph.setAutoCreate(true);
        graph.setStrict(false);
        //graph.addEdge("AB", "A", "B");
        //graph.addEdge("AC", "A", "C");
        //graph.addEdge("BC", "B", "C");
        //Node a = graph.getNode("A");
        //a.setAttribute("ui.class", "author");

        graph.addNode(name);
        /*
        Node n = graph.getNode(name);
        n.addAttribute("ui.class", path.charAt(0));
        n.addAttribute("ui.label", name);*/
        genGraph(d, name, path);

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.display();
    }

    private void genGraph(DomainMainController d,String name, String path){
        Node n = graph.getNode(name);
        n.addAttribute("ui.class", path.charAt(0));
        n.addAttribute("ui.label", name);
        if(name.length() > 1){
            switch (path.charAt(0)) {
                case 'A':
                    Author a = d.getAuthorsByName().get(name);
                    for(Integer i : a.getAuthorRelations()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName(), name, paper.getName());
                        genGraph(d, paper.getName(), path.substring(1));
                    }
                case 'P':
                    Paper p = d.getPapersByName().get(name);
                    switch (path.charAt(1)){
                        case 'A':
                            for(Integer i : p.getRelatedAuthors()){
                                Author auth = d.getAuthorsById().get(i);
                                graph.addEdge(name+auth.getName(), name, auth.getName());
                                genGraph(d, auth.getName(), path.substring(1));
                            }
                        case 'C':
                            Conference c = p.getConference();
                            graph.addEdge(name+c.getName(), name, c.getName());
                            genGraph(d, c.getName(), path.substring(1));
                        case 'T':
                            for(Integer i : p.getRelatedTerms()){
                                Term t = d.getTermsById().get(i);
                                graph.addEdge(name+t.getName(), name, t.getName());
                                genGraph(d, t.getName(), path.substring(1));
                            }
                    }
                case 'C':
                    Conference c = d.getConferencesByName().get(name);
                    for(Integer i : c.getExposedPapers()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName(), name, paper.getName());
                        genGraph(d, paper.getName(), path.substring(1));
                    }
                case 'T':
                    Term t = d.getTermsByName().get(name);
                    for(Integer i : t.getPapersWhichTalkAboutThis()){
                        Paper paper = d.getPapersById().get(i);
                        graph.addEdge(name+paper.getName(), name, paper.getName());
                        genGraph(d, paper.getName(), path.substring(1));
                    }
            }
        }
    }

}
