package main.java.ownClasses.presentation;

import main.java.ownClasses.domain.domainControllers.DomainMainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nicola on 10/05/2016.
 */
public class PresentationNewQuery extends JFrame{
    private JPanel panel;
    private JButton aButton;
    private JButton cButton;
    private JButton pButton;
    private JButton tButton;
    private JButton NEXTButton;
    private JRadioButton simpleRadioButton;
    private JRadioButton ambFiltresRadioButton;
    private JTextArea TextArea1;


    int queryType = 0;
    String path="";

    public PresentationNewQuery(DomainMainController mainController){
        super("NEW QUERY");

        ButtonGroup group = new ButtonGroup();
        group.add(simpleRadioButton);
        group.add(ambFiltresRadioButton);

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        simpleRadioButton.addActionListener(e1 -> selectTypeAction(-1));
        ambFiltresRadioButton.addActionListener(e1 -> selectTypeAction(-2));

        aButton.addActionListener(e1 -> concatpath("A"));
        cButton.addActionListener(e1 -> concatpath("C"));
        pButton.addActionListener(e1 -> concatpath("P"));
        tButton.addActionListener(e1 -> concatpath("T"));


        NEXTButton.addActionListener(e -> callNQ());

        setVisible(true);
    }

    private void selectTypeAction(int type){
        queryType = type;
        System.out.println("selected type "+type);
    }

    private void concatpath(String Node){
        if("".equals(path)) path = Node;
        else path = path.concat(Node);
        System.out.println(path);
        TextArea1.setText(path);
    }

    private void callNQ(){

    }

    private void createUIComponents(){
        simpleRadioButton = new JRadioButton("Simple");
        ambFiltresRadioButton = new JRadioButton("Amb Filtres");
    }
}
