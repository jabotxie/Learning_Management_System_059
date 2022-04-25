package client;

import util.Packet;
import util.Student;
import util.Teacher;
import util.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginUI implements ActionListener {
    Socket socket;
    ObjectInputStream is;
    ObjectOutputStream os;

    JFrame frame = new JFrame("Learning Management System");

    JButton loginButton = new JButton("Login");
    JButton createButton = new JButton("Create");
    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField usernameText = new JTextField(10);
    JTextField passwordText = new JTextField(10);


    public LoginUI(Socket socket, ObjectInputStream is, ObjectOutputStream os) {
        this.socket = socket;
        this.is = is;
        this.os = os;

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
        if (socket != null) {
            if (e.getSource() == loginButton) {
                String password = passwordText.getText();
                String username = usernameText.getText();
                System.out.println(username + "," + password);
                try {
                    os.writeObject(new Packet(Packet.LOGIN, new String[]{username, password}));
                    Packet packet = (Packet) is.readObject();
                    if (packet.isOperationSuccess()) {
                        frame.dispose();
                        JOptionPane.showMessageDialog(null, "You have successfully logged in.",
                                "Login Succeed", JOptionPane.INFORMATION_MESSAGE);
                        String userType = packet.getMsg()[0];
                        User currentUser = userType.equals("T") ? new Teacher(username) : new Student(username);
                        new CourseUI(socket, is, os, currentUser);
                    } else {
                        JOptionPane.showMessageDialog(null, "Username doesn't exist " +
                                "or the password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | ClassNotFoundException | ClassCastException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == createButton) {
                frame.dispose();
                new CreateUI(socket, is, os);
            }
        }
    }
}
