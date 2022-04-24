package client;

import data.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseUI implements ActionListener {
    User currentUser;
    JFrame frame = new JFrame("Learning Management System");
    JButton editButton = new JButton("edit");
    JButton createButton = new JButton("create");
    JButton deleteButton = new JButton("delete");
    JButton logoutButton = new JButton("Log out");
    ArrayList<JButton> courseButtons;

    public CourseUI(User currentUser) {
        this.currentUser = currentUser;
        editButton.setFocusable(false);
        editButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(this);


        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.add(editButton);
        panel.add(createButton);
        panel.add(deleteButton);
        panel.add(logoutButton);
        frame.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                    "Do you want to sign out?", "Log Out Confirmation", JOptionPane.YES_NO_OPTION)) {
                frame.dispose();
                new LoginUI();
            }
        }
    }
}
