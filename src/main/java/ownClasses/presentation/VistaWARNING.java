package main.java.ownClasses.presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nicola on 15/05/2016.
 */
public class VistaWARNING {

    public VistaWARNING(){

    }

    public void setVisible(String txt){
        String strTitulo = "ATENCIO";
        String[] strBotones = {"Ok"};

        JOptionPane optionPane = new JOptionPane(txt,JOptionPane.WARNING_MESSAGE);
        optionPane.setOptions(strBotones);
        optionPane.setBackground(Color.red);
        JDialog dialogOptionPane = optionPane.createDialog(new JFrame(),strTitulo);
        dialogOptionPane.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogOptionPane.pack();
        dialogOptionPane.setVisible(true);
    }
}
