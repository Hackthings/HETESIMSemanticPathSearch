package main.java.ownClasses.presentation;

import main.java.ownClasses.domain.domainControllers.DomainMainController;

import javax.swing.*;

/**
 * Created by Nicola on 15/05/2016.
 */
public class PresentationNewQuery2 extends JFrame {
    private JPanel panel1;
    private JRadioButton ascendentButton;
    private JRadioButton descendentButton;
    private JRadioButton SimpleButton;
    private JRadioButton LimitedButton;
    private JRadioButton IntervaledButton;
    private JTextField nField;
    private JTextField petitField;
    private JTextField granField;
    private JTextField nameField;

    private boolean ascendent;
    private String name;
    private int n;
    private double max;
    private double min;

    private int querytype = 1;

    public PresentationNewQuery2(DomainMainController mainController, String path){
        super("NEW QUERY");

        ButtonGroup group1 = new ButtonGroup();
        group1.add(ascendentButton);
        group1.add(descendentButton);
        ButtonGroup group2 = new ButtonGroup();
        group2.add(SimpleButton);
        group2.add(LimitedButton);
        group2.add(IntervaledButton);

        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ascendentButton.addActionListener(e -> setascendent(true));
        descendentButton.addActionListener(e -> setascendent(false));



    }

    private void setascendent(boolean set){
        ascendent = set;
    }




}