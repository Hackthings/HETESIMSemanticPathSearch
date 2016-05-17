package main.java.ownClasses.presentation;

import main.java.ownClasses.domain.domainControllers.DomainMainController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by andreurd on 03/05/16.
 */
public class PresentationController extends JFrame {
    private JButton newQueryButton;
    private JButton editGraphButton;
    private JPanel panel;

    public PresentationController(DomainMainController mainController) {
        super("HETESIM SEMANTIC PATH SEARCH");

        panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        final JLabel label1 = new JLabel();
        label1.setText("Hetesim Semantic Path Search");
        newQueryButton = new JButton();
        newQueryButton.setText("New Query");
        editGraphButton = new JButton();
        editGraphButton.setText("Edit Graph");

        panel.add(label1);
        panel.add(newQueryButton);
        panel.add(editGraphButton);

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newQueryButton.addActionListener(e -> new PresentationNewQuery(mainController));
        //newQueryButton.addActionListener(e->mainController.newQuery());
        editGraphButton.addActionListener(e -> mainController.editGraph());
        setVisible(true);
    }
}
