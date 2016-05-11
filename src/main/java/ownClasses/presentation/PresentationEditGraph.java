package main.java.ownClasses.presentation;

import main.java.sharedClasses.domain.domainControllers.DomainPersistanceController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by iansangines on 10/05/2016.
 */
public class PresentationEditGraph extends JFrame {
    private JButton editNodeButton;
    private JButton deleteNodeButton;
    private JButton addNodeButton;
    private JPanel buttons;
    private JList editList;
    private JList addList;
    private JList deleteList;
    private JPanel panel;

    public DomainPersistanceController persistanceController;

    public PresentationEditGraph (DomainPersistanceController persistanceController){
        super("EDIT GRAPH");

        this.persistanceController = persistanceController;
        setContentPane(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        editNodeButton.addActionListener(e->editNodeAction("edit"));
        deleteNodeButton.addActionListener(e->editNodeAction("delete"));
        addNodeButton.addActionListener(e->editNodeAction("add"));
        setVisible(true);
    }

    private void editNodeAction(String action){
        System.out.println("EditNodeAction");
        if(action.equals("edit")) {
            editList.setVisible(true);
            editList.addListSelectionListener(listListener);
        }
        else if(action.equals("delete")) {
            deleteList.setVisible(true);
            deleteList.addListSelectionListener(listListener);
        }
        else if(action.equals("add")) {
            addList.setVisible(true);
            addList.addListSelectionListener(listListener);
        }
    }

    ListSelectionListener listListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() == editList) {
                String selected = editList.getSelectedValue().toString();
                //persistanceController.editNode(selected);
            } else if (e.getSource() == deleteList) {

            } else if (e.getSource() == addList) {

            }
        }
    };
}
