package ownClasses.presentation;

import ownClasses.domain.domainControllers.DomainMainController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by iansangines on 21/06/2016.
 */
public class PresentationEditNodes extends JFrame {
    private JTextField nomField;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton afegeixRelacioButton;
    private JButton eliminaRelacioButton;
    private JButton cancelaButton;
    private JLabel nom1;
    private JLabel nom2;
    private JLabel nom3;
    private JPanel panel1;
    private DomainMainController domainMainController;

    public PresentationEditNodes(DomainMainController domainMainController, int selectedIndex) {
        super("EDIT NODE");
        $$$setupUI$$$();
        setContentPane(panel1);
        pack();

        this.domainMainController = domainMainController;
        cancelaButton.addActionListener(e -> super.dispose());

        switch (selectedIndex) {
            case (0):
                nom1.setText("Nom Article:");
                textField3.setVisible(false);
                nom2.setVisible(false);
                nom3.setVisible(false);
                textField4.setVisible(false);
                afegeixRelacioButton.addActionListener(e -> addAuthorRelation());
                eliminaRelacioButton.addActionListener(e -> deleteAuthorRelation());
                break;
            case (1):

                break;
            case (2):
                nom1.setText("Nom Article:");
                textField3.setVisible(false);
                nom2.setVisible(false);
                nom3.setVisible(false);
                textField4.setVisible(false);
                afegeixRelacioButton.addActionListener(e -> addTermTelation());
                break;
            case (3):
                nom1.setText("Nom Article:");
                textField3.setVisible(false);
                nom2.setVisible(false);
                nom3.setVisible(false);
                textField4.setVisible(false);
                afegeixRelacioButton.addActionListener(e -> changePaperConference());
                break;
        }

        setVisible(true);
    }

    public void addAuthorRelation() {
        String AuthorName = nomField.getText();
        if (AuthorName == null || AuthorName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Autor");
            return;
        }
        String paperName = textField2.getText();
        if (paperName == null || paperName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Article");
            return;
        }

        String res = domainMainController.getPersistanceController().newRelationPaperAuthor(paperName, AuthorName, domainMainController.getPapersByName(),
                domainMainController.getAuthorsByName());
        if (res.equals("NA")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquest Autor no existex");
            return;
        }
        if (res.equals("NP")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquest Article no existex");
            return;
        } else {
            JOptionPane.showMessageDialog(panel1, "S'ha afegit la relació");
            super.dispose();
        }
    }

    public void addTermTelation() {
        String termName = nomField.getText();
        if (termName == null || termName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Terme");
            return;
        }
        String paperName = textField2.getText();
        if (paperName == null || paperName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Article");
            return;
        }
        String res = domainMainController.getPersistanceController().newRelationPaperTerm(paperName, termName, domainMainController.getPapersByName(),
                domainMainController.getTermsByName());
        if (res.equals("TA")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquest Terme no existex");
            return;
        }
        if (res.equals("NP")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquest Article no existex");
            return;
        } else {
            JOptionPane.showMessageDialog(panel1, "S'ha afegit la relació");
            super.dispose();
        }
    }

    public void changePaperConference() {
        Object[] options = {"Acceptar", "Cancelar"};
        int op = JOptionPane.showOptionDialog(panel1, "Si afageixes aquesta relació eliminaras la relacio amb l'anterior conferècia", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (op == JOptionPane.YES_OPTION) {
            String confName = nomField.getText();
            if (confName == null || confName.equals("")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Introdueix una Conferencia");
                return;
            }
            String paperName = textField2.getText();
            if (paperName == null || paperName.equals("")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Introdueix un Article");
                return;
            }
            String res = domainMainController.getPersistanceController().newRelationPaperConference(paperName, confName, domainMainController.getPapersByName(),
                    domainMainController.getConferencesByName());
            if (res.equals("NC")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Aquest Autor no existex");
                return;
            }
            if (res.equals("NP")) {
                VistaWARNING vw = new VistaWARNING();
                vw.setVisible("Aquest Article no existex");
                return;
            } else {
                JOptionPane.showMessageDialog(panel1, "S'ha canviar la conferència");
            }
        }
        super.dispose();

    }

    public void deleteAuthorRelation(){
        String AuthorName = nomField.getText();
        if (AuthorName == null || AuthorName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Autor");
            return;
        }
        String paperName = textField2.getText();
        if (paperName == null || paperName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Article");
            return;
        }

        String res = domainMainController.getPersistanceController().deleteAuthorRelation(paperName,AuthorName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                domainMainController.getTermsById(),domainMainController.getTermsByName());

        validate(res);
    }

    public void deleteTermRelation(){
        String termName = nomField.getText();
        if (termName == null || termName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Terme");
            return;
        }
        String paperName = textField2.getText();
        if (paperName == null || paperName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Article");
            return;
        }

        String res = domainMainController.getPersistanceController().deleteTermRelation(paperName,termName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                domainMainController.getTermsById(),domainMainController.getTermsByName());

        validate(res);
    }


    public void deleteConfereceRelation(){
        String confName = nomField.getText();
        if (confName == null || confName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix una Conferència");
            return;
        }
        String paperName = textField2.getText();
        if (paperName == null || paperName.equals("")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Introdueix un Article");
            return;
        }

        String res = domainMainController.getPersistanceController().deleteConferenceRelation(paperName,confName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                domainMainController.getTermsById(),domainMainController.getTermsByName());

        validate(res);
    }

    private void validate(String res) {
        if(res == null){
            JOptionPane.showMessageDialog(panel1, "S'ha eliminat la relació");
            super.dispose();
            return;
        }
        else if (res.equals("NP")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquest Article no existex");
            return;
        }else if(res.equals("NR")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("No existeix la Relació");
            return;
        }
        else if (res.equals("NA")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquest Autor no existex");
            return;
        }
        else if (res.equals("NT")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquest Terme no existex");
            return;
        }
        else if (res.equals("NC")) {
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Aquesta Conferència no existex");
            return;
        }
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
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Nom:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipady = 30;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(label1, gbc);
        nomField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 300;
        gbc.ipady = 6;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel1.add(nomField, gbc);
        nom1 = new JLabel();
        nom1.setText("Nom Autor:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipady = 30;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(nom1, gbc);
        textField2 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 6;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel1.add(textField2, gbc);
        nom2 = new JLabel();
        nom2.setText("Nom Terme:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipady = 30;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(nom2, gbc);
        textField3 = new JTextField();
        textField3.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 6;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel1.add(textField3, gbc);
        nom3 = new JLabel();
        nom3.setText("Nom Conferència:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipady = 30;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(nom3, gbc);
        textField4 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 6;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel1.add(textField4, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Edit Node");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 100, 0, 0);
        panel1.add(label2, gbc);
        eliminaRelacioButton = new JButton();
        eliminaRelacioButton.setText("Elimina Relacio");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 20);
        panel1.add(eliminaRelacioButton, gbc);
        afegeixRelacioButton = new JButton();
        afegeixRelacioButton.setText("Afegeix Relacio");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 20, 20);
        panel1.add(afegeixRelacioButton, gbc);
        cancelaButton = new JButton();
        cancelaButton.setText("Cancela");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 20, 20);
        panel1.add(cancelaButton, gbc);
    }

}