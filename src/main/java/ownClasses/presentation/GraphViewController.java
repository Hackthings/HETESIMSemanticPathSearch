package main.java.ownClasses.presentation;

import main.java.ownClasses.domain.domainControllers.DomainMainController;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class GraphViewController extends JFrame{
    private Graph graph;

    private final String styleSheet =
        "node {" +
        "	fill-color: black;" +
        "}" +
        "node.author {" +
        "	fill-color: red;" +
        "}" +
        "node.paper {" +
        "	fill-color: yellow;" +
        "}" +
        "node.conference {" +
        "	fill-color: green;" +
        "}" +
        "node.term {" +
        "	fill-color: blue;" +
        "}";

    public GraphViewController(DomainMainController d, String name, String path){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        graph = new SingleGraph(path);

        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("BC", "B", "C");
        graph.addAttribute("ui.stylesheet", styleSheet);


        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        panel.add((Component) view);
    }

    private void genGraph(DomainMainController d, String path){

    }
}
