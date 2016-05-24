package main.java.ownClasses.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class PresentationResult extends JFrame {
    private JTextArea textArea1;
    private JPanel panel;

    public PresentationResult(ArrayList<String> resultat, String path,String name) {
        super("RESULTAT");

        $$$setupUI$$$();

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        for (int i = 0; i < resultat.size(); i++)
            textArea1.append("\n" + (i + 1) + ". " + resultat.get(i));

        setVisible(true);
    }

    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("NOM  -> RELEVANCE");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(label1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane1, gbc);
        textArea1 = new JTextArea(21,40);
        scrollPane1.setViewportView(textArea1);
    }

    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
