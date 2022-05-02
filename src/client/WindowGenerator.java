package client;

import javax.swing.*;

public class WindowGenerator {
    static void error(JFrame frame, String msg){
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static void showMsg(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Learning Management System",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static String requestClientInput(JFrame frame, String prompt) {
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
