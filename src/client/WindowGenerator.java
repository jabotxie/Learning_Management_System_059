package client;

import javax.swing.*;

public class WindowGenerator {
    static void error(String msg){
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static void showMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Learning Management System",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static String requestClientInput(String prompt) {
        return JOptionPane.showInputDialog(prompt);
    }
}
