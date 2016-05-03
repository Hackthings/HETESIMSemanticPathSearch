package main.java.ownClasses.presentation;

import main.java.ownClasses.domain.domainControllers.DomainMainController;

import javax.swing.*;

/**
 * Created by andreurd on 03/05/16.
 */
public class PresentationController extends JFrame {
    private JButton newQueryButton;
    private JButton editGraphButton;
    private JPanel panel;

    public PresentationController(DomainMainController mainController) {
        super("HETESIM SEMANTIC PATH SEARCH");

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newQueryButton.addActionListener(e->mainController.newQuery());
        editGraphButton.addActionListener(e->mainController.editGraph());
        setVisible(true);
    }
}
