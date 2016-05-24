package main.java.ownClasses.presentation;

import main.java.sharedClasses.domain.domainControllers.DomainPersistanceController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by iansangines on 10/05/2016.
 */
public class PresentationEditGraph extends JFrame {
    private JButton editNodeButton;
    private JButton deleteNodeButton;
    private JButton addNodeButton;
    private JPanel buttons;
    private JList editList;
    private JPanel panel;

    public DomainPersistanceController persistanceController;

    public PresentationEditGraph(DomainPersistanceController persistanceController) {
        super("EDIT GRAPH");

        this.persistanceController = persistanceController;
        buttons = new JPanel();
        $$$setupUI$$$();
        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        editNodeButton.addActionListener(e -> editNodeAction());
        deleteNodeButton.addActionListener(e -> deleteNodeAction());
        addNodeButton.addActionListener(e -> addNodeAction());
        setVisible(true);
    }

    private void editNodeAction() {
        System.out.println("EditNodeAction");
        // TODO: IMPLEMENT EDIT
    }

    private void addNodeAction() {
        System.out.println("DeleteNodeAction");
        // TODO: IMPLEMENT ADD
    }

    private void deleteNodeAction() {
        System.out.println("DeleteNodeAction");
        // TODO: IMPLEMENT DELETE
    }

    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("Edit Graph");
        label1.setHorizontalAlignment(JLabel.CENTER);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        panel.setLayout(new GridLayout(3,1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(label1);
        buttons.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 20.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(buttons, gbc);
        editNodeButton = new JButton();
        editNodeButton.setText("Edit Node");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttons.add(editNodeButton, gbc);
        deleteNodeButton = new JButton();
        deleteNodeButton.setText("Delete Node");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttons.add(deleteNodeButton, gbc);
        addNodeButton = new JButton();
        addNodeButton.setText("Add Node");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        buttons.add(addNodeButton, gbc);
        editList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("Author");
        defaultListModel1.addElement("Paper");
        defaultListModel1.addElement("Term");
        defaultListModel1.addElement("Conference");
        editList.setModel(defaultListModel1);
        editList.setVisible(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 1;
        gbc.weightx = 3.0;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(editList, gbc);
    }

    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
