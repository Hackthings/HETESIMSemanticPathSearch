package main.java.ownClasses.presentation;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Nicola on 16/05/2016.
 */
public class PresentationResult extends JFrame {
    private JTextArea textArea1;
    private JPanel panel;

    public PresentationResult(ArrayList<String> resultat){
        super("RESULTAT");

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        for (int i = 0; i < resultat.size(); i++)
            textArea1.append("\n" + resultat.get(i));

        setVisible(true);
    }
}
