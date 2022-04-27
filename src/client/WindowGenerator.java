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
        return JOptionPane.showInputDialog(frame, prompt);
    }

    static boolean confirm(JFrame frame, String warning, String title) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame,
                warning, title, JOptionPane.YES_NO_OPTION);
    }
}
