package client;

import util.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountLogin implements ActionListener {

    JFrame frame = new JFrame("Learning Management System");

    JButton loginButton = new JButton("Login");
    JButton createButton = new JButton("Create");
    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField usernameText = new JTextField(10);
    JTextField passwordText = new JTextField(10);


    public AccountLogin() {

        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        JPanel loginPanel = new JPanel();
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameText);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordText);
        loginPanel.add(loginButton);
        loginPanel.add(createButton);
        frame.add(loginPanel);

    }

    public AccountLogin(Point location) {

        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);

        frame.setSize(600, 400);
        frame.setLocation(location);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        JPanel loginPanel = new JPanel();
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameText);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordText);
        loginPanel.add(loginButton);
        loginPanel.add(createButton);
        frame.add(loginPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {

            String password = passwordText.getText();
            String username = usernameText.getText();
            if (username == null || password == null || username.equals("") || password.equals("")) {
                WindowGenerator.error(frame, "Please enter valid username and password");
                frame.dispose();
                new AccountCreateUI(frame.getLocation());
            } else {

                Packet request = new Packet(Packet.LOGIN, new String[]{username, password});

                Packet response = Client.getResponse(request);
                if (response.isOperationSuccess()) {
                    WindowGenerator.showMsg(frame, "You have successfully logged in!");
                    frame.dispose();
                    Client.username = username;
                    String userType = response.getMsg()[0];
                    if (userType.equals("T")) new TeacherCourseUI(frame.getLocation());
                    else new StudentCourseUI(frame.getLocation());

                } else {
                    WindowGenerator.error(frame, response.getMsg()[0]);
                    frame.dispose();
                    new AccountLogin(frame.getLocation());
                }
            }

        }
        if (e.getSource() == createButton) {
            frame.dispose();
            new AccountCreateUI(frame.getLocation());
        }

    }
}
