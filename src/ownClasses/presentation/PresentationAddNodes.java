package ownClasses.presentation;

import ownClasses.domain.domainControllers.DomainMainController;
import ownClasses.domain.domainControllers.DomainPersistanceController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by iansangines on 29/05/2016.
 */
public class PresentationAddNodes extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTextField aName1;
    private JComboBox comboBox2;
    private JTextField aName2;
    private JTextField aName3;
    private JTextField aName4;
    private JTextField aName5;
    private JTextField tName1;
    private JTextField cName;
    private JTextField tName2;
    private JTextField tName3;
    private JTextField tName4;
    private JTextField tName5;
    private JLabel Num1;
    private JLabel Nom;
    private JLabel Conferencia;
    private JLabel Num2;
    private JButton acceptarButton;
    private JButton cancelarButton;
    private JLabel addNode;
    private ArrayList<JTextField> firstTextFields;
    private ArrayList<JTextField> secondTextFields;
    private DomainMainController domainMainController;
    private String authoraux, termaux, confaux, paperaux;
    private String authorToAdd, termToAdd, confToAdd, paperToAdd;


    public PresentationAddNodes(DomainMainController domainMainController, int selecedIndex, String authoraux, String paperaux, String termaux, String confaux) {
        super("ADD NODE");
        $$$setupUI$$$();
        setContentPane(panel1);
        pack();

        this.authoraux = authoraux;
        this.termaux = termaux;
        this.confaux = confaux;
        this.paperaux = paperaux;

        this.authorToAdd = this.termToAdd = this.confToAdd = this.paperToAdd = null;

        this.domainMainController = domainMainController;
        firstTextFields = new ArrayList<>();
        firstTextFields.add(aName1);
        firstTextFields.add(aName2);
        firstTextFields.add(aName3);
        firstTextFields.add(aName4);
        firstTextFields.add(aName5);


        secondTextFields = new ArrayList<>();
        secondTextFields.add(tName1);
        secondTextFields.add(tName2);
        secondTextFields.add(tName3);
        secondTextFields.add(tName4);
        secondTextFields.add(tName5);


        for (int i = 1; i < firstTextFields.size(); i++) firstTextFields.get(i).setEnabled(false);

        for (int i = 1; i < secondTextFields.size(); i++) secondTextFields.get(i).setEnabled(false);


        comboBox1.addActionListener(e -> enableFields(firstTextFields, comboBox1));
        comboBox2.addActionListener(e -> enableFields(secondTextFields, comboBox2));
        cancelarButton.addActionListener(e->super.dispose());

        switch (selecedIndex) {
            case (0): { //AUTHOR
                addNode.setText("Afegir Autor");
                Num1.setText("Número d'Articles:");
                Num2.setVisible(false);
                comboBox2.setVisible(false);
                for (int i = 0; i < secondTextFields.size(); i++) secondTextFields.get(i).setVisible(false);
                Conferencia.setVisible(false);
                cName.setVisible(false);
                acceptarButton.addActionListener(e1 -> callAddAuthor());

                break;
            }
            case (1): { //PAPER
                addNode.setText("Afegir Article");
                Num1.setText("Número d'Autors");
                Num2.setText("Número de Termes");
                if (authoraux != null) {
                    textField1.setText(paperaux);
                    textField1.setEnabled(false);
                    comboBox1.setEnabled(false);
                    firstTextFields.get(0).setText(authoraux);
                    firstTextFields.get(0).setEnabled(false);
                    for (int i = 1; i < firstTextFields.size(); i++) {
                        firstTextFields.get(i).setVisible(false);
                    }
                } else if (termaux != null) {
                    textField1.setText(paperaux);
                    textField1.setEnabled(false);
                    comboBox2.setEnabled(false);
                    secondTextFields.get(0).setText(termaux);
                    secondTextFields.get(0).setEnabled(false);
                    for (int i = 1; i < firstTextFields.size(); i++) {
                        secondTextFields.get(i).setVisible(false);
                    }
                } else if (confaux != null) {
                    textField1.setText(paperaux);
                    textField1.setEnabled(false);
                    comboBox1.setEnabled(false);
                    cName.setText(confaux);
                    cName.setEnabled(false);
                }
                acceptarButton.addActionListener(e2 -> callAddPaper());
                break;
            }
            case (2): { //TERM
                addNode.setText("Afegir Terme");
                Num1.setText("Número d'Articles:");
                Num2.setVisible(false);
                comboBox2.setVisible(false);
                for (int i = 0; i < secondTextFields.size(); i++) secondTextFields.get(i).setVisible(false);
                Conferencia.setVisible(false);
                cName.setVisible(false);
                acceptarButton.addActionListener(e1 -> callAddTerm());
                break;
            }
            case (3): { //CONFERENCES
                addNode.setText("Afegir Conferència");
                Num1.setText("Número d'Articles:");
                Num2.setVisible(false);
                comboBox2.setVisible(false);
                for (int i = 0; i < secondTextFields.size(); i++) secondTextFields.get(i).setVisible(false);
                Conferencia.setVisible(false);
                cName.setVisible(false);
                acceptarButton.addActionListener(e1 -> callAddConference());
                break;
            }
        }

        setVisible(true);
    }

    private void enableFields(ArrayList<JTextField> a, JComboBox b) {
        int numF = Integer.parseInt((String) b.getSelectedItem());
        for (int i = 0; i < numF; i++) a.get(i).setEnabled(true);
        for(int i = numF; i < a.size(); i++) a.get(i).setEnabled(false);
    }

    private void callAddAuthor() {
        ArrayList<String> papersToAdd = new ArrayList<>();
        String authorName;
        if(paperaux == null )authorName = textField1.getText();
        else authorName = authorToAdd;
        if (authorName == null || authorName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un nom per l'Autor");
            return;
        }
        if (paperaux == null) {
            for (int i = 0; i < firstTextFields.size(); i++) {
                if (firstTextFields.get(i).isEnabled()) {
                    String pName = firstTextFields.get(i).getText();
                    if (pName == null || pName.equals("")) {
                        VistaWARNING vw = new VistaWARNING();
                        vw.setVisible("Introdueix un nom per l'Article");
                        return;
                    } else {
                        papersToAdd.add(pName);
                    }
                }
            }
        } else {
            papersToAdd.add(paperaux);
        }
        ArrayList<String> newPapers = domainMainController.getPersistanceController().addNewAuthor(authorName, papersToAdd,domainMainController.getAuthorsById(),
                domainMainController.getAuthorsByName(), domainMainController.getPapersById(),domainMainController.getPapersByName());
        if (newPapers != null) {
            if (newPapers.size() == 0) {
                //SHA CREAT TO BIEN
                return;
            } else if (newPapers.get(0).equals("Ja Existeix")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Aquest autor ja existeix");
                return;
            } else if(newPapers.size() > 0){
                Object[] options = {"Acceptar", "Cancelar"};
                int op = JOptionPane.showOptionDialog(panel1, "Hi ha Articles que no existeixen, els vols crear?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (op == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < newPapers.size(); i++) {
                        new PresentationAddNodes(domainMainController, 1, authorName, newPapers.get(i), null, null);
                    }
                } else {
                    domainMainController.getPersistanceController().deleteAuthor(authorName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                            domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                            domainMainController.getTermsById(),domainMainController.getTermsByName());
                   JOptionPane.showMessageDialog(panel1, "No s'ha pogut afegir l'Autor");
                    super.dispose();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(panel1, "S'ha afegit el nou Autor");
        super.dispose();
    }


    private void callAddPaper() {
        ArrayList<String> authorToAdd = new ArrayList<>();
        ArrayList<String> termsToAdd = new ArrayList<>();
        String confToAdd;
        String paperName = textField1.getText();
        if (paperName == null || paperName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un nom per l'Article");
            return;
        }
        if (authoraux == null) {
            for (int i = 0; i < firstTextFields.size(); i++) {
                if (firstTextFields.get(i).isEnabled()) {
                    String aName = firstTextFields.get(i).getText();
                    if (aName == null || aName.equals("")) {
                        VistaWARNING vw = new VistaWARNING();
                        vw.setVisible("Introdueix un nom per l'Autor");
                        return;
                    } else {
                        authorToAdd.add(aName);
                    }
                }
            }
        } else {
            authorToAdd.add(authoraux);
        }

        if (termaux == null) {
            for (int i = 0; i < secondTextFields.size(); i++) {
                if (secondTextFields.get(i).isEnabled()) {
                    String tName = secondTextFields.get(i).getText();
                    if (tName == null || tName.equals("")) {
                        VistaWARNING vw = new VistaWARNING();
                        vw.setVisible("Introdueix un nom per el Terme");
                        return;
                    } else {
                        termsToAdd.add(tName);
                    }
                }
            }
        } else {
            termsToAdd.add(termaux);
        }

        if(confaux == null) {
            confToAdd = cName.getText();
            if (confToAdd == null || confToAdd.equals("")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Introdueix un nom per la Conferència");
                return;
            }
        }
        else{
            confToAdd = confaux;
        }

        HashMap<String, ArrayList<String>> newNodes = domainMainController.getPersistanceController().addNewPaper(paperName, authorToAdd, termsToAdd, confToAdd,
                domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getAuthorsByName(),domainMainController.getConferencesByName(),
                domainMainController.getTermsByName());
        if (newNodes.get("Fail") != null) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("L'article ja existeix");
        } else {
            paperaux = paperName;
            Object[] options = {"Acceptar", "Cancelar"};
            ArrayList<String> newAuthors = newNodes.get("A");
            if (newAuthors != null) {
                if (newAuthors.size() > 0) {
                    int op = JOptionPane.showOptionDialog(panel1, "Hi ha Autors que no existeixen, els vols crear?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (op == JOptionPane.YES_OPTION) {
                        for (int i = 0; i < newAuthors.size(); i++) {
                            this.authorToAdd = newAuthors.get(i);
                            callAddAuthor();
                        }
                    }
                    else{
                        domainMainController.getPersistanceController().deletePaper(paperName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                                domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                                domainMainController.getTermsById(),domainMainController.getTermsByName());
                        JOptionPane.showMessageDialog(panel1, "No s'ha pogut afegir l'Article");
                        super.dispose();
                        return;
                    }
                }
            }

            ArrayList<String> newTerms = newNodes.get("T");
            if (newTerms != null) {
                if (newTerms.size() > 0) {
                    int op = JOptionPane.showOptionDialog(panel1, "Hi ha Termes que no existeixen, els vols crear?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (op == JOptionPane.YES_OPTION) {
                        for (int i = 0; i < newTerms.size(); i++) {
                            this.termToAdd = newTerms.get(i);
                            callAddTerm();
                        }
                    }
                    else{
                        domainMainController.getPersistanceController().deletePaper(paperName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                                domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                                domainMainController.getTermsById(),domainMainController.getTermsByName());
                        JOptionPane.showMessageDialog(panel1, "No s'ha pogut afegir l'Article");
                        super.dispose();
                        return;
                    }
                }
            }

            ArrayList<String> newConference = newNodes.get("C");
            if (newConference != null) {
                if (newConference.size() > 0) {
                    int op = JOptionPane.showOptionDialog(panel1, "La conferència no existeix, la vols crear?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (op == JOptionPane.YES_OPTION) {
                        for (int i = 0; i < newConference.size(); i++) {
                            this.confToAdd = newConference.get(i);
                            callAddConference();
                        }
                    }
                    else{
                        domainMainController.getPersistanceController().deletePaper(paperName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                                domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                                domainMainController.getTermsById(),domainMainController.getTermsByName());
                        JOptionPane.showMessageDialog(panel1, "No s'ha pogut afegir l'Article");
                        super.dispose();
                        return;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(panel1, "S'ha afegit el nou Article");
        super.dispose();
    }

    private void callAddTerm() {
        ArrayList<String> papersToAdd = new ArrayList<>();
        String termName;
        if(paperaux == null) termName = textField1.getText();
        else termName = this.termToAdd;
        if (termName == null || termName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un nom per el Terme");
            return;
        }
        if (paperaux == null) {
            for (int i = 0; i < firstTextFields.size(); i++) {
                if (firstTextFields.get(i).isEnabled()) {
                    String pName = firstTextFields.get(i).getText();
                    if (pName == null || pName.equals("")) {
                        VistaWARNING vw = new VistaWARNING();
                        vw.setVisible("Introdueix un nom per l'Article");
                        return;
                    } else {
                        papersToAdd.add(pName);
                    }
                }
            }
        } else {
            papersToAdd.add(paperaux);
        }
        ArrayList<String> newPapers = domainMainController.getPersistanceController().addNewTerm(termName, papersToAdd,domainMainController.getTermsById(),
                domainMainController.getTermsByName(),domainMainController.getPapersByName());
        if (newPapers != null) {
            if (newPapers.size() == 0) {
                //SHA CREAT TO BIEN
                return;
            } else if (newPapers.get(0).equals("Ja Existeix")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Aquest terme ja existeix");
                return;
            } else {
                Object[] options = {"Acceptar", "Cancelar"};
                int op = JOptionPane.showOptionDialog(panel1, "Hi ha Articles que no existeixen, els vols crear?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (op == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < newPapers.size(); i++) {
                        new PresentationAddNodes(domainMainController, 1, null, newPapers.get(i), termName, null);
                    }
                } else {
                    domainMainController.getPersistanceController().deleteTerm(termName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                            domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                            domainMainController.getTermsById(),domainMainController.getTermsByName());
                    JOptionPane.showMessageDialog(panel1, "No s'ha pogut afegir el Terme");
                    super.dispose();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(panel1, "S'ha afegit el nou Terme");
        super.dispose();
    }

    private void callAddConference() {
        ArrayList<String> papersToAdd = new ArrayList<>();
        String confName;
        if(paperaux == null) confName = textField1.getText();
        else {
            confName = confToAdd;
        }
        if (confName == null || confName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un nom per la Conferencia");
            return;
        }
        if (paperaux == null) {
            for (int i = 0; i < firstTextFields.size(); i++) {
                if (firstTextFields.get(i).isEnabled()) {
                    String pName = firstTextFields.get(i).getText();
                    if (pName == null || pName.equals("")) {
                        VistaWARNING vw = new VistaWARNING();
                        vw.setVisible("Introdueix un nom per l'Article");
                        return;
                    } else {
                        papersToAdd.add(pName);
                    }
                }
            }
        } else {
            papersToAdd.add(paperaux);
        }
        ArrayList<String> newPapers = domainMainController.getPersistanceController().addNewConference(confName, papersToAdd,domainMainController.getConferencesById(),
                domainMainController.getConferencesByName(),domainMainController.getPapersByName());
        if (newPapers != null) {
            if (newPapers.size() == 0) {
                //SHA CREAT TO BIEN
                return;
            } else if (newPapers.get(0).equals("Ja Existeix")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Aquesta conferència ja existeix");
                return;
            } else {
                Object[] options = {"Acceptar", "Cancelar"};
                int op = JOptionPane.showOptionDialog(panel1, "Hi ha Articles que no existeixen, els vols crear?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (op == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < newPapers.size(); i++) {
                        new PresentationAddNodes(domainMainController, 1, null, newPapers.get(i), null, confName);
                    }
                } else {
                    domainMainController.getPersistanceController().deleteConference(confName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                            domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                            domainMainController.getTermsById(),domainMainController.getTermsByName());
                    JOptionPane.showMessageDialog(panel1, "No s'ha pogut afegir la conferencia");
                    super.dispose();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(panel1, "S'ha afegit la nova conferència");
        super.dispose();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        Nom = new JLabel();
        Nom.setText("Nom");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 20;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(Nom, gbc);
        Num1 = new JLabel();
        Num1.setText("Num 1:");
        Num1.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 20;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(Num1, gbc);
        textField1 = new JTextField();
        textField1.setMaximumSize(new Dimension(14, 24));
        textField1.setPreferredSize(new Dimension(14, 28));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        gbc.insets = new Insets(0, 20, 0, 20);
        panel1.add(textField1, gbc);
        Num2 = new JLabel();
        Num2.setText("Num 2:");
        Num2.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(Num2, gbc);
        String[] numbers = {"1", "2", "3", "4", "5"};
        comboBox2 = new JComboBox(numbers);
        // final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        // comboBox2.setModel(defaultComboBoxModel1);
        comboBox2.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(comboBox2, gbc);
        Conferencia = new JLabel();
        Conferencia.setText("Conferencia");
        Conferencia.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 22;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 20;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(Conferencia, gbc);
        cName = new JTextField();
        cName.setPreferredSize(new Dimension(14, 28));
        cName.setText("");
        cName.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 22;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 10);
        panel1.add(cName, gbc);
        aName1 = new JTextField();
        aName1.setMinimumSize(new Dimension(14, 28));
        aName1.setPreferredSize(new Dimension(14, 28));
        aName1.setText("");
        aName1.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 150;
        gbc.insets = new Insets(0, 10, 0, 20);
        panel1.add(aName1, gbc);
        aName2 = new JTextField();
        aName2.setMinimumSize(new Dimension(14, 28));
        aName2.setPreferredSize(new Dimension(14, 28));
        aName2.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 150;
        gbc.insets = new Insets(0, 10, 0, 10);
        panel1.add(aName2, gbc);
        aName3 = new JTextField();
        aName3.setMinimumSize(new Dimension(14, 28));
        aName3.setPreferredSize(new Dimension(14, 28));
        aName3.setText("");
        aName3.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 150;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(aName3, gbc);
        aName4 = new JTextField();
        aName4.setMinimumSize(new Dimension(14, 28));
        aName4.setPreferredSize(new Dimension(14, 28));
        aName4.setText("");
        aName4.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 150;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(aName4, gbc);
        aName5 = new JTextField();
        aName5.setMinimumSize(new Dimension(14, 28));
        aName5.setPreferredSize(new Dimension(14, 28));
        aName5.setText("");
        aName5.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(aName5, gbc);
        tName1 = new JTextField();
        tName1.setMinimumSize(new Dimension(14, 28));
        tName1.setPreferredSize(new Dimension(14, 28));
        tName1.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(tName1, gbc);
        tName2 = new JTextField();
        tName2.setMinimumSize(new Dimension(14, 28));
        tName2.setPreferredSize(new Dimension(14, 28));
        tName2.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(tName2, gbc);
        tName3 = new JTextField();
        tName3.setMinimumSize(new Dimension(14, 28));
        tName3.setPreferredSize(new Dimension(14, 28));
        tName3.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(tName3, gbc);
        tName4 = new JTextField();
        tName4.setMinimumSize(new Dimension(14, 28));
        tName4.setPreferredSize(new Dimension(14, 28));
        tName4.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 10;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(tName4, gbc);
        tName5 = new JTextField();
        tName5.setMinimumSize(new Dimension(14, 28));
        tName5.setPreferredSize(new Dimension(14, 28));
        tName5.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 11;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 10, 3, 10);
        panel1.add(tName5, gbc);
        addNode = new JLabel();
        addNode.setText("Add Node");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.ipadx = 100;
        gbc.ipady = 30;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(addNode, gbc);
        comboBox1 = new JComboBox(numbers);
        comboBox1.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(comboBox1, gbc);
        cancelarButton = new JButton();
        cancelarButton.setText("Cancelar");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 23;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(cancelarButton, gbc);
        acceptarButton = new JButton();
        acceptarButton.setHorizontalAlignment(0);
        acceptarButton.setText("Acceptar");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 23;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.ipadx = 30;
        gbc.insets = new Insets(0, 0, 10, 200);
        panel1.add(acceptarButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
