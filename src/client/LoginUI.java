package client;

import util.Packet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {
            String password = passwordText.getText();
            String username = usernameText.getText();
            System.out.println(username + "," + password);

            Packet request = new Packet(Packet.LOGIN, new String[]{username, password});

            Packet response = Client.getResponse(request);
            if (response.isOperationSuccess()) {
                frame.dispose();
                JOptionPane.showMessageDialog(null, "You have successfully logged in.",
                        "Login Succeed", JOptionPane.INFORMATION_MESSAGE);
                Client.username = username;
                String userType = response.getMsg()[0];
                if (userType.equals("T")) new TeacherCourseUI();
                else new StudentCourseUI();

            } else {
                JOptionPane.showMessageDialog(null, "Username doesn't exist " +
                        "or the password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (e.getSource() == createButton) {
            frame.dispose();
            new CreateAccountUI();
        }

    }
}
