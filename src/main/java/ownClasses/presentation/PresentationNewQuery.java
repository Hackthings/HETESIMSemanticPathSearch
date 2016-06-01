package main.java.ownClasses.presentation;

import main.java.ownClasses.domain.domainControllers.DomainMainController;
import main.java.sharedClasses.domain.domainControllers.DomainPersistanceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Nicola on 10/05/2016.
 */
public class PresentationNewQuery extends JFrame {
    private JPanel panel;
    private JButton aButton;
    private JButton cButton;
    private JButton pButton;
    private JButton tButton;
    private JButton NEXTButton;
    private JTextArea TextArea1;
    private JButton DeleteButton;
    private JRadioButton SiFiltres;
    private JRadioButton NoFiltres;

    /*private JTextArea Asubset;
    private JScrollPane scrollPaneA;
    private JTextArea Csubset;
    private JScrollPane scrollPaneC;
    private JTextArea Psubset;
    private JScrollPane scrollPaneP;
    private JTextArea Tsubset;
    private JScrollPane scrollPaneT;*/

    private BrowseFileOnlyImportController Asubset;
    private BrowseFileOnlyImportController Csubset;
    private BrowseFileOnlyImportController Psubset;
    private BrowseFileOnlyImportController Tsubset;

    private JLabel A;
    private JLabel P;
    private JLabel T;
    private JLabel C;

    String path = "";
    boolean subset = false;
    boolean changed = false;

    String firstlast = null;

    ArrayList<String> authors;
    ArrayList<String> papers;
    ArrayList<String> terms;
    ArrayList<String> conferences;

    ArrayList<String> authorsclone;
    ArrayList<String> papersclone;
    ArrayList<String> termsclone;
    ArrayList<String> conferencesclone;

    DomainPersistanceController persistanceController;


    public PresentationNewQuery(DomainMainController mainController,DomainPersistanceController persistanceControllerpar) {
        super("NEW QUERY");

        persistanceController = persistanceControllerpar;

        $$$setupUI$$$();


        mainController.updateMatrix(null,null,null,null);

        authors = new ArrayList<>();
        papers = new ArrayList<>();
        terms = new ArrayList<>();
        conferences = new ArrayList<>();
        authorsclone = new ArrayList<>();
        papersclone = new ArrayList<>();
        termsclone = new ArrayList<>();
        conferencesclone = new ArrayList<>();

        ButtonGroup group1 = new ButtonGroup();
        group1.add(SiFiltres);
        group1.add(NoFiltres);

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        aButton.addActionListener(e1 -> concatpath("A"));
        cButton.addActionListener(e1 -> concatpath("C"));
        pButton.addActionListener(e1 -> concatpath("P"));
        tButton.addActionListener(e1 -> concatpath("T"));

        DeleteButton.addActionListener(e1 -> deletelast());

        SiFiltres.addActionListener(e1 -> ActivaFiltres(true));
        NoFiltres.addActionListener(e1 -> ActivaFiltres(false));


        NEXTButton.addActionListener(e -> callNQ(mainController));

        setVisible(true);

        setResizable(false);
    }


    private void concatpath(String Node) {
        if ("".equals(path)) path = Node;
        else {
            char last = path.charAt(path.length() - 1);
            if (last == 'P' && "P".equals(Node)) {
                callWarning("Sintaxi de paths incorrecte");
            } else {
                if (last != 'P' && !"P".equals(Node)) path = path.concat("P" + Node);
                else path = path.concat(Node);
            }
        }
        System.out.println(path);
        TextArea1.setText(path);
    }

    private void deletelast() {
        if (!path.isEmpty()) {
            path = path.substring(0, path.length() - 1);
            System.out.println(path);
            TextArea1.setText(path);
        }
    }

    private void ActivaFiltres(boolean act){
        A.setVisible(act);
        P.setVisible(act);
        C.setVisible(act);
        T.setVisible(act);

        Asubset.setVisible(act);
        Psubset.setVisible(act);
        Tsubset.setVisible(act);
        Csubset.setVisible(act);

        subset = act;
        if(act) setSize(300,340);
        else{
            setSize(274,185);
            firstlast = null;
        }
    }


    private void callNQ(DomainMainController mainController) {
        if (path.length()>1) {
            if(subset){
                authors = Asubset.getnodes();
                if(authors != null && !authors.isEmpty())
                {
                    checkArrayList(mainController,authors,'A');
                    authorsclone = (ArrayList<String>) authors.clone();
                }
                else authors = null;
                papers = Psubset.getnodes();
                if(papers!=null && !papers.isEmpty())
                { checkArrayList(mainController,papers,'P');
                    papersclone = (ArrayList<String>) papers.clone();
                }
                else{
                    papers = null;
                }
                terms = Tsubset.getnodes();
                if(terms !=null && !terms.isEmpty()){
                    checkArrayList(mainController,terms,'T');
                    termsclone = (ArrayList<String>) terms.clone();
                }
                else  terms = null;
                conferences = Csubset.getnodes();
                if(conferences != null && !conferences.isEmpty()) {
                    checkArrayList(mainController, conferences, 'C');
                    conferencesclone = (ArrayList<String>) conferences.clone();
                }
                else conferences = null;
                //checkTexts(mainController);

                mainController.updateMatrix(authors,papers,conferences,terms);

                changed = true;
            }
            else if(changed){
                mainController.updateMatrix(null,null,null,null);
                changed = false;
            }
            long timeini = System.currentTimeMillis();
            mainController.NQ(path);
            long timefinal = System.currentTimeMillis();
            System.out.println("Triga :"+(timefinal-timeini)+" milisegons en fer el path "+path);

            System.out.println(authorsclone.size()+" "+papersclone.size()+" "+conferencesclone.size()+" "+termsclone.size());

            PresentationNewQuery2 window = new PresentationNewQuery2(mainController, path,firstlast,authorsclone,papersclone,conferencesclone,termsclone,subset);
            System.out.println("DONE");

            if(authors != null && !authors.isEmpty()) Asubset.clearnodes();
            if(papers!=null && !papers.isEmpty()) Psubset.clearnodes();
            if(conferences != null && !conferences.isEmpty()) Csubset.clearnodes();
            if(terms !=null && !terms.isEmpty()) Tsubset.clearnodes();

        } else {
            callWarning("Path no valid");
        }
    }

