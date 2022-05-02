package client;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * Project 5 -- Learning Management System
 * <p>
 *
 *A class that generates windows and is JPanel manager
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version May 2nd, 2022
 */
public class WindowGenerator {
    static void error(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static void showMsg(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Learning Management System",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static String requestClientInput(JFrame frame, String prompt) {
        String input = null;
        try {
            String selection;

            do {
                String[] options = {"Input through the window", "Choose a text file from computer"};
                selection = (String) JOptionPane.showInputDialog(frame, prompt, "Select input method",
                        JOptionPane.PLAIN_MESSAGE, null, options, null);
                if (selection == null) break;
                if (selection.equals("Choose a text file from computer")) {
                    StringBuilder sb;
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word", "doc", "docx"));
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    int option = fileChooser.showOpenDialog(frame);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try {
                            BufferedReader bf = new BufferedReader(new FileReader(file));
                            String line = bf.readLine();
                            sb = new StringBuilder();
                            while (line != null) {
                                sb.append(line);
                                line = bf.readLine();
                            }
                            bf.close();
                            frame.dispose();
                            input = sb.toString();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else selection = "Again";

                } else {
                    input = JOptionPane.showInputDialog(frame, prompt);
                    while (input != null && input.equals("")) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid string");
                        input = JOptionPane.showInputDialog(frame, prompt);
                    }
                    if (input == null) selection = "Again";
                }

            } while (selection.equals("Again"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    static String input(JFrame frame, String prompt) {
        String input;
        input = JOptionPane.showInputDialog(frame, prompt);
        while (input == null || input.equals("")) {
            if (input == null) break;
            JOptionPane.showMessageDialog(frame, "Please enter a valid input.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            input = JOptionPane.showInputDialog(frame, prompt);
        }
        return input;
    }

    static boolean confirm(JFrame frame, String warning, String title) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame,
                warning, title, JOptionPane.YES_NO_OPTION);
    }
}
