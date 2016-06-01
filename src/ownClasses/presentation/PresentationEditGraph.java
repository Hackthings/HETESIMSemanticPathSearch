package ownClasses.presentation;

import ownClasses.domain.domainControllers.DomainPersistanceController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
        if(editList.getSelectedIndex() < 0){
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Selecciona l'element a editar");
        }
        else new PresentationEditNode(persistanceController,editList.getSelectedIndex());
    }

    private void addNodeAction() {
        System.out.println("AddNodeAction");
        if(editList.getSelectedIndex() < 0){
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Selecciona l'element a afegir");
        }
         else new PresentationAddNodes(persistanceController,editList.getSelectedIndex(),null,null,null,null);
    }

    private void deleteNodeAction() {
        System.out.println("DeleteNodeAction");
        if(editList.getSelectedIndex() < 0){
            VistaWARNING vw = new VistaWARNING();
            vw.setVisible("Selecciona l'element a eliminar");
        }
        else new PresentationdeleteNode(persistanceController,editList.getSelectedIndex());
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
        defaultListModel1.add(0,"Author");
        defaultListModel1.add(1,"Paper");
        defaultListModel1.add(2,"Term");
        defaultListModel1.add(3,"Conference");
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
