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

    boolean ascendent = true;
    String name = "";
    int n=0;
    double max=0.0;
    double min=0.0;

    int querytype = 1;

    public PresentationNewQuery2(DomainMainController mainController, String path){
        super("NEW QUERY2");

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

        SimpleButton.addActionListener(e1 -> setType(1));
        LimitedButton.addActionListener(e2 -> setType(2));
        IntervaledButton.addActionListener(e2 -> setType(3));

        nField.addActionListener(e1 -> setN());
        petitField.addActionListener(e1 -> setMin());
        granField.addActionListener(e1 -> setMax());

        nameField.addActionListener(e -> setname());

        setVisible(true);

    }

    private void setascendent(boolean set){
        ascendent = set;
        System.out.println("set ascendent to "+ascendent);
    }

    private void setname(){
        name = nameField.getText();
    }

    private void setType(int type){
        querytype = type;
        System.out.println("set type to "+type);
    }

    private void setN(){
        n = Integer.parseInt(nField.getText());
    }

    private void setMin(){
        min = Double.parseDouble(petitField.getText());
    }

    private void setMax(){
        max = Double.parseDouble(granField.getText());
    }

}