    private void callWarning(String cause){
        VistaWARNING vw = new VistaWARNING();
        vw.setVisible(cause);
    }

    private void checkTexts(DomainMainController mainController){
       /*int count = 0;
        for(int i =1; i<path.length() && count<3;++i){
            if(path.charAt(0)!=path.charAt(i)) {
                switch (path.charAt(i)) {
                    case ('A'):
                        if(mainController.checkName(Asubset.getText(),'A')){
                            author = Asubset.getText(); firstlast = null;
                        }
                        count++;
                        break;
                    case ('P'):
                        if(mainController.checkName(Psubset.getText(),'P')){
                            paper = Psubset.getText(); firstlast = null;
                        }
                        count++;
                        break;
                    case ('C'):
                        if(mainController.checkName(Csubset.getText(),'C')){
                            conf = Csubset.getText(); firstlast = null;
                        }
                        count++;
                        break;
                    case ('T'):
                        if(mainController.checkName(Tsubset.getText(),'T')){
                            term = Tsubset.getText(); firstlast = null;
                        }
                        count++;
                        break;
                }
            }
        }
        if(path.charAt(0)==path.charAt(path.length()-1)){
            switch (path.charAt(0)) {
                case ('A'):
                    if(mainController.checkName(Asubset.getText(),'A')){
                        firstlast = Asubset.getText();
                    }
                    break;
                case ('P'):
                    if(mainController.checkName(Psubset.getText(),'P')){
                        firstlast = Psubset.getText();
                    }
                    break;
                case ('C'):
                    if(mainController.checkName(Csubset.getText(),'C')){
                        firstlast = Csubset.getText();
                    }
                    break;
                case ('T'):
                    if(mainController.checkName(Tsubset.getText(),'T')){
                        firstlast = Tsubset.getText();
                    }
                    break;
            }
        }*/
    }

    private void checkArrayList(DomainMainController mainController, ArrayList<String> nodes, char tipus){
        boolean malament = false;
        for(int i = 0; i < nodes.size(); ++i){
            if(!mainController.checkName(nodes.get(i),tipus)){
                nodes.remove(i);
                i = i-1;
                malament = true;
            }
        }

        if(malament) callWarning("Error al filtre: nom no trobat al tipus"+tipus);

    }




    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(""));
        //Dimension d = new Dimension(6,20);
        //panel.setMinimumSize(d);
        GridBagConstraints gbc;
        final JLabel label2 = new JLabel();
        label2.setText("Selecciona el teu PATH");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label2, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("(només amb els botons)");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label4, gbc);
        cButton = new JButton();
        cButton.setText("C");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        panel.add(cButton, gbc);
        tButton = new JButton();
        tButton.setText("T");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        panel.add(tButton, gbc);
        pButton = new JButton();
        pButton.setText("P");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        panel.add(pButton, gbc);
        NEXTButton = new JButton();
        NEXTButton.setText("NEXT");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(NEXTButton, gbc);
        /*simpleRadioButton.setLabel("Simple");
        simpleRadioButton.setText("Simple");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(simpleRadioButton, gbc);
        ambFiltresRadioButton.setText("Amb Filtres");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(ambFiltresRadioButton, gbc);*/
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane1, gbc);
        TextArea1 = new JTextArea();
        scrollPane1.setViewportView(TextArea1);
        aButton = new JButton();
        aButton.setText("A");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        panel.add(aButton, gbc);
        DeleteButton = new JButton();
        DeleteButton.setText("DELETE");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(DeleteButton, gbc);
        NoFiltres = new JRadioButton();
        NoFiltres.setText("NO");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(NoFiltres, gbc);
        SiFiltres = new JRadioButton();
        SiFiltres.setText("SI");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(SiFiltres, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Filtre SubSetQuery?");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label3, gbc);


        ///////////////////////////////Labels Subset

        A = new JLabel();
        A.setText("A:");
        A.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(A, gbc);
        P = new JLabel();
        P.setText("P:");
        P.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(P, gbc);
        C = new JLabel();
        C.setText("C:");
        C.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(C, gbc);
        T = new JLabel();
        T.setText("T:");
        T.setVisible(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(T, gbc);

        /////////////////////////////////// Imports


        Asubset = new BrowseFileOnlyImportController(persistanceController);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        Asubset.setVisible(false);
        panel.add(Asubset, gbc);


        Psubset = new BrowseFileOnlyImportController(persistanceController);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        Psubset.setVisible(false);
        panel.add(Psubset, gbc);

        Csubset = new BrowseFileOnlyImportController(persistanceController);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        Csubset.setVisible(false);
        panel.add(Csubset, gbc);

        Tsubset = new BrowseFileOnlyImportController(persistanceController);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        Tsubset.setVisible(false);
        panel.add(Tsubset, gbc);



    }

    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
