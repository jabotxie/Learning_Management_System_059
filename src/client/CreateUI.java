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
import java.util.Objects;

public class CreateUI implements ActionListener {

    Socket socket;
    ObjectInputStream is;
    ObjectOutputStream os;

    JFrame frame = new JFrame("Learning Management System");

    JButton backToLoginButton = new JButton("Back to login");
    JButton createButton = new JButton("Create");
    JLabel usernameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");
    JTextField usernameText = new JTextField(10);
    JTextField passwordText = new JTextField(10);
    JLabel userTypeLabel = new JLabel("User Type");
    JRadioButton teacherRB = new JRadioButton("Teacher");
    JRadioButton studentRB = new JRadioButton("Student");
    ButtonGroup group = new ButtonGroup();

    public CreateUI(Socket socket, ObjectInputStream is, ObjectOutputStream os) {
        this.socket = socket;
        this.is = is;
        this.os = os;

        teacherRB.setActionCommand("T");
        teacherRB.setSelected(true);
        studentRB.setActionCommand("S");
        group.add(teacherRB);
        group.add(studentRB);

        backToLoginButton.setFocusable(false);
        backToLoginButton.addActionListener(this);
        createButton.setFocusable(false);
        createButton.addActionListener(this);

        userTypeLabel.setBounds(0, 0, 200, 20);
        teacherRB.setBounds(0, 20, 78, 20);
        studentRB.setBounds(78, 20, 78, 20);
        usernameLabel.setBounds(0, 40, 70, 30);
        passwordLabel.setBounds(0, 65, 70, 30);
        usernameText.setBounds(70, 45, 100, 20);
        passwordText.setBounds(70, 70, 100, 20);
        createButton.setBounds(0, 100, 72, 20);
        backToLoginButton.setBounds(0, 125, 109, 20);


        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(115, 30, 200, 150);
//        infoPanel.setBackground(Color.BLUE);
        infoPanel.setLayout(null);
        infoPanel.add(userTypeLabel);
        infoPanel.add(teacherRB);
        infoPanel.add(studentRB);
        infoPanel.add(usernameLabel);
        infoPanel.add(usernameText);
        infoPanel.add(passwordLabel);
        infoPanel.add(passwordText);
        infoPanel.add(createButton);
        infoPanel.add(backToLoginButton);

        frame.setSize(400, 250);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.add(infoPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (socket != null) {
            if (e.getSource() == backToLoginButton) {
                frame.dispose();
                new LoginUI(socket, is, os);

            }
            if (e.getSource() == createButton) {
                String userType = group.getSelection().getActionCommand();
                String password = passwordText.getText();
                String username = usernameText.getText();

                try {
                    os.writeObject(new Packet(Packet.CREATE, new String[]{userType, username, password}));
                    Packet response = (Packet) is.readObject();

                    if (response.isOperationSuccess()) {
                        JOptionPane.showMessageDialog(null, "You have successfully created an account " +
                                "and logged in.", "Login Succeed", JOptionPane.INFORMATION_MESSAGE);
                        new CourseUI(socket,is, os,
                                userType.equals("T") ? new Teacher(username) : new Student(username));
                    } else {
                        JOptionPane.showMessageDialog(null, "The username has already been taken. " +
                                "Please enter another username", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | ClassNotFoundException | ClassCastException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
