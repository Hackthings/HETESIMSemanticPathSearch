package ownClasses.presentation;

import ownClasses.domain.domainControllers.DomainMainController;
import ownClasses.domain.domainControllers.DomainPersistanceController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by iansangines on 24/06/2016.
 */
public class PresentationdeleteNode extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JButton acceptarButton;
    private JButton cancelarButton;
    private JLabel Nom;
    private JLabel DeleteNode;
    private DomainMainController domainMainController;


    public PresentationdeleteNode(DomainMainController domainMainController, int selectedIndex) {
        super("DELETE NODE");
        $$$setupUI$$$();
        setContentPane(panel1);
        pack();

        this.domainMainController = domainMainController;

        cancelarButton.addActionListener(e->super.dispose());

        switch (selectedIndex) {
            case (0):
                acceptarButton.addActionListener(e -> deleteAuthor());
                break;
            case (1):
                acceptarButton.addActionListener(e -> deletePaper());
                break;
            case (2):
                acceptarButton.addActionListener(e -> deleteTerm());
                break;
            case (3):
                acceptarButton.addActionListener(e -> deleteConference());
                break;
        }
        setVisible(true);
    }

    private void deleteAuthor() {
        String authorName = textField1.getText();
        if (authorName == null || authorName.equals("")) {
            VistaWARNING vm = new VistaWARNING();
            vm.setVisible("Introdueix un Autor a eliminar");
            return;
        } else {
            boolean deleted = domainMainController.getPersistanceController().deleteAuthor(authorName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                    domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                    domainMainController.getTermsById(),domainMainController.getTermsByName());
            if (!deleted) {
                VistaWARNING vm = new VistaWARNING();
                vm.setVisible("L'autor no existeix");
            } else {
                JOptionPane.showMessageDialog(panel1, "S'ha eliminat l'Autor");
                super.dispose();
            }
        }

    }

    private void deletePaper() {
        String paperName = textField1.getText();
        if (paperName == null || paperName.equals("")) {
            VistaWARNING vm = new VistaWARNING();
            vm.setVisible("Introdueix un Article a eliminar");
            return;
        } else {
            boolean deleted = domainMainController.getPersistanceController().deletePaper(paperName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                    domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                    domainMainController.getTermsById(),domainMainController.getTermsByName());
            if (!deleted) {
                VistaWARNING vm = new VistaWARNING();
                vm.setVisible("L'Article no existeix");
            } else {
                JOptionPane.showMessageDialog(panel1, "S'ha eliminat l'Article");
                super.dispose();
            }
        }

    }

    private void deleteTerm() {
        String termName = textField1.getText();
        if (termName == null || termName.equals("")) {
            VistaWARNING vm = new VistaWARNING();
            vm.setVisible("Introdueix un Terme a eliminar");
            return;
        } else {
            boolean deleted = domainMainController.getPersistanceController().deleteTerm(termName,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                    domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                    domainMainController.getTermsById(),domainMainController.getTermsByName());
            if (!deleted) {
                VistaWARNING vm = new VistaWARNING();
                vm.setVisible("El Terme no existeix");
            } else {
                JOptionPane.showMessageDialog(panel1, "S'ha eliminat el Terme");
                super.dispose();
            }
        }

    }

    private void deleteConference() {
        String conference = textField1.getText();
        if (conference == null || conference.equals("")) {
            VistaWARNING vm = new VistaWARNING();
            vm.setVisible("Introdueix un Article a eliminar");
            return;
        } else {
            boolean deleted = domainMainController.getPersistanceController().deleteConference(conference,domainMainController.getAuthorsById(),domainMainController.getAuthorsByName(),
                    domainMainController.getPapersById(),domainMainController.getPapersByName(),domainMainController.getConferencesById(),domainMainController.getConferencesByName(),
                    domainMainController.getTermsById(),domainMainController.getTermsByName());
            if (!deleted) {
                VistaWARNING vm = new VistaWARNING();
                vm.setVisible("La conferencia no existeix");
            } else {
                JOptionPane.showMessageDialog(panel1, "S'ha eliminat la Conferencia");
                super.dispose();
            }

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
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        Nom = new JLabel();
        Nom.setText("Nom:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipady = 150;
        gbc.insets = new Insets(0, 20, 0, 0);
        panel1.add(Nom, gbc);
        textField1 = new JTextField();
        textField1.setMargin(new Insets(3, 10, 3, 40));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 350;
        gbc.ipady = 5;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel1.add(textField1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer2, gbc);
        DeleteNode = new JLabel();
        DeleteNode.setText("Delete Node");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 100, 0, 0);
        panel1.add(DeleteNode, gbc);
        cancelarButton = new JButton();
        cancelarButton.setText("Cancelar");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 6;
        gbc.insets = new Insets(0, 0, 20, 10);
        panel1.add(cancelarButton, gbc);
        acceptarButton = new JButton();
        acceptarButton.setMaximumSize(new Dimension(66, 32));
        acceptarButton.setMinimumSize(new Dimension(66, 32));
        acceptarButton.setOpaque(false);
        acceptarButton.setPreferredSize(new Dimension(66, 32));
        acceptarButton.setText("Acceptar");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 250, 20, 10);
        panel1.add(acceptarButton, gbc);
    }
}
