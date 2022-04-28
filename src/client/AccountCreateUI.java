package client;

import util.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountCreateUI implements ActionListener {


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

    public AccountCreateUI(Point point) {


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
        frame.setLocation(point);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(infoPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backToLoginButton) {
            frame.dispose();
            new AccountLogin();
        }
        if (e.getSource() == createButton) {
            String userType = group.getSelection().getActionCommand();
            String password = passwordText.getText();
            String username = usernameText.getText();

            Packet response = Client.getResponse(new Packet(Packet.CREATE, new String[]{userType, username, password}));

            if (response.isOperationSuccess()) {
                WindowGenerator.showMsg(frame, "You have successfully created an account and logged in!");
                frame.dispose();
                if (userType.equals("T")) new TeacherCourseUI(frame.getLocation());
                else new StudentCourseUI(frame.getLocation());
            } else {
                WindowGenerator.error(frame, "The username has been taken. Please change try again");
                frame.dispose();
                new AccountCreateUI(frame.getLocation());
            }
        }
    }
}
