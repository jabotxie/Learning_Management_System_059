package client;

import util.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Project 5 -- Learning Management System
 * <p>
 *
 * A class that includes the GUI for the Login to account page. Here students and teachers will be able to enter their
 * existing username and password to access their course dashboard. They can also delete their account in this window.
 *
 * <p>Purdue University -- CS18000 -- Spring 2022</p>
 *
 * @author Jia Xie, Shreyash, Kundana, Garv
 * @version May 2nd, 2022
 */
public class AccountLogin implements ActionListener {

    JFrame frame = new JFrame("Learning Management System");

    JButton loginButton = new JButton("Login");
    JButton createButton = new JButton("Create");
    JButton deleteButton = new JButton("Delete Account");
    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField usernameText = new JTextField(10);
    JTextField passwordText = new JTextField(10);


    public AccountLogin() {

        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);

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
        loginPanel.add(deleteButton);
        frame.add(loginPanel);

    }

    public AccountLogin(Point location) {

        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);

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
        loginPanel.add(deleteButton);
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
                new AccountLogin(frame.getLocation());
            } else {

                Packet request = new Packet(Packet.LOGIN, new String[]{username, password});

                Packet response = Client.getResponse(request);
                if (response == null) {
                    frame.dispose();
                } else {
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

        }

        if (e.getSource() == createButton) {
            frame.dispose();
            new AccountCreateUI(frame.getLocation());
        }

        if (e.getSource() == deleteButton) {
            String password = passwordText.getText();
            String username = usernameText.getText();
            if (username == null || password == null || username.equals("") || password.equals("")) {
                WindowGenerator.error(frame, "Please enter valid username and password");
                frame.dispose();
                new AccountLogin(frame.getLocation());
            } else {
                if (WindowGenerator.confirm(frame, "Deleting the account will delete all the posts, replies, " +
                        "and votes under your account. Are you sure you want to delete this account?", "Warning!!!")) {
                    Packet request = new Packet(Packet.DELETE_ACCOUNT, new String[]{username, password});
                    Packet response = Client.getResponse(request);

                    if (response != null) {
                        if (response.isOperationSuccess()) {
                            WindowGenerator.showMsg(frame, "The account has been deleted. " +
                                    "You will be directed to the home page.");
                        } else {
                            WindowGenerator.showMsg(frame, response.getMsg()[0]);
                        }
                        new AccountLogin(frame.getLocation());
                    }
                    frame.dispose();
                }
            }
        }

    }
}
