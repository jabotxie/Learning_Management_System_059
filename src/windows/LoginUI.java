package windows;

import data.AccountInfoNotMatchException;
import data.DataServer;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI implements ActionListener {

    JFrame frame = new JFrame("Learning Management System");

    JButton loginButton = new JButton("Login");
    JButton createButton = new JButton("Create");
    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField usernameText = new JTextField(10);
    JTextField passwordText = new JTextField(10);


    public LoginUI() {
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);


        JPanel loginPanel = new JPanel();
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameText);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordText);
        loginPanel.add(loginButton);
        loginPanel.add(createButton);
        frame.add(loginPanel);

//        frame.add(usernameLabel);
//        frame.add(usernameText);
//        frame.add(passwordLabel);
//        frame.add(passwordText);
//        frame.add(loginButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String password = passwordText.getText();
            String username = usernameText.getText();
            System.out.println(username + "," + password);
            try {
                DataServer.login(username, password);
                frame.dispose();
                CourseUI courseUI = new CourseUI();

            } catch (AccountInfoNotMatchException aE) {
                aE.printStackTrace();
            }
        }
        if (e.getSource() == createButton) {
            
        }
    }
}
