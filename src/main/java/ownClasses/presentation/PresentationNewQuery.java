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
    private JButton DeleteButton;


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

        DeleteButton.addActionListener(e1 -> deletelast());


        NEXTButton.addActionListener(e -> callNQ(mainController));

        setVisible(true);
    }

    private void selectTypeAction(int type){
        queryType = type;
        System.out.println("selected type "+type);
    }

    private void concatpath(String Node){
        if("".equals(path)) path = Node;
        else{
            char last = path.charAt(path.length()-1);
            if(last=='P'&& "P".equals(Node)){
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Sintaxi de paths incorrecte");
            }
            else {
                if (last != 'P' && !"P".equals(Node)) path = path.concat("P" + Node);
                else path = path.concat(Node);
            }
        }
        System.out.println(path);
        TextArea1.setText(path);
    }

    private void deletelast(){
        if(!path.isEmpty()){
            path = path.substring(0,path.length()-1);
            System.out.println(path);
            TextArea1.setText(path);
        }
    }

    private void callNQ(DomainMainController mainController){
        if(!path.isEmpty()){
            mainController.NQ(path);
        }
        else{
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("PATH buit");
        }
    }

    private void createUIComponents(){
        simpleRadioButton = new JRadioButton("Simple");
        ambFiltresRadioButton = new JRadioButton("Amb Filtres");
    }
}
