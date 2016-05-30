package main.java.ownClasses.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class PresentationResult extends JFrame {
    private JTextArea textArea1;
    private JPanel panel;

    public PresentationResult(ArrayList<String> resultat, String path,String name, String firstlast) {
        super("RESULTAT");

        $$$setupUI$$$();

        setContentPane(panel);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String nodefinal = "";
        switch (path.charAt(path.length()-1)){
            case ('A'):
                nodefinal = "Authors";
                break;
            case ('P'):
                nodefinal = "Papers";
                break;
            case ('C'):
                nodefinal = "Conferences";
                break;
            case ('T'):
                nodefinal = "Terms";
                break;

        }

        textArea1.append(nodefinal+" relacionats amb "+ name +" amb el path "+ path +":");

        int count =0;
        for (int i = 0; i < resultat.size(); i++) {
            String namedone[] = resultat.get(i).split("  ->  ");
            if(!namedone[0].equals(name)) {
                if (firstlast == null) {
                    textArea1.append("\n" + (i + 1 - count) + ". " + resultat.get(i));
                }
                else{
                    if(namedone[0].equals(firstlast))
                        textArea1.append("\n" + (i + 1 - count) + ". " + resultat.get(i));
                }
            }
            else{
                count+=1;
            }
        }



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
