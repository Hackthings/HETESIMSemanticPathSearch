package ownClasses.presentation;

import ownClasses.domain.domainControllers.DomainPersistanceController;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrowseFileController extends JPanel
        implements ActionListener {
    static private final String newline = "\n";
    JButton importButton, exportButton;
    JTextArea log;
    JFileChooser fc;
    DomainPersistanceController domainPersistanceController;

    public BrowseFileController(DomainPersistanceController persistanceController) {
        super(new BorderLayout());

        domainPersistanceController = persistanceController;
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser
        fc = new JFileChooser();

        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        importButton = new JButton("Import file");
        importButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        exportButton = new JButton("Export File");
        exportButton.addActionListener(this);

        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == importButton) {
            int returnVal = fc.showOpenDialog(BrowseFileController.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Importing: " + file.getName() + "." + newline);
                domainPersistanceController.readAllFromFile(file.getAbsolutePath());
            } else {
                log.append("Import command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

            //Handle save button action.
        } else if (e.getSource() == exportButton) {
            int returnVal = fc.showSaveDialog(BrowseFileController.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                log.append("Exporting: " + file.getName() + "." + newline);
                domainPersistanceController.writeAllToFile(file.getAbsolutePath());
            } else {
                log.append("Export command by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = BrowseFileController.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}