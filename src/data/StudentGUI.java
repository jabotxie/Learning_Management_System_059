package data;

import javax.swing.*;

public class StudentGUI {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "You have already voted!",
                "Already voted", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "You do not have permission to create a forum.",
                "No Permission Forum", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "You do not have permission to edit a forum.",
                "No Permission Forum", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "You do not have permission to delete a forum.",
                "No Permission Forum", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "You do not have permission to create a course.",
                "No Permission Forum", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "You do not have permission to edit a course.",
                "No Permission Forum", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "You do not have permission to delete a course.",
                "No Permission Forum", JOptionPane.ERROR_MESSAGE);

    }



}

